package com.example.styczen.marcin.earthquakeapp.businessLogicLayer.webManager;

import android.util.Log;

import com.example.styczen.marcin.earthquakeapp.businessLogicLayer.webManager.interfaces.IWebEarthquakeManager;
import com.example.styczen.marcin.earthquakeapp.core.Earthquake;
import com.example.styczen.marcin.earthquakeapp.dataProvider.webProvider.EarthquakeWebProvider;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Marcin on 2016-10-22.
 */

public class WebEarthquakeManager implements IWebEarthquakeManager {
    private static String LOG_TAG = WebEarthquakeManager.class.getSimpleName();

    private EarthquakeWebProvider earthquakeWebProvider;

    //region Construct
    private static WebEarthquakeManager instance;

    private WebEarthquakeManager(EarthquakeWebProvider webProvider) {
        this.earthquakeWebProvider = webProvider;
    }

    public static WebEarthquakeManager getEarthquakeWebProvider() {
        if (instance == null) {
            instance = new WebEarthquakeManager(EarthquakeWebProvider.getEarthquakeWebProvider());
        }
        return instance;
    }
    //endregion

    //TODO wypchac errory wyzej
    @Override
    public List<Earthquake> downloadEarthquakesWithDate(String startTime, String endTime) {
        try {
            EarthquakeWebProvider.RetroEarthquakesWithDate service = earthquakeWebProvider.downloadEarthquakesWithDate();
            Call<ResponseBody> call = service.getEarthquakesWithDate("geojson", startTime, endTime);
            Response<ResponseBody> response = call.execute();
            String JSON = response.body().string();

            Log.e(LOG_TAG, JSON);

            List<Earthquake> earthquakeList = parseJsonToEarthquakes(JSON);
            return earthquakeList;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Get JSON and parse it to List<Earthquake>.
     * Format of JSON(geojson): http://earthquake.usgs.gov/earthquakes/feed/v1.0/geojson.php
     *
     * @param JSON
     * @return List<Earthquake>
     */
    private List<Earthquake> parseJsonToEarthquakes(String JSON) {
        List<Earthquake> earthquakeList = new ArrayList<>();
        /*JSONObject obj, obj2, obj3;
        try {
            obj = new JSONObject(JSON);

            JSONArray arr = obj.getJSONArray("features");
            String title = "";
            for (int i = 0; i < *//*arr.length()*//* 1; i++) {
                obj2 = arr.getJSONObject(i);
                obj3 = obj2.getJSONObject("properties");

                title = obj3.getString("title");

            }





            //earthquakeList.add(new Earthquake(null, title, null, null));
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        Map<String, Object> javaRootMapObject = new Gson().fromJson(JSON, Map.class);

        JsonObject obj = new Gson().fromJson(JSON, JsonObject.class);
        JsonArray featuresArray = obj.getAsJsonArray("features"); //"features"


        for (int i = 0; i < 1; i++) {
            JsonObject objProperties = featuresArray.get(i).getAsJsonObject().getAsJsonObject("properties");
            String place = objProperties.get("place").getAsString();
        }

        /*for (JsonElement jsonElement : featuresArray) {
            JsonObject objProperties = jsonElement.getAsJsonObject().getAsJsonObject("properties");
            String place = objProperties.get("place").getAsString();


        }*/

        return earthquakeList;
    }

    private Earthquake getEarthquake() {

        return null;
    }

}
