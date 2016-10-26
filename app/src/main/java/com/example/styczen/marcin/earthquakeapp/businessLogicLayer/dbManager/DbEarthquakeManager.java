package com.example.styczen.marcin.earthquakeapp.businessLogicLayer.dbManager;

import android.content.Context;

import com.example.styczen.marcin.earthquakeapp.businessLogicLayer.dbManager.interfaces.IDbEarthquakeManager;
import com.example.styczen.marcin.earthquakeapp.core.Earthquake;
import com.example.styczen.marcin.earthquakeapp.dataProvider.dbProvider.EartquakeDbProvider;
import com.example.styczen.marcin.earthquakeapp.dataProvider.dbProvider.interfaces.IEarthquakeDbProvider;
import com.example.styczen.marcin.earthquakeapp.exceptions.DataBaseException;

import java.util.List;

/**
 * Created by Marcin on 2016-10-13.
 */

public class DbEarthquakeManager implements IDbEarthquakeManager {
    private static final String LOG_TAG = DbEarthquakeManager.class.getSimpleName();

    private IEarthquakeDbProvider dataProvider;

    //Construct
    private static IDbEarthquakeManager earthquakeService;

    private DbEarthquakeManager(IEarthquakeDbProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    public static IDbEarthquakeManager getEarthquakeManager(Context context) throws DataBaseException {
        return getEarthquakeManager(EartquakeDbProvider.getEartquakeDbProvider(context));
    }

    private static IDbEarthquakeManager getEarthquakeManager(IEarthquakeDbProvider dataProvider) {
        if (earthquakeService == null) {
            earthquakeService = new DbEarthquakeManager(dataProvider);
        }
        return earthquakeService;
    }
    //endregion Construct
    @Override
    public Earthquake selectById(int id) throws DataBaseException {
        return id > 0 ? dataProvider.selectById(id) : null;
    }


    @Override
    public List<Earthquake> selectAll() throws DataBaseException {
        return dataProvider.selectAll();
    }

    @Override
    public long deleteById(int id) throws DataBaseException {
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
