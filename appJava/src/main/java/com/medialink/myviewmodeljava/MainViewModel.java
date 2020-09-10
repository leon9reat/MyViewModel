package com.medialink.myviewmodeljava;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;

public class MainViewModel extends ViewModel {
    private MutableLiveData<ArrayList<WeatherItems>> listWeather = new MutableLiveData<>();

    public LiveData<ArrayList<WeatherItems>> getListWeather() {
        return listWeather;
    }

    public void setListWeather(final String cities) {
        final ArrayList<WeatherItems> listItem = new ArrayList<>();
        String apiKey = "f534a61d3db189a098634a352b800871";
        String url = "http://api.openweathermap.org/data/2.5/group?id="
                + cities + "&units=metric&appid="
                + apiKey;

        AsyncHttpClient client = new AsyncHttpClient();
        Log.d("TAG", "onSuccess: "+url);
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                try {

                    Log.d("TAG", "onSuccess: "+result);
                    JSONObject responseObject = new JSONObject(result);

                    JSONArray list = responseObject.getJSONArray("list");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject weather = list.getJSONObject(i);
                        WeatherItems weatherItems = new WeatherItems();
                        weatherItems.setId(weather.getInt("id"));
                        weatherItems.setName(weather.getString("name"));
                        weatherItems.setCurrentWeather(weather.getJSONArray("weather").getJSONObject(0).getString("main"));
                        weatherItems.setDescription(weather.getJSONArray("weather").getJSONObject(0).getString("description"));
                        double tempInKelvin = weather.getJSONObject("main").getDouble("temp");
                        double tempInCelcius = tempInKelvin - 273;
                        weatherItems.setTemperature(new DecimalFormat("##.##").format(tempInKelvin));
                        listItem.add(weatherItems);
                    }

                    // set data to adapter
                    //adapter.setData(listItem);
                    //showLoading(false);
                    listWeather.postValue(listItem);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    Log.d("Exception", Objects.requireNonNull(e.getMessage()));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", Objects.requireNonNull(error.getMessage()));
            }
        });
    }
}
