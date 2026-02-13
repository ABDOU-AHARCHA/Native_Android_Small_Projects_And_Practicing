package com.example.broadcastrecieverpracticing

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var button:Button
    lateinit var tv:TextView
    //declaring an AirPlaneModeChecking globally and initializing it in the OnCreate to use it on the OnStop fun to avoid memory leak
    lateinit var reciever:AirPlaneModeChecking



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        tv = findViewById<TextView>(R.id.textView)

        //Initializing the receiver parameter by our BroadCastReceiver
        reciever = AirPlaneModeChecking()

        //telling the system to Catch the AirPlane Mode and register it with our BroadCastReceiver
        IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED).also {
            registerReceiver(reciever, it)
        }

        //Intent to move to the Activity 2
        button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
    }



    /*We override the OnStop method to avoid the memory leak by using unregisterReceiver,
    so the system will unregister that BroadCastReceiver when the activity or the app is not running (in the OnStop mode)*/
    override fun onStop() {
        super.onStop()
        unregisterReceiver(reciever)
    }
    //Testing pushing
}