package edu.umd.peripatos.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import edu.umd.peripatos.Answer;
import edu.umd.peripatos.Course;
import edu.umd.peripatos.User;
import edu.umd.peripatos.dao.UserDao;

public class HibernateUserDao implements UserDao {

	private SessionFactory sessionFactory;

	@Override
	public User findUserById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		User user = (User) session.get(User.class, id);
		return user;
	}

	@Override
	public User findUserByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(
				"FROM User as user " +
						"WHERE user.name = " + name);

		@SuppressWarnings("unchecked")
		List<User> results = query.list();
		if(results.size() < 1){
			return null;
		}
		return results.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllUsers() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("From User");
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Course> getCoursesByUser(User user) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(
				"FROM Course as course " +
						"inner join fetch course.users c_user " + 
						"WHERE c_user.id = " + user.getId()
				);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Answer> getAnswersByUser(User user) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(
				"From Answer as answer " +
						"inner join fetch answer.user u" +
						"WHERE u.id = " + user.getId()
				);
		return query.list();
	}

}
