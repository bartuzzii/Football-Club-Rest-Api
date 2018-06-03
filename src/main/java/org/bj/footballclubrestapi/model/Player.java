package org.bj.footballclubrestapi.model;

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
    Integer id;

    @Size(min = 2)
    String name;

    @Min(value = 1) @Max(value = 99)
    int shirtNumber;

    @Size(min = 2)
    String position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    FootballClub footballClub;

    public Player(Integer id,String name, int shirtNumber, String position) {
        this.id = id;
        this.name = name;
        this.shirtNumber = shirtNumber;
        this.position = position;
    }
    private Player(){

    }
}
