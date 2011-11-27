package edu.umd.peripatos.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import edu.umd.peripatos.Question;
import edu.umd.peripatos.User;
import edu.umd.peripatos.dao.QuestionDao;

@Transactional
public class HibernateQuestionDao implements QuestionDao {
	
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public void store(Question question) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(question);
	}

	@Override
	public void delete(Question question) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(question);
	}

	@Override
	public Question getQuestionById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		Question question = (Question) session.get(Question.class, id);
		return question;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Question> getQuestionsByUser(User user) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(
				"FROM Question as question " +
				"WHERE question.user.id = " + user.getId());
		
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Question> getAllQuestions() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Question");
		return query.list();
	}
}
