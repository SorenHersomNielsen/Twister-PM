package com.example.oblopgave

import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirebaseViewModel {
    private lateinit var auth: FirebaseAuth

    //var email: String = ""
    //var password: String = ""
    val message: MutableLiveData<String> = MutableLiveData()
    val user: MutableLiveData<FirebaseUser> = MutableLiveData()


    fun SignIn( email: String,password: String) {
        auth = Firebase.auth


        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                user.value = auth.currentUser
            } else {
                message.value = task.exception?.message.toString()
            }
        }
    }
    fun CreateUser(email: String,password: String) {
        auth = Firebase.auth

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                message.value = "User created"
            } else {
                message.value = task.exception?.message.toString()
            }
        }
    }
}
