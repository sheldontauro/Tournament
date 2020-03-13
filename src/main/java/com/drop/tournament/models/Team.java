package com.drop.tournament.models;

import com.drop.tournament.util.CustomSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.bson.types.ObjectId;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Data
public class Team {

    @JsonSerialize(using = CustomSerializer.class)
    private ObjectId id;

    @NotNull
    private ArrayList<Integer> matches;

    @NotNull
    private ArrayList<String> players;

    @NotNull
    private String name;

}
