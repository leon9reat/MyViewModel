package com.medialink.myviewmodeljava;

import android.os.Parcel;
import android.os.Parcelable;

public class WeatherItems implements Parcelable {
    private int id;
    private String name;
    private String currentWeather;
    private String description;
    private String temperature;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(String currentWeather) {
        this.currentWeather = currentWeather;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.currentWeather);
        dest.writeString(this.description);
        dest.writeString(this.temperature);
    }

    public WeatherItems() {
    }

    protected WeatherItems(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.currentWeather = in.readString();
        this.description = in.readString();
        this.temperature = in.readString();
    }

    public static final Parcelable.Creator<WeatherItems> CREATOR = new Parcelable.Creator<WeatherItems>() {
        @Override
        public WeatherItems createFromParcel(Parcel source) {
            return new WeatherItems(source);
        }

        @Override
        public WeatherItems[] newArray(int size) {
            return new WeatherItems[size];
        }
    };
}
