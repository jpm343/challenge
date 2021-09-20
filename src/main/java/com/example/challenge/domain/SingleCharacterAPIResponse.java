package com.example.challenge.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SingleCharacterAPIResponse {
    Integer id;
    String name;
    String status;
    String species;
    String type;
    String gender;
    Location origin;
    Location location;
    String image;
    List<String> episode;
    String url;
    String created;

    public String getOriginUrl() {
        return origin.getUrl();
    }
}

@NoArgsConstructor
@AllArgsConstructor
@Getter
class Location {
    String name;
    String url;
}
