package com.example.styczen.marcin.earthquakeapp.android.receivers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.styczen.marcin.earthquakeapp.android.services.EarthquakeDownloadService;

/**
 * Created by Marcin on 2016-10-18.
 */

public abstract class EarthquakeDownloadReceiver extends BroadcastReceiver {
    private static final String LOG_TAG = EarthquakeDownloadReceiver.class.getSimpleName();


    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String JSON = bundle.getString(EarthquakeDownloadService.INTENT_JSON);
            int resultCode = bundle.getInt(EarthquakeDownloadService.INTENT_RESULT);
            if (resultCode == Activity.RESULT_OK) {
                Log.e(LOG_TAG, JSON);
            } else {
                Log.e(LOG_TAG, String.valueOf(resultCode));
            }

            onReceiveSend(JSON); //error or not

            /*Intent i = new Intent(context, EarthquakeTabsActivity.class);
            i.putExtra("number", JSON);
            context.startActivity(i);*/
        }
    }

    protected abstract void onReceiveSend(String JSON);

}
