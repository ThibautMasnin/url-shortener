package com.pct.urlshortener.service;

/**
 * Service providing URL shortening and original URL retrieval methods.
 */
public interface UrlService {

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
    public String createShortenedUrl(String originalUrl);

    /**
     * Retrieve the original URL for the given shortened URL.
     * 
     * @param shortenedUrl the shortened URL
     * @return the original URL
     */
    public String getOriginalUrl(String shortenedUrl);

}
