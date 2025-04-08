package com.matawanservices.ogcnice.controllers;

import com.matawanservices.ogcnice.dtos.TeamDTO;
import com.matawanservices.ogcnice.models.Player;
import com.matawanservices.ogcnice.models.Team;
import com.matawanservices.ogcnice.services.TeamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    @Autowired
    TeamService teamService;

    /**
     * Find all teams in the database
     *
     * @param pageable pagination information
     * @return a list of teams
     */
    @GetMapping
    ResponseEntity<List<Team>> findAll(Pageable pageable) {
        final List<Team> teams = teamService.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize()
                )
        );
        return ResponseEntity.ok().body(teams);
    }

    /**
     * Save a team in the database
     *
     * @param teamDTO team to save
     * @return the saved team
     */
    @PostMapping
    ResponseEntity<Team> save(@Valid @RequestBody TeamDTO teamDTO) {
        // Conversion du DTO en entité Team
        Team team = new Team();
        team.setName(teamDTO.name());
        team.setAcronym(teamDTO.acronym());
        team.setBudget(teamDTO.budget());

        // Conversion de la liste de PlayerDTO en entité Player et ajout à l'équipe
        if (teamDTO.players() != null) {
            List<Player> players = teamDTO.players().stream()
                    .map(playerDTO -> {
                        Player player = new Player();
                        player.setName(playerDTO.name());
                        player.setPosition(playerDTO.position());
                        player.setTeam(team);
                        return player;
                    })
                    .collect(Collectors.toList());
            team.setPlayers(players);
        }

        final Team savedTeam = teamService.save(team);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTeam);
    }

}
