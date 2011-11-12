package edu.umd.peripatos.hibernate;

import java.util.Collection;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import edu.umd.peripatos.Assignment;
import edu.umd.peripatos.Question;
import edu.umd.peripatos.User;
import edu.umd.peripatos.dao.AssignmentDao;

public class HibernateAssignmentDao implements AssignmentDao{

	private SessionFactory sessionFactory;
	
	@Override
	public void store(Assignment assignment) {
		Session session = sessionFactory.getCurrentSession();
		session.save(assignment);
	}

	@Override
	public void delete(Assignment assignment) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(assignment);
	}

	@Override
	public Assignment findAssignmentById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		Assignment assignment = (Assignment) session.get(Assignment.class, id);
		return assignment;
	}

	@Override
	public List<Assignment> findAssignmentsByName(String name) {
		return null;
	}

	@Override
	public List<Assignment> findAssignmentByUser(User user) {
		return null;
	}

	@Override
	public List<Question> getQuestionsFromAssignment(Assignment assignment) {
		return null;
	}

	@Override
	public void addQuestionToAssignment(Assignment assignment, Question question) {
		
	}

	@Override
	public void removeQuestionFromAssignment(Assignment assignment,
			Question question) {
		
	}

	@Override
	public void addQuestionsToAssignment(Collection<Question> questions) {
		
	}

	@Override
	public void removeQuestionsFromAssignment(Collection<Question> questions) {
		
	}

	@Override
	public List<Assignment> getAllAssignments() {
		return null;
	}

}
