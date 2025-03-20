package com.example.myfitnessapp.viewmodels.repositories

import android.util.Log
import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.myfitnessapp.models.database.daos.UserDao
import com.example.myfitnessapp.models.entities.User

class AuthRepository(private val userDao: UserDao) {

    suspend fun registerUser(email: String, password: String, pseudonym: String, firstName: String, lastName: String): Boolean {
        if (userDao.isEmailTaken(email)) return false

        val hashedPassword = hashPassword(password)
        val user = User(
            id = 1,
            email = email,
            pseudonym = pseudonym,
            firstName = firstName,
            lastName = lastName,
            weight = 0.0,
            height = 0,
            birthdate = "",
            gender = "",
            level = "Débutant",
            passwordHash = hashedPassword
        )
        userDao.insertUser(user)
        return true
    }

    suspend fun loginUser(email: String, password: String): User? {
        val user = userDao.getUserByEmail(email) ?: return null
        return if (verifyPassword(password, user.passwordHash)) user else null
    }

    private fun hashPassword(password: String): String {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray())
    }

    private fun verifyPassword(password: String, hash: String): Boolean {
        return BCrypt.verifyer().verify(password.toCharArray(), hash).verified
    }

    suspend fun getCurrentUser(): User? {
        return try {
            val users = userDao.getAllUsers()
            users.firstOrNull()
        } catch (e: Exception) {
            Log.e("AuthRepository", "Erreur lors de la récupération de l'utilisateur", e)
            null
        }
    }

    suspend fun registerUser(email: String, password: String): Boolean {
        if (userDao.isEmailTaken(email)) return false

        val hashedPassword = hashPassword(password)
        val newUser = User(
            id = 1,
            email = email,
            pseudonym = "",
            firstName = "",
            lastName = "",
            weight = 0.0,
            height = 0,
            birthdate = "",
            gender = "",
            level = "Débutant",
            passwordHash = hashedPassword
        )
        userDao.insertUser(newUser)
        return true
    }
}