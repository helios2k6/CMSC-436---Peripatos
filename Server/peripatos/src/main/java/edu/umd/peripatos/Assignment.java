package edu.umd.peripatos;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;

@Entity
@Table(name="ASSIGNMENT")
public class Assignment {
	private Long id;
	private String name;
	private DateTime dueDate;
	
	//Join values
	private User user;
	private List<Question> questions;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinTable(name = "USER_ASSIGNMENT", joinColumns = {@JoinColumn(name = "USER_ID")})
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	@NotNull
	@Column(name = "NAME")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@OneToMany(mappedBy = "ASSIGNMENT", targetEntity = Question.class, cascade = CascadeType.ALL)
	@JoinTable(name = "ASSIGNMENT_QUESTION", joinColumns = {@JoinColumn(name = "ASSIGNMENT_ID")}, inverseJoinColumns = {@JoinColumn(name = "QUESTION_ID")})
	public List<Question> getQuestions() {
		return questions;
	}
	
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	
	@NotNull
	@Column(name = "DATE")
	public DateTime getDueDate() {
		return dueDate;
	}
	
	public void setDueDate(DateTime dueDate) {
		this.dueDate = dueDate;
	}
	
	
}
