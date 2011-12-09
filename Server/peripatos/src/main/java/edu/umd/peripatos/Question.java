package edu.umd.peripatos;

import java.util.List;
import java.util.Random;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "question")
public class Question {
	
	private static final Random random = new Random();
	
	private Long id;
	private String title;
	private String body;
	
	//Join Values
	private User user;
	private GeoLocation location;
	
	//Mandatory Mappings
	private List<Assignment> assignments;
	private List<Answer> answers;
	
	@OneToMany
	@JoinTable(name = "assignment_question", 
	joinColumns={@JoinColumn(name="questionId")}, 
	inverseJoinColumns={@JoinColumn(name="assignmentId")})
	public List<Assignment> getAssignments() {
		return assignments;
	}

	public void setAssignments(List<Assignment> assignments) {
		this.assignments = assignments;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "answer_question", 
	joinColumns={@JoinColumn(name="questionId")}, 
	inverseJoinColumns={@JoinColumn(name="answerId")})
	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public Question(){
		id = random.nextLong();
	}
	
	@Id
	@Column(name = "questionId")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinTable(name = "user_question", joinColumns = {@JoinColumn(name = "questionId")},
			inverseJoinColumns = {@JoinColumn(name="username")})
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	@NotNull
	@Column(name = "title")
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@NotNull
	@Column(name = "body")
	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	@NotNull
	@OneToOne
	@JoinTable(name = "question_geolocation", 
	joinColumns = {@JoinColumn(name="questionId")}, 
	inverseJoinColumns = {@JoinColumn(name = "geolocationId")})
	public GeoLocation getLocation() {
		return location;
	}
	
	public void setLocation(GeoLocation location) {
		this.location = location;
	}
}
