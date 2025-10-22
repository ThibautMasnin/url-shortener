package com.pct.urlshortener.service.impl;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.pct.urlshortener.entity.UrlMapping;
import com.pct.urlshortener.repository.UrlRepository;
import com.pct.urlshortener.service.UrlService;
import com.pct.urlshortener.util.Base62Encoder;

import lombok.RequiredArgsConstructor;

/**
 * Implementation of {@link UrlService}.
 * 
 * {@inheritDoc}
 */
@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {

    /**
     * Repository for {@link UrlMapping} entities management.
     */
    private final UrlRepository urlRepository;

    /**
     * Utility for Base62 encoding.
     */
    private final Base62Encoder base62Encoder;

    /**
     * {@inheritDoc}
     */
    @Override
    public String createShortenedUrl(String originalUrl) {
        URI uri = stringToURI(originalUrl);
        String baseUrl = extractBaseUrl(uri);
        String relativeUrl = extractRelativeUrl(uri);

        // Return the shortened URL if already exists
        // Else create the URL mapping and return the shortened URL
        return urlRepository.findByBaseUrlAndRelativeUrl(baseUrl, relativeUrl)
                .map(UrlMapping::getShortenedUrl)
                .orElseGet(() -> createUrlMapping(baseUrl, relativeUrl));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOriginalUrl(String shortenedUrl) {
        URI uri = stringToURI(shortenedUrl);
        String baseUrl = extractBaseUrl(uri);
        String urlCode = extractUrlCode(uri);

        // Return the original URL if found
        // Else throw NOT_FOUND exception
        return urlRepository.findByBaseUrlAndUrlCode(baseUrl, urlCode)
                .map(UrlMapping::getOriginalUrl)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No original URL found for this shortened URL: " + shortenedUrl));
    }

    /**
     * Creates a new URL mapping and returns the shortened URL.
     * 
     * @param baseUrl     the base URL
     * @param relativeUrl the relative URL
     * @return the shortened URL
     */
    private String createUrlMapping(String baseUrl, String relativeUrl) {
        // Create the URL mapping
        UrlMapping newUrl = new UrlMapping(null, baseUrl, relativeUrl, null);
        urlRepository.save(newUrl);

        // Set the URL code
        String urlCode = base62Encoder.encode(newUrl.getId());
        newUrl.setUrlCode(urlCode);
        urlRepository.save(newUrl);

        return newUrl.getShortenedUrl();
    }

    /**
     * Converts a String to a URI.
     * 
     * Throw BAD_REQUEST exception if the format is invalid.
     * 
     * @param url the URL string
     * @return the URI
     */
    private URI stringToURI(String url) {
        // URI format: [scheme://authority:port/path?query#fragment]
        try {
            URI uri = new URI(url);

            // Throw BAD_REQUEST exception if URL is not valid
            if (uri.getScheme() == null || uri.getAuthority() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid URL format: " + url);
            }

            return uri;
        } catch (URISyntaxException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid URL format: " + url, e);
        }
    }

    /**
     * Extracts the base URL from a URI.
     * 
     * @param uri the URI
     * @return the base URL
     */
    private String extractBaseUrl(URI uri) {
        return uri.getScheme() + "://" + uri.getAuthority();
    }

    /**
     * Extracts the relative URL from a URI.
     * 
     * @param uri the URI
     * @return the relative URL
     */
    private String extractRelativeUrl(URI uri) {
        String relativeUrl = "";
        if (uri.getPath() != null) {
            relativeUrl += uri.getPath();
        }
        if (uri.getQuery() != null) {
            relativeUrl += "?" + uri.getQuery();
        }
        if (uri.getFragment() != null) {
            relativeUrl += "#" + uri.getFragment();
        }
        return relativeUrl;
    }

    /**
     * Extracts the URL code from a URI.
     * 
     * Throw BAD_REQUEST exception if the format is invalid.
     * 
     * @param uri the URI
     * @return the URL code
     */
    private String extractUrlCode(URI uri) {
        // Throw BAD_REQUEST exception if URL code is not present
        if (uri.getPath() == null || uri.getPath().length() < 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Invalid shortened URL format: " + uri.toString());
        }
        return uri.getPath().substring(1);
    }
}
