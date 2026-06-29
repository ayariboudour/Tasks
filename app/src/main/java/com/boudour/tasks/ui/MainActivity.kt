package com.boudour.tasks.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.boudour.tasks.data.GetItDoneDatabase
import com.boudour.tasks.data.Task
import com.boudour.tasks.data.TaskDao
import com.boudour.tasks.databinding.ActivityMainBinding
import com.boudour.tasks.databinding.AddDialogTaskBinding
import com.boudour.tasks.ui.tasks.TasksFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: GetItDoneDatabase
    private val taskDao: TaskDao by lazy {
        database.getTaskDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager.adapter = PagerAdapter(this)
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = "Tasks"
        }.attach()
        binding.fab.setOnClickListener {
            showAddTaskDialog()
        }
        database = GetItDoneDatabase.createDatabase(this)
    }

    private fun showAddTaskDialog() {
        val dialogBinding = AddDialogTaskBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(dialogBinding.root)
        dialogBinding.buttonShowDetails.setOnClickListener {
            dialogBinding.editTextTaskDetails.visibility =
                if (dialogBinding.editTextTaskDetails.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }

        dialogBinding.buttonSave.setOnClickListener {
            val task = Task(
                title = dialogBinding.editTextTaskTitle.text.toString(),
                description = dialogBinding.editTextTaskDetails.text.toString()
            )
            thread {
                taskDao.createTask(task)
            }
            dialog.dismiss()
        }
        dialog.show()
    }

    inner class PagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
        override fun createFragment(position: Int): Fragment {
            return TasksFragment()
        }

        override fun getItemCount(): Int = 1

    }
}