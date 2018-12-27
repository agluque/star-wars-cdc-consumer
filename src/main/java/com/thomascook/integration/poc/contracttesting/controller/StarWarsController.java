package com.thomascook.integration.poc.contracttesting.controller;

import com.thomascook.integration.poc.contracttesting.external.CharacterService;
import com.thomascook.integration.poc.contracttesting.external.model.Character;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class StarWarsController {

    private final CharacterService characterService;

    @GetMapping("/rebels/{rebelName}")
    public Character getRebel(@PathVariable String rebelName) {
        return characterService.fetchCharacter(rebelName);
    }

}
