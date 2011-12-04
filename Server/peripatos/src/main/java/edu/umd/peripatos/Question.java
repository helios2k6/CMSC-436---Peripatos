package edu.umd.peripatos;

import java.util.Random;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "QUESTION")
public class Question {
	
	private static final Random random = new Random();
	
	private Long id;
	private String title;
	private String body;
	
	//Join Values
	private User user;
	private GeoLocation location;
	
	public Question(){
		id = random.nextLong();
	}
	
	@Id
	@Column(name = "QUESTION_ID")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinTable(name = "USER_QUESTION", joinColumns = {@JoinColumn(name = "QUESTION_ID")}, inverseJoinColumns = {@JoinColumn(name="USERNAME")})
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	@NotNull
	@Column(name = "TITLE")
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@NotNull
	@Column(name = "BODY")
	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	@NotNull
	@OneToOne(cascade=CascadeType.ALL)
	@JoinTable(name = "QUESTION_GEOLOCATION", joinColumns = {@JoinColumn(name="QUESTION_ID")}, inverseJoinColumns = {@JoinColumn(name = "GEOLOCATION_ID")})
	public GeoLocation getLocation() {
		return location;
	}
	
	public void setLocation(GeoLocation location) {
		this.location = location;
	}
}
