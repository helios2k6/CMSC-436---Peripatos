package edu.umd.peripatos;

import java.util.List;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "course")
public class Course {
	
	private static final Random random = new Random();
	
	private Long id;
	private String name;
	
	//Join values
	private List<User> users;
	private List<Assignment> assignments;
	
	public Course(){
		id = random.nextLong();
	}
	
	@Id
	@Column(name = "courseId")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
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
	@JoinTable(name = "course_assignment", joinColumns = {@JoinColumn(name = "courseId")}, 
	inverseJoinColumns = {@JoinColumn(name = "assignmentId")})
	public List<Assignment> getAssignments() {
		return assignments;
	}
	
	public void setAssignments(List<Assignment> assignments) {
		this.assignments = assignments;
	}
	
	@OneToMany
	@JoinTable(name = "course_user", joinColumns = {@JoinColumn(name = "courseId")}, 
	inverseJoinColumns = {@JoinColumn(name = "username")})
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}
