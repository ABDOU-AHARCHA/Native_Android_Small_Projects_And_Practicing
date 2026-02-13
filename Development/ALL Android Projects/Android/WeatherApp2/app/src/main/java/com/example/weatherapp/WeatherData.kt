package com.example.weatherapp

import org.json.JSONException
import org.json.JSONObject
import java.lang.Math.rint


class WeatherData {

    private var mTemperature: String? = null
    private var micon: String? = null
    private var mcity: String? = null
    private var mWeatherType: String? = null
    private var mCondition = 0

    companion object {
        @JvmStatic
        fun fromJson(jsonObject: JSONObject): WeatherData? {
            return try {
                val weatherD = WeatherData()
                weatherD.mcity = jsonObject.getString("name")
                weatherD.mCondition = jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id")
                weatherD.mWeatherType = jsonObject.getJSONArray("weather").getJSONObject(0).getString("main")
                weatherD.micon = updateWeatherIcon(weatherD.mCondition)
                val tempResult = jsonObject.getJSONObject("main").getDouble("temp") - 273.15
                val roundedValue = rint(tempResult).toInt()
                weatherD.mTemperature = roundedValue.toString()
                weatherD
            } catch (e: JSONException) {
                e.printStackTrace()
                null
            }
        }

        private fun updateWeatherIcon(condition: Int): String {
            return when {
                condition in 0..300 -> "thunderstrom1"
                condition in 300..500 -> "lightrain"
                condition in 500..600 -> "shower"
                condition in 600..700 -> "snow2"
                condition in 701..771 -> "fog"
                condition in 772..800 -> "overcast"
                condition == 800 -> "sunny"
                condition in 801..804 -> "cloudy"
                condition in 900..902 -> "thunderstrom1"
                condition == 903 -> "snow1"
                condition == 904 -> "sunny"
                condition in 905..1000 -> "thunderstrom2"
                else -> "dunno"
            }
        }
    }

    fun getmTemperature(): String? {
        return "$mTemperature°C"
    }

    fun getMicon(): String? {
        return micon
    }

    fun getMcity(): String? {
        return mcity
    }

    fun getmWeatherType(): String? {
        return mWeatherType
    }
}
