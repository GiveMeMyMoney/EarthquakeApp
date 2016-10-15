package com.example.styczen.marcin.earthquakeapp.utils;

/**
 * Created by Marcin on 2016-10-13.
 */

public class AndroidUtils {

    public static final String getStoragePath() {
        //TODO
        String storagePath = "/sdcard";
        /*if (Environment.getExternalStorageState().equals("mounted")) {
            storagePath = Environment.getExternalStorageDirectory().toString();
        }*/
        return storagePath;
    }

}
