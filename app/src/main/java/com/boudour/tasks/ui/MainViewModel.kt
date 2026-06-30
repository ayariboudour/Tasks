package com.boudour.tasks.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boudour.tasks.GetItDoneApplication
import com.boudour.tasks.data.Task
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val taskDao = GetItDoneApplication.taskDao

    fun createTask(title: String, description: String) {
        val task = Task(
            title = title,
            description = description
        )
        viewModelScope.launch {
            taskDao.createTask(task)
        }
    }
}