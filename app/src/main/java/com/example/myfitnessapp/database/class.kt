@Composable
fun SessionCard(exerciseName: String, exerciseGifUrl: String) {
    // **Block 1: State Variables Declaration**
    val context = LocalContext.current
    var defaultSets by remember { mutableStateOf<Int?>(null) }
    var defaultReps by remember { mutableStateOf<Int?>(null) }

    // **Block 2: Database Initialization**
    val database = AppDatabase.getDatabase(context)
    val muscleDao = database.muscleDao()

    // **Block 3: Exercise-to-Muscle Mapping**
    val exerciseTargetMusclesMap = mapOf(
        "Push-ups" to "biceps", // Example, adjust as needed
        "Squats" to "quads",
        // ... (Add your complete mapping here) ...
    )

    // **Block 4: LaunchedEffect for Database Query (Side-Effect)**
    LaunchedEffect(exerciseName) {

        val targetMuscleName = exerciseTargetMusclesMap[exerciseName]
        if (targetMuscleName != null) {
            val muscle = muscleDao.getMuscleByName(targetMuscleName)
            if (muscle != null) {
                defaultSets = muscle.defaultSets
                defaultReps = muscle.defaultReps
            } else {
                Log.e("SessionCard", "Muscle '$targetMuscleName' not found in database!")
                defaultSets = 0
                defaultReps = 0
            }
        } else {
            Log.e("SessionCard", "No target muscle for exercise '$exerciseName'")
            defaultSets = 0
            defaultReps = 0
        }
    }

    // **Block 5: Column Layout and UI Elements**
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = exerciseName, style = MaterialTheme.typography.h6)

        // Display GIF - Placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.LightGray)
        ) {
            Text("GIF Placeholder for $exerciseName", Modifier.align(Alignment.Center))
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Display Default Sets
        Text(text = "Default Sets: ${defaultSets ?: "-"}")

        Spacer(modifier = Modifier.height(8.dp))

        // Display Default Reps
        Text(text = "Default Reps: ${defaultReps ?: "-"}")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { /* ... Start Session Logic ... */ }) {
            Text("Start Session")
        }
    }
}