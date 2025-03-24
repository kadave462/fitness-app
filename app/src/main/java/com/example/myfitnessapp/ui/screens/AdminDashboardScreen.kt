package com.example.myfitnessapp.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myfitnessapp.models.entities.User
import com.example.myfitnessapp.ui.theme.Modifiers
import com.example.myfitnessapp.viewmodels.repositories.UserRepository
import kotlinx.coroutines.launch

@Composable
fun AdminDashboardScreen(
    modifiers: Modifiers,
    navController: NavController,
    adminUser: User,
    userRepository: UserRepository
) {
    var users by remember { mutableStateOf<List<User>>(emptyList()) }
    var showAddUserDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    // Load all users
    LaunchedEffect(key1 = Unit) {
        coroutineScope.launch {
            users = userRepository.getAllUsers()
        }
    }

    Column(
        modifier = modifiers.bigPaddingModifier(true),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Admin Dashboard",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Add User Button
        Button(
            onClick = { showAddUserDialog = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add New User")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // User List
        Text(
            text = "Manage Users",
            style = MaterialTheme.typography.titleMedium
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(users) { user ->
                UserListItem(
                    user = user,
                    onDelete = {
                        coroutineScope.launch {
                            userRepository.deleteUser(user.id)
                            // Refresh the list
                            users = userRepository.getAllUsers()
                        }
                    }
                )
            }
        }
    }

    // Add User Dialog
    if (showAddUserDialog) {
        AddUserDialog(
            onDismiss = { showAddUserDialog = false },
            onAddUser = { newUser ->
                coroutineScope.launch {
                    userRepository.addUser(newUser)
                    // Refresh the list
                    users = userRepository.getAllUsers()
                    showAddUserDialog = false
                }
            }
        )
    }
}

@Composable
private fun UserListItem(
    user: User,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${user.firstName} ${user.lastName}",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = user.email,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Level: ${user.level}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete User",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
private fun AddUserDialog(
    onDismiss: () -> Unit,
    onAddUser: (User) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var isAdmin by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New User") },
        text = {
            Column {
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text("First Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text("Last Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked = isAdmin,
                        onCheckedChange = { isAdmin = it }
                    )
                    Text("Admin User")
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    // Create a new user with the provided information
                    val passwordHash = org.mindrot.jbcrypt.BCrypt.hashpw(password, org.mindrot.jbcrypt.BCrypt.gensalt())
                    val newUser = User(
                        id = 0, // Will be auto-generated
                        email = email,
                        passwordHash = passwordHash,
                        pseudonym = "$firstName $lastName",
                        firstName = firstName,
                        lastName = lastName,
                        weight = 0.0, // Default values
                        height = 0,
                        birthdate = "",
                        gender = "Other",
                        level = "Débutant",
                        isAdmin = isAdmin
                    )
                    onAddUser(newUser)
                }
            ) {
                Text("Add User")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}