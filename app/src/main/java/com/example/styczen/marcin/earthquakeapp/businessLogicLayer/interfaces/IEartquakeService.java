package com.example.styczen.marcin.earthquakeapp.businessLogicLayer.interfaces;

import com.example.styczen.marcin.earthquakeapp.core.cos.Earthquake;

import java.util.List;

/**
 * Created by Marcin on 2016-10-13.
 */

public interface IEartquakeService {
    /**
     * Select all favorites earthquakes
     * @return List<Earthquake>
     */
    List<Earthquake> selectAll();

    /**
     * Delete one earthquake from favorites
     * @return List<Earthquake>
     */
    int deleteById(int id);

    /**
     * Insert new earthquake into favorites
     * @param earthquake - new earthquake when sb add
     */
    boolean insert(Earthquake earthquake);

}
