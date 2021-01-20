package com.cleevio.watchshopservice.service.impl;

import com.cleevio.watchshopservice.dao.WatchDao;
import com.cleevio.watchshopservice.model.Watch;
import com.cleevio.watchshopservice.service.WatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of {@link WatchService} which stores watch in database.
 */
@Service
@Slf4j
public class WatchServiceImpl implements WatchService {

    @Autowired
    private WatchDao watchDao;

    @Transactional
    @Override
    public void createWatch(Watch watch) {
        log.trace("Creating new watch, model object = {}", watch);
        watchDao.createWatch(watch);
    }

    @Transactional
    @Override
    public List<Watch> findAllWatches() {
        log.trace("Searching for all watches");
        return watchDao.findAllWatches();
    }
}
