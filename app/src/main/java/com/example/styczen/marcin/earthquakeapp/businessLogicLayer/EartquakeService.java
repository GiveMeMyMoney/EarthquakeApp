package com.example.styczen.marcin.earthquakeapp.businessLogicLayer;

import com.example.styczen.marcin.earthquakeapp.businessLogicLayer.interfaces.IEartquakeService;
import com.example.styczen.marcin.earthquakeapp.core.cos.Earthquake;
import com.example.styczen.marcin.earthquakeapp.dataProvider.EartquakeDbProvider;
import com.example.styczen.marcin.earthquakeapp.dataProvider.interfaces.IEarthquakeDbProvider;

import java.util.List;

/**
 * Created by Marcin on 2016-10-13.
 */

public class EartquakeService implements IEartquakeService {

    private IEarthquakeDbProvider dataProvider;

    //Construct
    private static IEartquakeService earthquakeService;

    private EartquakeService(IEarthquakeDbProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    public static IEartquakeService getEarthquakeService() /*throws ServiceException*/ {
        return getEarthquakeService(EartquakeDbProvider.getEartquakeDbProvider());
        /*try {
        } catch (DBException e) {
            throw new ServiceException();
        }*/
    }

    private static IEartquakeService getEarthquakeService(IEarthquakeDbProvider dataProvider) {
        if (earthquakeService == null) {
            earthquakeService = new EartquakeService(dataProvider);
        }
        return earthquakeService;
    }
    //endregion Construct


    @Override
    public List<Earthquake> selectAll() {
        return dataProvider.selectAll();
    }

    @Override
    public int deleteById(int id) {
        return id > 0 ? dataProvider.deleteById(id) : id;
    }

    @Override
    public boolean insert(Earthquake earthquake) {
        if (earthquake != null && earthquake.isValid()) {
            dataProvider.insert(earthquake);
        }
        return false;
    }
}
