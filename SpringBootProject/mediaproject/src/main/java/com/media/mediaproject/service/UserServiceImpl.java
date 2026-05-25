package com.media.mediaproject.service;

import com.media.mediaproject.enums.Role;
import com.media.mediaproject.model.Team;
import com.media.mediaproject.model.User;
import com.media.mediaproject.repository.TeamRepository;
import com.media.mediaproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private TeamRepository teamRepository;
    

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User updatedUser) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());
            user.setPassword(updatedUser.getPassword());
            return userRepository.save(user);
        }
        return null;
    }

    public String deleteUser(Long id) {
        userRepository.deleteById(id);
		return null;
    }

    public User changeUserRole(Long id, String role) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setRole(Role.valueOf(role));
            return userRepository.save(user);
        }
        return null;
    }

	@Override
	public List<User> getUsersByTeamName(String teamName) {
		Optional<Team> teamOptional= teamRepository.findByName(teamName); 
		if(teamOptional.isPresent()) {
			return teamOptional.get().getUsers();
			
		}else {
			return new ArrayList<>();
		}
	}

	@Override
	public List<User> getUsersByTeamId(Long teamId) {
		Optional<Team> teamOptional = teamRepository.findById(teamId);
		return teamOptional.map(Team::getUsers).orElse(Collections.emptyList());
	}
    
    
}