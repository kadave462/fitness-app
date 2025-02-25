package com.example.myfitnessapp.models.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myfitnessapp.models.datas.Muscle
import com.example.myfitnessapp.models.datas.Session
import com.example.myfitnessapp.models.datas.User
import com.example.myfitnessapp.models.datas.UserSession

@Database(entities = [Muscle::class, User::class, Session::class, UserSession::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getMuscleDao(): MuscleDao
    abstract fun getUserDao(): UserDao
    abstract fun getSessionDao(): SessionDAO
    abstract fun getUserSessionDao(): UserSessionDAO

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