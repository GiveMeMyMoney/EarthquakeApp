package com.example.styczen.marcin.earthquakeapp.database;

import com.example.styczen.marcin.earthquakeapp.core.Earthquake;
import com.example.styczen.marcin.earthquakeapp.core.Geometry;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

/**
 * Created by Marcin on 2016-10-13.
 */

public class DaoContainer {
    private ConnectionSource connectionSource;
    private Dao<Earthquake, Integer> earthquakeDAO;
    private Dao<Geometry, Integer> geometryDAO;

    public DaoContainer(ConnectionSource source) {
        this.connectionSource = source;
    }

    public Dao<Earthquake, Integer> getEarthquakeDAO() throws java.sql.SQLException {
        if (earthquakeDAO == null) {
            earthquakeDAO = DaoManager.createDao(connectionSource, Earthquake.class);
        }

        return earthquakeDAO;
    }

    public Dao<Geometry, Integer> getGeometryDAO() throws java.sql.SQLException {
        if (geometryDAO == null) {
            geometryDAO = DaoManager.createDao(connectionSource, Geometry.class);
        }

        return geometryDAO;
    }



}
