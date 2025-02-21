package com.jppin.taskmanager.ui
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.jppin.taskmanager.R
import com.jppin.taskmanager.data.entities.Task
import com.jppin.taskmanager.databinding.CustomDialogFragmentBinding
import com.jppin.taskmanager.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DialogFragmentAdd: DialogFragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding : CustomDialogFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        isCancelable = false

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding = CustomDialogFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        spinnerExibition()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.btnAdd.setOnClickListener {
            val taskName = binding.etTaskName.text.toString()
            val taskDescription = binding.etTaskDescription.text.toString()
            val taskPriority = when (binding.spinnerPriority.selectedItemPosition) {
                0 -> 1
                1 -> 2
                else -> 3
            }
            val task = Task(null,taskName, taskDescription, taskPriority)
            viewModel.addTask(task)
            dismiss()
        }
    }

    private fun spinnerExibition() {
        val items = resources.getStringArray(R.array.spinner_items)
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item ,items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerPriority.adapter = adapter
    }
}