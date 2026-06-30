package com.boudour.tasks

import android.app.Application
import com.boudour.tasks.data.GetItDoneDatabase
import com.boudour.tasks.data.TaskDao

class GetItDoneApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        database = GetItDoneDatabase.getDataBase(this)
        taskDao = database.getTaskDao()
    }

    companion object {
        lateinit var database: GetItDoneDatabase
        lateinit var taskDao: TaskDao
    }
}