package com.example.myfitnessapp.models.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import com.example.myfitnessapp.models.database.daos.MuscleDao
import com.example.myfitnessapp.models.database.daos.SessionDAO
import com.example.myfitnessapp.models.database.daos.UserDao
import com.example.myfitnessapp.models.entities.Muscle
import com.example.myfitnessapp.models.entities.Session
import com.example.myfitnessapp.models.entities.User

@Database(entities = [Muscle::class, User::class, Session::class], version = 4, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getMuscleDao(): MuscleDao
    abstract fun getUserDao(): UserDao
    abstract fun getSessionDao(): SessionDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}