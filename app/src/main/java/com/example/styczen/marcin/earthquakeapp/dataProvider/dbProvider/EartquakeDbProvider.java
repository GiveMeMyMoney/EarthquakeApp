package com.example.styczen.marcin.earthquakeapp.dataProvider.dbProvider;

import android.content.Context;

import com.example.styczen.marcin.earthquakeapp.core.Earthquake;
import com.example.styczen.marcin.earthquakeapp.core.Geometry;
import com.example.styczen.marcin.earthquakeapp.dataProvider.dbProvider.interfaces.IEarthquakeDbProvider;
import com.example.styczen.marcin.earthquakeapp.database.DBAdapter;
import com.example.styczen.marcin.earthquakeapp.database.DaoContainer;
import com.example.styczen.marcin.earthquakeapp.exceptions.DataBaseException;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Marcin on 2016-10-13.
 */

public class EartquakeDbProvider implements IEarthquakeDbProvider {
    private static final String LOG_TAG = EartquakeDbProvider.class.getSimpleName();

    private DBAdapter dbAdapter;
    private Dao<Earthquake, Integer> earthquakeDao;
    private Dao<Geometry, Integer> geometryDao;

    //Construct
    private static EartquakeDbProvider instance;

    private EartquakeDbProvider(Context context) throws DataBaseException {
        try {
            dbAdapter = DBAdapter.getDbAdapter(context);
            DaoContainer daoContainer = dbAdapter.getDbAdapter(context).getDaoContainer();
            earthquakeDao = daoContainer.getEarthquakeDAO();
            geometryDao = daoContainer.getGeometryDAO();
        } catch (SQLException e) {
            throw new DataBaseException(String.format("Eartquake Provider is inaccessible. %s", e.getMessage()));
        }
    }

    public static EartquakeDbProvider getEartquakeDbProvider(Context context) throws DataBaseException {
        if (instance == null) {
            instance = new EartquakeDbProvider(context);
        }
        return instance;
    }
    //endregion Construct

    private void throwError(String message) throws DataBaseException {
        throw new DataBaseException(message);
    }

    @Override
    public Earthquake selectById(int id) throws DataBaseException {
        Earthquake earthquake = null;
        try {
            QueryBuilder<Earthquake, Integer> qb = earthquakeDao.queryBuilder();
            earthquake = qb.where().eq("_id", id).queryForFirst();
        } catch (Exception e) {
            throwError(String.format("Eartquake Provider selectAll(). %s", e.getMessage()));
        }
        return earthquake;
    }

    /**
     * Select all favorites earthquakes
     *
     * @return List<Earthquake>
     */
    @Override
    public List<Earthquake> selectAll() throws DataBaseException {
        List<Earthquake> earthquakes = null;
        //TODO szybciej|prosciej
        try {
            QueryBuilder<Earthquake, Integer> qb = earthquakeDao.queryBuilder();
            qb.orderBy(Earthquake.EARTQUAKE_COL_NAME, false);
            earthquakes = qb.query();
        } catch (Exception e) {
            throwError(String.format("Eartquake Provider selectAll(). %s", e.getMessage()));
        }

        return earthquakes;
    }

    /**
     * Delete one earthquake from favorites
     *
     * @return deletedRows.
     * @param id
     */
    @Override
    public int deleteById(int id) throws DataBaseException {
        int deletedRows = 0;
        try {
            deletedRows = earthquakeDao.deleteById(id);
        } catch (Exception e) {
            throwError(String.format("Eartquake Provider deleteById(). %s", e.getMessage()));
        }

        return deletedRows;
    }

    /**
     * Insert new earthquake into db
     *
     * @param earthquake - new earthquake when sb add
     */
    @Override
    public boolean insertOrUpdate(Earthquake earthquake) throws DataBaseException {
        try {
            geometryDao.createOrUpdate(earthquake.getGeometry());
            earthquakeDao.createOrUpdate(earthquake);
        } catch (Exception e) {
            throwError(String.format("Eartquake Provider insertOrUpdate(). %s", e.getMessage()));
        }
        return false;
    }


}
