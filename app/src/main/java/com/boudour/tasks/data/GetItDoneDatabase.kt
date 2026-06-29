package com.boudour.tasks.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1)
abstract class GetItDoneDatabase : RoomDatabase() {
    abstract fun getTaskDao(): TaskDao

    companion object {
        @Volatile //main memory visibility of changes to the instance variable across threads
        private var DATABASE_INSTANCE: GetItDoneDatabase? = null
        fun getDataBase(context: Context): GetItDoneDatabase {

            return DATABASE_INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    GetItDoneDatabase::class.java,
                    "get-it-done-database"
                ).build()
                DATABASE_INSTANCE = instance
                instance
            }
        }
    }
}