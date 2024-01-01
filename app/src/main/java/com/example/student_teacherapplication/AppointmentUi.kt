package com.example.student_teacherapplication
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    private val appointmentViewModel: AppointmentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp(appointmentViewModel = appointmentViewModel)
        }
    }
}
@Composable
fun MyApp(appointmentViewModel: AppointmentViewModel) {
    var isGreen by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Öğretmenin Randevu Saatleri", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(3) { dateIndex ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (timeIndex in 0 until 3) {
                        val timeText = "${10 + timeIndex}:00"
                        val isSelected = selectedDate == "2023-01-0${dateIndex + 1}" && selectedTime == timeText

                        DateCard(
                            date = "2023-01-0${dateIndex + 1}",
                            time = timeText,
                            isSelected = isSelected,
                            onCardClick = {
                                selectedDate = "2023-01-0${dateIndex + 1}"
                                selectedTime = timeText
                                isGreen = true


                                showDialog = true
                                appointmentViewModel.createAppointment(selectedDate, selectedTime, "Student")
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        if (showDialog) {
            AlertDialog(
                onDismissRequest = {
                    // AlertDialog kapatılınca showDialog'u false yap
                    showDialog = false
                },
                title = {
                    Text(text = "Seçiminiz Yapıldı")
                },
                text = {
                    Text(text = "Randevunuz oluşturuldu.")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            // Kullanıcının seçimiyle ilgili başka bir işlem yapılabilir
                            showDialog = false
                        }
                    ) {
                        Text(text = "Tamam")
                    }
                }
            )
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DateCard(date: String, time: String, isSelected: Boolean, onCardClick: () -> Unit) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = Modifier
            .size(120.dp)
            .background(if (isSelected) Color.Green else Color.Red)
            .clickable {
                onCardClick()
                keyboardController?.hide()
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(date, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(time, fontWeight = FontWeight.Bold)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MyAppPreview() {
    val fakeViewModel = AppointmentViewModel(AppointmentRepository())
    MyApp(appointmentViewModel = fakeViewModel)
}