package com.cleevio.watchshopservice.dao.impl;

import com.cleevio.watchshopservice.dao.WatchDao;
import com.cleevio.watchshopservice.model.Watch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Implementation of {@link WatchDao}.
 */
@Repository
@Slf4j
public class WatchDaoImpl implements WatchDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void createWatch(Watch watch) {
        log.trace("Saving new watch to database, model object = {}", watch);
        em.persist(watch);
    }

    @Override
    public List<Watch> findAllWatches() {
        log.trace("Searching for all watches in database");
        return em.createQuery("SELECT w FROM Watch w", Watch.class).getResultList();
    }
}
