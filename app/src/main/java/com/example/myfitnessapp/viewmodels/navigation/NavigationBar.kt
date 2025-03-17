package com.example.myfitnessapp.viewmodels.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myfitnessapp.R
import com.example.myfitnessapp.viewmodels.utils.NavigationUtils

@Composable
fun NavigationBar(navController: NavController){
    val currentRoute = NavigationUtils().currentRoute(navController)

    BottomAppBar(
        contentColor = MaterialTheme.colorScheme.primary,
        containerColor = MaterialTheme.colorScheme.background
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(
                onClick = { if (currentRoute!= "all_sessions_screen") navController.navigate("all_sessions_screen") },
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_history),
                    contentDescription = "History",
                    modifier = Modifier.size(48.dp)
                )
            }

            IconButton(
                onClick = { if (currentRoute!= "exercise_screen") navController.navigate("exercise_screen") },
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_fitness),
                    contentDescription = "Exercises",
                    modifier = Modifier.size(48.dp)
                )
            }

            IconButton(
                onClick = { if (currentRoute!= "home_screen") navController.navigate("home_screen") },
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    Icons.Filled.Home,
                    contentDescription = "Home",
                    modifier = Modifier.size(64.dp)
                )
            }

            IconButton(
                onClick = { if (currentRoute!= "profile_screen") navController.navigate("profile_screen") },
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = "Profile",
                    modifier = Modifier.size(48.dp)
                )
            }
        }
    }
}