package com.pct.urlshortener.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entity representing the mapping between an original URL and a shortened URL.
 * 
 * Original URL = baseUrl + relativeUrl
 * Shortened URL = baseUrl + '/' + urlCode
 * 
 * Constraints: The pairs (baseUrl, relativeUrl) and (baseUrl, urlCode) must be
 * unique.
 * 
 * 
 * Example:
 * Original URL: "https://www.portagecybertech.com/fr/a-propos/carrieres#postes"
 * Shortened URL: "https://www.portagecybertech.com/Rr4981eD0a"
 * 
 * baseUrl = "https://www.portagecybertech.com"
 * relativeUrl = "/fr/a-propos/carrieres#postes"
 * urlCode = "Rr4981eD0a"
 */
@Entity
@Table(name = "url_mappings", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "base_url", "relative_url" }),
        @UniqueConstraint(columnNames = { "base_url", "url_code" })
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UrlMapping {

    /**
     * Primary key (auto-generated).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The base URL of the URL.
     * Example: "https://www.portagecybertech.com"
     */
    @NotBlank(message = "The base URL name cannot be empty.")
    @Size(max = 253, message = "The base URL name is too long.")
    @Column(nullable = false, length = 253)
    private String baseUrl;

    /**
     * The relative URL of the original URL.
     * Example: "/fr/produits/outils-de-signature-numerique"
     */
    @NotNull
    @Size(max = 2048, message = "The relative URL is too long.")
    @Column(name = "relative_url", nullable = false, length = 2048)
    private String relativeUrl;

    /**
     * The URL code of the shortened URL, generated from the original URL.
     * Example: "0a1eDRr498"
     */
    @Size(max = 10, message = "The URL code is too long.")
    @Column(name = "url_code", length = 10)
    private String urlCode;

    public String getOriginalUrl() {
        return this.baseUrl + this.relativeUrl;
    }

    public String getShortenedUrl() {
        return this.baseUrl + "/" + this.urlCode;
    }

}
