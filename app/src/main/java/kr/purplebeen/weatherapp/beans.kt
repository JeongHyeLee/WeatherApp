package kr.purplebeen.weatherapp

import com.google.gson.annotations.SerializedName

/**
 * Created by baehy on 2018. 1. 29..
 */

class Cooord {
    var ion : Double = 0.0
    var lat : Double = 0.0
}

class Weather {
    var id : Int = 0
    var main : String? = null
    var description : String? = null
    var icon: String? = null
}



class Main {
    var temp : String? = null
    var pressure : Int = 0
    var humidity : Int = 0
    @SerializedName("temp_min")
    var minTemp : Int = 0
    @SerializedName("temp_max")
    var maxTemp : Int = 0
}

class Wind {
    var speed : Double = 0.0
    var deg : Double = 0.0
}

class Clouds {
    var all : Int = 0
}

class Data {
    var type : Int = 0
    var id : Int = 0
    var message : Double = 0.0
    var country : String? = null
    var sunrise : Int = 0
    var sunset : Int = 0
}

class WeatherInfo {
    var coord : Cooord? = null
    var weather : List<Weather>? = null
    var base : String? = null
    var main : Main? = null
    var visibility : Int = 0
    var wind : Wind? = null
    var clouds : Clouds? = null
    var dt : Int = 0
    var sys : Data? = null
    var id : Int = 0
    var name : String? = null
    var cod : Int = 0
}