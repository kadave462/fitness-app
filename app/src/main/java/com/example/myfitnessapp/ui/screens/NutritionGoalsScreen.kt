package com.example.myfitnessapp.ui.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.myfitnessapp.models.entities.User
import com.example.myfitnessapp.ui.theme.Modifiers
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.example.myfitnessapp.models.entities.Food
import com.example.myfitnessapp.viewmodels.repositories.NutritionRepository
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.ui.text.input.KeyboardType
import com.example.myfitnessapp.models.entities.NutritionGoal
import com.example.myfitnessapp.ui.components.DateField
import java.time.LocalDate
@Composable
fun NutritionGoalsScreen(
    modifiers: Modifiers,
    navController: NavController,
    user: User
) {
    val context = LocalContext.current
    val nutritionRepository = remember { NutritionRepository(context, user.id) }
    val coroutineScope = rememberCoroutineScope()

    var nutritionGoal by remember { mutableStateOf<NutritionGoal?>(null) }
    var calorieGoal by remember { mutableStateOf("") }
    var proteinGoal by remember { mutableStateOf("") }
    var carbsGoal by remember { mutableStateOf("") }
    var fatGoal by remember { mutableStateOf("") }

    // Load current goals
    LaunchedEffect(Unit) {
        nutritionGoal = nutritionRepository.getNutritionGoal()

        nutritionGoal?.let { goal ->
            calorieGoal = goal.calorieGoal.toString()
            proteinGoal = goal.proteinGoal.toString()
            carbsGoal = goal.carbsGoal.toString()
            fatGoal = goal.fatGoal.toString()
        }
    }

    Column(
        modifier = modifiers.bigPaddingModifier(true),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Back button and title
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }

            Text(
                text = "Nutrition Goals",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Goals form
        OutlinedTextField(
            value = calorieGoal,
            onValueChange = { calorieGoal = it },
            label = { Text("Daily Calorie Goal") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            suffix = { Text("kcal") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = proteinGoal,
            onValueChange = { proteinGoal = it },
            label = { Text("Daily Protein Goal") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth(),
            suffix = { Text("g") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = carbsGoal,
            onValueChange = { carbsGoal = it },
            label = { Text("Daily Carbs Goal") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth(),
            suffix = { Text("g") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = fatGoal,
            onValueChange = { fatGoal = it },
            label = { Text("Daily Fat Goal") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth(),
            suffix = { Text("g") }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Calculate recommended goals
        Button(
            onClick = {
                coroutineScope.launch {
                    val defaultGoal = nutritionRepository.generateDefaultNutritionGoals(user)
                    calorieGoal = defaultGoal.calorieGoal.toString()
                    proteinGoal = String.format("%.1f", defaultGoal.proteinGoal)
                    carbsGoal = String.format("%.1f", defaultGoal.carbsGoal)
                    fatGoal = String.format("%.1f", defaultGoal.fatGoal)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calculate Recommended Goals")
        }

        Spacer(modifier = Modifier.weight(1f))

        // Save button
        Button(
            onClick = {
                val calorieGoalValue = calorieGoal.toIntOrNull() ?: 2000
                val proteinGoalValue = proteinGoal.toDoubleOrNull() ?: 100.0
                val carbsGoalValue = carbsGoal.toDoubleOrNull() ?: 200.0
                val fatGoalValue = fatGoal.toDoubleOrNull() ?: 65.0

                coroutineScope.launch {
                    nutritionRepository.setNutritionGoal(
                        calorieGoal = calorieGoalValue,
                        proteinGoal = proteinGoalValue,
                        carbsGoal = carbsGoalValue,
                        fatGoal = fatGoalValue
                    )

                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Goals")
        }
    }
}