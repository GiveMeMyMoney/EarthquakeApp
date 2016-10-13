package com.example.styczen.marcin.earthquakeapp.businessLogicLayer;

import android.content.Context;

import com.example.styczen.marcin.earthquakeapp.businessLogicLayer.interfaces.IEartquakeService;
import com.example.styczen.marcin.earthquakeapp.core.cos.Earthquake;
import com.example.styczen.marcin.earthquakeapp.dataProvider.EartquakeDbProvider;
import com.example.styczen.marcin.earthquakeapp.dataProvider.interfaces.IEarthquakeDbProvider;
import com.example.styczen.marcin.earthquakeapp.exceptions.DataBaseException;

import java.util.List;

/**
 * Created by Marcin on 2016-10-13.
 */

public class EarthquakeService implements IEartquakeService {

    private IEarthquakeDbProvider dataProvider;

    //Construct
    private static IEartquakeService earthquakeService;

    private EarthquakeService(IEarthquakeDbProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    public static IEartquakeService getEarthquakeService(Context context) throws DataBaseException {
        return getEarthquakeService(EartquakeDbProvider.getEartquakeDbProvider(context));
    }

    private static IEartquakeService getEarthquakeService(IEarthquakeDbProvider dataProvider) {
        if (earthquakeService == null) {
            earthquakeService = new EarthquakeService(dataProvider);
        }
        return earthquakeService;
    }
    //endregion Construct


    @Override
    public List<Earthquake> selectAll() throws DataBaseException {
        return dataProvider.selectAll();
    }

    @Override
    public int deleteById(int id) throws DataBaseException {
        return id > 0 ? dataProvider.deleteById(id) : id;
    }

    @Override
    public boolean insert(Earthquake earthquake) throws DataBaseException {
        if (earthquake != null && earthquake.isValid()) {
            dataProvider.insertOrUpdate(earthquake);
        }
        return false;
    }
}
