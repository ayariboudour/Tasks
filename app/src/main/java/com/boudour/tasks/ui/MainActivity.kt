package com.boudour.tasks.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.boudour.tasks.databinding.ActivityMainBinding
import com.boudour.tasks.databinding.AddDialogTaskBinding
import com.boudour.tasks.ui.tasks.TasksFragment
import com.boudour.tasks.util.InputValidator
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()
    private val tasksFragment: TasksFragment = TasksFragment()

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

            buttonSave.isEnabled = false

            editTextTaskTitle.addTextChangedListener { input ->
                buttonSave.isEnabled = InputValidator.isInputValid(input?.toString())
            }

            buttonShowDetails.setOnClickListener {
                editTextTaskDetails.visibility =
                    if (editTextTaskDetails.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            }

            buttonSave.setOnClickListener {
                viewModel.createTask(
                    editTextTaskTitle.text.toString(),
                    editTextTaskDetails.text.toString()
                )
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