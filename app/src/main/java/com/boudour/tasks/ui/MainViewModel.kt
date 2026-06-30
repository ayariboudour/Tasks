package com.boudour.tasks.ui

import androidx.lifecycle.ViewModel
import com.boudour.tasks.GetItDoneApplication
import com.boudour.tasks.data.Task
import kotlin.concurrent.thread

class MainViewModel : ViewModel() {

    private val taskDao = GetItDoneApplication.taskDao

    fun createTask(title: String, description: String) {
        val task = Task(
            title = title,
            description = description
        )
        thread {
            taskDao.createTask(task)
        }
    }
}