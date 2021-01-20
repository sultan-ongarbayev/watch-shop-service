package com.cleevio.watchshopservice.controller.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

/**
 * Create watch DTO object.
 */
@Data
public class WatchDto {

    /**
     * Watch title.
     */
    private String title;

    /**
     * Watch price.
     */
    private String price;

    /**
     * Watch description.
     */
    private String description;

    /**
     * Watch fountain image, base64 encoded.
     */
    private String fountainImageBase64;
}
