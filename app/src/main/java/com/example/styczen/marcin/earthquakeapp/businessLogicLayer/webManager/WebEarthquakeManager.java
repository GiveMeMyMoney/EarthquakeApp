package com.example.styczen.marcin.earthquakeapp.businessLogicLayer.webManager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.styczen.marcin.earthquakeapp.businessLogicLayer.webManager.interfaces.IWebEarthquakeManager;
import com.example.styczen.marcin.earthquakeapp.core.Earthquake;
import com.example.styczen.marcin.earthquakeapp.core.Geometry;
import com.example.styczen.marcin.earthquakeapp.dataProvider.webProvider.EarthquakeWebProvider;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public static WebEarthquakeManager getWebEarthquakeManager() {
        if (instance == null) {
            instance = new WebEarthquakeManager(EarthquakeWebProvider.getEarthquakeWebProvider());
        }
        return instance;
    }
    //endregion

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public boolean hasInternetAccess(Context context) throws IOException {
        if (isNetworkAvailable(context)) {
            return earthquakeWebProvider.hasInternetAccess();
        } else {
            Log.d(LOG_TAG, "No network connection");
            return false;
        }
    }

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
     * @param JSON - from ResponseBody
     * @return List<Earthquake>
     */
    private List<Earthquake> parseJsonToEarthquakes(String JSON) {
        List<Earthquake> earthquakeList = new ArrayList<>();

        //JSON parser
        JsonObject jsonObject = new Gson().fromJson(JSON, JsonObject.class);
        JsonArray featuresArray = jsonObject.getAsJsonArray("features"); //"features"

        for (JsonElement jsonElement : featuresArray) {
            JsonObject obj = jsonElement.getAsJsonObject().getAsJsonObject("geometry"); //geometry
            Geometry geometry = getGeometry(obj);
            obj = jsonElement.getAsJsonObject().getAsJsonObject("properties"); //geometry
            Earthquake earthquake = getEarthquake(geometry, obj);
            earthquakeList.add(earthquake);
        }

        return earthquakeList;
    }

    private Geometry getGeometry(JsonObject geoObj) {
        String type = geoObj.get("type").getAsString();
        List<Double> coordinatesList = new ArrayList<>();
        JsonArray coordinatesArray = geoObj.getAsJsonArray("coordinates");
        for (JsonElement jsonElement : coordinatesArray) {
            coordinatesList.add(jsonElement.getAsDouble());
        }

        return new Geometry(type, coordinatesList.get(0), coordinatesList.get(1), coordinatesList.get(2)); //(Y,X,depth)
    }

    private Earthquake getEarthquake(Geometry geometry, JsonObject objEarthquake) {
        String uniqueId = objEarthquake.get("ids").getAsString();
        Double magnitude = objEarthquake.get("mag").getAsDouble();
        String place = objEarthquake.get("place").getAsString();
        String urlDetail = objEarthquake.get("url").getAsString();
        String code= objEarthquake.get("code").getAsString();
        String type= objEarthquake.get("type").getAsString();
        String title= objEarthquake.get("title").getAsString();
        long time= objEarthquake.get("time").getAsLong();

        return new Earthquake(uniqueId, magnitude, place, urlDetail, code, type, title, time, geometry);
    }

}
