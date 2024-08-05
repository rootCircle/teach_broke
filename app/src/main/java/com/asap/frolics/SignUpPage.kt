package com.asap.frolics

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NO_HISTORY
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import kotlinx.android.synthetic.main.activity_signup_page.*
import com.google.firebase.ktx.Firebase as Firebase

class SignUpPage : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var progressSignUp:ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_page)
        auth = Firebase.auth
        progressSignUp = findViewById(R.id.progressBarSignUp)
        SignUpRegisterButton.setOnClickListener {
            signUp()
        }
        tvSignUpLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }


    private fun signUp(){
        val name:String = etSignUpName.text.toString()
        val email:String = etSignUpEmail.text.toString()
        val password:String = etSignUpPassword.text.toString()
        val rePassword:String = etSignUpRePassword.text.toString()
        if(validateSignUp(name, email,password,rePassword)) {
            progressSignUp.visibility=View.VISIBLE
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        progressSignUp.visibility = View.GONE
                        val user=auth.currentUser
                        updateUI(user)
                        finish()
                    } else {
                        progressSignUp.visibility = View.GONE
                        Toast.makeText(baseContext,"SignUp Failed! ${task.exception}",Toast.LENGTH_LONG).show()
                        updateUI(null)
                    }
                }
        }
    }


    private fun updateUI(currentUser: FirebaseUser?){
        if (currentUser!=null){
            val intent = Intent(this, HomePage::class.java).apply {
                addFlags(FLAG_ACTIVITY_NO_HISTORY)
            }
            startActivity(intent)
            finish()
        }
    }

    private fun validateSignUp(name:String, email:String, password:String, rePass:String):Boolean {
        var retVal = validateName(name)
        retVal=validateEmail(email) && retVal
        retVal=validatePasswords(password, rePass) && retVal
        return retVal
    }

    private fun validateEmail(email:String):Boolean{
        if(email.isEmpty()) etSignUpEmail.error = "Field can't be empty"
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) etSignUpEmail.error = "Invalid Email!"
        else return true
        return false
    }

    private fun validateName(name:String):Boolean{
        if(name.isEmpty()){
            etSignUpName.error = "Field can't be empty"
            return false
        }
        return true
    }

    private fun validatePass(password: String):Boolean{
        when {
            password.isEmpty() -> {
                etSignUpPassword.error = "Field can't be empty"
            }
            password.length<6 -> {
                etSignUpPassword.error = "Weak Password"
            }
            else -> {
                return true
            }
        }
        return false
    }


    private fun validatePasswords(password: String, rePass: String):Boolean{
        return if(validatePass(password)) {
            when {
                rePass.isEmpty() -> {
                    etSignUpRePassword.error = "Field can't be empty"
                }
                rePass != password -> {
                    etSignUpRePassword.error = "Password Mismatch"
                }
                else -> {
                    return true
                }
            }
            false
        } else{
            false
        }
    }
}