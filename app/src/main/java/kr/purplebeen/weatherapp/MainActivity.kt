package kr.purplebeen.weatherapp

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import pub.devrel.easypermissions.EasyPermissions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    //Retrofit Setting
    val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://api.openweathermap.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    val weatherService: WeatherService = retrofit.create(WeatherService::class.java)

    //Location Setting
    var lon: Double = 0.0
    var lat: Double = 0.0
    lateinit var call: Call<WeatherInfo>

    lateinit var locationManager : LocationManager

    val mLocationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            lon = location.longitude
            lat = location.latitude
            Log.d("test", location.longitude.toString())
            call = weatherService.getWeather(lat, lon, "metric", "b1947fd3ccbca568608f4bd32ddfd5b4")
            loadData()
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        }

        override fun onProviderEnabled(provider: String?) {
        }

        override fun onProviderDisabled(provider: String?) {
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        updateLocation()

        refreshButton.setOnClickListener {
            updateLocation()
        }
    }

    fun updateLocation() {
        if(EasyPermissions.hasPermissions(this@MainActivity, android.Manifest.permission.ACCESS_COARSE_LOCATION) &&
                EasyPermissions.hasPermissions(this@MainActivity, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            try {
                Log.d("test", "start")
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0.1f, mLocationListener)
            } catch (e : SecurityException) {
                Toast.makeText(applicationContext, "권한이 필요합니다!" , Toast.LENGTH_LONG).show()
                requestPermissions()
            }
        } else {
            requestPermissions()
        }
    }

    fun requestPermissions() {
        EasyPermissions.requestPermissions(this@MainActivity, "현재 위치 획득을 위해서는 권한이 필요합니다", 300, android.Manifest.permission.ACCESS_FINE_LOCATION)
    }
    fun loadData() {
        call.enqueue(object : Callback<WeatherInfo> {
            override fun onResponse(call: Call<WeatherInfo>?, response: Response<WeatherInfo>) {
                Log.d("test", response.body().toString())
                icon.loadUrl("http://openweathermap.org/img/w/" + response.body()!!.weather!![0].icon + ".png")
                temp.text = response.body()!!.main!!.temp + "°C"
                cityName.text = response.body()!!.name
                status.text = response.body()!!.weather!![0].main
                description.text = response.body()!!.weather!![0].description
                locationManager.removeUpdates(mLocationListener)
            }

            override fun onFailure(call: Call<WeatherInfo>?, t: Throwable?) {
                Toast.makeText(applicationContext, "알 수 없는 오류로 인해 데이터를 가져올 수 없습니다!", Toast.LENGTH_LONG).show()
                Log.e("error", t!!.message)
               }
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        requestPermissions()
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
//        updateLocation()
    }

    fun ImageView.loadUrl(url : String) {
        Glide.with(applicationContext).load(url).into(this)
    }
}

