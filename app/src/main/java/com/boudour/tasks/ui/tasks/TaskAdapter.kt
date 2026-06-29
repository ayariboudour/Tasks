package com.boudour.tasks.ui.tasks

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.boudour.tasks.data.Task
import com.boudour.tasks.databinding.ItemTaskBinding
import com.google.android.material.checkbox.MaterialCheckBox

class TaskAdapter(private val listener: TaskAdapter.TaskUpdatedListener) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    private var tasks: List<Task> = listOf()
    override fun getItemCount() = tasks.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(tasks[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setTasks(tasks: List<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.checkBox.isChecked = task.isCompleted
            binding.toggleStar.isChecked = task.isStarted

            if (task.isCompleted) {
                binding.textViewTitle.paintFlags =
                    binding.textViewTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                binding.textViewDetails.paintFlags =
                    binding.textViewDetails.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                binding.textViewTitle.paintFlags = 0
                binding.textViewDetails.paintFlags = 0
            }

            binding.textViewTitle.text = task.title
            binding.textViewDetails.text = task.description
            binding.checkBox.setOnClickListener {
                val updateTask = task.copy(isCompleted = binding.checkBox.isChecked)
                listener.onTaskUpdated(updateTask)
            }
            binding.toggleStar.setOnClickListener {
                val updateTask = task.copy(isStarted = binding.toggleStar.isChecked)
                listener.onTaskUpdated(updateTask)
            }
        }
    }

    interface TaskUpdatedListener {
        fun onTaskUpdated(task: Task)
    }

}