package com.example.styczen.marcin.earthquakeapp.dataProvider;

import android.content.Context;

import com.example.styczen.marcin.earthquakeapp.core.cos.Earthquake;
import com.example.styczen.marcin.earthquakeapp.dataProvider.interfaces.IEarthquakeDbProvider;
import com.example.styczen.marcin.earthquakeapp.database.DBHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Marcin on 2016-10-13.
 */

public class EartquakeDbProvider implements IEarthquakeDbProvider {
    private static final String LOG_TAG = EartquakeDbProvider.class.getSimpleName();

    private DBHelper dataBase;
    private Dao<Earthquake, Integer> earthquakeDao;

    //Construct
    private static EartquakeDbProvider instance;

    private EartquakeDbProvider(Context context) /*throws DBException*/ {
        try {
            dataBase = OpenHelperManager.getHelper(context, DBHelper.class);
            //DaoContainer daoContainer = dataBase.getDbAdapter(context).getDaoContainer();
            earthquakeDao = dataBase.getEarthquakeDao();
        } catch (SQLException e) {
            //TODO
            //throw new DBException(String.format("Provider inwentaryzacji niedostępny. %s", e.getMessage()));
        }
    }

    public static EartquakeDbProvider getEartquakeDbProvider() /*throws DBException */ {
        return getEartquakeDbProvider(/*getContext()*/);
    }

    public static EartquakeDbProvider getEartquakeDbProvider(Context context) /*throws DBException*/ {
        if (instance == null) {
            instance = new EartquakeDbProvider(context);
        }
        return instance;
    }
    //endregion Construct

    /**
     * Select all favorites earthquakes
     *
     * @return List<Earthquake>
     */
    @Override
    public List<Earthquake> selectAll() {
        List<Earthquake> earthquakes = null;
        //TODO szybciej|prosciej
        try {
            QueryBuilder<Earthquake, Integer> qb = earthquakeDao.queryBuilder();
            qb.orderBy(Earthquake.EARTQUAKE_COL_NAME, false);
            earthquakes = qb.query();
        } catch (Exception e) {
            //logger.error(LOG_TAG, "Błąd podczas pobierania listy arkuszy.\nException message: " + e.getMessage());
        }

        return earthquakes;
    }

    /**
     * Delete one earthquake from favorites
     *
     * @return List<Earthquake>
     */
    @Override
    public int deleteById(int id) {
        int deletedRows = 0;

        try {
            deletedRows = earthquakeDao.deleteById(id);
        } catch (Exception e) {
            //logger.error(LOG_TAG, "Błąd podczas usuwania arkusza o id: " + id + "\nException message: " + e.getMessage());
        }

        return deletedRows;
    }

    /**
     * Insert new earthquake into favorites
     *
     * @param earthquake - new earthquake when sb add
     */
    @Override
    public boolean insert(Earthquake earthquake) {
        try {
            earthquakeDao.createOrUpdate(earthquake);
        } catch (Exception e) {
            //e.printStackTrace();
            //throw new BladBazodanowyException(String.format("Blad podczas zapisywania arkusza %s. %s", arkuszSpisowy.getNumer(), e.getMessage() + " " + e.getCause()));
        }
        return false;
    }
}
