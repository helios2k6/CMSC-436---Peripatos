package edu.umd.peripatos.dao;

import java.util.Collection;
import java.util.List;

import edu.umd.peripatos.Assignment;
import edu.umd.peripatos.Question;
import edu.umd.peripatos.User;

public interface AssignmentDao {
	//Usual stuff
	public void store(Assignment assignment);
	public void delete(Assignment assignment);
	
	//Searching
	public Assignment findAssignmentById(Long id);
	public List<Assignment> findAssignmentsByName(String name);
	
	//Get all assignments by author
	public List<Assignment> findAssignmentByUser(User user);
	
	//Assignment --> Question Relationship
	public void addQuestionToAssignment(Assignment assignment, Question question);
	public void removeQuestionFromAssignment(Assignment assignment, Question question);
	
	//Support functions for editing and creating an assignment
	public void addQuestionsToAssignment(Assignment assignment, Collection<Question> questions);
	public void removeQuestionsFromAssignment(Assignment assignment, Collection<Question> questions);
	
	//Get all assignments no matter what
	public List<Assignment> getAllAssignments();
}
