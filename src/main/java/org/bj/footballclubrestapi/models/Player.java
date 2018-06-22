package org.bj.footballclubrestapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@ToString(exclude ="footballClub")
@Getter @Setter
@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 2)
    private String name;

    @Min(value = 1) @Max(value = 99)
    private int shirtNumber;

    @Size(min = 2)
    private String position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private FootballClub footballClub;

    public Player(Integer id,String name, int shirtNumber, String position) {
        this.id = id;
        this.name = name;
        this.shirtNumber = shirtNumber;
        this.position = position;
    }
    private Player(){

    }
}
