package com.medialink.myviewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception
import java.text.DecimalFormat

class MainViewModel: ViewModel() {
    private val listWeather = MutableLiveData<ArrayList<WeatherItems>>()

    fun setWeather(cities: String) {
        val listItem = ArrayList<WeatherItems>()
        val apiKey = "f534a61d3db189a098634a352b800871"
        val url = "http://api.openweathermap.org/data/2.5/group?id=${cities}&units=metric&appid=${apiKey}"

        val client = AsyncHttpClient()
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                try {
                    val result = responseBody?.let { String(it) }
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("list")

                    for (i in 0 until list.length()) {
                        val weather = list.getJSONObject(i)
                        val weatherItem = WeatherItems()
                        with(weatherItem) {
                            id = weather.getInt("id")
                            name = weather.getString("name")
                            currentWeather = weather.getJSONArray("weather").getJSONObject(0).getString("main")
                            description = weather.getJSONArray("weather").getJSONObject(0).getString("description")

                            val tempInKelvin = weather.getJSONObject("main").getDouble("temp")
                            val tempInCelsius = tempInKelvin - 273
                            temperature = DecimalFormat("##.##").format(tempInKelvin)
                            listItem.add(weatherItem)
                        }
                    }

                    //set data ke adapter
                    //adapter.setData(listItem)
                    //showLoading(false)
                    listWeather.postValue(listItem)
                } catch (e: Exception) {
                    Log.e("TAG", "onSuccess: ${e.message.toString()}")
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d("TAG", "onFailure : ${error?.message.toString()}")
            }
        })
    }

    fun getWeather(): LiveData<ArrayList<WeatherItems>> {
        return listWeather
    }
}