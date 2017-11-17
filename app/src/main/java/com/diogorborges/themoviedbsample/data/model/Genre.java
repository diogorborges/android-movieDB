package com.diogorborges.themoviedbsample.data.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "name"
})
public class Genre {

    @JsonProperty("id")
    public Integer id;

    @JsonProperty("name")
    public String name;


}