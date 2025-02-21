package com.jppin.taskmanager.data.repository

import androidx.lifecycle.LiveData
import com.jppin.taskmanager.data.entities.Task
import com.jppin.taskmanager.data.local.TaskDao
import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskDao: TaskDao) {

    val readAllData: LiveData<List<Task>> = taskDao.getAll()

    suspend fun addTask(task: Task) {
        taskDao.insert(task)
    }

    suspend fun deleteTask(task: Task) {
        taskDao.delete(task)
    }

    suspend fun updateTask(task: Task) {
        taskDao.update(task)
    }
}