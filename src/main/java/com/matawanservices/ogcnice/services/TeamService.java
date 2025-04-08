package com.matawanservices.ogcnice.services;

import com.matawanservices.ogcnice.models.Team;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TeamService {

    Team save(Team team);

    List<Team> findAll(Pageable pageable);
}
