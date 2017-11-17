package com.diogorborges.themoviedbsample.data.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "name",
        "poster_path",
        "backdrop_path"
})
public class BelongsToCollection {

    @JsonProperty("id")
    public int id;
    @JsonProperty("name")
    public String name;
    @JsonProperty("poster_path")
    public String posterPath;
    @JsonProperty("backdrop_path")
    public String backdropPath;

}
