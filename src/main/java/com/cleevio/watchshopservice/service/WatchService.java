package com.cleevio.watchshopservice.service;

import com.cleevio.watchshopservice.model.Watch;

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
}
