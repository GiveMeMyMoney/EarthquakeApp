package com.example.styczen.marcin.earthquakeapp.businessLogicLayer.webManager;

import android.util.Log;

import com.example.styczen.marcin.earthquakeapp.businessLogicLayer.webManager.interfaces.IWebEarthquakeManager;
import com.example.styczen.marcin.earthquakeapp.core.cos.Earthquake;
import com.example.styczen.marcin.earthquakeapp.dataProvider.webProvider.EarthquakeWebProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    private List<Earthquake> parseJsonToEarthquakes(String JSON) {
        List<Earthquake> earthquakeList = new ArrayList<>();
        JSONObject obj, obj2, obj3;
        try {
            obj = new JSONObject(JSON);

            JSONArray arr = obj.getJSONArray("features");
            String title = "";
            for (int i = 0; i < /*arr.length()*/ 1; i++) {
                obj2 = arr.getJSONObject(i);
                obj3 = obj2.getJSONObject("properties");

                title = obj3.getString("title");

            }

            //earthquakeList.add(new Earthquake(null, title, null, null));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return earthquakeList;
    }


}
