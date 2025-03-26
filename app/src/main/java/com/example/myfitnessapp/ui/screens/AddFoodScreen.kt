package com.example.myfitnessapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.myfitnessapp.models.entities.User
import com.example.myfitnessapp.ui.theme.Modifiers
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.example.myfitnessapp.models.database.daos.NutritionTotals
import com.example.myfitnessapp.models.entities.Food
import com.example.myfitnessapp.viewmodels.repositories.NutritionRepository
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.text.input.KeyboardType
import com.example.myfitnessapp.ui.components.DropdownSelector


@Composable
fun AddFoodScreen(
    modifiers: Modifiers,
    navController: NavController,
    user: User
) {
    val context = LocalContext.current
    val nutritionRepository = remember { NutritionRepository(context, user.id) }
    val coroutineScope = rememberCoroutineScope()

    var searchQuery by remember { mutableStateOf("") }
    var searchResults by remember { mutableStateOf<List<Food>>(emptyList()) }
    var isSearching by remember { mutableStateOf(false) }
    var showAddCustomFoodDialog by remember { mutableStateOf(false) }

    LaunchedEffect(searchQuery) {
        if (searchQuery.length >= 2) {
            isSearching = true
            searchResults = nutritionRepository.searchFoods(searchQuery)
            isSearching = false
        } else {
            searchResults = emptyList()
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
                text = "Add Food",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Search bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search for food") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Search")
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Add custom food button
        TextButton(
            onClick = { showAddCustomFoodDialog = true },
            modifier = Modifier.align(Alignment.End)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add custom food")
            Spacer(modifier = Modifier.width(4.dp))
            Text("Add custom food")
        }

        // Search results
        if (isSearching) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (searchResults.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(searchResults) { food ->
                    FoodSearchResultItem(
                        food = food,
                        onClick = {
                            navController.navigate("add_food_entry/${food.id}")
                        }
                    )
                }
            }
        } else if (searchQuery.length >= 2) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No foods found for '$searchQuery'",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Search for a food to add to your meal",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }

    // Add custom food dialog
    if (showAddCustomFoodDialog) {
        AddCustomFoodDialog(
            onDismiss = { showAddCustomFoodDialog = false },
            onAddFood = { food ->
                coroutineScope.launch {
                    val foodId = nutritionRepository.addCustomFood(food).toInt()
                    showAddCustomFoodDialog = false
                    navController.navigate("add_food_entry/$foodId")
                }
            }
        )
    }
}

@Composable
fun FoodSearchResultItem(
    food: Food,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = food.name,
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = "${food.servingSize} ${food.servingUnit} · ${food.calories} kcal",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "P: ${food.protein}g",
                    style = MaterialTheme.typography.bodySmall
                )

                Text(
                    text = "C: ${food.carbs}g",
                    style = MaterialTheme.typography.bodySmall
                )

                Text(
                    text = "F: ${food.fat}g",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCustomFoodDialog(
    onDismiss: () -> Unit,
    onAddFood: (Food) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var calories by remember { mutableStateOf("") }
    var protein by remember { mutableStateOf("") }
    var carbs by remember { mutableStateOf("") }
    var fat by remember { mutableStateOf("") }
    var servingSize by remember { mutableStateOf("") }
    var servingUnit by remember { mutableStateOf("g") }

    // State to track if the input fields are valid
    var isNameValid by remember { mutableStateOf(true) }
    var isCaloriesValid by remember { mutableStateOf(true) }
    var isProteinValid by remember { mutableStateOf(true) }
    var isCarbsValid by remember { mutableStateOf(true) }
    var isFatValid by remember { mutableStateOf(true) }
    var isServingSizeValid by remember { mutableStateOf(true) }

    // Function to validate the input fields
    fun validateFields() {
        isNameValid = name.isNotBlank()
        isCaloriesValid = calories.isNotBlank() && calories.toIntOrNull() != null
        isProteinValid = protein.isNotBlank() && protein.toDoubleOrNull() != null
        isCarbsValid = carbs.isNotBlank() && carbs.toDoubleOrNull() != null
        isFatValid = fat.isNotBlank() && fat.toDoubleOrNull() != null
        isServingSizeValid = servingSize.isNotBlank() && servingSize.toDoubleOrNull() != null
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Custom Food") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Food Name") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = !isNameValid,
                    supportingText = { if (!isNameValid) Text("Name cannot be empty") }
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = calories,
                    onValueChange = { calories = it },
                    label = { Text("Calories") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    isError = !isCaloriesValid,
                    supportingText = { if (!isCaloriesValid) Text("Invalid calories") }
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = protein,
                        onValueChange = { protein = it },
                        label = { Text("Protein (g)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.weight(1f),
                        isError = !isProteinValid,
                        supportingText = { if (!isProteinValid) Text("Invalid protein") }
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    OutlinedTextField(
                        value = carbs,
                        onValueChange = { carbs = it },
                        label = { Text("Carbs (g)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.weight(1f),
                        isError = !isCarbsValid,
                        supportingText = { if (!isCarbsValid) Text("Invalid carbs") }
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    OutlinedTextField(
                        value = fat,
                        onValueChange = { fat = it },
                        label = { Text("Fat (g)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.weight(1f),
                        isError = !isFatValid,
                        supportingText = { if (!isFatValid) Text("Invalid fat") }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = servingSize,
                        onValueChange = { servingSize = it },
                        label = { Text("Serving Size") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.weight(1f),
                        isError = !isServingSizeValid,
                        supportingText = { if (!isServingSizeValid) Text("Invalid serving size") }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    DropdownSelector(
                        label = "Unit",
                        options = listOf("g", "ml", "oz", "piece", "cup", "tbsp", "tsp"),
                        selectedOption = servingUnit,
                        onOptionSelected = { servingUnit = it },
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    validateFields()
                    if (isNameValid && isCaloriesValid && isProteinValid && isCarbsValid && isFatValid && isServingSizeValid) {
                        val caloriesInt = calories.toIntOrNull() ?: 0
                        val proteinDouble = protein.toDoubleOrNull() ?: 0.0
                        val carbsDouble = carbs.toDoubleOrNull() ?: 0.0
                        val fatDouble = fat.toDoubleOrNull() ?: 0.0
                        val servingSizeDouble = servingSize.toDoubleOrNull() ?: 0.0

                        val food = Food(
                            name = name,
                            calories = caloriesInt,
                            protein = proteinDouble,
                            carbs = carbsDouble,
                            fat = fatDouble,
                            servingSize = servingSizeDouble,
                            servingUnit = servingUnit,
                            isUserCreated = true
                        )
                        onAddFood(food)
                    }
                }
            ) {
                Text("Add Food")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}