package edu.umd.peripatos;

import java.util.Date;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="assignment")
public class Assignment {
	private static final Random random = new Random();
	
	private Long id;
	private String name;
	private Date dueDate;
	
	//Join values
	private User user;
	private List<Question> questions;
	
	//Mandatory Relationships
	private List<Answer> answers;
	private List<Course> courses;
	
	public Assignment(){
		id = random.nextLong();
	}
	
	@Id
	@Column(name = "assignmentId")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@OneToMany
	@JoinTable(name = "course_assignment", joinColumns = {@JoinColumn(name = "assignmentId")}, 
	inverseJoinColumns = {@JoinColumn(name = "courseId")})	
	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	@ManyToOne
	@JoinTable(name = "user_assignment", 
	joinColumns = {@JoinColumn(name = "assignmentId")}, 
	inverseJoinColumns={@JoinColumn(name = "username")})
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "answer_assignment", 
	joinColumns = {@JoinColumn(name = "assignmentId")},
	inverseJoinColumns={@JoinColumn(name="answerId")})
	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	@NotNull
	@Column(name = "name")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@OneToMany
	@JoinTable(name = "assignment_question", joinColumns = {@JoinColumn(name = "assignmentId")}, 
	inverseJoinColumns = {@JoinColumn(name = "questionId")})
	public List<Question> getQuestions() {
		return questions;
	}
	
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	
	@NotNull
	@Column(name = "dueDate")
	@DateTimeFormat(style="yyyy-MM-dd")
	public Date getDueDate() {
		return dueDate;
	}
	
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	
	
}
