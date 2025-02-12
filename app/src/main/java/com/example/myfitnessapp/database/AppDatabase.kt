package com.example.myfitnessapp.database // Make sure this is your correct package name

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Muscle::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() { // 2. abstract class AppDatabase

    abstract fun muscleDao(): MuscleDao // 3. abstract fun muscleDao()

    companion object { // 4. companion object
        @Volatile // 5. @Volatile
        private var INSTANCE: AppDatabase? = null // 6. INSTANCE variable

        fun getDatabase(context: Context): AppDatabase { // 7. getDatabase function
            return INSTANCE ?: synchronized(this) { // 8. Singleton pattern
                val instance = Room.databaseBuilder( // 9. Room.databaseBuilder
                    context.applicationContext, // 10. context.applicationContext
                    AppDatabase::class.java, // 11. AppDatabase::class.java
                    "app_database" // 12. Database name "app_database"
                )
                    .fallbackToDestructiveMigration() // 13. fallbackToDestructiveMigration (for development)
                    .build() // 14. .build()
                INSTANCE = instance // 15. Assign instance to INSTANCE
                instance // 16. Return instance
            }
        }
    }
}