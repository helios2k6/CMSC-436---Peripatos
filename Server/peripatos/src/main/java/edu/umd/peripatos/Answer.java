package edu.umd.peripatos;

import java.util.Date;
import java.util.Random;

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
@Table(name = "ANSWER")
public class Answer {

	private static final Random random = new Random();

	private Long id;
	private Date submissionDate;
	private String body;
	//Joined Values
	private User user;
	private Course course;
	private Assignment assignment;
	private Question question;

	public Answer(){
		id = random.nextLong();
	}

	@Id
	@Column(name = "ANSWER_ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotNull
	@ManyToOne
	@JoinTable(name = "USER_ANSWER", 
	joinColumns = {@JoinColumn(name = "ANSWER_ID")}, 
	inverseJoinColumns = {@JoinColumn(name="USERNAME")})
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@NotNull
	@Column(name = "DATE")
	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	@NotNull
	@OneToOne(targetEntity = Assignment.class)
	@JoinTable(name = "ANSWER_QUESTION_ASSIGNMENT_COURSE", 
	joinColumns = {@JoinColumn(name = "ANSWER_ID")},
	inverseJoinColumns={@JoinColumn(name="ASSIGNMENT_ID")})
	public Assignment getAssignment() {
		return assignment;
	}

	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}


	@NotNull
	@OneToOne(targetEntity = Question.class)
	@JoinTable(name = "ANSWER_QUESTION_ASSIGNMENT_COURSE", joinColumns = {@JoinColumn(name = "ANSWER_ID")},
	inverseJoinColumns={@JoinColumn(name="QUESTION_ID")	})
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
	@OneToOne(targetEntity = Course.class)
	@JoinTable(name = "ANSWER_QUESTION_ASSIGNMENT_COURSE", joinColumns = {@JoinColumn(name = "ANSWER_ID")},
	inverseJoinColumns={@JoinColumn(name="COURSE_ID")})
	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
}
