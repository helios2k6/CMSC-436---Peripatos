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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "COURSE")
public class Course {
	private Long id;
	private String name;
	
	//Join values
	private List<User> users;
	private List<Assignment> assignments;
	
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
	@Column(name = "NAME")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@OneToMany(mappedBy = "COURSE", targetEntity = Assignment.class, cascade = CascadeType.ALL)
	@JoinTable(name = "COURSE_ASSIGNMENTS", joinColumns = {@JoinColumn(name = "COURSE_ID")}, inverseJoinColumns = {@JoinColumn(name = "ASSIGNMENT_ID")})
	public List<Assignment> getAssignments() {
		return assignments;
	}
	
	public void setAssignments(List<Assignment> assignments) {
		this.assignments = assignments;
	}
	
	@OneToMany(mappedBy = "COURSE", targetEntity = User.class, cascade = CascadeType.ALL)
	@JoinTable(name = "COURSE_USER", joinColumns = {@JoinColumn(name = "COURSE_ID")}, inverseJoinColumns = {@JoinColumn(name = "USER_ID")})
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}
