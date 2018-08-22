package org.bj.footballclubrestapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bj.footballclubrestapi.models.FootballClub;
import org.bj.footballclubrestapi.models.Player;
import org.bj.footballclubrestapi.repositories.FootballClubRepository;
import org.bj.footballclubrestapi.repositories.PlayerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FootballClubResourcesTest {

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private MockMvc mockMvc;
    @Mock
    FootballClubRepository footballClubRepository;

    @Mock
    PlayerRepository playerRepository;

    @InjectMocks
    FootballClubResources footballClubResources;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(footballClubResources).build();
    }

    @Test
    public void getAll() throws Exception {
        List<FootballClub> footballClubs = new ArrayList<>();
        footballClubs.add(new FootballClub(1,"Arsenal","Kowalski"));
        footballClubs.add(new FootballClub(2,"Chelsea","Sarri"));

        when(footballClubRepository.findAll()).thenReturn(footballClubs);
        mockMvc.perform(get("/fbclubs").contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Arsenal")))
                .andExpect(jsonPath("$[0].coach", is("Kowalski")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Chelsea")))
                .andExpect(jsonPath("$[1].coach", is("Sarri")));
        verify(footballClubRepository,times(1)).findAll();
    }

    @Test
    public void addClub() throws Exception {
        FootballClub footballClub = new FootballClub(1,"Chelsea","Sarri");

        when(footballClubRepository.save(any(FootballClub.class))).thenReturn(footballClub);
        mockMvc.perform(post("/fbclubs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(footballClub)))
                .andExpect(status().isCreated());
        verify(footballClubRepository,times(1)).save(any(FootballClub.class));


    }

    @Test
    public void findClubById() throws Exception {
        Optional<FootballClub> footballClub = Optional.of(new FootballClub(1,"Chelsea","Sarri"));

        when(footballClubRepository.findById(1)).thenReturn(footballClub);
        mockMvc.perform(get("/fbclubs/{id}",1).contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(1)));
        verify(footballClubRepository,times(2)).findById(1);
    }

    @Test
    public void deleteClubById() throws Exception {
        Optional<FootballClub> footballClub = Optional.of(new FootballClub(1,"Chelsea","Sarri"));

        when(footballClubRepository.findById(1)).thenReturn(footballClub);
        mockMvc.perform(delete("/fbclubs/{id}",1).contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(footballClubRepository,times(1)).deleteById(1);

    }



    @Test
    public void addPlayer() throws Exception {
        Optional<FootballClub> footballClub = Optional.of(new FootballClub(1,"Chelsea","Sarri"));
        Optional<Player> player = Optional.of(new Player(1,"Hazard",10,"Winger"));

        when(footballClubRepository.findById(1)).thenReturn(footballClub);
        when(playerRepository.save(any(Player.class))).thenReturn(player.get());
        mockMvc.perform(post("/fbclubs/1/players").contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(player.get())))
                .andExpect(status().isCreated());
        verify(playerRepository,times(1)).save(any(Player.class));

    }

    @Test
    public void findPlayerById() throws Exception {
        Optional<FootballClub> footballClub = Optional.of(new FootballClub(1,"Chelsea","Sarri"));
        Optional<Player> player = Optional.of(new Player(1,"Hazard",10,"Winger"));

        when(footballClubRepository.findById(1)).thenReturn(footballClub);
        when(playerRepository.findById(1)).thenReturn(player);
        mockMvc.perform(get("/fbclubs/1/players/1").contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Hazard")))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.shirtNumber", is(10)))
                .andExpect(jsonPath("$.position", is("Winger")));

        verify(playerRepository,times(1)).findById(1);
    }

    @Test
    public void updateClub() throws Exception {

        FootballClub footballClub = new FootballClub(1,"Chelsea","Kowalski");
        FootballClub updateClub = new FootballClub(footballClub.getId(),footballClub.getName(),footballClub.getCoach());

        when(footballClubRepository.findById(1)).thenReturn(Optional.of(footballClub));
        when(footballClubRepository.save(any(FootballClub.class))).thenReturn(updateClub);
        mockMvc.perform(put("/fbclubs/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(footballClub)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Chelsea")))
                .andExpect(jsonPath("$.coach", is("Kowalski")));

    }



    @Test(expected = Exception.class)
    public void testClubNotFound() throws Exception {
        when(footballClubRepository.findById(12)).thenReturn(null);
        mockMvc.perform(get("/fbclubs/{id}", 12))
                .andExpect(status().isNotFound());
    }
}