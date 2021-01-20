package com.cleevio.watchshopservice.controller;

import com.cleevio.watchshopservice.config.WebConfiguration;
import com.cleevio.watchshopservice.controller.dto.CreateWatchDto;
import com.cleevio.watchshopservice.model.Watch;
import com.cleevio.watchshopservice.service.WatchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigInteger;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {
        WebConfiguration.class,
        WatchControllerTest.Context.class
})
public class WatchControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private WatchService watchServiceMock;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void postWatchTest() throws Exception {
        String title = "title";
        String description = "description";
        int price = 100;
        String fountainImageBase64 = "base64FountainImage";

        Capture<Watch> watchModelCapture = EasyMock.newCapture();

        watchServiceMock.createWatch(EasyMock.capture(watchModelCapture));
        EasyMock.expectLastCall();

        EasyMock.replay(watchServiceMock);

        CreateWatchDto requestDto = new CreateWatchDto();
        requestDto.setTitle(title);
        requestDto.setDescription(description);
        requestDto.setPrice(String.valueOf(price));
        requestDto.setFountainImageBase64(fountainImageBase64);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/watch")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        EasyMock.verify(watchServiceMock);

        Watch watch = watchModelCapture.getValue();
        MatcherAssert.assertThat(watch.getTitle(), CoreMatchers.equalTo(title));
        MatcherAssert.assertThat(watch.getDescription(), CoreMatchers.equalTo(description));
        MatcherAssert.assertThat(watch.getPrice(), CoreMatchers.equalTo(BigInteger.valueOf(price)));
        MatcherAssert.assertThat(watch.getFountainImageBase64(), CoreMatchers.equalTo(fountainImageBase64));
    }

    @Configuration
    public static class Context {

        @Bean
        public WatchService watchServiceMock() {
            return EasyMock.createMock(WatchService.class);
        }
    }
}