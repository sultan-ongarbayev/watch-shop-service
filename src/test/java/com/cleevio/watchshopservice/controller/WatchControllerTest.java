package com.cleevio.watchshopservice.controller;

import com.cleevio.watchshopservice.config.WebConfiguration;
import com.cleevio.watchshopservice.controller.dto.WatchDto;
import com.cleevio.watchshopservice.model.Watch;
import com.cleevio.watchshopservice.service.WatchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.After;
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
import java.util.Arrays;

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

    @After
    public void resetMocks() {
        EasyMock.reset(watchServiceMock);
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

        WatchDto requestDto = new WatchDto();
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

    @Test
    public void getAllWatchesTest_json() throws Exception {
        Watch watch1 = new Watch();
        watch1.setWatchId(1L);
        watch1.setTitle("watch_title_1");
        watch1.setDescription("watch_description_1");
        watch1.setPrice(BigInteger.valueOf(100L));
        watch1.setFountainImageBase64("fountain_image_1_base_64");

        Watch watch2 = new Watch();
        watch2.setWatchId(2L);
        watch2.setTitle("watch_title_2");
        watch2.setDescription("watch_description_2");
        watch2.setPrice(BigInteger.valueOf(200L));
        watch2.setFountainImageBase64("fountain_image_2_base_64");

        MediaType requestedMediaType = MediaType.APPLICATION_JSON;

        EasyMock.expect(watchServiceMock.findAllWatches())
                .andReturn(Arrays.asList(watch1, watch2));

        EasyMock.replay(watchServiceMock);

        mockMvc.perform(MockMvcRequestBuilders.get("/watch")
                .accept(requestedMediaType))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(requestedMediaType))

                // first watch comparing
                .andExpect(MockMvcResultMatchers.jsonPath("$.watches[0].title")
                        .value(watch1.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.watches[0].description")
                        .value(watch1.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.watches[0].price")
                        .value(watch1.getPrice().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.watches[0].fountainImageBase64")
                        .value(watch1.getFountainImageBase64()))

                // second watch comparing
                .andExpect(MockMvcResultMatchers.jsonPath("$.watches[1].title")
                        .value(watch2.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.watches[1].description")
                        .value(watch2.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.watches[1].price")
                        .value(watch2.getPrice().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.watches[1].fountainImageBase64")
                        .value(watch2.getFountainImageBase64()));

        EasyMock.verify(watchServiceMock);
    }

    @Test
    public void getAllWatchesTest_xml() throws Exception {
        Watch watch1 = new Watch();
        watch1.setWatchId(1L);
        watch1.setTitle("watch_title_1");
        watch1.setDescription("watch_description_1");
        watch1.setPrice(BigInteger.valueOf(100L));
        watch1.setFountainImageBase64("fountain_image_1_base_64");

        Watch watch2 = new Watch();
        watch2.setWatchId(2L);
        watch2.setTitle("watch_title_2");
        watch2.setDescription("watch_description_2");
        watch2.setPrice(BigInteger.valueOf(200L));
        watch2.setFountainImageBase64("fountain_image_2_base_64");

        MediaType requestedMediaType = MediaType.APPLICATION_XML;

        EasyMock.expect(watchServiceMock.findAllWatches())
                .andReturn(Arrays.asList(watch1, watch2));

        EasyMock.replay(watchServiceMock);

        mockMvc.perform(MockMvcRequestBuilders.get("/watch")
                .accept(requestedMediaType))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(requestedMediaType))

                // first watch comparing
                .andExpect(MockMvcResultMatchers.xpath("/response/watch[1]/title")
                        .string(watch1.getTitle()))
                .andExpect(MockMvcResultMatchers.xpath("/response/watch[1]/description")
                        .string(watch1.getDescription()))
                .andExpect(MockMvcResultMatchers.xpath("/response/watch[1]/price")
                        .string(watch1.getPrice().toString()))
                .andExpect(MockMvcResultMatchers.xpath("/response/watch[1]/fountainImageBase64")
                        .string(watch1.getFountainImageBase64()))

                // second watch comparing
                .andExpect(MockMvcResultMatchers.xpath("/response/watch[2]/title")
                        .string(watch2.getTitle()))
                .andExpect(MockMvcResultMatchers.xpath("/response/watch[2]/description")
                        .string(watch2.getDescription()))
                .andExpect(MockMvcResultMatchers.xpath("/response/watch[2]/price")
                        .string(watch2.getPrice().toString()))
                .andExpect(MockMvcResultMatchers.xpath("/response/watch[2]/fountainImageBase64")
                        .string(watch2.getFountainImageBase64()));

        EasyMock.verify(watchServiceMock);
    }

    @Configuration
    public static class Context {

        @Bean
        public WatchService watchServiceMock() {
            return EasyMock.createMock(WatchService.class);
        }
    }
}