package com.matawanservices.ogcnice.services;

import com.matawanservices.ogcnice.exceptions.BusinessException;
import com.matawanservices.ogcnice.models.Team;
import com.matawanservices.ogcnice.repositories.TeamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    private static final Logger logger = LoggerFactory.getLogger(TeamServiceImpl.class.getName());

    @Autowired
    private TeamRepository repository;

    /**
     * save a team in the database
     *
     * @param team team to save
     * @return the saved team
     */
    public Team save(Team team) {
        logger.info("Enregistrement de l'équipe : {}", team);

        if (repository.existsByName(team.getName())) {
            logger.error("L'équipe avec le nom {} existe déjà", team.getName());
            throw new BusinessException("L'équipe avec le nom " + team.getName() + " existe déjà");
        }
        logger.info("Tentative de création de l'équipe : {}", team.getName());
        Team savedTeam = repository.save(team);
        logger.info("L'équipe '{}' a été enregistrée avec succès.", savedTeam.getName());

        return savedTeam;
    }

    /**
     * Find all teams in the database
     *
     * @param pageable pagination information
     * @return a list of teams
     */
    public List<Team> findAll(Pageable pageable) {
        try{
        logger.info("Tentative de récupération des équipes avec pagination et tri : page {} et taille {}", pageable.getPageNumber(), pageable.getPageSize());

        Sort sort = Sort.by(
                Sort.Order.asc("name"), // Tri par 'name' dans l'ordre croissant
                Sort.Order.asc("acronym"), // Tri par 'acronym' dans l'ordre croissant
                Sort.Order.asc("budget")   // Tri par 'budget' dans l'ordre croissant
        );
        Page<Team> page = repository.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        sort
                )
        );

        logger.info("Nombre d'équipes récupérées : {}", page.getTotalElements());
        return page.getContent();
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des équipes : {}", e.getMessage());
            throw new BusinessException("Erreur lors de la récupération des équipes : " + e.getMessage());
        }
    }
}
