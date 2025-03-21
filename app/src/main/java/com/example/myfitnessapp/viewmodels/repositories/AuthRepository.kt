package com.example.myfitnessapp.viewmodels.repositories

import android.content.Context
import android.util.Base64
import com.example.myfitnessapp.models.database.AppDatabase
import com.example.myfitnessapp.models.entities.User
import org.mindrot.jbcrypt.BCrypt
import java.security.MessageDigest
import java.security.SecureRandom

class AuthRepository(context: Context) {
    private val userDao = AppDatabase.getDatabase(context).getUserDao()

    suspend fun registerUser(email: String, password: String): Boolean {
        if (userDao.getUserByEmail(email) != null) {
            return false
        }

        val hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt())

        val user = User(
            id = 0,
            email = email,
            passwordHash = hashedPassword,
            pseudonym = "",
            firstName = "",
            lastName = "",
            weight = 0.0,
            height = 0,
            birthdate = "",
            gender = "",
            level = "Débutant"
        )
        userDao.insertUser(user)
        return true
    }

    suspend fun loginUser(email: String, password: String): User? {
        val user = userDao.getUserByEmail(email) ?: return null
        return if (BCrypt.checkpw(password, user.passwordHash)) user else null
    }
}