package com.example.androidproject

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_welcome.*

class Welcome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        message.setText("Student ID : 24083814")

        buttonGetStarted.setOnClickListener(){
            val navigateOtherActivity = Intent(this, MainActivity::class.java)
            startActivity(navigateOtherActivity)
        }
    }
}