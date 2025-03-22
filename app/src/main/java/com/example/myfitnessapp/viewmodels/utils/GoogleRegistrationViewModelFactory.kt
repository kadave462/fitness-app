package com.example.myfitnessapp.viewmodels.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myfitnessapp.ui.views.getGoogleUserInfo

class GoogleRegistrationViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegistrationViewModel::class.java)) {
            // Create a new ViewModel
            val viewModel = RegistrationViewModel()

            // Try to get Google user info
            val googleUserInfo = getGoogleUserInfo(context)

            // Pre-fill the ViewModel if Google data exists
            if (googleUserInfo != null) {
                // Pre-fill fields with Google data
                viewModel.pseudonym = googleUserInfo.displayName.takeIf { it.isNotEmpty() } ?: ""
                viewModel.firstName = googleUserInfo.givenName.takeIf { it.isNotEmpty() } ?: ""
                viewModel.lastName = googleUserInfo.familyName.takeIf { it.isNotEmpty() } ?: ""

                // Clear the stored data after using it
                // clearGoogleUserInfo(context)  // Uncomment if you want to clear after use
            }

            return viewModel as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
