package de.bwoester.coldfrontier.auth;

import jakarta.enterprise.context.ApplicationScoped;

/**
 * Repository for managing user data
 * In a production environment, this would be connected to a database
 */
@ApplicationScoped
public class UserRepository {

//    // In-memory storage for users (would be a database in production)
//    private final Map<UUID, User> users = new ConcurrentHashMap<>();
//
//    // In-memory storage for sessions (would be a database or Redis in production)
//    private final Map<String, UUID> sessions = new ConcurrentHashMap<>();
//
//    /**
//     * Creates a new user session after OAuth authentication
//     *
//     * @param tokens The OAuth tokens from the provider
//     * @param provider The provider name (google, github, facebook)
//     * @return A session ID for the authenticated user
//     */
//    public String createUserSession(Tokens tokens, String provider) {
//        // Extract user information from the tokens
//        JsonObject userInfo = extractUserInfo(tokens, provider);
//
//        // Find existing user or create a new one
//        User user = findOrCreateUser(userInfo, provider);
//
//        // Update last login time
//        user.updateLastLogin();
//
//        // Create a session ID
//        String sessionId = UUID.randomUUID().toString();
//        sessions.put(sessionId, user.getId());
//
//        return sessionId;
//    }
//
//    /**
//     * Find a user by their session ID
//     *
//     * @param sessionId The session ID cookie value
//     * @return The User object or null if session is invalid
//     */
//    public User findUserBySessionId(String sessionId) {
//        UUID userId = sessions.get(sessionId);
//        if (userId == null) {
//            return null;
//        }
//        return users.get(userId);
//    }
//
//    /**
//     * Invalidates a user session
//     *
//     * @param sessionId The session ID to invalidate
//     */
//    public void invalidateSession(String sessionId) {
//        sessions.remove(sessionId);
//    }
//
//    /**
//     * Finds an existing user by provider ID or creates a new user
//     */
//    private User findOrCreateUser(JsonObject userInfo, String provider) {
//        String providerId = userInfo.getString("id");
//
//        // Try to find existing user
//        for (User user : users.values()) {
//            if (provider.equals(user.getAuthProvider()) &&
//                providerId.equals(user.getAuthProviderId())) {
//                return user;
//            }
//        }
//
//        // Create new user if not found
//        String email = userInfo.getString("email", "");
//        String name = userInfo.getString("name", "");
//        if (name.isEmpty()) {
//            name = userInfo.getString("login", ""); // For GitHub
//        }
//        String pictureUrl = userInfo.getString("picture", "");
//
//        User newUser = new User(email, name, provider, providerId);
//        newUser.setProfilePictureUrl(pictureUrl);
//
//        // Store user
//        users.put(newUser.getId(), newUser);
//
//        return newUser;
//    }
//
//    /**
//     * Extracts user information from OAuth tokens based on provider
//     */
//    private JsonObject extractUserInfo(Tokens tokens, String provider) {
//        // In a real implementation, you would make API calls to the provider
//        // to get user information based on the access token
//
//        // This is a simplified mock implementation
//        String accessToken = tokens.getAccessToken();
//
//        // For demo purposes, we'll create a fake user info object
//        // In production, this would come from the OAuth provider's API
//        JsonObject userInfo = new JsonObject();
//        userInfo.put("id", "oauth-" + UUID.randomUUID().toString().substring(0, 8));
//        userInfo.put("email", "user@example.com");
//        userInfo.put("name", "Commander " + provider.substring(0, 1).toUpperCase() + provider.substring(1));
//
//        if (provider.equals("google")) {
//            userInfo.put("picture", "https://ui-avatars.com/api/?name=G+U&background=4285F4&color=fff");
//        } else if (provider.equals("github")) {
//            userInfo.put("login", "GithubUser");
//            userInfo.put("picture", "https://ui-avatars.com/api/?name=G+H&background=24292e&color=fff");
//        } else if (provider.equals("facebook")) {
//            userInfo.put("picture", "https://ui-avatars.com/api/?name=F+B&background=3b5998&color=fff");
//        }
//
//        return userInfo;
//    }
}
