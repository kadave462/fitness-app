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
import com.example.myfitnessapp.ui.components.DateField
import java.time.LocalDate

@Composable
fun AddFoodEntryScreen(
    modifiers: Modifiers,
    navController: NavController,
    foodId: Int,
    user: User
) {
    val context = LocalContext.current
    val nutritionRepository = remember { NutritionRepository(context, user.id) }
    val coroutineScope = rememberCoroutineScope()

    var food by remember { mutableStateOf<Food?>(null) }
    var servings by remember { mutableStateOf("1") }
    var mealType by remember { mutableStateOf("Breakfast") }
    var date by remember { mutableStateOf(LocalDate.now().toString()) }

    // Load food details
    LaunchedEffect(foodId) {
        food = nutritionRepository.getFoodById(foodId)
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
                text = "Add to Meal",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Food details
        food?.let { foodItem ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = foodItem.name,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Nutrition info
                    val servingsValue = servings.toDoubleOrNull() ?: 1.0
                    val calories = (foodItem.calories * servingsValue).toInt()
                    val protein = (foodItem.protein * servingsValue)
                    val carbs = (foodItem.carbs * servingsValue)
                    val fat = (foodItem.fat * servingsValue)

                    Text(
                        text = "Nutrition per serving",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Calories: $calories kcal",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Text(
                        text = "Protein: ${String.format("%.1f", protein)}g",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Text(
                        text = "Carbs: ${String.format("%.1f", carbs)}g",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Text(
                        text = "Fat: ${String.format("%.1f", fat)}g",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Servings input
            OutlinedTextField(
                value = servings,
                onValueChange = { servings = it },
                label = { Text("Servings") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    Text(
                        text = foodItem.servingUnit,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Meal type selection
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Meal",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    MealTypeChip(
                        mealType = "Breakfast",
                        selected = mealType == "Breakfast",
                        onClick = { mealType = "Breakfast" }
                    )

                    MealTypeChip(
                        mealType = "Lunch",
                        selected = mealType == "Lunch",
                        onClick = { mealType = "Lunch" }
                    )

                    MealTypeChip(
                        mealType = "Dinner",
                        selected = mealType == "Dinner",
                        onClick = { mealType = "Dinner" }
                    )

                    MealTypeChip(
                        mealType = "Snack",
                        selected = mealType == "Snack",
                        onClick = { mealType = "Snack" }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Date picker
            DateField(
                label = "Date",
                date = date,
                onDateSelected = { date = it }
            )

            Spacer(modifier = Modifier.weight(1f))

            // Add to meal button
            Button(
                onClick = {
                    val servingsValue = servings.toDoubleOrNull() ?: 1.0

                    coroutineScope.launch {
                        nutritionRepository.addMealEntry(
                            foodId = foodId,
                            servings = servingsValue,
                            mealType = mealType,
                            date = date
                        )

                        // Navigate back to nutrition dashboard
                        navController.navigate("nutrition_dashboard") {
                            popUpTo("nutrition_dashboard") { inclusive = true }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add to Meal")
            }
        } ?: run {
            // Loading or error state
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealTypeChip(
    mealType: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(mealType) },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}