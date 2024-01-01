package com.example.student_teacherapplication
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(onBackClick: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var resetEmailSent by remember { mutableStateOf(false) }
    val auth = FirebaseAuth.getInstance()

    if (resetEmailSent) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text("Şifre sıfırlama e-postası gönderildi. Lütfen e-posta kutunuzu kontrol edin.")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onBackClick) {
                Text("Geri")
            }
        }
    } else {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("Şifrenizi mi unuttunuz?")
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("E-posta") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.fillMaxWidth()
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

                        auth.sendPasswordResetEmail(email)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {

                                    resetEmailSent = true
                                } else {

                                    println("Şifre sıfırlama e-postası gönderilemedi: ${task.exception?.message}")
                                }
                            }
                    }
                ) {
                    Text("Şifremi Sıfırla")
                }

                TextButton(
                    onClick = onBackClick
                ) {
                    Text("Geri")
                }
            }
        }
    }
}

@Preview
@Composable
fun ForgotPasswordPreview() {
    ForgotPasswordScreen {}
}
