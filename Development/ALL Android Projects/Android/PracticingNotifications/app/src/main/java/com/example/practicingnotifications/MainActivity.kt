package com.example.practicingnotifications

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    var CHANNEL_ID = "OurNotifications"
    var NOTIFICATION_ID=0

    lateinit var Button1:Button

    lateinit var intent1:Intent
    lateinit var pendingIntent: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Button1= findViewById<Button>(R.id.button)
        CreateNotificationChannel()
        OnClickNotificationRespond()
        CreateNotificationCustomization()

    }






    //Creating Notification channel that required; to give the user the preferences of customizing their notification setting
    fun CreateNotificationChannel() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            val name = "The APP From Aharcha"
            val descriptionText = "This is the channel of messages in your app"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID,name,importance).apply {
                description=descriptionText
            }
            var notificationmanager:NotificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationmanager.createNotificationChannel(channel)
        }

    }

    //Using Intent and IntentPending making the notification responds to the user click...
    fun OnClickNotificationRespond(){
        intent1 = Intent(this,MainActivity::class.java)
        pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)
    }

    //Create notification customization that build our notification design and some behaviours like .setAutoCancel() or Priority...
    @SuppressLint("MissingPermission")
    fun CreateNotificationCustomization(){

        val notification = NotificationCompat.Builder(applicationContext,CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_stat_name)
            .setContentTitle("My Notification")
            .setContentText("Hello World!")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        var notificationmanager2 = NotificationManagerCompat.from(applicationContext)

        Button1.setOnClickListener(View.OnClickListener {
            notificationmanager2.notify(NOTIFICATION_ID,notification)
        })
    }



}