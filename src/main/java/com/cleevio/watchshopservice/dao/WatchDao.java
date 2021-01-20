package com.cleevio.watchshopservice.dao;

import com.cleevio.watchshopservice.model.Watch;

import java.util.List;

/**
 * DAO for storing watch objects in database.
 */
public interface WatchDao {

    /**
     * Store watch in database.
     *
     * @param watch watch domain object
     */
    void createWatch(Watch watch);

    /**
     * Returns all watches stored in database.
     *
     * @return list of all watches
     */
    List<Watch> findAllWatches();
}
