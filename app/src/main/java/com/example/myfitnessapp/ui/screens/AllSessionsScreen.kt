package com.example.myfitnessapp.ui.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myfitnessapp.models.entities.Session
import com.example.myfitnessapp.models.interfaces.SessionRepositoryInterface
import com.example.myfitnessapp.ui.theme.Modifiers
import com.example.myfitnessapp.ui.views.SessionView
import com.example.myfitnessapp.viewmodels.repositories.tests.TestSessionRepository
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AllSessionsScreen(modifiers: Modifiers, sessionRepository: SessionRepositoryInterface, navController: NavController){
    var allSessions by remember { mutableStateOf(emptyList<List<Session>>()) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(sessionRepository){
        Log.d("SessionS", "LaunchedEffect called")
        allSessions = sessionRepository.getAllSavedSessions().toList()
        Log.d("SessionS", "All sessions: ${allSessions.size}")
    }

    Column(modifier = modifiers.bigPaddingModifier(true)) {
        Text("Sessions personnalisées", style = MaterialTheme.typography.titleLarge)
        LazyColumn(modifiers.containerModifier){
            items(allSessions){session ->
                var isSelected by remember { mutableStateOf(false) }
                SessionView(modifiers
                    .containerModifier
                    .combinedClickable(
                        onClick = {
                            navController.navigate("session_detail_screen/${session.first().id}")},
                        onLongClick = {
                            isSelected = !isSelected
                        }
                    )
                    ,session, isSelected, onDelete = {
                        scope.launch {
                            isSelected = false
                            sessionRepository.deleteSessions(it)
                            Log.d("SessionS", "Deleting sessions with id = ${it}")
                            allSessions = sessionRepository.getAllSavedSessions().toList()
                        }
                        //navController.navigate("all_session_screen")
                    }
                )
            }
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



