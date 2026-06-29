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
    private val database: GetItDoneDatabase by lazy { GetItDoneDatabase.getDataBase(this) }
    private val tasksFragment: TasksFragment= TasksFragment()
    private val taskDao: TaskDao by lazy {
        database.getTaskDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            viewPager.adapter = PagerAdapter(this@MainActivity)
            TabLayoutMediator(tabs, viewPager) { tab, _ ->
                tab.text = "Tasks"
            }.attach()
            fab.setOnClickListener {
                showAddTaskDialog()
            }
            setContentView(root)
        }

    }

    private fun showAddTaskDialog() {
        AddDialogTaskBinding.inflate(layoutInflater).apply {
            val dialog = BottomSheetDialog(this@MainActivity)
            dialog.setContentView(root)
            buttonShowDetails.setOnClickListener {
                editTextTaskDetails.visibility =
                    if (editTextTaskDetails.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            }

            buttonSave.setOnClickListener {
                val task = Task(
                    title = editTextTaskTitle.text.toString(),
                    description = editTextTaskDetails.text.toString()
                )
                thread {
                    taskDao.createTask(task)
                }
                dialog.dismiss()
                tasksFragment.fetchAllTasks()
            }
            dialog.show()
        }

    }

    inner class PagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
        override fun createFragment(position: Int): Fragment {
            return tasksFragment
        }

        override fun getItemCount(): Int = 1

    }
}