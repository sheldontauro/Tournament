package com.drop.tournament.models;

import com.drop.tournament.util.CustomSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.bson.types.ObjectId;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Data
public class Match {

    @JsonSerialize(using = CustomSerializer.class)
    private ObjectId id;

    @NotNull
    private String homeTeam;

    @NotNull
    private String awayTeam;

    @NotNull
    private int matchNumber;

    @NotNull // score format -> $home_team_score-$away_team_score
    private String score;

}
