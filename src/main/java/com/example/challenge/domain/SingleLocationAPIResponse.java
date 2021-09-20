package com.example.challenge.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SingleLocationAPIResponse {
    Integer id;
    String name;
    String type;
    String dimension;
    List<String> residents;
    String url;
    String created;
}
