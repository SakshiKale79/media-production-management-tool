package com.media.mediaproject.repository;

import com.media.mediaproject.model.Project;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> { 
	List<Project> findByManagerId(Long managerId);
}