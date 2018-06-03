package org.bj.footballclubrestapi.controllers;

import org.bj.footballclubrestapi.model.FootballClub;
import org.bj.footballclubrestapi.repositories.FootballClubRepository;
import org.bj.footballclubrestapi.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("fbclubs")
public class FootballClubResources {
    private FootballClubRepository footballClubRepository;
    private PlayerRepository playerRepository;

    @Autowired
    public FootballClubResources(FootballClubRepository footballClubRepository, PlayerRepository playerRepository) {
        this.footballClubRepository = footballClubRepository;
        this.playerRepository = playerRepository;
    }

    @GetMapping
    public List<FootballClub> getAll()
    {
        return footballClubRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<FootballClub> addClub(@RequestBody FootballClub footballClub){
        FootballClub savedFootballClub= footballClubRepository.save(footballClub);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedFootballClub.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

}
