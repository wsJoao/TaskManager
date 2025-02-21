package com.jppin.taskmanager.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jppin.taskmanager.R
import com.jppin.taskmanager.data.entities.Task
import com.jppin.taskmanager.databinding.ActivityMainBinding
import com.jppin.taskmanager.databinding.CustomDeleteAlertDialogBinding
import com.jppin.taskmanager.databinding.CustomDialogFragmentBinding
import com.jppin.taskmanager.ui.adapter.TaskAdapter
import com.jppin.taskmanager.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: TaskAdapter
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        adapter = TaskAdapter(emptyList(), false,
            { task -> showDeleteTaskDialog(task) },
            { task -> showEditTaskDialog(task) })

        binding.rvTaskList.apply{
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
        
        setEditMode()
        setObservers()
        addTask()
    }

    private fun setObservers() {
        viewModel.tasks.observe(this) { tasks ->
            adapter.submitList(tasks)
        }

        viewModel.isEditMode.observe(this) { isEditMode ->
            adapter.updateEditMode(isEditMode)
        }


    }

    private fun addTask() {
        binding.fabAdd.setOnClickListener {
            val dialog = DialogFragmentAdd()
            dialog.show(supportFragmentManager, "DialogFragmentAdd")
        }
    }

    private fun setEditMode() {
        binding.switchEdit.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setEditMode(isChecked)
        }
    }

    private fun showEditTaskDialog(task: Task) {
        val binding = CustomDialogFragmentBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this)
            .setView(binding.root)
            .create()

        binding.apply {
            btnAdd.text = getString(R.string.update)
            etTaskName.setText(task.title)
            etTaskDescription.setText(task.description)

            val priorityArray = resources.getStringArray(R.array.spinner_items)
            val adapter = ArrayAdapter( this@MainActivity,R.layout.spinner_item, priorityArray)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerPriority.adapter = adapter
            spinnerPriority.setSelection(task.priority - 1)

            btnAdd.setOnClickListener {
                val updatedTask = task.copy(
                    title = etTaskName.text.toString(),
                    description = etTaskDescription.text.toString(),
                    priority = spinnerPriority.selectedItemPosition + 1
                )
                viewModel.updateTask(updatedTask)
                dialog.dismiss()
            }

            btnCancel.setOnClickListener { dialog.dismiss() }
        }

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

    }

    private fun showDeleteTaskDialog(task: Task) {
        val binding = CustomDeleteAlertDialogBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this)
            .setView(binding.root)
            .create()
        binding.apply {
            txtTaskName.text = task.title
            btnYes.setOnClickListener {
                viewModel.deleteTask(task)
                dialog.dismiss()
            }
            btnNo.setOnClickListener { dialog.dismiss() }
        }

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }
}