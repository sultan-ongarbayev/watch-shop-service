package com.cleevio.watchshopservice.service.impl;

import com.cleevio.watchshopservice.dao.WatchDao;
import com.cleevio.watchshopservice.model.Watch;
import com.cleevio.watchshopservice.service.WatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link WatchService} which stores watch in database.
 */
@Service
public class WatchServiceImpl implements WatchService {

    @Autowired
    private WatchDao watchDao;

    @Transactional
    @Override
    public void createWatch(Watch watch) {
        watchDao.createWatch(watch);
    }
}
