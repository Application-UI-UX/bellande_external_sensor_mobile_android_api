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
package com.bellande_api.bellande_lidar;

import java.io.IOException;
import retrofit2.Response;

public class bellande_lidar_service {
    private final bellande_lidar_api lidarApi;
    private final String apiAccessKey;
    private final String lidarEndpoint;

    public bellande_lidar_service(String apiUrl, String lidarEndpoint, String apiAccessKey, bellande_lidar_api lidarApi) {
        this.lidarApi = lidarApi;
        this.apiAccessKey = apiAccessKey;
        this.lidarEndpoint = lidarEndpoint;
    }

    public bellande_lidar_api.BellandeResponse sendLidarData(String lidarData, String connectivityPasscode) throws IOException {
        bellande_lidar_api.RequestBody requestBody = new bellande_lidar_api.RequestBody(connectivityPasscode, "send_data", lidarData);
        Response<bellande_lidar_api.BellandeResponse> response = lidarApi.sendLidarData(lidarEndpoint, requestBody, apiAccessKey).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Failed to send LiDAR data: " + response.code() + " - " + response.message());
        }
        return response.body();
    }

    public String receiveLidarData(String connectivityPasscode) throws IOException {
        bellande_lidar_api.RequestBody requestBody = new bellande_lidar_api.RequestBody(connectivityPasscode, "receive_data", null);
        Response<bellande_lidar_api.BellandeResponse> response = lidarApi.receiveLidarData(lidarEndpoint, requestBody, apiAccessKey).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Failed to receive LiDAR data: " + response.code() + " - " + response.message());
        }
        return response.body().getLidarData();
    }
}