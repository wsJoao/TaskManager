package com.jppin.taskmanager.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jppin.taskmanager.data.entities.Task

@Dao
interface TaskDao {
    @Query("SELECT * FROM task ORDER BY priority DESC , title ASC")
    fun getAll(): LiveData<List<Task>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: Task)

    @Delete
    fun delete(task: Task)

    @Update
    fun update(task: Task)
}