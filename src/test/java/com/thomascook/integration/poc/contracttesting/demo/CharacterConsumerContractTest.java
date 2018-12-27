package com.thomascook.integration.poc.contracttesting.demo;

import au.com.dius.pact.consumer.ConsumerPactBuilder;
import au.com.dius.pact.consumer.DefaultResponseValues;
import au.com.dius.pact.consumer.PactVerificationResult;
import au.com.dius.pact.consumer.dsl.PactDslResponse;
import au.com.dius.pact.model.MockProviderConfig;
import au.com.dius.pact.model.PactSpecVersion;
import au.com.dius.pact.model.RequestResponsePact;
import com.google.common.collect.ImmutableMap;
import com.thomascook.integration.poc.contracttesting.external.CharacterService;
import com.thomascook.integration.poc.contracttesting.external.model.Character;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static au.com.dius.pact.consumer.ConsumerPactRunnerKt.runConsumerTest;
import static io.pactfoundation.consumer.dsl.LambdaDsl.newJsonBody;
import static org.assertj.core.api.Assertions.assertThat;

public class CharacterConsumerContractTest extends AbstractConsumerContractTest {

    @Autowired
    private CharacterService characterPersonalInfoService;

    @DefaultResponseValues
    public void defaultResponseValues(PactDslResponse response) {
        Map<String, String> headers = ImmutableMap.of(
                "Content-Type", "application/json"
        );
        response.headers(headers);
    }

    @Test
    public void shouldBeAValidContractForCharacterPersonalInformation() {
        RequestResponsePact pact = ConsumerPactBuilder
                .consumer("star-wars-cdc-consumer")
                .hasPactWith("star-wars-cdc-character-provider")
                .given("A Star Wars character named Luke Skywalker and ID luke", ImmutableMap.of("id", "luke"))
                .uponReceiving("Fetch character personal information")
                .path("/characters")
                .query("id=luke")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(newJsonBody((root) -> {
                    root.stringValue("name", "Luke Skywalker");
                    root.stringValue("height", "172");
                    root.stringValue("mass", "77");
                }).build())
                .toPact();

        MockProviderConfig config = MockProviderConfig.createDefault(PactSpecVersion.V3);
        PactVerificationResult result = runConsumerTest(pact, config, mockServer -> {
            characterPersonalInfoService.setProviderUrl(mockServer.getUrl());
            Character personalInfo = characterPersonalInfoService.fetchCharacter("luke");

            assertThat(personalInfo.getName()).isEqualTo("Luke Skywalker");
            assertThat(personalInfo.getHeight()).isEqualTo("172");
            assertThat(personalInfo.getMass()).isEqualTo("77");
        });

        checkResult(result);
    }
}
