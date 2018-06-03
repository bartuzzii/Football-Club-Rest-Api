package org.bj.footballclubrestapi.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@ToString
@Getter @Setter
@Entity
public class FootballClub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Size(min = 2)
    String name;

    @Size(min = 2)
    String league;

    @Size(min = 2)
    String coach;

    @OneToMany(mappedBy = "footballClub")
    List<Player> players;

    public FootballClub(Integer id, String name, String coach) {
        this.id = id;
        this.name = name;
        this.coach = coach;
    }

    private FootballClub(){

    }
}
