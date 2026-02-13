package com.example.broadcastrecieverpracticing

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.widget.Toast

class AirPlaneModeChecking:BroadcastReceiver (){

    //The OnReceive method is overridden from the BroadCastReceiver class to do some actions when the System catch the changes of our broadcast,
    // in our case Airplane mode
    override fun onReceive(ourContext: Context?, ourIntent: Intent?) {

        // Creating a variable that hold the intent comes from the system and set its value to false by default
        val AirplanemodeisEnabled = ourIntent?.getBooleanExtra("state",false) ?: return



        //conditions and what we should do if the BroadcastReceiver receive an Intent from the system
        if (AirplanemodeisEnabled) {
            Toast.makeText(ourContext,"Airplane mode is enebled",Toast.LENGTH_LONG).show()

            //Catching ourContext variable from the OnReceive method which contains the Context where we should do these things above when the system send the BroadCastReceiver
            ourContext as MainActivity
            ourContext.tv.text="Airplane mode is enebled"

//            ourContext as MainActivity2
//            ourContext.tv1.text="Airplane mode is enebled"
//            ourContext.tv2.text=""

        } else{
            Toast.makeText(ourContext,"Airplane mode is Disabled",Toast.LENGTH_LONG).show()
            ourContext as MainActivity
            ourContext.tv.text="Airplane mode is disabled"



//            ourContext as MainActivity2
//            ourContext.tv2.text="Airplane mode is disabled"
//            ourContext.tv1.text=""
        }

    }

}