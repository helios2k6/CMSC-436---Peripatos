package edu.umd.peripatos.dao;

import edu.umd.peripatos.Assignment;
import edu.umd.peripatos.Course;

public interface CourseDao {
	public void store(Course course);
	public void delete(Course course);
	
	public Course findCourseById(Long id);
	
	//Manage Assignments for the Course here
	//Add Assignment
	public void addAssignment(Course course, Assignment assignment);
	
	//Remove Assignment
	public void removeAssignment(Course course, Assignment assignment);
	
}
