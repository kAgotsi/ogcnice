package com.matawanservices.ogcnice;

import com.matawanservices.ogcnice.exceptions.BusinessException;
import com.matawanservices.ogcnice.models.Team;
import com.matawanservices.ogcnice.repositories.TeamRepository;
import com.matawanservices.ogcnice.services.TeamServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class TeamServiceTest {
    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TeamServiceImpl teamService;

    private Team team;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialiser les mocks
        team = new Team();
        team.setName("Nice Fc");
        team.setAcronym("NFC");
        team.setBudget(1000000.0);
    }

    @Test
    void testSaveTeamSuccessfully() {
        // Mocking the behavior of the repository
        when(teamRepository.existsByName(team.getName())).thenReturn(false);
        when(teamRepository.save(team)).thenReturn(team);

        Team savedTeam = teamService.save(team);

        assertNotNull(savedTeam);
        assertEquals(team.getName(), savedTeam.getName());
        assertEquals(team.getAcronym(), savedTeam.getAcronym());
        assertEquals(team.getBudget(), savedTeam.getBudget());
    }

    @Test
    void testSaveTeamThrowsTeamAlreadyExistsException() {
        when(teamRepository.existsByName(team.getName())).thenReturn(true);

        // Test of exception raised when trying to save a team with an existing name
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            teamService.save(team);
        });

        assertEquals("L'équipe avec le nom " + team.getName() +" existe déjà" , exception.getMessage());
    }
}
