package com.cleevio.watchshopservice.dao;

import com.cleevio.watchshopservice.model.Watch;

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
}
