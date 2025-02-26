package com.example.myfitnessapp.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myfitnessapp.models.entities.Session
import com.example.myfitnessapp.ui.theme.Modifiers
import com.example.myfitnessapp.ui.views.CategoryView
import com.example.myfitnessapp.ui.views.SessionView
import com.example.myfitnessapp.viewmodels.repositories.SessionRepository

@Composable
fun SessionsScreen(modifiers: Modifiers, sessionRepository: SessionRepository, navController: NavController){
    var allSessions: List<List<Session>> = emptyList()

    LaunchedEffect(sessionRepository){
        allSessions = sessionRepository.getAllSavedSessions().toMutableList()
    }

    LazyColumn(modifiers.containerModifier){
        items(allSessions){session ->
            SessionView(modifiers, session)
        }
    }
}

@Preview
@Composable
fun SessionsScreenPreview(){

    val session: List<List<Session>> = listOf(
        listOf(
            Session(
                id = 1,
                exerciseId = "ex1",
                name = "Jambes",
                totalSets = 4
            ),
            Session(
                id = 2,
                exerciseId = "ex2",
                name = "Deadlift",
                totalSets = 3
            ),
            Session(
                id = 3,
                exerciseId = "ex3",
                name = "Bench Press",
                totalSets = 4
            ),
            Session(
                id = 4,
                exerciseId = "ex4",
                name = "Pull-up",
                totalSets = 3
            )
        ),
        listOf(
            Session(
                id = 1,
                exerciseId = "ex1",
                name = "Jambes",
                totalSets = 4
            ),
            Session(
                id = 2,
                exerciseId = "ex2",
                name = "Deadlift",
                totalSets = 3
            ),
            Session(
                id = 3,
                exerciseId = "ex3",
                name = "Bench Press",
                totalSets = 4
            ),
            Session(
                id = 4,
                exerciseId = "ex4",
                name = "Pull-up",
                totalSets = 3
            )
        )
    )

    val modifiers = Modifiers()
    val navController = rememberNavController()

    //SessionsScreen(modifiers, session, navController)

}


