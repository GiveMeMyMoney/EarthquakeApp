package com.example.styczen.marcin.earthquakeapp.businessLogicLayer.dbManager.interfaces;

import com.example.styczen.marcin.earthquakeapp.core.Earthquake;
import com.example.styczen.marcin.earthquakeapp.exceptions.DataBaseException;

import java.util.List;

/**
 * Created by Marcin on 2016-10-13.
 */

public interface IDbEarthquakeManager {
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
    boolean insert(Earthquake earthquake) throws DataBaseException;
}
