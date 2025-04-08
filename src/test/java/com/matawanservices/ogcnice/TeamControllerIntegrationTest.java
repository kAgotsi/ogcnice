package com.matawanservices.ogcnice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matawanservices.ogcnice.dtos.PlayerDTO;
import com.matawanservices.ogcnice.dtos.TeamDTO;
import com.matawanservices.ogcnice.repositories.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TeamControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void cleanUp() {
        teamRepository.deleteAll(); // delete all teams before each test
    }

    @Test
    void testCreateTeam() throws Exception {
        // create a DTO for the team
        TeamDTO teamDTO = new TeamDTO("Nice FC", "NFC", 1000000.0, null);

        // convert DTO to JSON
        String jsonContent = objectMapper.writeValueAsString(teamDTO);

        // Send request and check that the team is created
        mockMvc.perform(post("/api/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Nice FC"))
                .andExpect(jsonPath("$.acronym").value("NFC"))
                .andExpect(jsonPath("$.budget").value(1000000.0));
    }

    @Test
    void testCreateTeamWithPlayers() throws Exception {
        // Create a DTO for the team with players
        PlayerDTO player1 = new PlayerDTO("Player1", "Forward");
        PlayerDTO player2 = new PlayerDTO("Player2", "Defender");
        TeamDTO teamDTO = new TeamDTO("Nice FC", "NFC", 1000000.0, List.of(player1, player2));

        // Convert DTO to JSON
        String jsonContent = objectMapper.writeValueAsString(teamDTO);

        // Send request and check that the team is created with players
        mockMvc.perform(post("/api/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Nice FC"))
                .andExpect(jsonPath("$.acronym").value("NFC"))
                .andExpect(jsonPath("$.budget").value(1000000.0))
                .andExpect(jsonPath("$.players[0].name").value("Player1"))
                .andExpect(jsonPath("$.players[1].name").value("Player2"));
    }
}
