package com.example.student_teacherapplication
// AppointmentRepository.kt

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AppointmentRepository {

    private val db = FirebaseFirestore.getInstance()

    suspend fun addAppointment(appointment: Appointment) {
        db.collection("randevular")
            .add(appointment)
            .await()
    }

    suspend fun getUserEmailsByUserType(userType: String): List<String> {
        val querySnapshot = db.collection("users")
            .whereEqualTo("userType", userType)
            .get()
            .await()

        return querySnapshot.documents.mapNotNull { it.getString("email") }
    }
}
