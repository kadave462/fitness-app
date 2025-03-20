package com.example.myfitnessapp.ui.screens

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myfitnessapp.models.entities.Exercise
import com.example.myfitnessapp.models.entities.Session
import com.example.myfitnessapp.models.interfaces.SessionRepositoryInterface
import com.example.myfitnessapp.ui.theme.Modifiers
import com.example.myfitnessapp.ui.views.ExerciseView
import com.example.myfitnessapp.viewmodels.repositories.ExerciseRepository
import com.example.myfitnessapp.viewmodels.repositories.tests.TestSessionRepository


@SuppressLint("UnrememberedMutableState")
@Composable
fun SessionDetailScreen(modifier: Modifier = Modifiers().bigPaddingModifier(true),
                                navController: NavController,
                                exerciseRepository: ExerciseRepository,
                                sessionRepository: SessionRepositoryInterface,
                                sessionId: String){

    val sessionId = sessionId.toInt()
    var sessionName: String by remember { mutableStateOf("") }
    var sessions by remember { mutableStateOf(emptyList<Session>()) }
    var exercises by remember {mutableStateOf(emptyList<Exercise>()) }



    LaunchedEffect(Unit) {
        Log.d("SessionS", "LaunchedEffect called with id = ${sessionId}")
        sessions = sessionRepository.getSessionById(sessionId)
        Log.d("SessionS", "Screen -> Session: ${sessionId}")
        sessionName = sessionRepository.getSessionName(sessions)

        exercises = sessions.map { session -> exerciseRepository.getExerciseByName(session.exerciseId) }
    }



    Text(sessionName, style = MaterialTheme.typography.titleLarge)

    LazyColumn(modifier) {
        items(exercises) { exercise ->
            ExerciseView(Modifiers(), exercise, false, 0) { }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun PreviewSessionDetailScreen() {
    val navController = rememberNavController()
    val sessionRepository = TestSessionRepository()
    val exerciseRepository = ExerciseRepository(LocalContext.current)

    SessionDetailScreen(
        navController = navController,
        sessionRepository = sessionRepository,
        exerciseRepository = exerciseRepository,
        sessionId = "0"
    )
}
