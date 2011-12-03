package edu.umd.peripatos.dao;

import java.util.List;

import edu.umd.peripatos.Answer;
import edu.umd.peripatos.Course;
import edu.umd.peripatos.User;

public interface UserDao {
	/*
	 * Do not create, store, or delete users. This must be done by an administrator
	 */
	
	public User findUserByName(String name);
	
	public List<User> getAllUsers();
	
	public List<Course> getCoursesByUser(User user);
	public List<Answer> getAnswersByUser(User user);
	
}
