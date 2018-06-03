package org.bj.footballclubrestapi.controllers;

import org.bj.footballclubrestapi.model.ClubNotFoundException;
import org.bj.footballclubrestapi.model.FootballClub;
import org.bj.footballclubrestapi.repositories.FootballClubRepository;
import org.bj.footballclubrestapi.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


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

    @GetMapping("/{id}")
    public Resource<FootballClub> findById(@PathVariable int id) {
        Optional<FootballClub> footballClub = footballClubRepository.findById(id);
        if (!footballClub.isPresent())
            throw new ClubNotFoundException("id-" + id);

        //HATEOAS
        Resource<FootballClub> resource = new Resource<>(footballClub.get());
        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAll());
        resource.add(linkTo.withRel("all-users"));
        return resource;
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id) {
        footballClubRepository.deleteById(id);
    }



}
