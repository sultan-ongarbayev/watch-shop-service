package com.cleevio.watchshopservice.service;

import com.cleevio.watchshopservice.model.Watch;

import java.util.List;

/**
 * Watch service.
 */
public interface WatchService {

    /**
     * Creates and stores new watch.
     *
     * @param watch watch domain object
     */
    void createWatch(Watch watch);

    List<Watch> findAllWatches();
}
