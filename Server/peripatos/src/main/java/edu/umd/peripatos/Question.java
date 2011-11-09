package edu.umd.peripatos;

public class Question {
	private Long id;
	private User user;
	private String title;
	private String body;
	private GeoLocation location;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public GeoLocation getLocation() {
		return location;
	}
	public void setLocation(GeoLocation location) {
		this.location = location;
	}
}
