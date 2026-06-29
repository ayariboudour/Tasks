package com.boudour.tasks.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.boudour.tasks.data.Task
import com.boudour.tasks.databinding.FragmentTasksBinding

class TasksFragment : Fragment() {
    private lateinit var binding: FragmentTasksBinding

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
        binding.recyclerView.adapter = TaskAdapter(tasks = listOf(
            Task(title = "Task 1", description = "Description 1"), Task(title = "Task 2", description = "Description 2"),
            Task(title = "Task 3", description = "Description 3")))
    }
}