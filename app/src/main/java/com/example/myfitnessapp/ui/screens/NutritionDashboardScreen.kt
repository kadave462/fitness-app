package com.example.myfitnessapp.ui.screens
import com.example.myfitnessapp.models.entities.NutritionGoal
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
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
import com.example.myfitnessapp.models.entities.MealEntry
import com.example.myfitnessapp.viewmodels.repositories.NutritionRepository
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip

import java.time.LocalDate
import java.time.format.DateTimeFormatter



@Composable
fun NutritionDashboardScreen(
    modifiers: Modifiers,
    navController: NavController,
    user: User
) {
    val context = LocalContext.current
    val nutritionRepository = remember { NutritionRepository(context, user.id) }
    val coroutineScope = rememberCoroutineScope()

    // State
    var currentDate by remember { mutableStateOf(LocalDate.now().toString()) }
    var nutritionTotals by remember { mutableStateOf(NutritionTotals()) }
    var nutritionGoal by remember { mutableStateOf<NutritionGoal?>(null) }
    var mealEntries by remember { mutableStateOf(emptyMap<MealEntry, Food>()) }

    // Load data
    LaunchedEffect(currentDate) {
        nutritionTotals = nutritionRepository.getNutritionTotalsForDate(currentDate)
        mealEntries = nutritionRepository.getMealEntriesForDate(currentDate)
    }

    LaunchedEffect(Unit) {
        // Get or create nutrition goals
        nutritionGoal = nutritionRepository.getNutritionGoal() ?: run {
            val defaultGoal = nutritionRepository.generateDefaultNutritionGoals(user)
            nutritionRepository.setNutritionGoal(
                defaultGoal.calorieGoal,
                defaultGoal.proteinGoal,
                defaultGoal.carbsGoal,
                defaultGoal.fatGoal
            )
            defaultGoal
        }
    }

    Column(
        modifier = modifiers.bigPaddingModifier(true),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Date selector
        DateSelector(
            currentDate = currentDate,
            onDateChange = { newDate -> currentDate = newDate }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Nutrition summary
        NutritionSummaryCard(
            nutritionTotals = nutritionTotals,
            nutritionGoal = nutritionGoal
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Meal entries by type
        MealList(
            mealEntries = mealEntries,
            onDeleteEntry = { entryId ->
                coroutineScope.launch {
                    nutritionRepository.deleteMealEntry(entryId)
                    // Refresh data
                    nutritionTotals = nutritionRepository.getNutritionTotalsForDate(currentDate)
                    mealEntries = nutritionRepository.getMealEntriesForDate(currentDate)
                }
            }
        )

        Spacer(modifier = Modifier.weight(1f))

        // Add food button
        Button(
            onClick = { navController.navigate("add_food") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Food")
        }

        // Nutrition goals button
        Button(
            onClick = { navController.navigate("nutrition_goals") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Set Nutrition Goals")
        }
    }
}

@Composable
fun DateSelector(
    currentDate: String,
    onDateChange: (String) -> Unit
) {
    val localDate = LocalDate.parse(currentDate)

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            val previousDate = localDate.minusDays(1)
            onDateChange(previousDate.toString())
        }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Previous Day")
        }

        Text(
            text = localDate.format(DateTimeFormatter.ofPattern("EEE, MMM d")),
            style = MaterialTheme.typography.titleMedium
        )

        IconButton(onClick = {
            val nextDate = localDate.plusDays(1)
            // Don't allow future dates
            if (!nextDate.isAfter(LocalDate.now())) {
                onDateChange(nextDate.toString())
            }
        }) {
            Icon(Icons.Default.ArrowForward, contentDescription = "Next Day")
        }
    }
}

@Composable
fun NutritionSummaryCard(
    nutritionTotals: NutritionTotals,
    nutritionGoal: NutritionGoal?
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Daily Summary",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Calories
            NutrientProgressBar(
                label = "Calories",
                current = nutritionTotals.totalCalories.toFloat(),
                goal = nutritionGoal?.calorieGoal?.toFloat() ?: 2000f,
                unit = "kcal"
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Protein
            NutrientProgressBar(
                label = "Protein",
                current = nutritionTotals.totalProtein.toFloat(),
                goal = nutritionGoal?.proteinGoal?.toFloat() ?: 100f,
                unit = "g"
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Carbs
            NutrientProgressBar(
                label = "Carbs",
                current = nutritionTotals.totalCarbs.toFloat(),
                goal = nutritionGoal?.carbsGoal?.toFloat() ?: 200f,
                unit = "g"
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Fat
            NutrientProgressBar(
                label = "Fat",
                current = nutritionTotals.totalFat.toFloat(),
                goal = nutritionGoal?.fatGoal?.toFloat() ?: 65f,
                unit = "g"
            )
        }
    }
}

@Composable
fun NutrientProgressBar(
    label: String,
    current: Float,
    goal: Float,
    unit: String
) {
    val progress = (current / goal).coerceIn(0f, 1f)

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "${current.toInt()} / ${goal.toInt()} $unit",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = if (progress > 1f) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}

@Composable
fun MealList(
    mealEntries: Map<MealEntry, Food>,
    onDeleteEntry: (Int) -> Unit
) {
    val groupedEntries = mealEntries.entries
        .groupBy { it.key.mealType }
        .toSortedMap(compareBy {
            when (it) {
                "Breakfast" -> 0
                "Lunch" -> 1
                "Dinner" -> 2
                else -> 3 // Snacks
            }
        })

    Column {
        Text(
            text = "Today's Meals",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (mealEntries.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No meals recorded for today",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            groupedEntries.forEach { (mealType, entries) ->
                MealSection(
                    mealType = mealType,
                    entries = entries,
                    onDeleteEntry = onDeleteEntry
                )
            }
        }
    }
}

@Composable
fun MealSection(
    mealType: String,
    entries: List<Map.Entry<MealEntry, Food>>,
    onDeleteEntry: (Int) -> Unit
) {
    Column(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Text(
            text = mealType,
            style = MaterialTheme.typography.titleSmall
        )

        entries.forEach { (entry, food) ->
            FoodEntryItem(
                food = food,
                servings = entry.servings,
                onDelete = { onDeleteEntry(entry.id) }
            )
        }
    }
}

@Composable
fun FoodEntryItem(
    food: Food,
    servings: Double,
    onDelete: () -> Unit
) {
    val caloriesForServing = (food.calories * servings).toInt()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = food.name,
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "$servings ${food.servingUnit} · $caloriesForServing kcal",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        IconButton(onClick = onDelete) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete entry",
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
}