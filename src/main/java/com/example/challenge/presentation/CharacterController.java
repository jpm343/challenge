package com.example.challenge.presentation;

import com.example.challenge.application.CharacterService;
import com.example.challenge.presentation.dto.CharacterResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/characters")
public class CharacterController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CharacterController.class);

    private final CharacterService service;

    public CharacterController(CharacterService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a single character by id")
    public Mono<CharacterResponse> getCharacter(final @PathVariable("id") Integer id) {
        LOGGER.info("getCharacter|request. id={}", id);
        return service.getCharacter(id).doOnSuccess(it -> LOGGER.info("getCharacter|response. character name={}", it.getName()));
    }
}
