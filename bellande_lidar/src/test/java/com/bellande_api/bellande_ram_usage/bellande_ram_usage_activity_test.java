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

package com.bellande_api.bellande_lidar;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.io.InputStreamReader;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.content.Context;

public class bellande_lidar_activity_test {

    private bellande_lidar_activity bellande_lidar_activity;
    @Mock
    private Context mockContext;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        bellande_lidar_activity = new bellande_lidar_activity(mockContext);
    }

    @Test
    public void testLoadConfigFromFile() throws IOException {
        // Arrange
        InputStream inputStream = mock(InputStream.class);
        when(mockContext.getAssets().open("configs.json")).thenReturn(inputStream);

        Map<String, Object> mockConfig = mock(Map.class);
        when(mockConfig.containsKey("url")).thenReturn(true);
        when(mockConfig.containsKey("endpoint_path")).thenReturn(true);
        when(mockConfig.containsKey("Bellande_Framework_Access_Key")).thenReturn(true);

        when(new Gson().fromJson(new InputStreamReader(inputStream), new TypeToken<Map<String, Object>>() {}.getType())).thenReturn(mockConfig);

        // Act
        Map<String, Object> config = bellande_lidar_activity.loadConfigFromFile(mockContext);

        // Assert
        assertTrue(config.containsKey("url"));
        assertTrue(config.containsKey("endpoint_path"));
        assertTrue(config.containsKey("Bellande_Framework_Access_Key"));
    }

    @Test(expected = IOException.class)
    public void testLoadConfigFromFile_FileNotFound() throws IOException {
        // Arrange
        when(mockContext.getAssets().open("configs.json")).thenThrow(new IOException("File not found"));

        // Act
        bellande_lidar_activity.loadConfigFromFile(mockContext);
    }
}