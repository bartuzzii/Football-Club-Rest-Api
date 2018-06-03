package org.bj.footballclubrestapi.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@ToString
@Getter @Setter
@Entity
public class FootballClub {
    @Id
    @GeneratedValue
    Integer id;
    String name;
    String league;
    String coach;
    @OneToMany(mappedBy = "footballClub")

    List<Player> players;

    private FootballClub(){

    }

    public FootballClub(Integer id, String name, String coach) {
        this.id = id;
        this.name = name;
        this.coach = coach;
    }
}
