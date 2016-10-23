package com.example.styczen.marcin.earthquakeapp.businessLogicLayer.webManager.interfaces;

import com.example.styczen.marcin.earthquakeapp.core.cos.Earthquake;

import java.util.List;

/**
 * Created by Marcin on 2016-10-22.
 */

public interface IWebEarthquakeManager {
    List<Earthquake> downloadEarthquakesWithDate(String startTime, String endTime);
}
