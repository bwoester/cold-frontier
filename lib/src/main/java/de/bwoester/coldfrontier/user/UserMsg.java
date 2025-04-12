package de.bwoester.coldfrontier.user;

import java.util.Collection;

public record UserMsg(String id, Collection<String> linkedIdentities, UserSettingsMsg userSettings, UserProfileMsg userProfile) {
}
