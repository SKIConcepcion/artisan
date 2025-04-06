package com.example.artisan.domain.controller

import androidx.lifecycle.ViewModel
import com.example.artisan.domain.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class UserViewModel : ViewModel() {

    private var _userList = MutableStateFlow<List<User>>(emptyList())
    var userList = _userList.asStateFlow()

    fun getUserList() {
        val db = FirebaseFirestore.getInstance()

        db.collection("users")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.e("UserViewModel", "Error fetching users", error)
                    return@addSnapshotListener
                }

                if (value != null) {
                    // Print the retrieved data to the log
                    val users = value.toObjects(User::class.java)
                    Log.d("UserViewModel", "Retrieved users: $users")

                    // Update the user list
                    _userList.value = users
                }
            }
    }

    fun loginUser(email: String, password: String, onResult: (Boolean) -> Unit) {
        val user = _userList.value.find { it.email == email && it.password == password }
        if (user != null) {
            Log.d("UserViewModel", "Login Successful: ${user.firstname}")
            onResult(true)
        } else {
            Log.d("UserViewModel", "Login Failed")
            onResult(false)
        }
    }

}
