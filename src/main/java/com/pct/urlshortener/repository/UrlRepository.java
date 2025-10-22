package com.pct.urlshortener.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pct.urlshortener.entity.UrlMapping;

/**
 * Repository interface for {@link UrlMapping} entities management.
 */
public interface UrlRepository extends JpaRepository<UrlMapping, Long> {

    /**
     * Finds a mapping by its base URL and its relative URL.
     * 
     * @param baseUrl     the base URL
     * @param relativeUrl the relative URL
     * @return an optional {@link UrlMapping} entity if found
     */
    Optional<UrlMapping> findByBaseUrlAndRelativeUrl(String baseUrl, String relativeUrl);

    /**
     * Finds a mapping by its base URL and shortened code.
     * 
     * @param baseUrl the base URL
     * @param urlCode the short unique code
     * @return an optional {@link UrlMapping} entity if found
     */
    Optional<UrlMapping> findByBaseUrlAndUrlCode(String baseUrl, String urlCode);
}
