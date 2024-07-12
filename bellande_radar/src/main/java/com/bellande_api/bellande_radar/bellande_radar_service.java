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
package com.bellande_api.bellande_radar;

import java.io.IOException;
import retrofit2.Response;

public class bellande_radar_service {
    private final bellande_radar_api radarApi;
    private final String apiAccessKey;
    private final String radarEndpoint;

    public bellande_radar_service(String apiUrl, String radarEndpoint, String apiAccessKey, bellande_radar_api radarApi) {
        this.radarApi = radarApi;
        this.apiAccessKey = apiAccessKey;
        this.radarEndpoint = radarEndpoint;
    }

    public bellande_radar_api.BellandeResponse sendRadarData(String radarData, String connectivityPasscode) throws IOException {
        bellande_radar_api.RequestBody requestBody = new bellande_radar_api.RequestBody(connectivityPasscode, "send_data", radarData);
        Response<bellande_radar_api.BellandeResponse> response = radarApi.sendRadarData(radarEndpoint, requestBody, apiAccessKey).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Failed to send Radar data: " + response.code() + " - " + response.message());
        }
        return response.body();
    }

    public String receiveLidarData(String connectivityPasscode) throws IOException {
        bellande_radar_api.RequestBody requestBody = new bellande_radar_api.RequestBody(connectivityPasscode, "receive_data", null);
        Response<bellande_radar_api.BellandeResponse> response = radarApi.receiveRadarData(radarEndpoint, requestBody, apiAccessKey).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Failed to receive Radar data: " + response.code() + " - " + response.message());
        }
        return response.body().getRadarData();
    }
}