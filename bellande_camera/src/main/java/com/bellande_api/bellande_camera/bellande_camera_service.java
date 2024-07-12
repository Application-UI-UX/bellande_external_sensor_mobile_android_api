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
 **/
package com.bellande_api.bellande_camera;

import com.bellande_api.bellande_camera.bellande_camera_api;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class bellande_camera_service {
    private final bellande_camera_api cameraApi;
    private final String apiAccessKey;
    private final String streamEndpoint;

    public bellande_camera_service(String apiUrl, String streamEndpoint, String apiAccessKey, bellande_camera_api cameraApi) {
        this.cameraApi = cameraApi;
        this.apiAccessKey = apiAccessKey;
        this.streamEndpoint = streamEndpoint;
    }

    public bellande_camera_api.BellandeResponse streamVideo(File videoFile, String connectivityPasscode) throws IOException {
        RequestBody videoBody = RequestBody.create(MediaType.parse("video/*"), videoFile);
        MultipartBody.Part videoPart = MultipartBody.Part.createFormData("video", videoFile.getName(), videoBody);

        Response<bellande_camera_api.BellandeResponse> response = cameraApi.streamVideo(streamEndpoint, videoPart, connectivityPasscode, apiAccessKey).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Failed to stream video: " + response.code() + " - " + response.message());
        }
        return response.body();
    }

    public InputStream receiveVideoStream(String connectivityPasscode) throws IOException {
        bellande_camera_api.RequestBody requestBody = new bellande_camera_api.RequestBody(connectivityPasscode, "receive_stream");
        Response<ResponseBody> response = cameraApi.receiveVideoStream(streamEndpoint, requestBody, apiAccessKey).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Failed to receive video stream: " + response.code() + " - " + response.message());
        }
        return response.body().byteStream();
    }
}
