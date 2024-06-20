package com.example.edukaone

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.edukaone.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Set up sign-up button click listener
        binding.btnPrimary.setOnClickListener {
            val email = binding.textEmail.text.toString().trim()
            val password = binding.editPassword.text.toString().trim()
            if (validateInput(email, password)) {
                registerUser(email, password)
            }
        }

        // Set up text view click listener to navigate back to LoginActivity
        binding.textLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun validateInput(email: String, password: String): Boolean {
        return when {
            email.isEmpty() -> {
                binding.textEmail.error = "Email is required"
                binding.editPassword.requestFocus()
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.textEmail.error = "Enter a valid email"
                binding.editPassword.requestFocus()
                false
            }
            password.isEmpty() -> {
                binding.editPassword.error = "Password is required"
                binding.editPassword.requestFocus()
                false
            }
            password.length < 6 -> {
                binding.editPassword.error = "Password should be at least 6 characters"
                binding.editPassword.requestFocus()
                false
            }
            else -> true
        }
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign up success, navigate to LoginActivity
                    Toast.makeText(baseContext, "Registration successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                } else {
                    // If sign up fails, display a message to the user.
                    Toast.makeText(baseContext, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

}