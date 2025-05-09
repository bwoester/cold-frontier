package de.bwoester.coldfrontier.auth;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a user in the Cold Frontier game
 */
public class User {
    
    private UUID id;
    private String username;
    private String email;
    private String displayName;
    private String profilePictureUrl;
    private String authProvider; // "google", "github", "facebook"
    private String authProviderId; // ID from the provider
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
    
    // Default constructor
    public User() {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
        this.lastLoginAt = LocalDateTime.now();
    }
    
    // Constructor for creating a user from OAuth provider data
    public User(String email, String displayName, String authProvider, String authProviderId) {
        this();
        this.email = email;
        this.displayName = displayName;
        this.authProvider = authProvider;
        this.authProviderId = authProviderId;
        
        // Generate a username from email (simplified)
        if (email != null && email.contains("@")) {
            this.username = email.substring(0, email.indexOf('@'));
        } else {
            this.username = "user" + this.id.toString().substring(0, 8);
        }
    }

    // Getters and setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getAuthProvider() {
        return authProvider;
    }

    public void setAuthProvider(String authProvider) {
        this.authProvider = authProvider;
    }

    public String getAuthProviderId() {
        return authProviderId;
    }

    public void setAuthProviderId(String authProviderId) {
        this.authProviderId = authProviderId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }
    
    /**
     * Updates the last login timestamp to the current time
     */
    public void updateLastLogin() {
        this.lastLoginAt = LocalDateTime.now();
    }
    
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", displayName='" + displayName + '\'' +
                ", authProvider='" + authProvider + '\'' +
                ", createdAt=" + createdAt +
                ", lastLoginAt=" + lastLoginAt +
                '}';
    }
}
