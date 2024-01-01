package com.example.student_teacherapplication


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

enum class UserType {
    Student,Teacher
}

@Composable
fun RadioButtonGroup(
    options: List<UserType>,
    selectedOption: UserType,
    onOptionSelected: (UserType) -> Unit
) {
    Column {
        options.forEach { option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                RadioButton(
                    selected = option == selectedOption,
                    onClick = { onOptionSelected(option) }
                )
                Text(text = option.name)
            }
        }
    }
}
@Composable
fun UserTypeSelectionScreen(onNextClick: (UserType) -> Unit) {
    var selectedUserType by remember { mutableStateOf(UserType.Student) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)

    ) {
        // Kullanıcının türünü seçmesine izin veren radyo düğmeleri
        RadioButtonGroup(
            options = UserType.values().toList(),
            selectedOption = selectedUserType,
            onOptionSelected = { userType ->
                selectedUserType = userType
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Devam et butonu
        Button(
            onClick = { onNextClick(selectedUserType) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Keep Continue")
        }
    }
}


@Composable
@Preview
fun SignInPreview() {
    SignInScreen(onForgotPasswordClick = {  })
}










@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun SignInScreen(onForgotPasswordClick: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var userType by remember { mutableStateOf<UserType?>(null) }

    if (userType == null) {
        // Kullanıcı türünü seçme ekranı
        UserTypeSelectionScreen { selectedUserType ->
            userType = selectedUserType
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(color = Color.Blue)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .scale(0.8f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("E-posta") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(
                onClick = {
                    if (email.isNotEmpty() && password.isNotEmpty() && userType != null) {

                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {

                                    addUserToDatabase(email, userType!!)
                                } else {


                                }
                            }
                    }
                }
            ) {
                Text("Sign Up")
            }

            TextButton(
                onClick = onForgotPasswordClick
            ) {
                Text("Forgot password")
            }
        }
    }
}

private fun addUserToDatabase(email: String, userType: UserType) {
    val database = FirebaseFirestore.getInstance()
    val usersRef = database.collection("users")

    val user = hashMapOf(
        "email" to email,
        "userType" to userType.name
    )

    usersRef.add(user)
}
