package com.example.nac.dto;

public class RegisterRequest {

    private String fullName;
    private String username;
    private String password;
    private String email;
    private String roleName; // String now, matches DB role names

    public RegisterRequest() {}

    public RegisterRequest(String fullName, String username, String password, String email, String roleName) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.roleName = roleName;
    }

    // Getters & Setters
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }
}