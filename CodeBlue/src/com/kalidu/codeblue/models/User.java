package com.kalidu.codeblue.models;

public class User {
	private int id;
	private String username;
	private String profileImageURL;
	private String gcmRegId;
	private int latitude;
	private int longitude;
	
	public User(int id, String username, String profileImageURL, String gcmRegId, int latitude, int longitude){
		this.setId(id);
		this.setUsername(username);
		this.setProfileImageURL(profileImageURL);
		this.setGcmRegId(gcmRegId);
		this.setLatitude(latitude);
		this.setLongitude(longitude);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getProfileImageURL() {
		return profileImageURL;
	}

	public void setProfileImageURL(String profileImageURL) {
		this.profileImageURL = profileImageURL;
	}

	public String getGcmRegId() {
		return gcmRegId;
	}

	public void setGcmRegId(String gcmRegId) {
		this.gcmRegId = gcmRegId;
	}

	public int getLatitude() {
		return latitude;
	}

	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}

	public int getLongitude() {
		return longitude;
	}

	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}
}
