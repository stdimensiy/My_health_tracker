package ru.vdv.myhealthtracker

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import ru.vdv.myhealthtracker.databinding.FragmentFirstBinding
import ru.vdv.myhealthtracker.domain.Record
import ru.vdv.myhealthtracker.domain.Separator
import ru.vdv.myhealthtracker.ui.common.ILongClicked
import ru.vdv.myhealthtracker.ui.main.MainAdapter
import ru.vdv.myhealthtracker.ui.main.MainViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    private lateinit var adapter: MainAdapter
    private lateinit var viewModel: MainViewModel
    private var contextMenuItemPosition: Int = 0

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapter = MainAdapter()
        adapter.itemLongClicked = object : ILongClicked {
            override fun onItemLongClicked(view: View, position: Int, itemObject: Any) {
                if (itemObject is Record) {
                    Log.d(
                        "Моя проверка",
                        "Я чувствую как выполняется длительное нажатие, теперь фрагмент знает, что пользователь сделал это ..."
                    )
                    val pop = PopupMenu(view.context, view)
                    pop.inflate(R.menu.record_context_menu)
                    pop.setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.action_delete_record -> {
                                Log.d("Моя проверка", "Клиент выбрал удаление ...")
                                viewModel.deleteRecord(itemObject)
                            }
                        }
                        true
                    }
                    pop.show()
                } else if (itemObject is Separator) {
                    // реализация метода удаления группы элементов (т.е. за день или в будущем за период)
                    // необходимо выполнить здесь
                }
            }
        }
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mineList = binding.rvMain
        mineList.adapter = adapter
        mineList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewModel.fetchCurrentList()
        viewModel.listForMain.observe(viewLifecycleOwner) {
            Log.d(
                "Моя проверка",
                "Я чувствую как изменение течения силы, надобы обновить данные..."
            )
            adapter.items = it
            adapter.notifyDataSetChanged()
        }

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            showDialogInputTitleNewNote()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showDialogInputTitleNewNote() {
        val view: View = layoutInflater.inflate(R.layout.input_new_record_dialog, null)
        val systolicPressure = view.findViewById<View>(R.id.et_systolic_pressure) as EditText
        val diastolicPressure = view.findViewById<View>(R.id.et_diastolic_pressure) as EditText
        val heartRate = view.findViewById<View>(R.id.et_heartRate) as EditText
        val inputTitle: AlertDialog = AlertDialog.Builder(requireContext())
            .setTitle(R.string.dialog_input_title)
            .setView(view)
            .setIcon(R.drawable.ic_baseline_input_24)
            .setPositiveButton(R.string.btn_ok_text,
                DialogInterface.OnClickListener { dialog, which -> //notesViewModel.addNewNote(requireContext());
                    //ViewModel.addNewTask(requireContext(), newTitleTask.getText().toString());
                    //Toast.makeText(requireContext(), "Пользователь ввел: " + systolicPressure.text + " И контент : " + diastolicPressure.text + " пульс" + heartRate.text, Toast.LENGTH_SHORT).show();
                    viewModel.addNewRecord(
                        systolicPressure.text.toString(),
                        diastolicPressure.text.toString(),
                        heartRate.text.toString()
                    )
                })
            .setNeutralButton(R.string.btn_cancel_text,
                DialogInterface.OnClickListener { dialog, which ->
                    //Toast.makeText(requireContext(), "Пользователь нажал отмену", Toast.LENGTH_SHORT).show();
                })
            .setCancelable(false)
            .create()
        inputTitle.show()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        Log.d("Моя проверка", "onCreateContextMenu стаботал")
        super.onCreateContextMenu(menu, v, menuInfo)
        val menuInflater = requireActivity().menuInflater
        menuInflater.inflate(R.menu.record_context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_delete_record) {
            Log.d("Моя проверка", "Пользователь активировал удаление элемента...")
        }
        return super.onContextItemSelected(item)
    }
}