package com.diogorborges.themoviedbsample.data.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "id"
})
public class ProductionCompany {

    @JsonProperty("name")
    public String name;
    @JsonProperty("id")
    public int id;

}
