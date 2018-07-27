package org.Daemons.messenger.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.Daemons.messenger.database.DatabaseClass;
import org.Daemons.messenger.models.Profile;

public class ProfileService {


	private Map<String, Profile> profiles = DatabaseClass.getProfiles();
	
	public ProfileService() {
		profiles.put("manojmahijpr", new Profile(1L,"manojmahijpr","Manoj","Sharma"));
		profiles.put("sahuds95", new Profile(2L,"sahuds95","Deepak", "Sahu"));
	}
	
	
	public List<Profile> getAllProfiles() {
		return new ArrayList<Profile>(profiles.values());
	}
	
	public Profile getProfile(String profileName) {
		return profiles.get(profileName);
	}
	
	public Profile addProfile(Profile profile) {
		profile.setId(profiles.size()+1);
		if(profile.getCreated()==null)
			profile.setCreated(new Date());
		
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}
	public Profile updateProfile(Profile profile) {
		if(profile.getId() <= 0) {
			return null;
		}
		if(profile.getCreated()==null)
			profile.setCreated(new Date());
		profiles.put(profile.getProfileName(),profile);
		return profile;
	}
	public Profile removeProfile(String profileName) {
		return profiles.remove(profileName);
	}
}
