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
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.File;
import java.sql.SQLException;

/**
 * Database class
 */

public class DBAdapter {
    private static String LOG_TAG = DBAdapter.class.getSimpleName();

    private static final String DATABASE_NAME = "earthquake.db";
    private static final int DATABASE_VERSION = 1;

    private final Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private boolean opened;

    //region Construct
    private static DBAdapter instance;

    private DBAdapter(Context ctx) {
        this.context = ctx;

        String dbPath = setDbPath();
        Log.v("DBPATH", dbPath);
        dbHelper = new DBHelper(this.context, dbPath);
    }

    public static synchronized DBAdapter getDbAdapter(Context ctx) {
        if (instance == null) {
            instance = new DBAdapter(ctx);
        }
        return instance;
    }

    private String setDbPath() {
        return AndroidUtils.getStoragePath()
                + File.separator + "EarthquakeMobile" + File.separator
                + DATABASE_NAME;
    }
    //endregion Construct

    private class DBHelper extends OrmLiteSqliteOpenHelper {

        // DAO (Database Access Object)
        private Dao<Earthquake, Integer> earthquakeDao = null;

        public DBHelper(final Context context, String path) {
            super(context, path, null, DATABASE_VERSION);
        }

        /**
         * OnCreate - createTables
         *
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

        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);
            // foreign_keys wymagane do poprawnego działania ORMLite
            db.execSQL("PRAGMA foreign_keys=ON;");
        }

        /**
         * OnUpgrade - new DB Version
         *
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
        /*public RuntimeExceptionDao<Earthquake, Integer> getSimpleDataDao() {
            if (earthquakeRuntimeDao == null) {
                earthquakeRuntimeDao = getRuntimeExceptionDao(Earthquake.class);
            }
            return earthquakeRuntimeDao;
        }*/

        /**
         * Close the database connections and clear any cached DAOs.
         */
        @Override
        public void close() {
            super.close();
        }
    }

    /*public DBAdapter open(char[] pass) throws android.database.SQLException {
        if (!opened) {
            try {
                db = dbHelper.getWritableDatabase();
            } catch (android.database.SQLException e) {
                Log.e("TAG", e.getMessage());
                db = dbHelper.getReadableDatabase();
            }
            opened = true;
        }
        return this;
    }*/

    public DaoContainer getDaoContainer() {
        return new DaoContainer(dbHelper.getConnectionSource());
    }

    public ConnectionSource getConnectionSource() {
        return dbHelper.getConnectionSource();
    }

    public void close() {
        dbHelper.close();
        opened = false;
    }
}
