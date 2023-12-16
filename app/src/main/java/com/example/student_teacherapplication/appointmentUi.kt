package com.example.student_teacherapplication
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp



class SecondActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                val viewModel = AppointmentViewModelPage()
                AppointmentScreen(viewModel)
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AppointmentScreen(viewModel: AppointmentViewModelPage) {
        var newAppointmentDate by remember { mutableStateOf("") }
        var newAppointmentTime by remember { mutableStateOf("") }
        var newAppointmentLocation by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            TextField(
                value = newAppointmentDate,
                onValueChange = { newAppointmentDate = it },
                label = { Text("Date") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            )

            TextField(
                value = newAppointmentTime,
                onValueChange = { newAppointmentTime = it },
                label = { Text("Time") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            )

            TextField(
                value = newAppointmentLocation,
                onValueChange = { newAppointmentLocation = it },
                label = { Text("Location") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            )

            Button(
                onClick = {
                    val newAppointment = Appointment(
                        id = viewModel.appointment.size + 1,
                        date = newAppointmentDate,
                        time = newAppointmentTime,
                        location = newAppointmentLocation
                    )
                    viewModel.addAppointment(newAppointment)

                    newAppointmentDate = ""
                    newAppointmentTime = ""
                    newAppointmentLocation = ""
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Send, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Add Appointment")
                }
            }
        }
    }


    @Composable
    fun AppointmentItem(appointment: Appointment, onDelete: (Appointment) -> Unit) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    Text(text = "Date: ${appointment.date}")
                    Text(text = "Time: ${appointment.time}")
                    Text(text = "Location: ${appointment.location}")
                }
            }
        }
    }

    @Preview
    @Composable
    fun Deneme() {
        AppointmentItem(appointment = Appointment(12920, "2001", "12", "irem"), onDelete = {})
    }
}

