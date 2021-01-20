package com.cleevio.watchshopservice.controller.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

/**
 * List of watches DTO.
 */
@Data
@JacksonXmlRootElement(localName = "response")
public class WatchListDto {

    /**
     * Watches.
     */
    @JacksonXmlProperty(localName = "watch")
    @JacksonXmlElementWrapper(useWrapping = false)
    List<WatchDto> watches;
}
