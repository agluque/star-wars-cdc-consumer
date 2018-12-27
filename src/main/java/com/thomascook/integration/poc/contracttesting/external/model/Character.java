package com.thomascook.integration.poc.contracttesting.external.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Character {

    private String name;

    private String height;

    private String mass;

}
