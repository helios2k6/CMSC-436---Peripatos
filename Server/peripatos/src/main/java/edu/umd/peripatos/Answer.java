package edu.umd.peripatos;

import java.util.Date;
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
@Table(name = "answer")
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
	@Column(name = "answerId")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotNull
	@ManyToOne
	@JoinTable(name = "user_answer", 
	joinColumns = {@JoinColumn(name = "answerId")}, 
	inverseJoinColumns = {@JoinColumn(name="username")})
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@NotNull
	@Column(name = "submissionDate")
	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	@NotNull
	@OneToOne(targetEntity = Assignment.class)
	@JoinTable(name = "answer_assignment", 
	joinColumns = {@JoinColumn(name = "answerId")},
	inverseJoinColumns={@JoinColumn(name="assignmentId")})
	public Assignment getAssignment() {
		return assignment;
	}

	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}


	@NotNull
	@OneToOne(targetEntity = Question.class, cascade=CascadeType.ALL)
	@JoinTable(name = "answer_question", joinColumns = {@JoinColumn(name = "answerId")},
	inverseJoinColumns={@JoinColumn(name="questionId")})
	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
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
	@OneToOne(targetEntity = Course.class)
	@JoinTable(name = "answer_course", joinColumns = {@JoinColumn(name = "answerId")},
	inverseJoinColumns={@JoinColumn(name="courseId")})
	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
}
