package com.pct.urlshortener.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pct.urlshortener.service.impl.UrlServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

/**
 * REST controller providing endpoints for URL shortening and original URL
 * retrieval.
 */
@RestController
@RequestMapping("/api/urls")
@RequiredArgsConstructor
public class UrlController {

    /**
     * Service providing URL shortening and original URL retrieval methods.
     */
    private final UrlServiceImpl urlService;

    /**
     * Create a shortened URL for the given URL.
     * 
     * If the mapping of the provided URL already exists, the shortened URL is
     * returned.
     * Otherwise, a new mapping is created and the shortened URL is returned.
     * 
     * @param originalUrl the original URL
     * @return the shortened URL
     */
    @PostMapping
    @Operation(summary = "Generate the shortened URL", description = "From a URL, create and return the shortened URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shortened URL generated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid URL format")
    })
    public ResponseEntity<String> createShortenedUrl(@RequestBody String originalUrl) {
        String shortCode = urlService.createShortenedUrl(originalUrl);
        return ResponseEntity.ok(shortCode);
    }

    /**
     * Retrieve the original URL for the given shortened URL.
     * 
     * @param shortenedUrl the shortened URL
     * @return the original URL
     */
    @GetMapping
    @Operation(summary = "Get the original URL", description = "From a shortened URL, return the original URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Original URL retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid URL format"),
            @ApiResponse(responseCode = "404", description = "Original URL not found")
    })
    public ResponseEntity<String> getOriginalUrl(@RequestParam String shortenedUrl) {
        String originalUrl = urlService.getOriginalUrl(shortenedUrl);
        return ResponseEntity.ok(originalUrl);
    }
}
