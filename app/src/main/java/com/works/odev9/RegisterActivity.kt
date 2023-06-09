package com.works.odev9

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    lateinit var editTxtRegisterEmail : EditText
    lateinit var editTxtRegisterPassword : EditText
    lateinit var editTxtRegisterPassword2 : EditText
    lateinit var btnRegister2 : Button

    lateinit var user : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        editTxtRegisterEmail = findViewById(R.id.editTxtRegisterEmail)
        editTxtRegisterPassword = findViewById(R.id.editTxtRegisterPassword)
        editTxtRegisterPassword2 = findViewById(R.id.editTxtRegisterPassword2)
        btnRegister2 = findViewById(R.id.btnRegister2)

        user = FirebaseAuth.getInstance()

        btnRegister2.setOnClickListener {
            val email = editTxtRegisterEmail.text.toString()
            val password = editTxtRegisterPassword.text.toString()
            val password2 = editTxtRegisterPassword2.text.toString()
            if (email == "" || password == "" || password2 == "")
            {
                Toast.makeText(this,"Lütfen alanları eksiksiz doldurunuz.",Toast.LENGTH_LONG).show()
            }
            else if (password != password2) {
                Toast.makeText(this,"Parolalarınız birbirleriyle uyuşmuyor.Kontrol ediniz.",Toast.LENGTH_LONG).show()
            }
            else
            {
                user.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
                    Toast.makeText(this,"Kullanıcı başarılı şekilde kayıt oldu.",Toast.LENGTH_LONG).show()
                    val intent = Intent(this,LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }.addOnFailureListener {
                    Toast.makeText(this,it.message,Toast.LENGTH_LONG).show()
                }
            }

        }
    }
}