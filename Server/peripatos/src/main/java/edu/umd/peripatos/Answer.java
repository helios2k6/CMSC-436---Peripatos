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

import org.joda.time.DateTime;

@Entity
@Table(name = "ANSWER")
public class Answer {
	private Long id;
	private User user;
	private DateTime submissionDate;
	private Course course;
	private Assignment assignment;
	private Question question;
	private String body;
	
	public Answer(){}
	
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
	
	@NotNull
	@ManyToOne
	@JoinTable(name = "USER_ANSWER", joinColumns = {@JoinColumn(name = "USER_ID")})
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	@NotNull
	@Column(name = "DATE")
	public DateTime getSubmissionDate() {
		return submissionDate;
	}
	
	public void setSubmissionDate(DateTime submissionDate) {
		this.submissionDate = submissionDate;
	}
	
	@NotNull
	@Column(name = "ASSIGNMENT")
	public Assignment getAssignment() {
		return assignment;
	}
	
	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}
	
	@NotNull
	@Column(name = "QUESTION")
	public Question getQuestion() {
		return question;
	}
	
	public void setQuestion(Question question) {
		this.question = question;
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
	@ManyToOne
	@JoinTable(name = "COURSE_ANSWER", joinColumns = {@JoinColumn(name = "COURSE_ID")})
	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
}
