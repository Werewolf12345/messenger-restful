package com.alexboriskin.messenger.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alexboriskin.messenger.database.Database;
import com.alexboriskin.messenger.models.Profile;

public class ProfileService {
    private Map<String, Profile> profiles = Database.getProfiles();

    public ProfileService() {
        Profile profile1 = new Profile(1L, "Ivan123", "Ivan", "Ivanov");
        Profile profile2 = new Profile(2L, "Sidor123", "Sidor", "Sidorov");
        
        profiles.put(profile1.getProfileName(), profile1);
        profiles.put(profile2.getProfileName(), profile2);
    }
    
    public List<Profile> getAllProfiles() {
        return new ArrayList<Profile>(profiles.values()); 
    }
    
    public Profile getProfile(String profileName) {
        return profiles.get(profileName);
    }
    
    public Profile addProfile(Profile profile) {
        profile.setId(profiles.size() + 1);
        profiles.put(profile.getProfileName(), profile);
        return profile;
    }
    
    public Profile updateProfile(Profile profile) {
        if (profile.getProfileName().isEmpty()) {
            return null;
        }
        profiles.put(profile.getProfileName(), profile);
        return profile;
    }
    
    public Profile removeProfile(String profileName) {
        return profiles.remove(profileName);
    }

}
