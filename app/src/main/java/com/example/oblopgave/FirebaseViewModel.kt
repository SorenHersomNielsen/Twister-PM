package com.example.oblopgave

import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirebaseViewModel {
    private lateinit var auth: FirebaseAuth

    var email: String = ""
    var password: String = ""
    var message: String = ""
    fun SignIn() {
        auth = Firebase.auth

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                message = "ok"
            } else {
                message = task.exception?.message.toString()
            }
        }

        fun CreateUser() {
            auth = Firebase.auth

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    message = "User created"
                } else {
                    message = task.exception?.message.toString()
                }
            }
        }
    }
}
