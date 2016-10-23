package com.example.styczen.marcin.earthquakeapp.dataProvider.webProvider;

import com.example.styczen.marcin.earthquakeapp.core.cos.Earthquake;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Marcin on 2016-10-18.
 */

public class EarthquakeWebProvider {
    private static final String LOG_TAG = EarthquakeWebProvider.class.getSimpleName();
    private static Logger logger = LoggerFactory.getLogger(EarthquakeWebProvider.class);

    private static final String METHOD_TYPE = "query";
    private static final String FORMAT_TYPE = "geojson";
    private static final String STARTTIME_TXT = "&starttime=%s";
    private static final String ENDTIME_TXT = "&endtime=%s";
    private static final String SERVICE_URL = "http://earthquake.usgs.gov/?format=%s";

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

    //http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2014-01-01&endtime=2014-01-02

    public interface RetroEarthquakesWithDate {
        @GET("fdsnws/event/1/query")
        Call<List<Earthquake>> getEarthquakesWithDate(@Query("format") String format, @Query("starttime") String starttime, @Query("endtime") String endtime);
    }

    public static RetroEarthquakesWithDate downloadEarthquakesWithDate() {
        return new Retrofit.Builder()
                .baseUrl(SERVICE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetroEarthquakesWithDate.class);
    }


}
