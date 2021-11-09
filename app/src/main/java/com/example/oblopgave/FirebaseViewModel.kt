package com.example.oblopgave

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseError
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.math.log

class FirebaseViewModel : ViewModel(){
    private lateinit var auth: FirebaseAuth

    val message: MutableLiveData<String> = MutableLiveData()
    val user: MutableLiveData<FirebaseUser?> = MutableLiveData()


    fun SignIn( email: String,password: String) {
        auth = Firebase.auth

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                user.value = auth.currentUser
                Log.d("apple", "login success")

            } else {
                message.value = task.exception?.message
                Log.d("Apple", " login fail")
            }
        }

    }
    fun CreateUser(email: String, password: String) {
        auth = Firebase.auth

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                user.value = auth.currentUser
                Log.d("Apple", "create user success")
            } else {
                message.value = task.exception?.message
                Log.d("Apple", "create user fail")
            }
        }

    }

    fun SignOut(){
        Log.d("apple", "signout")
        Firebase.auth.signOut()
        user.value = null
    }
}
