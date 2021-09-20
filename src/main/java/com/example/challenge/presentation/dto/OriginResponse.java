package com.example.challenge.presentation.dto;

import lombok.Value;

import java.util.List;

@Value
public class OriginResponse {
    String name;
    String url;
    String dimension;
    List<String> residents;
}
