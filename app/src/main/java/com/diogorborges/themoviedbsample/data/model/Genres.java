package com.diogorborges.themoviedbsample.data.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "genres",
        "id",
        "name"
})
public class Genres {

    @JsonProperty("genres")
    public List<Genre> genres = null;
    @JsonProperty("id")
    public int id;
    @JsonProperty("name")
    public String name;

}
