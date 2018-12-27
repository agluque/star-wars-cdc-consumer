package com.thomascook.integration.poc.contracttesting.external;

import com.thomascook.integration.poc.contracttesting.external.model.Character;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Setter
public class CharacterService {

    private String providerUrl = "http://localhost:9090";

    public Character fetchCharacter(String characterName) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromUriString(providerUrl)
                .path("characters")
                .queryParam("id", characterName);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uriBuilder.toUriString(), Character.class);
    }

}
