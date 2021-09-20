package com.example.challenge.integration;

import com.example.challenge.application.CharacterService;
import io.swagger.models.HttpMethod;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

@ExtendWith(MockServerExtension.class)
public class CharacterServiceIT {
    private CharacterService characterService;

    private final WebClient webClient;
    private final ClientAndServer clientAndServer;

    public CharacterServiceIT(ClientAndServer clientAndServer) {
        this.clientAndServer = clientAndServer;
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:" + clientAndServer.getPort())
                .build();
    }

    @BeforeEach
    public void setup() {
        characterService = new CharacterService(webClient);
    }

    @AfterEach
    public void reset() {
        clientAndServer.reset();
    }

    @Test
    public void callCharacterAndLocationToBuildResponse() {

        //first request and response for character
        HttpRequest expectedCharacterRequest = HttpRequest.request()
                .withMethod(HttpMethod.GET.name());

        this.clientAndServer.when(
                expectedCharacterRequest
        ).respond(
                HttpResponse.response()
                .withBody("{\"id\":1,\"name\":\"Rick Sanchez\",\"status\":\"Alive\",\"species\":\"Human\",\"type\":\"\",\"gender\":\"Male\",\"origin\":{\"name\":\"Earth (C-137)\",\"url\":\"https://rickandmortyapi.com/api/location/1\"},\"location\":{\"name\":\"Earth (Replacement Dimension)\",\"url\":\"https://rickandmortyapi.com/api/location/20\"},\"image\":\"https://rickandmortyapi.com/api/character/avatar/1.jpeg\",\"episode\":[\"https://rickandmortyapi.com/api/episode/1\"],\"url\":\"https://rickandmortyapi.com/api/character/1\",\"created\":\"2017-11-04T18:48:46.250Z\"}")
                .withContentType(MediaType.APPLICATION_JSON)
        );

        //second request and response for location
        HttpRequest expectedLocationRequest = HttpRequest.request()
                .withMethod(HttpMethod.GET.name())
                .withPath("/api/location/1");

        this.clientAndServer.when(
                expectedLocationRequest
        ).respond(
                HttpResponse.response()
                .withBody("{\"id\":1,\"name\":\"Earth (C-137)\",\"type\":\"Planet\",\"dimension\":\"Dimension C-137\",\"residents\":[\"https://rickandmortyapi.com/api/character/394\"],\"url\":\"https://rickandmortyapi.com/api/location/1\",\"created\":\"2017-11-10T12:42:04.162Z\"}")
                .withContentType(MediaType.APPLICATION_JSON)
        );

        //verify episode count calculation
        StepVerifier.create(this.characterService.getCharacter(1))
                .expectNextMatches(characterResponse ->
                        characterResponse.getEpisode_count().equals(1)
                )
                .verifyComplete();
    }


}
