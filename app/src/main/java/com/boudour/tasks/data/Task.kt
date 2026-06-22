package com.boudour.tasks.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @ColumnInfo(name = "task_id") @PrimaryKey(autoGenerate = true) val taskId: Int = 1,
    val title: String,
    val description: String? = null,
    @ColumnInfo(name = "is_started")val isStarted: Boolean = false
)