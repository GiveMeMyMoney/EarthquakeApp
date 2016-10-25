package com.example.styczen.marcin.earthquakeapp.businessLogicLayer.webManager.interfaces;

import android.content.Context;

import com.example.styczen.marcin.earthquakeapp.core.Earthquake;

import java.io.IOException;
import java.util.List;

/**
 * Created by Marcin on 2016-10-22.
 */

public interface IWebEarthquakeManager {
    /**
     * If device is connected to the NET.
     * @return true | false
     * @param context
     */
    boolean hasInternetAccess(Context context) throws IOException;

    List<Earthquake> downloadEarthquakesWithDate(String startTime, String endTime);
}
