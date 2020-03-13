package com.drop.tournament;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter @Setter
public class TournamentConfiguration extends Configuration {
    @JsonProperty
    @NotNull
    public String mongoHost;

    @JsonProperty
    @Min(1)
    @Max(65535)
    public int mongoPort;

    @JsonProperty
    @NotNull
    public String mongoDB;

    @JsonProperty
    @NotNull
    public String collectionName;

    @JsonProperty
    @NotNull
    public String collectionName1;

    @JsonProperty
    @NotNull
    public String collectionName2;
}
