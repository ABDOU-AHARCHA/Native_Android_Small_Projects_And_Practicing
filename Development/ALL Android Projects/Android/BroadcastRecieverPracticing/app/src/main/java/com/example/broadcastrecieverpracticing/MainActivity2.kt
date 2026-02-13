package com.example.broadcastrecieverpracticing

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity2 : AppCompatActivity() {




    lateinit var tv1: TextView
    lateinit var tv2: TextView

    //this is a variable used in case of the developer wants to enable or disable the Airplane mode in this second Activity (you can check the MainActivity1 to see)
    lateinit var reciever:AirPlaneModeChecking
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)


        reciever = AirPlaneModeChecking()


        IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED).also {
            registerReceiver(reciever, it)
        }

        tv1 = findViewById(R.id.textView1)
        tv2 = findViewById(R.id.textView2)









    }
}