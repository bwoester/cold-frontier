package de.bwoester.coldfrontier.user;

public record UserMsg(String id, UserSettingsMsg userSettings, UserProfileMsg userProfile) {
}
