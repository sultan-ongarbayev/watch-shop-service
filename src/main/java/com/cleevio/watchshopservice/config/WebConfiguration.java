package com.cleevio.watchshopservice.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web application context configuration.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.cleevio.watchshopservice.controller")
public class WebConfiguration implements WebMvcConfigurer {
}
