package com.boudour.tasks.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.boudour.tasks.data.GetItDoneDatabase
import com.boudour.tasks.data.Task
import com.boudour.tasks.data.TaskDao
import com.boudour.tasks.databinding.FragmentTasksBinding
import kotlin.concurrent.thread

class TasksFragment : Fragment(), TaskAdapter.TaskUpdatedListener {
    private lateinit var binding: FragmentTasksBinding
    private val taskDeo: TaskDao by lazy{
        GetItDoneDatabase.getDataBase(requireContext()).getTaskDao()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchAllTasks()
    }

    fun fetchAllTasks() {
        thread {
            val tasks = taskDeo.getAllTasks()
            requireActivity().runOnUiThread {
                binding.recyclerView.adapter = TaskAdapter(tasks = tasks, listener = this)
            }
        }
    }

    override fun onTaskUpdated(task: Task) {
        thread {
            taskDeo.updateTask(task)
            fetchAllTasks()
        }
    }
}