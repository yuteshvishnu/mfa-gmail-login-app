package com.example.mfa_gmail_login_app

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MFAActivity : AppCompatActivity() {
    private lateinit var digit1: EditText
    private lateinit var digit2: EditText
    private lateinit var digit3: EditText
    private lateinit var digit4: EditText
    private lateinit var emailText: TextView
    private lateinit var verifyButton: Button
    private lateinit var verificationCode: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gmail_mfa)

        // Initialize views
        digit1 = findViewById(R.id.digit1)
        digit2 = findViewById(R.id.digit2)
        digit3 = findViewById(R.id.digit3)
        digit4 = findViewById(R.id.digit4)
        emailText = findViewById(R.id.emailText)
        verifyButton = findViewById(R.id.verifyButton)

        // Get email and verification code from intent
        val email = intent.getStringExtra("email") ?: ""
        verificationCode = intent.getStringExtra("verificationCode") ?: ""
        emailText.text = email

        // Set up auto-focus for verification code input
        setupVerificationCodeInput()

        verifyButton.setOnClickListener {
            val code = "${digit1.text}${digit2.text}${digit3.text}${digit4.text}"
            if (code == "1234") {
                // Navigate to home page
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                // Clear all previous activities from stack
                finishAffinity()
            } else {
                Toast.makeText(this, "Invalid verification code", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupVerificationCodeInput() {
        val digits = listOf(digit1, digit2, digit3, digit4)

        digits.forEachIndexed { index, editText ->
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s?.length == 1 && index < digits.size - 1) {
                        // Move to next digit
                        digits[index + 1].requestFocus()
                    }
                }

                override fun afterTextChanged(s: Editable?) {}
            })
        }
    }
}
