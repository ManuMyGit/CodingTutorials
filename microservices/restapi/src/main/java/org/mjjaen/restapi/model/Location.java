package org.mjjaen.restapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Location {
    private String ip;
    private String version;
    private String city;
    private String region;
    @JsonProperty("region_code")
    private String regionCode;
    @JsonProperty("country_code")
    private String countryCode;
    @JsonProperty("country_code_iso3")
    private String countryCodeIso3;
    @JsonProperty("country_name")
    private String countryName;
    @JsonProperty("country_capital")
    private String countryCapital;
    @JsonProperty("country_tld")
    private String countryTld;
    @JsonProperty("continent_code")
    private String continentCode;
    @JsonProperty("in_eu")
    private boolean inEu;
    private String postal;
    private Double latitude;
    private Double longitude;
    private String timezone;
    @JsonProperty("utc_offset")
    private String utcOffset;
    @JsonProperty("country_calling_code")
    private String countryCallingCode;
    private String currency;
    @JsonProperty("currency_name")
    private String currencyName;
    private String languages;
    @JsonProperty("country_area")
    private Double countryArea;
    @JsonProperty("country_population")
    private Long countryPopulation;
    private String asn;
    private String org;
    private String hostname;
}