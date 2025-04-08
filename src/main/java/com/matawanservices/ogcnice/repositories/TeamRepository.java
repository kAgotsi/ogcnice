package com.matawanservices.ogcnice.repositories;

import com.matawanservices.ogcnice.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface TeamRepository extends JpaRepository<Team, Long>, PagingAndSortingRepository<Team, Long> {

    /**
     * Check if a team exists by its name
     *
     * @param name the name of the team
     * @return true if the team exists, false otherwise
     */
     boolean existsByName(String name);
}
