package com.example.styczen.marcin.earthquakeapp.android.services;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by Marcin on 2016-10-18.
 */

public class EarthquakeDownloadService extends IntentService {

    private int result = Activity.RESULT_CANCELED;

    public static final String INTENT_JSON = EarthquakeDownloadService.class.getName() + ".earthquakeJson";
    public static final String INTENT_RESULT = EarthquakeDownloadService.class.getName() + ".earthquakeResult";
    public static final String NOTIFICATION = "com.example.styczen.marcin.earthquakeapp.businessLogicLayer.services";

    public EarthquakeDownloadService() {
        super("EarthquakeDownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String allJson = doJob();
        publishResult(allJson, result);
    }

    private String doJob() {
        //InputStream stream = null;
        BufferedReader reader = null;

        try {
            URL url = new URL("http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2014-01-01&endtime=2014-01-02");
            //stream = url.openConnection().getInputStream();
            //InputStreamReader reader = new InputStreamReader(stream);

            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            // successfully finished
            result = Activity.RESULT_OK;

            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "nic";
    }

    private void publishResult(String json, int result) {
        Intent i = new Intent(NOTIFICATION);
        i.putExtra(INTENT_JSON, json);
        i.putExtra(INTENT_RESULT, result);
        sendBroadcast(i);
    }


}
