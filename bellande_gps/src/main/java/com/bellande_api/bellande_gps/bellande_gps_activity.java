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
import android.util.Log;
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

public class bellande_gps_activity extends AppCompatActivity {
    protected bellande_gps_service networkUsageService;
    protected String connectivityPasscode;

    public bellande_gps_activity(Context context) {
        Map<String, Object> config = loadConfigFromFile(context);
        String apiUrl = (String) config.get("url");
        Map<String, String> endpointPaths = (Map<String, String>) config.get("endpoint_path");
        String inputEndpoint = endpointPaths.get("input_data");
        String outputEndpoint = endpointPaths.get("output_data");
        String apiAccessKey = (String) config.get("Bellande_Framework_Access_Key");
        this.connectivityPasscode = (String) config.get("connectivity_passcode");

        bellande_gps_api networkUsageApi = new Retrofit.Builder()
                .baseUrl(apiUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(bellande_gps_api.class);

        networkUsageService = new bellande_gps_service(apiUrl, inputEndpoint, outputEndpoint, apiAccessKey, networkUsageApi);
    }

    @SuppressLint("LongLogTag")
    private Map<String, Object> loadConfigFromFile(Context context) {
        try {
            InputStream inputStream = context.getAssets().open("configs.json");
            InputStreamReader reader = new InputStreamReader(inputStream);
            Type type = new TypeToken<Map<String, Object>>(){}.getType();
            return new Gson().fromJson(reader, type);
        } catch (IOException e) {
            Log.e("bellande_gps_base_activity", "Error reading config file: " + e.getMessage());
        }
        return null;
    }
}
