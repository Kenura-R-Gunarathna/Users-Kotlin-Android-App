package com.example.myapplication.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
// Removed unused background import if not needed elsewhere
// import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
// Removed unused shape import if not needed elsewhere
// import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
// Removed unused direct Color import
// import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.entities.User
import com.example.myapplication.ui.viewModels.UserViewModel

@Composable
fun UserListScreen(viewModel: UserViewModel, onNavigateToEdit: (User) -> Unit, onNavigateToAddUser: () -> Unit) {
    val users by viewModel.users.collectAsState()

    // UserListDesign now handles the main layout including Scaffold and FAB
    UserListDesign(
        users = users,
        onEdit = { user -> onNavigateToEdit(user) },
        onDelete = { user -> viewModel.deleteUser(user) },
        onEntry = { onNavigateToAddUser() }
    )
}

// --- Previews remain largely the same, just calling the updated UserListDesign ---

@Preview(showBackground = true) // Added showBackground for better preview context
@Composable
fun UserListPreview(){
    val users = listOf(
        User(1, "Kenura", "Ransana"),
        User(2, "Lisara", "Damsana"),
        User(3, "Sanuli", "Nehansa")
    )
    // Wrap preview in a theme if not already done by the project setup
    // MyApplicationTheme { // Replace with your actual theme
    UserListDesign(
        users = users,
        onEdit = { user ->
            Log.v("user_edit", "Edit user - firstname: ${user.firstName}, lastname: ${user.lastName}")
        },
        onDelete = { user ->
            Log.v("user_delete", "Delete user - firstname: ${user.firstName}, lastname: ${user.lastName}")
        },
        onEntry = { Log.v("user_entry", "New user") }
    )
    // }
}

@Preview(showBackground = true)
@Composable
fun UserListEmptyPreview() {
    // Wrap preview in a theme if not already done by the project setup
    // MyApplicationTheme { // Replace with your actual theme
    UserListDesign(
        users = emptyList(),
        onEdit = { },
        onDelete = { },
        onEntry = { }
    )
    // }
}


// --- UserListDesign refactored to use Scaffold ---

@OptIn(ExperimentalMaterial3Api::class) // Often needed for Scaffold/TopAppBar etc.
@Composable
fun UserListDesign(
    users: List<User>,
    onEdit: (User) -> Unit,
    onDelete: (User) -> Unit,
    onEntry: () -> Unit
){
    Scaffold(
        floatingActionButton = {
            SmallFloatingActionButton(
                onClick = { onEntry() },
                // Use theme colors
                containerColor = MaterialTheme.colorScheme.primaryContainer, // Example theme color
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer  // Example theme color
                // No align modifier needed here, Scaffold places it
                // No extra padding needed here unless you want specific margin; Scaffold handles placement
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add User") // Improved description
            }
        }
    ) { innerPadding -> // Content lambda receives padding from Scaffold (for status/nav bars, FAB)

        // Box to handle the empty state centering within the Scaffold content area
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // Apply padding from Scaffold
        ) {
            if (users.isEmpty()) {
                // Show a message when no users are available
                Text(
                    text = "No Users Available",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground // Use theme color
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    // Add content padding for spacing inside the list area
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    // Use key for better performance and state management
                    items(users, key = { user -> user.id }) { user ->
                        ElevatedCard(
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 4.dp
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clickable {
                                    Log.d("UserList", "Editing user: ${user.firstName} ${user.lastName}")
                                    onEdit(user)
                                }
                        ) {
                        Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 12.dp), // Padding inside the card
                                // Removed explicit background - Card provides it
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "${user.firstName} ${user.lastName}",
                                    // Use theme color appropriate for Card's surface
                                    color = MaterialTheme.colorScheme.onSurfaceVariant, // Or onSurface
                                    style = MaterialTheme.typography.bodyLarge, // Example style
                                    modifier = Modifier.weight(1f).padding(end = 8.dp) // Take available space, add padding before icon
                                )
                                IconButton(onClick = { onDelete(user) }) {
                                    Icon(
                                        Icons.Default.Delete,
                                        contentDescription = "Delete ${user.firstName} ${user.lastName}", // Better accessibility
                                        // Use theme error color for delete action
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}