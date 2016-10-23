package com.example.styczen.marcin.earthquakeapp.android.services;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;

import com.example.styczen.marcin.earthquakeapp.businessLogicLayer.webManager.WebEarthquakeManager;
import com.example.styczen.marcin.earthquakeapp.businessLogicLayer.webManager.interfaces.IWebEarthquakeManager;
import com.example.styczen.marcin.earthquakeapp.core.cos.Earthquake;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcin on 2016-10-18.
 */

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

    /*
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
    }*/

    private void publishResult(List<Earthquake> earthquakeList, int result) {
        Intent i = new Intent(NOTIFICATION);
        i.putParcelableArrayListExtra(INTENT_EARTHQUAKES, new ArrayList<>(earthquakeList));
        i.putExtra(INTENT_RESULT, result);
        sendBroadcast(i);
    }


}
