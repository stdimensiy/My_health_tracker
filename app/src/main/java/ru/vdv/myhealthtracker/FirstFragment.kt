package ru.vdv.myhealthtracker

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import ru.vdv.myhealthtracker.databinding.FragmentFirstBinding
import ru.vdv.myhealthtracker.ui.main.MainAdapter
import ru.vdv.myhealthtracker.ui.main.MainViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    private lateinit var adapter: MainAdapter
    private lateinit var viewModel: MainViewModel

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapter = MainAdapter()
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
                    //Toast.makeText(requireContext(), "Пользователь ввел: " + userInputTitle.getText() + " И контент : " + userInputContent.getText(), Toast.LENGTH_SHORT).show();
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
}