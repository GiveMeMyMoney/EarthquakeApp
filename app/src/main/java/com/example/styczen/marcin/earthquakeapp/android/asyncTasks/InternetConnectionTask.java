package com.example.styczen.marcin.earthquakeapp.android.asyncTasks;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.Toast;

import com.example.styczen.marcin.earthquakeapp.R;
import com.example.styczen.marcin.earthquakeapp.businessLogicLayer.webManager.WebEarthquakeManager;
import com.example.styczen.marcin.earthquakeapp.businessLogicLayer.webManager.interfaces.IWebEarthquakeManager;

import java.io.IOException;


/**
 * Created by Marcin on 2016-10-25.
 */

public class InternetConnectionTask extends AsyncTask<Void, Void, Boolean> {
    private static final String LOG_TAG = InternetConnectionTask.class.getSimpleName();

    private IWebEarthquakeManager webEarthquakeManager;
    private FragmentActivity mActivity;

    public interface IInternetConnection {
        void getInternetConnectionResult(boolean connection);
    }


    public InternetConnectionTask(FragmentActivity activity) {
        this.mActivity = activity;
        webEarthquakeManager = WebEarthquakeManager.getWebEarthquakeManager();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            return webEarthquakeManager.hasInternetAccess(mActivity);
        } catch (IOException e) {
            Toast.makeText(mActivity, "Error checking internet connection: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(LOG_TAG, "Error checking internet connection", e);
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean connection) {
        super.onPostExecute(connection);
        FragmentManager fm = mActivity.getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.contentContainer);
        if (fragment != null && fragment instanceof IInternetConnection) {
            ((IInternetConnection) fragment).getInternetConnectionResult(connection);
        }
    }
}
