package com.example.styczen.marcin.earthquakeapp.businessLogicLayer.webManager;

import com.example.styczen.marcin.earthquakeapp.businessLogicLayer.webManager.interfaces.IWebEarthquakeManager;
import com.example.styczen.marcin.earthquakeapp.core.cos.Earthquake;
import com.example.styczen.marcin.earthquakeapp.dataProvider.webProvider.EarthquakeWebProvider;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Marcin on 2016-10-22.
 */

public class WebEarthquakeManager implements IWebEarthquakeManager {

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
            Call<List<Earthquake>> call = service.getEarthquakesWithDate("geojson", startTime, endTime);
            Response<List<Earthquake>> response = call.execute();
            List<Earthquake> earthquakeList = response.body();
            return earthquakeList;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
