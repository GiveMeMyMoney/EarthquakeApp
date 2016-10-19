package com.example.styczen.marcin.earthquakeapp.dataProvider.dbProvider.interfaces;

import com.example.styczen.marcin.earthquakeapp.core.cos.Earthquake;
import com.example.styczen.marcin.earthquakeapp.exceptions.DataBaseException;

import java.util.List;

/**
 * Created by Marcin on 2016-10-13.
 */

public interface IEarthquakeDbProvider {
    /**
     * Select all favorites earthquakes
     * @return List<Earthquake>
     */
    List<Earthquake> selectAll() throws DataBaseException;

    /**
     * Delete one earthquake from favorites
     * @return List<Earthquake>
     */
    int deleteById(int id) throws DataBaseException;

    /**
     * Insert new earthquake into favorites
     * @param earthquake - new earthquake when sb add
     */
    boolean insertOrUpdate(Earthquake earthquake) throws DataBaseException;
}
