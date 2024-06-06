package com.example.pageconnexion;


import android.content.Context;
import android.content.res.AssetManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class LoginManager {
    private Context context;

    public LoginManager(Context context) {
        this.context = context;
    }

    public boolean validateLogin(String email, String password) {
        try {
            // Read the JSON file from the assets folder
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open("logininfo.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, "UTF-8");

            // Parse the JSON data
            JSONObject jsonObject = new JSONObject(json);
            JSONArray usersArray = jsonObject.getJSONArray("users");

            // Search for the email and password combination
            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject userObject = usersArray.getJSONObject(i);
                String storedEmail = userObject.getString("email");
                String storedPassword = userObject.getString("password");

                if (email.equals(storedEmail) && password.equals(storedPassword)) {
                    return true; // Valid credentials found
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return false; // Invalid credentials or error occurred
    }
}
