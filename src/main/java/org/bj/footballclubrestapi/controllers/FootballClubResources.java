package org.bj.footballclubrestapi.controllers;

import lombok.extern.slf4j.Slf4j;
import org.bj.footballclubrestapi.models.FootballClub;
import org.bj.footballclubrestapi.models.NotFoundException;
import org.bj.footballclubrestapi.models.Player;
import org.bj.footballclubrestapi.repositories.FootballClubRepository;
import org.bj.footballclubrestapi.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


@RestController
@Slf4j
@RequestMapping("fbclubs")
public class FootballClubResources {

    private final FootballClubRepository footballClubRepository;
    private final PlayerRepository playerRepository;

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
    public ResponseEntity<FootballClub> addClub(@Valid @RequestBody FootballClub footballClub){
        FootballClub savedFootballClub= footballClubRepository.save(footballClub);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedFootballClub.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public Resource<FootballClub> findClubById(@PathVariable Integer id) {
        validateClubId(id);
        Optional<FootballClub> footballClub = footballClubRepository.findById(id);

        //HATEOAS
        Resource<FootballClub> resource = new Resource<>(footballClub.get());
        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAll());
        resource.add(linkTo.withRel("all-football-clubs"));
        return resource;
    }

    @DeleteMapping("/{id}")
    public void deleteClubById(@PathVariable Integer id) {
        validateClubId(id);
        footballClubRepository.deleteById(id);
    }

    //players

    @GetMapping("/{id}/players")
    public List<Player> getAllPlayers(@PathVariable Integer id) {
        validateClubId(id);
        Optional<FootballClub> footballClub = footballClubRepository.findById(id);
        return footballClub.get().getPlayers();
    }

    @PostMapping("/{id}/players")
    public ResponseEntity<Object> addPlayer(@Valid @PathVariable Integer id, @RequestBody Player player) {
        validateClubId(id);
        Optional<FootballClub> optionalFb = footballClubRepository.findById(id);
        FootballClub footballClub=optionalFb.get();

        // map player->football club
        player.setFootballClub(footballClub);
        playerRepository.save(player);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(player.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}/players/{idp}")
    public Resource<Player> findPlayerById(@PathVariable Integer id, @PathVariable Integer idp) {
        validateClubId(id);
        Optional<Player> player=playerRepository.findById(idp);
        if (!player.isPresent()) {
            throw new NotFoundException("Player not found");
        }
        //HATEOAS
        Resource<Player> resource = new Resource<>(player.get());
        ControllerLinkBuilder linkTo = linkTo(FootballClubResources.class);
        resource.add(linkTo.withRel("all-football-clubs"));
        return resource;
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<FootballClub> updateClub(@PathVariable Integer id, @RequestBody FootballClub footballClub){
        validateClubId(id);
        log.debug("Updating... football club id: "+id);
        Optional <FootballClub> optionalFootballClub = footballClubRepository.findById(id);
        FootballClub currentClub= optionalFootballClub.get();
        currentClub.setCoach(footballClub.getCoach());
        currentClub.setLeague(footballClub.getLeague());
        currentClub.setName(footballClub.getName());
      //  footballClubRepository.save(currentClub); @Transactional zbedne- dirty checking
        return new ResponseEntity<>(currentClub, HttpStatus.OK);





    }


    private void validateClubId(int id){
        this.footballClubRepository
                .findById(id)
                .orElseThrow(()-> new NotFoundException("Club not found id: "+id));
    }

}
