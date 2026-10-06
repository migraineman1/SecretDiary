package org.hyperskill.secretdiary

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.hyperskill.secretdiary.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val pin = binding.etPin.text.toString()
            if (pin == "1234") {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                binding.etPin.error = getString(R.string.error_incorrect_password)
            }
        }
    }
}
