package com.media.mediaproject.service;
import com.media.mediaproject.model.User;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id);
    User createUser(User user);
    User updateUser(Long id, User user);
    String deleteUser(Long id);
    User changeUserRole(Long id, String role);
    List<User> getUsersByTeamName(String teamName);
    List<User> getUsersByTeamId(Long teamId);
}