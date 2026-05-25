package com.media.mediaproject.service;

import com.media.mediaproject.model.Team;

import java.util.List;
import java.util.Optional;

public interface TeamService {
    List<Team> getAllTeams();
    Optional<Team> getTeamById(Long id);
    Team createTeam(Team team);
    Team updateTeam(Team team);
}