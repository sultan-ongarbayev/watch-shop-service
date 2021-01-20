package com.cleevio.watchshopservice.controller;

import com.cleevio.watchshopservice.controller.dto.WatchDto;
import com.cleevio.watchshopservice.controller.dto.WatchListDto;
import com.cleevio.watchshopservice.model.Watch;
import com.cleevio.watchshopservice.service.WatchService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Watch Rest API controller.
 */
@RestController
@RequestMapping("/watch")
public class WatchController {

    @Autowired
    private WatchService watchService;

    /**
     * Creates new watch and saves it in database.
     *
     * @param request request body
     * @return HTTP status, empty response body
     */
    @PostMapping
    public ResponseEntity<Void> createWatch(@RequestBody WatchDto request) {
        if (!validateRequest(request)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Watch model = convertDtoToModel(request);
        watchService.createWatch(model);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Returns all watches stored in database.
     *
     * @return list of all watches
     */
    @GetMapping(produces = {"application/json", "application/xml"})
    public ResponseEntity<WatchListDto> listWatches() {
        List<Watch> watches = watchService.findAllWatches();
        WatchListDto response = new WatchListDto();
        List<WatchDto> watchDtos = watches.stream()
                .map(this::convertModelToDto)
                .collect(Collectors.toList());
        response.setWatches(watchDtos);
        return ResponseEntity.ok(response);
    }

    private boolean validateRequest(WatchDto request) {
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

    private Watch convertDtoToModel(WatchDto dto) {
        Watch model = new Watch();
        model.setTitle(dto.getTitle());
        model.setDescription(dto.getDescription());
        model.setPrice(new BigInteger(dto.getPrice()));
        model.setFountainImageBase64(dto.getFountainImageBase64());
        return model;
    }

    private WatchDto convertModelToDto(Watch model) {
        WatchDto dto = new WatchDto();
        dto.setTitle(model.getTitle());
        dto.setDescription(model.getDescription());
        dto.setPrice(model.getPrice().toString());
        dto.setFountainImageBase64(model.getFountainImageBase64());
        return dto;
    }
}
