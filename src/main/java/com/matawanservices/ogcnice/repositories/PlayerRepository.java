package com.matawanservices.ogcnice.repositories;

import com.matawanservices.ogcnice.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
