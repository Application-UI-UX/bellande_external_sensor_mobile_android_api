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

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface bellande_lidar_api {
    @POST
    Call<BellandeResponse> sendLidarData(@Url String url,
                                         @Body RequestBody body,
                                         @Header("Bellande-Framework-Access-Key") String apiKey);

    @POST
    Call<BellandeResponse> receiveLidarData(@Url String url,
                                            @Body RequestBody body,
                                            @Header("Bellande-Framework-Access-Key") String apiKey);

    class RequestBody {
        private final String connectivityPasscode;
        private final String action;
        private final String data;

        public RequestBody(String connectivityPasscode, String action, String data) {
            this.connectivityPasscode = connectivityPasscode;
            this.action = action;
            this.data = data;
        }
    }

    class BellandeResponse {
        private String status;
        private String message;
        private String lidarData;

        public String getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }

        public String getLidarData() {
            return lidarData;
        }
    }
}