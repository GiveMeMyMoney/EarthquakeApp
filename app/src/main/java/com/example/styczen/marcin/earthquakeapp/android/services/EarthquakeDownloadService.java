package com.example.styczen.marcin.earthquakeapp.android.services;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;

import com.example.styczen.marcin.earthquakeapp.businessLogicLayer.webManager.WebEarthquakeManager;
import com.example.styczen.marcin.earthquakeapp.businessLogicLayer.webManager.interfaces.IWebEarthquakeManager;
import com.example.styczen.marcin.earthquakeapp.core.Earthquake;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcin on 2016-10-18.
 */

//TODO javadoc
public class EarthquakeDownloadService extends IntentService {

    private int result = Activity.RESULT_CANCELED;

    public static final String INTENT_EARTHQUAKES = EarthquakeDownloadService.class.getName() + ".earthquakeJson";
    public static final String INTENT_RESULT = EarthquakeDownloadService.class.getName() + ".earthquakeResult";
    public static final String NOTIFICATION = "com.example.styczen.marcin.earthquakeapp.businessLogicLayer.services";

    private IWebEarthquakeManager webEarthquakeManager;


    public EarthquakeDownloadService() {
        super("EarthquakeDownloadService");
        webEarthquakeManager = WebEarthquakeManager.getEarthquakeWebProvider();

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        List<Earthquake> earthquakeList = webEarthquakeManager.downloadEarthquakesWithDate("2014-01-01", "2014-01-02");
        if (earthquakeList != null) {
            //TODO trzeba rzowiazac result OK.
            // successfully finished
            result = Activity.RESULT_OK;
        }
        publishResult(earthquakeList, result);
    }

    private void publishResult(List<Earthquake> earthquakeList, int result) {
        Intent i = new Intent(NOTIFICATION);
        i.putParcelableArrayListExtra(INTENT_EARTHQUAKES, new ArrayList<>(earthquakeList));
        i.putExtra(INTENT_RESULT, result);
        sendBroadcast(i);
    }


}
