package edu.umd.peripatos.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import edu.umd.peripatos.Answer;
import edu.umd.peripatos.Assignment;
import edu.umd.peripatos.Course;
import edu.umd.peripatos.User;
import edu.umd.peripatos.dao.UserDao;

@Transactional
public class HibernateUserDao implements UserDao {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}

	private Logger logger = Logger.getLogger(HibernateUserDao.class);

	@Override
	public User findUserByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(
				"FROM User as user " +
						"WHERE user.username = '" + name + "'");

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
		logger.info("Getting courses by user for "+ user.getUsername());
		Session session = sessionFactory.getCurrentSession();

		String queryString = "FROM Course as course " +
				"inner join fetch course.users as c_user " +
				"WHERE c_user.username = '" + user.getUsername() + "'";

		Query query = session.createQuery(queryString);

		List<Course> courses = query.list();

		for(Course c : courses){
			List<Assignment> assignments = c.getAssignments();
			for(Assignment a : assignments){
				Hibernate.initialize(a.getQuestions());
			}
			Hibernate.initialize(c.getUsers());
		}

		return courses;


		//		Criteria crit = session.createCriteria(Course.class);
		//
		//		List<Course> courses = crit.list();
		//
		//		List<Course> allAvailableCourses = new ArrayList<Course>();
		//
		//		for(Course c : courses){
		//			logger.info("Got course " + c.getName());
		//			for(User u : c.getUsers()){
		//				logger.info("User in course is " + u.getUsername());
		//
		//				if(u.getUsername().equals(user.getUsername())){
		//					allAvailableCourses.add(c);
		//				}
		//			}
		//		}
		//
		//		for(Course c : allAvailableCourses){
		//			List<Assignment> assignments = c.getAssignments();
		//			for(Assignment a : assignments){
		//				Hibernate.initialize(a.getQuestions());
		//			}
		//		}
		//
		//
		//		return allAvailableCourses;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Answer> getAnswersByUser(User user) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(
				"From Answer as answer " +
						"inner join fetch answer.user u" +
						"WHERE u.username = " + user.getUsername()
				);
		return query.list();

		//		Criteria crit = session.createCriteria(Answer.class);
		//		crit.add(Restrictions.eq("user", user.getUsername()));
		//
		//		return crit.list();
	}

}
