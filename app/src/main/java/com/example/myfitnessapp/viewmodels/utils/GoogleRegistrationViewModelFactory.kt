package com.example.myfitnessapp.viewmodels.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GoogleRegistrationViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegistrationViewModel::class.java)) {
            val viewModel = RegistrationViewModel()

            val googleUserInfo = getGoogleUserInfo(context)

            if (googleUserInfo != null) {
                viewModel.pseudonym = googleUserInfo.displayName.takeIf { it.isNotEmpty() } ?: ""
                viewModel.firstName = googleUserInfo.givenName.takeIf { it.isNotEmpty() } ?: ""
                viewModel.lastName = googleUserInfo.familyName.takeIf { it.isNotEmpty() } ?: ""
            }

            return viewModel as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
