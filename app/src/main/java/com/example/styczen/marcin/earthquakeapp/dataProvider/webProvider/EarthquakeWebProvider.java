package com.example.styczen.marcin.earthquakeapp.dataProvider.webProvider;

import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.ResponseBody;
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
    private static final String SERVICE_URL = "http://earthquake.usgs.gov/";

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

    public boolean hasInternetAccess() throws IOException {
        HttpURLConnection urlc = (HttpURLConnection) (new URL("http://clients3.google.com/generate_204").openConnection());
        urlc.setRequestProperty("User-Agent", "Android");
        urlc.setRequestProperty("Connection", "close");
        urlc.setConnectTimeout(1500);
        urlc.connect();
        return (urlc.getResponseCode() == 204 && urlc.getContentLength() == 0);
    }

    //http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2014-01-01&endtime=2014-01-02
    public interface RetroEarthquakesWithDate {
        /**
         *  Download all eartquakes with match (starttime; endtime)
         *
         * @param format - format like geojson or XML
         * @param starttime - from
         * @param endtime - to
         * @return - ResponseBody which is a JSON.
         */
        @GET("fdsnws/event/1/query")
        Call<ResponseBody> getEarthquakesWithDate(@Query("format") String format, @Query("starttime") String starttime, @Query("endtime") String endtime);
    }

    public static RetroEarthquakesWithDate downloadEarthquakesWithDate() {
        return new Retrofit.Builder()
                .baseUrl(SERVICE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetroEarthquakesWithDate.class);
    }


}
