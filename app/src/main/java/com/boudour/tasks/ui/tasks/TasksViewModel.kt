package com.boudour.tasks.ui.tasks

import androidx.lifecycle.ViewModel
import com.boudour.tasks.GetItDoneApplication
import com.boudour.tasks.data.GetItDoneDatabase
import com.boudour.tasks.data.Task
import com.boudour.tasks.data.TaskDao
import kotlin.concurrent.thread

class TasksViewModel : ViewModel() {

    private val taskDao = GetItDoneApplication.taskDao

    fun fetchTasks(callback: (List<Task>) -> Unit) {
       thread {
           val tasks = taskDao.getAllTasks()
           callback(tasks)
       }
    }

    fun updateTask(task: Task) {
        thread {
            taskDao.updateTask(task)

        }
    }

    fun deleteTask(task: Task) {
        thread {
            taskDao.deleteTask(task)
        }

    }
}