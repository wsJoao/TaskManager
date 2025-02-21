package com.jppin.taskmanager.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jppin.taskmanager.data.entities.Task
import javax.inject.Singleton


@Database(entities = [Task::class], version = 1)
@Singleton
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

}