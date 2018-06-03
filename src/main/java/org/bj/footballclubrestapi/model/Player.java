package org.bj.footballclubrestapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@ToString(exclude ="footballClub")
@Getter @Setter
@Entity
public class Player {
    @Id
    @GeneratedValue
    Integer id;

    String name;
    int shirtNumber;
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
