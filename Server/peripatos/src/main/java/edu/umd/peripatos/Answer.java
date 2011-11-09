package edu.umd.peripatos;

import org.joda.time.DateTime;

public class Answer {
	private Long id;
	private User user;
	private DateTime submissionDate;
	private Assignment assignment;
	private Question question;
	private String body;
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
	public DateTime getSubmissionDate() {
		return submissionDate;
	}
	public void setSubmissionDate(DateTime submissionDate) {
		this.submissionDate = submissionDate;
	}
	public Assignment getAssignment() {
		return assignment;
	}
	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
}
