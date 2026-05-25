package com.media.mediaproject.dto;

public class ResponseMessage {
    private String message;
    private String username;
    private String role;
    private String redirectUrl;
    private Long id;
    //    // Constructors
    public ResponseMessage(String message) {
        this.message = message;
    }

    public ResponseMessage(String message,  String username,String role, String redirectUrl,Long id) {
        this.message = message;
        this.role = role;
        this.username=username;
        this.redirectUrl = redirectUrl;
        this.id=id;
    }

	public Long getId() {
		return id;
	}

	public void setId( Long id) {
		this.id = id;
	}

	// Getters and Setters
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    
    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

    public String getRedirectUrl() { return redirectUrl; }
    public void setRedirectUrl(String redirectUrl) { this.redirectUrl = redirectUrl; }
}