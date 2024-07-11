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
package com.bellande_api.bellande_camera;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface bellande_camera_api {
    @Multipart
    @POST
    Call<BellandeResponse> streamVideo(@Url String url, 
                                       @Part MultipartBody.Part video,
                                       @Part("connectivityPasscode") String connectivityPasscode,
                                       @Header("Bellande-Framework-Access-Key") String apiKey);

    @Streaming
    @POST
    Call<ResponseBody> receiveVideoStream(@Url String url, 
                                          @Body RequestBody body,
                                          @Header("Bellande-Framework-Access-Key") String apiKey);

    class RequestBody {
        private final String connectivityPasscode;
        private final String action;

        public RequestBody(String connectivityPasscode, String action) {
            this.connectivityPasscode = connectivityPasscode;
            this.action = action;
        }

        public String getConnectivityPasscode() {
            return connectivityPasscode;
        }

        public String getAction() {
            return action;
        }
    }

    class BellandeResponse {
        private String status;
        private String message;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
