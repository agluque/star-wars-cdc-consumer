package com.thomascook.integration.poc.contracttesting.external;

import com.thomascook.integration.poc.contracttesting.external.model.Planet;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Setter
public class PlanetService {

    private String providerUrl = "http://localhost:3000";

    public Planet fetchPlanetInfo(String planetName) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromUriString(providerUrl)
                .path("planets")
                .queryParam("name", planetName);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uriBuilder.toUriString(), Planet.class);
    }

}
