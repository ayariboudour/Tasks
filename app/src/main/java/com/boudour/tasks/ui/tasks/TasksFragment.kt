package com.boudour.tasks.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.boudour.tasks.data.GetItDoneDatabase
import com.boudour.tasks.data.Task
import com.boudour.tasks.data.TaskDao
import com.boudour.tasks.databinding.FragmentTasksBinding
import kotlin.concurrent.thread

class TasksFragment : Fragment(), TaskAdapter.TaskItemClickListener {
    private lateinit var binding: FragmentTasksBinding
    private val viewModel: TasksViewModel by viewModels()
    private val adapter: TaskAdapter = TaskAdapter(this)

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
        binding.recyclerView.adapter = adapter
        fetchAllTasks()
    }

    fun fetchAllTasks() {
        viewModel.fetchTasks { tasks ->
            requireActivity().runOnUiThread {
                adapter.setTasks(tasks)
            }
        }
    }

    override fun onTaskUpdated(task: Task) {
        viewModel.updateTask(task)
    }

    override fun onTaskDeleted(task: Task) {
        viewModel.deleteTask(task)
    }
}