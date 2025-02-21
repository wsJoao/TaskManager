package com.jppin.taskmanager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jppin.taskmanager.data.entities.Task
import com.jppin.taskmanager.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val taskRepository: TaskRepository) : ViewModel()  {

    val tasks: LiveData<List<Task>> = taskRepository.readAllData

    private val _isEditMode = MutableLiveData(false)
    val isEditMode: LiveData<Boolean> get() = _isEditMode

    fun addTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.addTask(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.deleteTask(task)
        }
    }

    fun setEditMode(enabled: Boolean) {
        _isEditMode.value = enabled
    }

    fun updateTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.updateTask(task)
        }
    }
}
