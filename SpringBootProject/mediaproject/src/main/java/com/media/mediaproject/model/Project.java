package com.media.mediaproject.model;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity @Table(name = "projects") public class Project { @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

private String title;
private String description;
private String status; // e.g., "PENDING", "IN_PROGRESS", "COMPLETED"
private LocalDate deadline;

@ManyToOne
@JoinColumn(name = "manager_id")
private User manager;

@OneToOne
@JoinColumn(name = "team_id")
private Team team;


public Team getTeam() {
	return team;
}
public void setTeam(Team team) {
	this.team = team;
}
public Long getId() { return id; }
public void setId(Long id) { this.id = id; }

public String getTitle() { return title; }
public void setTitle(String title) { this.title = title; }

public String getDescription() { return description; }
public void setDescription(String description) { this.description = description; }

public String getStatus() { return status; }
public void setStatus(String status) { this.status = status; }

public User getManager() { return manager; }
public void setManager(User manager) { this.manager = manager; }

public LocalDate getDeadline() {
	return deadline;
}
public void setDeadline(LocalDate deadline) {
	this.deadline = deadline;
}

}