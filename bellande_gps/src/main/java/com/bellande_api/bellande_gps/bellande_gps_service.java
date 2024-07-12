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

import java.io.IOException;
import retrofit2.Response;

public class bellande_gps_service {
    private final bellande_gps_api gpsApi;
    private final String apiAccessKey;
    private final String gpsEndpoint;

    public bellande_gps_service(String apiUrl, String gpsEndpoint, String apiAccessKey, bellande_gps_api gpsApi) {
        this.gpsApi = gpsApi;
        this.apiAccessKey = apiAccessKey;
        this.gpsEndpoint = gpsEndpoint;
    }

    public bellande_gps_api.BellandeResponse sendGpsData(String gpsData, String connectivityPasscode) throws IOException {
        bellande_gps_api.RequestBody requestBody = new bellande_gps_api.RequestBody(connectivityPasscode, gpsData);
        Response<bellande_gps_api.BellandeResponse> response = gpsApi.sendGpsData(gpsEndpoint, requestBody, apiAccessKey).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Failed to send GPS data: " + response.code() + " - " + response.message());
        }
        return response.body();
    }
}
