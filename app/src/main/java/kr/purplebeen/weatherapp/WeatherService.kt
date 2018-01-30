package kr.purplebeen.weatherapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by baehy on 2018. 1. 29..
 */

interface WeatherService {
    @GET("/data/2.5/weather")
    fun getWeather(@Query("lat") lat : Double, @Query("lon") lon : Double, @Query("units") units : String,
                   @Query("APPID") APPID : String) : Call<WeatherInfo>
}