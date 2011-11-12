package edu.umd.peripatos.dao;

import java.util.List;

import edu.umd.peripatos.Assignment;
import edu.umd.peripatos.Course;
import edu.umd.peripatos.User;

public interface CourseDao {
	public void store(Course course);
	public void delete(Course course);
	
	public Course getCourseById(Long id);
	
	public List<User> getUsersByCourse(Course course);
	
	//Manage Assignments for the Course here
	//Add Assignment
	public void addAssignment(Course course, Assignment assignment);
	
	//Remove Assignment
	public void removeAssignment(Course course, Assignment assignment);
	
}
