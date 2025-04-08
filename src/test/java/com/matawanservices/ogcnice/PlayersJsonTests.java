package com.matawanservices.ogcnice;


import com.matawanservices.ogcnice.models.Player;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class PlayersJsonTests {

    @Autowired
    private JacksonTester<Player> jsonTeamMember;

    @Test
    void shouldSerializePlayer() throws Exception {

        // Create a sample TeamMember object
        Player player = new Player();
        player.setId(1L);
        player.setName("Kasper Dolberg");
        player.setPosition("Forward");

        // Check if the JSON serialization is correct

        assertThat(jsonTeamMember.write(player)).isStrictlyEqualToJson("expected-player.json");
        assertThat(jsonTeamMember.write(player)).hasJsonPathStringValue("@.name");
        assertThat(jsonTeamMember.write(player)).hasJsonPathValue("@.name", "Kasper Dolberg");
        assertThat(jsonTeamMember.write(player)).hasJsonPathValue("@.position", "Forward");
    }

    @Test
    void shouldDeserializePlayer() throws Exception {
        String playerJson = """     
                {
                  "id": 1,
                  "name": "Kasper Dolberg",
                  "position": "Forward"
                }
                """;

        // Check if the JSON deserialization is correct
        assertThat(jsonTeamMember.parseObject(playerJson).getId()).isEqualTo(1L);
    }
}
