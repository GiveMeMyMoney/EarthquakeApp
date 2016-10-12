package com.example.styczen.marcin.earthquakeapp.database;

/**
 * Created by Marcin on 2016-10-13.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.styczen.marcin.earthquakeapp.core.cos.Earthquake;
import com.example.styczen.marcin.earthquakeapp.utils.AndroidUtils;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.File;
import java.sql.SQLException;

/**
 * DataBase class
 */
public class DBHelper extends OrmLiteSqliteOpenHelper {
    private static String LOG_TAG = DBHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "earthquake.db";
    private static final int DATABASE_VERSION = 1;
    //TODO
    private static String dbPath = AndroidUtils.getStoragePath()
            + File.separator + "InSpace" + File.separator + "databases" + File.separator
            + DATABASE_NAME;

    // DAO (Database Access Object)
    private Dao<Earthquake, Integer> earthquakeDao = null;
    private RuntimeExceptionDao<Earthquake, Integer> earthquakeRuntimeDao = null;

    /*private static DBHelper instance;
    private DaoContainer daoContainer;*/

    public DBHelper(Context context) {
        super(context, dbPath, null, DATABASE_VERSION);
    }

    /*public static synchronized DBHelper getDbAdapter(Context ctx) {
        if (instance == null) {
            instance = new DBHelper(ctx);
        }
        return instance;
    }*/

    /**
     * OnCreate
     * @param db
     * @param connectionSource
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(LOG_TAG, "createTable (onCreate)");
            TableUtils.createTable(connectionSource, Earthquake.class);
        } catch (SQLException e) {
            Log.e(LOG_TAG, "Can't create database (onCreate)", e);
            throw new RuntimeException(e);
        }

        // here we try inserting data in the on-create as a test
        /*RuntimeExceptionDao<Earthquake, Integer> dao = getSimpleDataDao();
        long millis = System.currentTimeMillis();
        // create some entries in the onCreate
        Earthquake simple = new Earthquake(millis);
        dao.create(simple);
        simple = new Earthquake(millis + 1);
        dao.create(simple);
        Log.i(DBHelper.class.getName(), "created new entries in onCreate: " + millis);*/
    }

    /**
     * OnUpgrade
     * @param db
     * @param connectionSource
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(LOG_TAG, "dropTable (onUpgrade)");
            TableUtils.dropTable(connectionSource, Earthquake.class, true);
            // after we drop the old databases, we create the new ones
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(LOG_TAG, "Can't drop databases (onUpgrade)", e);
            throw new RuntimeException(e);
        }
    }

    /*public DaoContainer getDaoContainer() {
        return new DaoContainer(instance.getConnectionSource());
    }

    public ConnectionSource getConnectionSource() {
        return instance.getConnectionSource();
    }*/

    /**
     * @return EarthquakeDao
     * @throws SQLException
     */
    public Dao<Earthquake, Integer> getEarthquakeDao() throws SQLException {
        Log.i(LOG_TAG, "getEarthquakeDao");
        if (earthquakeDao == null) {
            earthquakeDao = getDao(Earthquake.class);
        }
        return earthquakeDao;
    }

    /**
     * @return RuntimeExceptionDao only through RuntimeExceptions
     */
    public RuntimeExceptionDao<Earthquake, Integer> getSimpleDataDao() {
        if (earthquakeRuntimeDao == null) {
            earthquakeRuntimeDao = getRuntimeExceptionDao(Earthquake.class);
        }
        return earthquakeRuntimeDao;
    }

    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
        earthquakeRuntimeDao = null;
    }
}
