package edu.umd.peripatos;

import java.util.List;

public class Course {
	private Long id;
	private String name;
	private List<User> professors;
	private List<User> students;
	private List<Assignment> assignments;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<User> getProfessors() {
		return professors;
	}
	public void setProfessors(List<User> professors) {
		this.professors = professors;
	}
	public List<User> getStudents() {
		return students;
	}
	public void setStudents(List<User> students) {
		this.students = students;
	}
	public List<Assignment> getAssignments() {
		return assignments;
	}
	public void setAssignments(List<Assignment> assignments) {
		this.assignments = assignments;
	}
	
	
}
