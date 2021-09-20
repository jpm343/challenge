package com.example.challenge.presentation.dto;

import lombok.Value;

@Value
public class CharacterResponse {
    Integer id;
    String name;
    String status;
    String type;
    String species;
    Integer episode_count;
    OriginResponse origin;
}
