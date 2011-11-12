package edu.umd.peripatos.dao;

import java.util.List;

import edu.umd.peripatos.Answer;
import edu.umd.peripatos.Assignment;
import edu.umd.peripatos.Course;
import edu.umd.peripatos.Question;
import edu.umd.peripatos.User;

public interface UserDao {
	/*
	 * Do not create, store, or delete users. This must be done by an administrator
	 */
	
	//Look these up in the join table
	public String getPasswordByUser(User user);
	public String getPasswordById(Long id);
	public String getPasswordByName(String name);
	
	public User findUserById(Long id);
	public User findUserByName(String name);
	
	public List<User> getAllUsers();
	
	public List<Course> getCoursesByUser(User user);
	public List<Assignment> getAssignmentsByUser(User user);
	public List<Question> getQuestionsByUser(User user);
	public List<Answer> getAnswersByUser(User user);
	
}
