package com.example.intentservicepracticing

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btnStop = findViewById<Button>(R.id.button)
        var btnStart = findViewById<Button>(R.id.button2)
        var thestate = findViewById<TextView>(R.id.textView)


        btnStart.setOnClickListener {
            Intent(this, TestingIntentService::class.java).also {
                startActivity(it)
                thestate.text= "service is running"

            }
        }

        btnStop.setOnClickListener {
            TestingIntentService.StopService()
            thestate.text= "service stopped..."

        }

    }
}