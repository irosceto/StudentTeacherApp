package com.example.student_teacherapplication
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AppointmentViewModel(private val repository: AppointmentRepository) : ViewModel() {

    fun createAppointment(selectedDate: String, selectedTime: String, userType: String) {
        viewModelScope.launch {
            val userEmails = repository.getUserEmailsByUserType(userType)

            for (userEmail in userEmails) {
                repository.addAppointment(
                    Appointment(
                        teacherEmail = if (userType == "Teacher") userEmail else "default_teacher@example.com",
                        studentEmail = if (userType == "Student") userEmail else "default_student@example.com",
                        date = selectedDate,
                        time = selectedTime
                    )
                )
            }
        }
    }
}
