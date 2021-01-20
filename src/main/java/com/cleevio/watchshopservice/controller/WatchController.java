package com.cleevio.watchshopservice.controller;

import com.cleevio.watchshopservice.controller.dto.CreateWatchDto;
import com.cleevio.watchshopservice.model.Watch;
import com.cleevio.watchshopservice.service.WatchService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@RestController
public class WatchController {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private WatchService watchService;

    @PostMapping("/watch")
    public ResponseEntity<Void> createWatch(@RequestBody CreateWatchDto request) {
        if (!validateRequest(request)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Watch model = convertDtoToModel(request);
        watchService.createWatch(model);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private boolean validateRequest(CreateWatchDto request) {
        if (StringUtils.isEmpty(request.getTitle())) {
            return false;
        }

        if (StringUtils.isEmpty(request.getDescription())) {
            return false;
        }

        // price must be integer, not decimal
        if (!StringUtils.isNumeric(request.getPrice())) {
            return false;
        }

        if (StringUtils.isEmpty(request.getFountainImageBase64())) {
            return false;
        }

        return true;
    }

    private Watch convertDtoToModel(CreateWatchDto dto) {
        Watch model = new Watch();
        model.setTitle(dto.getTitle());
        model.setDescription(dto.getDescription());
        model.setPrice(new BigInteger(dto.getPrice()));
        model.setFountainImageBase64(dto.getFountainImageBase64());
        return model;
    }
}
