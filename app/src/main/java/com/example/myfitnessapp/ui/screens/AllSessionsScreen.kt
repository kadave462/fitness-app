package com.example.myfitnessapp.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myfitnessapp.models.entities.Session
import com.example.myfitnessapp.models.interfaces.SessionRepositoryInterface
import com.example.myfitnessapp.ui.theme.Modifiers
import com.example.myfitnessapp.ui.views.SessionView
import com.example.myfitnessapp.viewmodels.repositories.SessionRepository
import com.example.myfitnessapp.viewmodels.repositories.tests.TestSessionRepository

@Composable
fun AllSessionsScreen(modifiers: Modifiers, sessionRepository: SessionRepositoryInterface, navController: NavController){
    var allSessions by remember { mutableStateOf(emptyList<List<Session>>()) }

    LaunchedEffect(sessionRepository){
        Log.d("SessionS", "LaunchedEffect called")
        allSessions = sessionRepository.getAllSavedSessions().toList()
        Log.d("SessionS", "All sessions: $allSessions")
    }


    LazyColumn(modifiers.containerModifier){
        items(allSessions){session ->
            SessionView(modifiers
                .containerModifier
                .clickable {
                navController.navigate("session_detail_screen")
            }, session)
        }
    }
}

@Preview
@Composable
fun SessionsScreenPreview(){
    val repository = TestSessionRepository()
    val modifiers = Modifiers()
    val navController = rememberNavController()

    AllSessionsScreen(modifiers, repository, navController)

}



