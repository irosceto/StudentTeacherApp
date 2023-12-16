package com.example.student_teacherapplication
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class AppointmentViewModelPage :ViewModel() {
    private val _appointment = mutableStateListOf<Appointment>()
    val appointment: List<Appointment>
        get() = _appointment

    fun addAppointment(appointment: Appointment) {
        _appointment.add(appointment)

    }

    fun removeAppointment(appointment: Appointment) {

        _appointment.remove(appointment)
    }
}
