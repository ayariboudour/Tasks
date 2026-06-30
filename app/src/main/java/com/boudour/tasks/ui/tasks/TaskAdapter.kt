package com.boudour.tasks.ui.tasks

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.boudour.tasks.data.Task
import com.boudour.tasks.databinding.ItemTaskBinding

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
        this.tasks = tasks.sortedBy {
            it.isCompleted
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.apply {
                checkBox.isChecked = task.isCompleted
                toggleStar.isChecked = task.isStarted

                if (task.isCompleted) {
                    textViewTitle.paintFlags =
                        textViewTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    textViewDetails.paintFlags =
                        textViewDetails.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    textViewTitle.paintFlags = 0
                    textViewDetails.paintFlags = 0
                }
                textViewTitle.text = task.title
                if (task.description.isNullOrEmpty()) {
                    textViewDetails.visibility = View.GONE
                } else {
                    textViewDetails.text = task.description
                    textViewDetails.visibility = View.VISIBLE
                }
                checkBox.setOnClickListener {
                    val updateTask = task.copy(isCompleted = checkBox.isChecked)
                    listener.onTaskUpdated(updateTask)
                }
                toggleStar.setOnClickListener {
                    val updateTask = task.copy(isStarted = toggleStar.isChecked)
                    listener.onTaskUpdated(updateTask)
                }
            }
        }
    }

    interface TaskUpdatedListener {
        fun onTaskUpdated(task: Task)
    }

}