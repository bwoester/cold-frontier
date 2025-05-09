package de.bwoester.coldfrontier.auth;

import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.container.ContainerRequestContext;

/**
 * Resource for user profile management
 */
@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserRepository userRepository;

    /**
     * Returns current authenticated user profile
     */
    @GET
    @Path("/me")
    @Authenticated
    public Response getCurrentUser(@CookieParam("session_id") String sessionId, @Context ContainerRequestContext requestContext) {
        // The user is already validated by the AuthFilter
        User user = (User) requestContext.getProperty("user");
        
        return Response.ok(buildUserProfileResponse(user)).build();
    }
    
    /**
     * Updates current user's display name
     */
    @PUT
    @Path("/me/display-name")
    @Authenticated
    public Response updateDisplayName(
            @CookieParam("session_id") String sessionId,
            @Context ContainerRequestContext requestContext,
            String displayName) {
        
        User user = (User) requestContext.getProperty("user");
        
        // Simple validation
        if (displayName == null || displayName.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Display name cannot be empty\"}")
                    .build();
        }
        
        // Update the user's display name
        user.setDisplayName(displayName.trim());
        
        return Response.ok(buildUserProfileResponse(user)).build();
    }
    
    /**
     * Updates current user's username
     */
    @PUT
    @Path("/me/username")
    @Authenticated
    public Response updateUsername(
            @CookieParam("session_id") String sessionId,
            @Context ContainerRequestContext requestContext,
            String username) {
        
        User user = (User) requestContext.getProperty("user");
        
        // Simple validation
        if (username == null || username.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Username cannot be empty\"}")
                    .build();
        }
        
        // Update the user's username
        user.setUsername(username.trim());
        
        return Response.ok(buildUserProfileResponse(user)).build();
    }
    
    private String buildUserProfileResponse(User user) {
        return String.format(
            "{\"id\": \"%s\", \"username\": \"%s\", \"displayName\": \"%s\", \"email\": \"%s\", \"profilePicture\": \"%s\"}",
            user.getId(), user.getUsername(), user.getDisplayName(), user.getEmail(), user.getProfilePictureUrl());
    }
}
