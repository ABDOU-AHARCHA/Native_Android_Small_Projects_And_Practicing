package com.example.weatherapp

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import com.loopj.android.http.RequestParams
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    val APP_ID = "cb96f0d033deb03547e4eff4a7e7d88f"
    val WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather"

    val MIN_TIME: Long = 5000
    val MIN_DISTANCE = 1000f
    val REQUEST_CODE = 101

    var Location_Provider = LocationManager.GPS_PROVIDER

    var mLocationManager: LocationManager? = null
    var mLocationListner: LocationListener? = null

    var weatherState: TextView? = null
    var Temperature: TextView? = null
    var mweatherIcon: ImageView? = null
    var mCityFinder: RelativeLayout? = null
    var NameofCity: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        weatherState = findViewById(R.id.weatherCondition)
        Temperature = findViewById(R.id.temperature)
        mweatherIcon = findViewById(R.id.weatherIcon)
        mCityFinder = findViewById(R.id.cityFinder)
        NameofCity = findViewById(R.id.cityName)

        mCityFinder!!.setOnClickListener() {
            val intent = Intent(this, CityFinder::class.java)
            startActivity(intent)
        }
    }


    override fun onResume() {
        super.onResume()
        val mIntent = intent
        val city = mIntent.getStringExtra("City")
        if (city != null) {
            getWeatherForNewCity(city)
        } else {
            GetWeatherForCurrentLocation()
        }
    }


    private fun getWeatherForNewCity(city: String) {
        val params = RequestParams()
        params.put("q", city)
        params.put("appid", APP_ID)
        letsdoSomeNetworking(params)
    }

    fun GetWeatherForCurrentLocation() {
        mLocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        mLocationListner = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                var Latitude: String = (location.getLatitude()).toString()
                var Longitude: String = (location.getLongitude()).toString()
                var params = RequestParams()
                params.put("lat", Latitude)
                params.put("lon", Longitude)
                params.put("appid", APP_ID)
                letsdoSomeNetworking(params)
            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}

            override fun onProviderEnabled(provider: String) {}

            override fun onProviderDisabled(provider: String) {}
        }

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE
            )
            return
        }

        mLocationManager!!.requestLocationUpdates(
            Location_Provider,
            MIN_TIME,
            MIN_DISTANCE,
            mLocationListner as LocationListener
        )
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Location get Succesffully", Toast.LENGTH_SHORT).show();
                GetWeatherForCurrentLocation();
            } else {
                //user denied the permission
            }
        }
    }


    fun letsdoSomeNetworking(params: RequestParams) {
        val client = AsyncHttpClient()
        client.get(WEATHER_URL, params, object : JsonHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out cz.msebera.android.httpclient.Header>?,
                response: JSONObject
            ) {
                val weatherD: WeatherData? = WeatherData.fromJson(response)
                if (weatherD != null) {
                    updateUI(weatherD)
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out cz.msebera.android.httpclient.Header>?,
                throwable: Throwable?,
                errorResponse: JSONObject?
            ) {
            }
        })
    }


    private fun updateUI(weather: WeatherData) {
        Temperature?.text = weather.getmTemperature()
        NameofCity?.text = weather.getMcity()
        weatherState?.text = weather.getmWeatherType()
        val resourceID = resources.getIdentifier(weather.getMicon(), "drawable", packageName)
        mweatherIcon?.setImageResource(resourceID)
    } }



