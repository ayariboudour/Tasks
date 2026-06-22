package com.boudour.tasks.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.boudour.tasks.data.Task

@Database(entities = [Task::class], version = 1)
abstract class GetItDoneDatabase : RoomDatabase() {
    abstract fun getTaskDao(): TaskDao
}