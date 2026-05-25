package com.media.mediaproject.model;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    @Column(name="discription")
    private String description;
    
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<User> members;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<Task> tasks;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<User> users;
    
	public List<Task> getTasks() {
		return tasks;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	// Constructors
    public Team() {}

    public Team(String name, String description) {
        this.name = name;
        this.description= description;
    }

    // Getters & Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public List<User> getMembers() { return members; }

    public void setMembers(List<User> members) { this.members = members; }
    
    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}