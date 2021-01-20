package com.cleevio.watchshopservice.dao.impl;

import com.cleevio.watchshopservice.dao.WatchDao;
import com.cleevio.watchshopservice.model.Watch;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Implementation of {@link WatchDao}.
 */
@Repository
public class WatchDaoImpl implements WatchDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void createWatch(Watch watch) {
        em.persist(watch);
    }

    @Override
    public List<Watch> findAllWatches() {
        return em.createQuery("SELECT w FROM Watch w", Watch.class).getResultList();
    }
}
