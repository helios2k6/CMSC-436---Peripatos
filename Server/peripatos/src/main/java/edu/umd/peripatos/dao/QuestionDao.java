package edu.umd.peripatos.dao;

import java.util.List;

import edu.umd.peripatos.Question;
import edu.umd.peripatos.User;

public interface QuestionDao {
	public void store(Question question);
	public void delete(Question question);
	
	public Question getQuestionById(Long id);
	
	public List<Question> getQuestionsByUser(User user);
	public List<Question> getAllQuestions();
}
