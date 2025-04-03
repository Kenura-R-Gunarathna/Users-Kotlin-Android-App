package com.example.myapplication.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.entities.User
import com.example.myapplication.ui.viewModels.UserViewModel

@Composable
fun UserEditScreen(user: User, viewModel: UserViewModel, onNavigateBack: () -> Unit) {
    UserEditDesign(
        user = user,
        onEdit = { firstName, lastName ->
        if (firstName.isNotEmpty() && lastName.isNotEmpty()) {
            viewModel.updateUser(user.copy(firstName = firstName, lastName = lastName))
            onNavigateBack()
        }
    })
}

@Preview
@Composable
fun PreviewUserEdit(){
    UserEditDesign(
        user = User(firstName="Kenura", lastName="Ransana"),
        onEdit = { firstName, lastName ->
        if (firstName.isNotEmpty() && lastName.isNotEmpty()) {
            Log.v("user_edit", "Edit user - firstname: $firstName, lastname: $lastName")
        }
    })
}

@Composable
fun UserEditDesign(
    user: User,
    onEdit: (firstName: String, lastName: String) -> Unit
) {
    var firstName by remember { mutableStateOf(user.firstName) }
    var lastName by remember { mutableStateOf(user.lastName) }

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
                Text(text = "Edit User Details", style = MaterialTheme.typography.headlineSmall)

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
                    onClick = { onEdit(firstName, lastName) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Edit User")
                }
            }
        }
    }
}
