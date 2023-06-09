package com.works.odev9

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ResetPasswordActivity : AppCompatActivity() {

    lateinit var editTxtResetPasswordFromEmail: EditText
    lateinit var btnResetPassword: Button

    lateinit var user: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        editTxtResetPasswordFromEmail = findViewById(R.id.editTxtResetPasswordFromEmail)
        btnResetPassword = findViewById(R.id.btnResetPassword)
        user = FirebaseAuth.getInstance()

        val email = intent.getStringExtra("email")
        editTxtResetPasswordFromEmail.setText(email)

        btnResetPassword.setOnClickListener {
            val ResetEmail = editTxtResetPasswordFromEmail.text.toString()
            if (ResetEmail != "") {
                user.sendPasswordResetEmail(ResetEmail).addOnSuccessListener {
                    Toast.makeText(this, "Lütfen emailinizi kontrol ediniz", Toast.LENGTH_LONG)
                        .show()
                    finish()
                }.addOnFailureListener {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this,"Lütfen boşluğu doldurunuz.",Toast.LENGTH_LONG).show()
            }
        }
    }
}