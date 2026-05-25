package com.media.mediaproject.controller;

import com.media.mediaproject.model.Team;
import com.media.mediaproject.model.User;
import com.media.mediaproject.service.TeamService;
import com.media.mediaproject.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teams")
@CrossOrigin(origins = "http://localhost:4200")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @Autowired
    private UserService userService;

    // ✅ View All Teams
    @GetMapping
    public ResponseEntity<List<Team>> getAllTeams() {
        return ResponseEntity.ok(teamService.getAllTeams());
    }

    // ✅ View Single Team by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getTeamById(@PathVariable Long id) {
        Optional<Team> team = teamService.getTeamById(id);
        return team.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ Create New Team
    @PostMapping("/create")
    public ResponseEntity<Team> createTeam(@RequestBody Team team) {
        Team savedTeam = teamService.createTeam(team);
        return ResponseEntity.ok(savedTeam);
    }

    // ✅ Update Team
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTeam(@PathVariable Long id, @RequestBody Team updatedTeam) {
        Optional<Team> teamOptional = teamService.getTeamById(id);
        if (teamOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Team team = teamOptional.get();
        team.setName(updatedTeam.getName());
        team.setDescription(updatedTeam.getDescription());

        return ResponseEntity.ok(teamService.updateTeam(team));
    }

    // ✅ View Members of a Team
    @GetMapping("/{teamId}/members")
    public ResponseEntity<List<User>> getTeamMembers(@PathVariable Long teamId) {
        return ResponseEntity.ok(userService.getUsersByTeamId(teamId));
    }
}