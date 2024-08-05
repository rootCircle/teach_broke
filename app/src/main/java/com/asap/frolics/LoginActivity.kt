package com.asap.frolics

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login_activity.*


class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var progress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_activity)
        auth = Firebase.auth
        progress = findViewById(R.id.progressBarLogin)
        // Handling clicks
        loginButton.setOnClickListener {
            login()
        }
        tvLoginRegister.setOnClickListener{
            startActivity(Intent(this, SignUpPage::class.java))
            finish()
        }
        tvLoginForgotPass.setOnClickListener{
            startActivity(Intent(this, ForgotPassword::class.java))
            finish()
        }
    }

    private fun validate(mail:String, password:String) :Boolean{
        var ret = false
        if (mail.isEmpty()) {
            etLoginEmail.error = "Field can't be empty!"
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            etLoginEmail.error = "Invalid Email!"
        }
        else{
            ret = true
        }
        if (password.isEmpty()){
            etLoginPassword.error = "Field can't be empty!"
            ret=false
        }
        return ret
    }


    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }


    private fun updateUI(currentUser: FirebaseUser?){
        if (currentUser!=null){
            val intent = Intent(this, HomePage::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            }
            startActivity(intent)
            finish()
        }
    }


    private fun login(){
        val email:String = etLoginEmail.text.toString()
        val password:String = etLoginPassword.text.toString()

        if(validate(email,password)) {
            progress.visibility = View.VISIBLE
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        progress.visibility = View.GONE
                        val user=auth.currentUser
                        updateUI(user)
                        finish()
                    } else {
                        progress.visibility = View.GONE
                        Toast.makeText(baseContext, """Authentication failed.${task.exception}""",
                            Toast.LENGTH_LONG).show()
                        updateUI(null)
                    }
                }
        }
    }
}
