package com.example.myfitnessapp.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.myfitnessapp.models.entities.Session
import com.example.myfitnessapp.models.interfaces.SessionRepositoryInterface
import com.example.myfitnessapp.ui.theme.Modifiers

@Composable
fun SessionDetailScreen(modifier: Modifier = Modifiers().bigPaddingModifier(true),
                        navController: NavController,
                        sessionRepository: SessionRepositoryInterface){

    var session: List<Session>

    Text("Session Detail Screen")

}