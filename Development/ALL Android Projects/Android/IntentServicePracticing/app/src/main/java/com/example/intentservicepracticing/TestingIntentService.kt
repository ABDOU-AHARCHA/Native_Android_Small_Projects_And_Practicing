package com.example.intentservicepracticing

import android.app.IntentService
import android.content.Intent
import android.util.Log

class TestingIntentService:IntentService ("TestingIntentService"){

    init {

        instance = this


    }


    companion object{

        private lateinit var instance:TestingIntentService
        var isRunning = true

        fun StopService (){
            isRunning = false
//            Log.d("TestingTheIntentService....","Service Is Stopping....")
            instance.stopSelf()

        }



    }



    override fun onHandleIntent(p0: Intent?) {
        try {

            isRunning = true
            while (isRunning){
//                Log.d("TestingTheIntentService....","Service Is Running....")
                Thread.sleep(1000)

            }









        }

        catch (e: InterruptedException) {

            Thread.currentThread().interrupt()

        }
    }



}