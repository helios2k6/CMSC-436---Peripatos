package edu.umd.peripatos.hibernate;

import java.util.Collection;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import edu.umd.peripatos.Assignment;
import edu.umd.peripatos.Question;
import edu.umd.peripatos.User;
import edu.umd.peripatos.dao.AssignmentDao;

@Transactional
public class HibernateAssignmentDao implements AssignmentDao{

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}
	
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Assignment> findAssignmentsByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(
				"FROM Assignment as assignment " +
						"WHERE assignment.name = " + name);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Assignment> findAssignmentByUser(User user) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(
				"FROM Assignment as assignment " + 
						"WHERE assignment.user.username = " + user.getUsername());
		return query.list();
	}

	@Override
	public void addQuestionToAssignment(Assignment assignment, Question question) {
		Session session = sessionFactory.getCurrentSession();
		List<Question> questions = assignment.getQuestions();
		if(!questions.contains(question)){
			questions.add(question);
			session.saveOrUpdate(assignment);
		}
	}

	@Override
	public void removeQuestionFromAssignment(Assignment assignment,	Question question) {
		Session session = sessionFactory.getCurrentSession();

		List<Question> questions = assignment.getQuestions();
		questions.remove(question);

		session.saveOrUpdate(assignment);
	}

	@Override
	public void addQuestionsToAssignment(Assignment assignment, Collection<Question> questions) {
		Session session = sessionFactory.getCurrentSession();
		List<Question> listOfQuestions = assignment.getQuestions();

		for(Question q : questions){
			if(!listOfQuestions.contains(q)){
				listOfQuestions.add(q);
			}

		}

		session.saveOrUpdate(assignment);
	}

	@Override
	public void removeQuestionsFromAssignment(Assignment assignment, Collection<Question> questions) {
		Session session = sessionFactory.getCurrentSession();
		List<Question> listOfQuesitons = assignment.getQuestions();

		listOfQuesitons.removeAll(questions);

		session.saveOrUpdate(assignment);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Assignment> getAllAssignments() {
		Session session = sessionFactory.getCurrentSession();

		Query query = session.createQuery("FROM Assignment");

		return query.list();
	}

}
