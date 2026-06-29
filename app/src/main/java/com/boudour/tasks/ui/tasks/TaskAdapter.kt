package com.boudour.tasks.ui.tasks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.boudour.tasks.data.Task
import com.boudour.tasks.databinding.ItemTaskBinding

class TaskAdapter(val tasks: List<Task>) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

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


    inner class ViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.textViewTitle.text = task.title
            binding.textViewDetails.text = task.description
        }
    }

}