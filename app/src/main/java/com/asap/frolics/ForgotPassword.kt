package com.asap.frolics

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPassword : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        var regMail:String
        auth = Firebase.auth
        etForgotButton.setOnClickListener {
            regMail = etForgotEmail.text.toString()
            if (validateForgotEmail(regMail)) {
                auth.sendPasswordResetEmail(regMail).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(baseContext,"Sent Reset Email Successfully", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    }
                    else {
                        Toast.makeText(baseContext, "Process failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        ibForgotBack.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
    private fun validateForgotEmail(email:String):Boolean{
        var ret = false
        if (email.isEmpty()) etForgotEmail.error = "Field can't be empty!"
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) etForgotEmail.error = "Invalid Email!"
        else ret = true
        return ret
    }
}