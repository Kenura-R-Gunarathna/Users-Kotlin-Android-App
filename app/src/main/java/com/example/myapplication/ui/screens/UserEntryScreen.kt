package com.example.myapplication.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.viewModels.UserViewModel

@Composable
fun UserEntryScreen(viewModel: UserViewModel, onNavigateToList: () -> Unit) {
    UserEntryDesign(onEntry = { firstName, lastName ->
        if (firstName.isNotEmpty() && lastName.isNotEmpty()) {
            viewModel.insertUser(firstName, lastName)
            onNavigateToList()
        }
    })
}


@Preview
@Composable
fun UserEntryPreview() {
    UserEntryDesign(onEntry = { firstName, lastName ->
        if (firstName.isNotEmpty() && lastName.isNotEmpty()) {
            Log.v("user_entry", "New user - firstname: $firstName, lastname: $lastName")
        }
    })
}

@Composable
fun UserEntryDesign(
    onEntry: (firstName: String, lastName: String) -> Unit
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "Enter User Details", style = MaterialTheme.typography.headlineSmall)

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

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { onEntry(firstName, lastName) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add User")
                }
            }
        }
    }
}
