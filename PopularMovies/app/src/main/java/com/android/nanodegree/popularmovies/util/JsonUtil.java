package com.android.nanodegree.popularmovies.util;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by rhatori on 21/12/2016.
 */

public class JsonUtil {
    public static JSONObject getJSONObject(InputStream stream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder stringBuilder = new StringBuilder();
        String data;
        JSONObject result = null;

        try {
            while ((data = reader.readLine()) != null) {
                stringBuilder.append(data);
            }

            result = new JSONObject(stringBuilder.toString());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }
}
