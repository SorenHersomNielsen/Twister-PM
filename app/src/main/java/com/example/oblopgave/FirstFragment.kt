package com.example.oblopgave

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.oblopgave.databinding.FragmentFirstBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    private var firebaseViewModel: FirebaseViewModel = FirebaseViewModel()



    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signIn.setOnClickListener {
            Log.d("apple", "signin")
            // TODO DRY
            val email = binding.emailInputField.text.toString().trim()
            val password = binding.passwordInputField.text.toString().trim()
            if (email.isEmpty()) {
                binding.emailInputField.error = "No email"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.passwordInputField.error = "No password"
                return@setOnClickListener
            }

            firebaseViewModel.SignIn(email, password)

            firebaseViewModel.user.observe(viewLifecycleOwner, Observer
            {
                if (firebaseViewModel.user != null) {
                    findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                }
            })

            firebaseViewModel.message.observe(viewLifecycleOwner,
                {
                    if (firebaseViewModel.message != null) {
                        binding.messageView.text = firebaseViewModel.message.value
                    }
                }
            )
        }

        binding.buttonCreateUser.setOnClickListener {
            Log.d("apple", "create")
            val email = binding.emailInputField.text.toString().trim()
            val password = binding.passwordInputField.text.toString().trim()
            if (email.isEmpty()) {
                binding.emailInputField.error = "No email"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.passwordInputField.error = "No password"
                return@setOnClickListener
            }

            firebaseViewModel.CreateUser(email, password)
                firebaseViewModel.message.observe(viewLifecycleOwner, {
                    if (firebaseViewModel.message != null) {
                        binding.messageView.text = firebaseViewModel.message.value
                    }
                })
        }
    }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    }
