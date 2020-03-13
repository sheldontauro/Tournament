package com.drop.tournament.models;

import com.drop.tournament.util.CustomSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.bson.types.ObjectId;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Data
public class Player {

    @JsonSerialize(using = CustomSerializer.class)
    private ObjectId id;

    @NotNull
    private String name;

    @NotNull
    private String teamName;

    @NotNull
    @Min(1)
    @Max(99)
    private double age;

}
