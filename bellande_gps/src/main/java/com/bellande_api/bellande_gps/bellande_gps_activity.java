/**
 * Copyright (C) 2024 Bellande Application UI UX Research Innovation Center, Ronaldson Bellande
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.bellande_api.bellande_gps;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class bellande_gps_activity extends AppCompatActivity implements LocationListener {
    protected bellande_gps_service gpsService;
    protected String connectivityPasscode;
    private LocationManager locationManager;
    private TextView locationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_gps);

        //locationText = findViewById(R.id.location_text);
        initializeService();
        setupLocationManager();
    }

    private void initializeService() {
        Map<String, Object> config = loadConfigFromFile(this);
        String apiUrl = (String) config.get("url");
        Map<String, String> endpointPaths = (Map<String, String>) config.get("endpoint_path");
        String gpsEndpoint = endpointPaths.get("gps");
        String apiAccessKey = (String) config.get("Bellande_Framework_Access_Key");
        this.connectivityPasscode = (String) config.get("connectivity_passcode");

        bellande_gps_api gpsApi = new Retrofit.Builder()
                .baseUrl(apiUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(bellande_gps_api.class);

        gpsService = new bellande_gps_service(apiUrl, gpsEndpoint, apiAccessKey, gpsApi);
    }

    @SuppressLint("MissingPermission")
    private void setupLocationManager() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    @SuppressLint("LongLogTag")
    private Map<String, Object> loadConfigFromFile(Context context) {
        try {
            InputStream inputStream = context.getAssets().open("configs.json");
            InputStreamReader reader = new InputStreamReader(inputStream);
            Type type = new TypeToken<Map<String, Object>>(){}.getType();
            return new Gson().fromJson(reader, type);
        } catch (IOException e) {
            Log.e("bellande_gps_activity", "Error reading config file: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        String locationData = "Latitude: " + location.getLatitude() + ", Longitude: " + location.getLongitude();
        locationText.setText(locationData);
        
        new Thread(() -> {
            try {
                gpsService.sendGpsData(locationData, connectivityPasscode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onProviderDisabled(String provider) {}
}
