package com.matawanservices.ogcnice;


import com.matawanservices.ogcnice.models.Player;
import com.matawanservices.ogcnice.models.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class TeamJsonTests {

    @Autowired
    private JacksonTester<Team> jsonTeam;

    @Test
    void shouldSerializeTeam() throws Exception {
        // Create players
        Player player1 = new Player();
        player1.setId(1L);
        player1.setName("Kasper Dolberg");
        player1.setPosition("Forward");

        Player player2 = new Player();
        player2.setId(2L);
        player2.setName("Amine Gouiri");
        player2.setPosition("Forward");

        // Create a Team
        Team team = new Team(1L, "OGC Nice", "OGCN", 50000000.0, List.of(player1, player2));

        // Check if the JSON serialization is correct
        assertThat(jsonTeam.write(team)).isStrictlyEqualToJson("expected-team.json");

    }

    @Test
    void shouldDeserializeTeam() throws Exception {
        String teamJson = """
                {
                  "id": 1,
                  "name": "OGC Nice",
                  "acronym": "OGCN",
                  "budget": 50000000.0,
                  "players": [
                    {
                      "id": 1,
                      "name": "Kasper Dolberg",
                      "position": "Forward"
                    },
                    {
                      "id": 2,
                      "name": "Amine Gouiri",
                      "position": "Forward"
                    }
                  ]
                }
                """;

        // Check if the JSON deserialization is correct
        assertThat(jsonTeam.parseObject(teamJson).getId()).isEqualTo(1L);
        assertThat(jsonTeam.parseObject(teamJson).getAcronym()).isEqualTo("OGCN");
    }
}
