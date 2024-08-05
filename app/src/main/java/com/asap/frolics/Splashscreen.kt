package com.asap.frolics

import android.content.Intent
import android.content.Intent.*
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class Splashscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        val background = object : Thread(){
            override fun run() {
                try{
                    Thread.sleep(5000)
                    val intent= Intent(baseContext, LoginActivity::class.java).apply {
                        addFlags(FLAG_ACTIVITY_NEW_TASK)
                        addFlags(FLAG_ACTIVITY_CLEAR_TOP)
                    }
                    startActivity(intent)
                }
                catch(e:Exception){
                    e.printStackTrace()
                }
            }
        }
        background.start()
    }
}