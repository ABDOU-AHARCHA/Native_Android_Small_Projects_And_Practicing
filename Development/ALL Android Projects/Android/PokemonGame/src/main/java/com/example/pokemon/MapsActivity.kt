package com.example.pokemon

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build.VERSION
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.pokemon.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        LoadPockemons()
        CheckPermession()
    }

    val AccessLocation = 1

    fun CheckPermession(){
        if(VERSION.SDK_INT>=23){
            if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),AccessLocation)
                return
            }
        }
        GetUserLocation()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            AccessLocation -> {
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    GetUserLocation()
                }else{
                    Toast.makeText(this,"Location access is denyed",Toast.LENGTH_LONG).show();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    @SuppressLint("MissingPermission")
    fun GetUserLocation(){

        var myLocation=MyLocationListener()
        var locationManager=getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3,3f,myLocation)
        Toast.makeText(this,"Location access is accepted",Toast.LENGTH_LONG).show();
        val myThread= MyThread()
        myThread.start()
    }







    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }


    var MyPower:Double = 0.0
    var ListPockemons = ArrayList<Pokemon>()

    fun LoadPockemons (){
        ListPockemons.add(Pokemon(R.drawable.charmander,
            "Charmander", "Charmander living in japan", 55.0, 32.682407, -4.730811))
        ListPockemons.add(Pokemon(R.drawable.bulbasaur,
            "Bulbasaur", "Bulbasaur living in usa", 90.5, 32.678501, -4.726897))
        ListPockemons.add(Pokemon(R.drawable.squirtle,
            "Squirtle", "Squirtle living in iraq", 33.5, 32.679841, -4.734762))
    }






    var myLocation:Location?=null
    inner class MyLocationListener:LocationListener {
        constructor(){
            myLocation= Location("me")
            myLocation!!.longitude=0.0
            myLocation!!.latitude=0.0
        }
        override fun onLocationChanged(p0:Location) {
            myLocation=p0;
        }
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }



    var oldlocation:Location?=null
    inner class MyThread:Thread{
        constructor():super(){
            oldlocation= Location("oldLoctaion")
            oldlocation!!.longitude=0.0
            oldlocation!!.latitude=0.0
        }
        override fun run() {

            while(true){
                try{
                    if(oldlocation!!.distanceTo(myLocation!!)==0f){
                        continue
                    }
                    oldlocation=myLocation

                    runOnUiThread {
                        mMap!!.clear()
                        val Hawai = LatLng(myLocation!!.latitude,myLocation!!.longitude)
                        mMap.addMarker(MarkerOptions()
                            .position(Hawai)
                            .title("Here is my Home !").snippet("This is my location ")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.mario)))
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(Hawai))


                        //show Pockemons
                        for (i in 0..ListPockemons.size -1){
                            var newPockemon = ListPockemons[i]

                            if(newPockemon.IsCatch == false){

                                val PokemonLocation = LatLng(newPockemon.location!!.latitude,newPockemon.location!!.longitude)
                                mMap.addMarker(MarkerOptions()
                                    .position(PokemonLocation)
                                    .title(newPockemon.name)
                                    .snippet(newPockemon.des+ "Power:"+newPockemon.power)
                                    .icon(BitmapDescriptorFactory.fromResource(newPockemon.image!!)))
                                //catching pockemon
                                if (myLocation!!.distanceTo(newPockemon.location!!)<2){
                                    newPockemon.IsCatch=true
                                    MyPower += newPockemon.power!!
                                    ListPockemons[i]=newPockemon
                                    Toast.makeText(applicationContext,
                                            "You catch new pockemon your new pwoer is " +MyPower,
                                        Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                    }
                    Thread.sleep(1000)

                }catch (Ex:Exception){

                }
            }

        }
    }
}
