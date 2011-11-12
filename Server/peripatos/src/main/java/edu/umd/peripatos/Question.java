package edu.umd.peripatos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "QUESTION")
public class Question {
	private Long id;
	private User user;
	private String title;
	private String body;
	private GeoLocation location;
	
	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinTable(name = "USER_QUESTION", joinColumns = {@JoinColumn(name = "USER_ID")})
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
	
	@Column(name = "LOCATION")
	public GeoLocation getLocation() {
		return location;
	}
	
	public void setLocation(GeoLocation location) {
		this.location = location;
	}
}
