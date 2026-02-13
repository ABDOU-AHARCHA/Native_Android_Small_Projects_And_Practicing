# 🌦️ WeatherApp

## 📝 Overview

A dynamic Android weather application that provided real-time weather updates based on the user's current GPS location or a manual city search.

---

## 🚀 Key Features

### 📍 Dual Location Support
- Automatically detected weather using GPS  
- Allowed users to search manually for specific cities  

### 🌡️ Real-time Weather Data
- Fetched temperature (Celsius)  
- Displayed weather conditions  
- Showed city names  
- Retrieved data using the OpenWeatherMap API  

### 🎨 Dynamic UI
- Automatically updated weather icons  
- Displayed conditions such as sunny, cloudy, stormy, etc.  
- Adapted icons based on weather condition codes  

### 🔄 Smooth Navigation
- Dedicated `CityFinder` activity for searching  
- Main dashboard activity for weather display  

---

## 🛠️ Technologies Used

- **Language:** Kotlin  
- **Networking:** AsyncHttpClient & JsonHttpResponseHandler  
- **Location Services:** Android LocationManager (GPS_PROVIDER)  
- **API:** OpenWeatherMap API  

---

## 📂 Project Structure

- `MainActivity.kt` → Handles core logic, location permissions, and networking  
- `WeatherData.kt` → Model class for JSON parsing and weather icon selection  
- `CityFinder.kt` → Manages city search and user input  
- `activity_main.xml` & `activity_city_finder.xml` → Define the modern dark-themed UI  
- `AndroidManifest.xml` → Configures internet and high-accuracy location permissions  

---

## 🎯 Purpose of the Project

This project was developed to practice:

- Working with third-party APIs  
- Handling runtime location permissions  
- JSON data parsing  
- Multi-activity navigation  
- Dynamic UI updates based on data  

---

### 👨‍💻 Author
Abdelilah Aharcha  
Mobile Developer
