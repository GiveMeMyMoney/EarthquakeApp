package com.example.styczen.marcin.earthquakeapp.dataProvider.webProvider;

import com.example.styczen.marcin.earthquakeapp.core.cos.Earthquake;
import com.example.styczen.marcin.earthquakeapp.dataProvider.webProvider.interfaces.IEarthquakesWebProvider;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Marcin on 2016-10-18.
 */

public class EarthquakeWebProvider implements IEarthquakesWebProvider {
    private static final String LOG_TAG = EarthquakeWebProvider.class.getSimpleName();
    private static Logger logger = LoggerFactory.getLogger(EarthquakeWebProvider.class);

    private static final String METHOD_TYPE = "query";
    private static final String FORMAT_TYPE = "geojson";
    private static final String STARTTIME_TXT = "&starttime=%s";
    private static final String ENDTIME_TXT = "&endtime=%s";
    private final String SERVICE_URL = "http://earthquake.usgs.gov/fdsnws/event/1/%s?format=%s";

    //region Construct
    private static EarthquakeWebProvider instance;

    private EarthquakeWebProvider() {
    }

    public static EarthquakeWebProvider getEarthquakeWebProvider() {
        if (instance == null) {
            instance = new EarthquakeWebProvider();
        }
        return instance;
    }
    //endregion Construct

    public List<Earthquake> downloadEarthquakesWithDate(Date startDate, Date endDate) {
        List<Earthquake> earthquakeList;
        String url = String.format(SERVICE_URL, METHOD_TYPE, FORMAT_TYPE);
        url += String.format(STARTTIME_TXT, String.valueOf(startDate)) + String.format(ENDTIME_TXT, String.valueOf(endDate));

        earthquakeList = downloadEarthquakesUrl(url);

        return earthquakeList;
    }

    //TODO JSONClient
    //TODO JSONDataMapper

    private List<Earthquake> downloadEarthquakesUrl(String finalUrl) {
        List<Earthquake> earthquakeList = new ArrayList<>();




        return earthquakeList;
    }



}
