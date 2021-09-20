package com.example.challenge.application;

import com.example.challenge.domain.SingleCharacterAPIResponse;
import com.example.challenge.domain.SingleLocationAPIResponse;
import com.example.challenge.presentation.dto.CharacterResponse;
import com.example.challenge.presentation.dto.OriginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CharacterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CharacterService.class);

    private final WebClient rickAndMortyAPIWebClient;

    public CharacterService(WebClient rickAndMortyAPIWebClient) {
        this.rickAndMortyAPIWebClient = rickAndMortyAPIWebClient;
    }

    @Value("${rick-and-morty-api.single-character-url}")
    private String singleCharacterUrl;

    public Mono<CharacterResponse> getCharacter(Integer id) {
        LOGGER.trace("getCharacter|id={}", id);

        return this.rickAndMortyAPIWebClient.get()
                .uri(singleCharacterUrl + id.toString())
                .retrieve()
                .bodyToMono(SingleCharacterAPIResponse.class)
                .zipWhen(singleCharacterAPIResponse ->
                        rickAndMortyAPIWebClient.get()
                                .uri(singleCharacterAPIResponse.getOriginUrl())
                                .retrieve()
                                .bodyToMono(SingleLocationAPIResponse.class),
                        this::buildResponse
                        );


    }

    private CharacterResponse buildResponse(SingleCharacterAPIResponse character, SingleLocationAPIResponse location) {
        return new CharacterResponse(
                character.getId(),
                character.getName(),
                character.getStatus(),
                character.getType(),
                character.getSpecies(),
                character.getEpisode().size(),
                new OriginResponse(
                        location.getName(),
                        location.getUrl(),
                        location.getDimension(),
                        location.getResidents()
                )
        );
    }
}
