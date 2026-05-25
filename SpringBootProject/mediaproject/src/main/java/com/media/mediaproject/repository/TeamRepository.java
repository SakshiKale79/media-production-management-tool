package com.media.mediaproject.repository;

import com.media.mediaproject.model.Team;
import com.media.mediaproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

	
	 Optional<Team> findByName(String name);
    @Query("SELECT u FROM User u WHERE u.team.id = :teamId")
    List<User> findUsersByTeamId(@Param("teamId") Long teamId);
    
   
}