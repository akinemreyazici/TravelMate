package com.works.odev9

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    lateinit var editTxtLoginEmail: EditText
    lateinit var editTxtLoginPassword: EditText
    lateinit var txtForgotPassword: TextView
    lateinit var btnLogin: Button
    lateinit var btnRegister: Button

    lateinit var user: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editTxtLoginEmail = findViewById(R.id.editTxTLoginEmail)
        editTxtLoginPassword = findViewById(R.id.editTxtLoginPassword)
        txtForgotPassword = findViewById(R.id.txtForgotPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnRegister = findViewById(R.id.btnRegister)

        user = FirebaseAuth.getInstance()

        btnLogin.setOnClickListener {
            val email = editTxtLoginEmail.text.toString()
            val password = editTxtLoginPassword.text.toString()

            if (email == "" || password == "") {
                Toast.makeText(
                    this,
                    "Lütfen bilgileri eksiksiz şekilde doldurunuz",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                user.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(
                            this,
                            "Kullanıcı başarıyla giriş yaptı",
                            Toast.LENGTH_LONG
                        ).show()
                        val intent = Intent(this,AppActivity::class.java)
                        startActivity(intent)
                        finish()

                    } else {
                        Toast.makeText(this, it.exception!!.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
        btnRegister.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }
        txtForgotPassword.setOnClickListener {
            val intent = Intent(this, ResetPasswordActivity::class.java)
            val email = editTxtLoginEmail.text.toString()
            if (email != "") {

                intent.putExtra("email", email)
            }
            startActivity(intent)
        }
    }
}