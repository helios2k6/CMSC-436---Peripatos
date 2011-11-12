package edu.umd.peripatos.dao;

import java.util.List;

import edu.umd.peripatos.Answer;
import edu.umd.peripatos.Assignment;
import edu.umd.peripatos.Course;
import edu.umd.peripatos.Question;
import edu.umd.peripatos.User;

public interface AnswerDao {
	//Usual stuff
	public void store(Answer answer);
	public void delete(Answer answer);
	
	public Answer getAnswerById(Long id);
	
	//Get a specific answer
	public Answer getAnswerByAssignmentAndUserAndQuestion(Assignment assignment, User user, Question question);
	
	//Get all the answers by a user that pertain to a certain assignment
	public List<Answer> getAnswerByAssignmentAndUserAndCourse(Assignment assignment, User user, Course course);
}
