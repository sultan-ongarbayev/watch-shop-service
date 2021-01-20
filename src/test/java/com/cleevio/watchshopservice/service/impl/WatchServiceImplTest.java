package com.cleevio.watchshopservice.service.impl;

import com.cleevio.watchshopservice.dao.WatchDao;
import com.cleevio.watchshopservice.model.Watch;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigInteger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {
                WatchServiceImpl.class,
                WatchServiceImplTest.Context.class
        })
public class WatchServiceImplTest {

    @Autowired
    private WatchServiceImpl tested;

    @Autowired
    private WatchDao watchDaoMock;

    @After
    public void resetMocks() {
        EasyMock.reset(watchDaoMock);
    }

    @Test
    public void createWatch() {
        Watch watch = new Watch();
        watch.setTitle("title");
        watch.setDescription("description");
        watch.setPrice(BigInteger.valueOf(100));
        watch.setFountainImageBase64("base64fountainImage");

        watchDaoMock.createWatch(watch);
        EasyMock.expectLastCall();

        EasyMock.replay(watchDaoMock);

        // verify that WatchServiceImpl calls WatchDao.createWatch(Watch)
        tested.createWatch(watch);

        EasyMock.verify(watchDaoMock);
    }

    @Configuration
    public static class Context {

        @Bean
        public WatchDao watchDaoMock() {
            return EasyMock.createMock(WatchDao.class);
        }
    }
}