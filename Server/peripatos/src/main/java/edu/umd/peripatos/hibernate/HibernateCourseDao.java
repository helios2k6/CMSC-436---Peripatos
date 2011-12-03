package edu.umd.peripatos.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import edu.umd.peripatos.Assignment;
import edu.umd.peripatos.Course;
import edu.umd.peripatos.dao.CourseDao;

@Transactional
public class HibernateCourseDao implements CourseDao{

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public void store(Course course) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(course);
	}

	@Override
	public void delete(Course course) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(course);
	}

	@Override
	public Course findCourseById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		Course course = (Course)session.get(Course.class, id);
		return course;
	}

	@Override
	public void addAssignment(Course course, Assignment assignment) {
		Session session = sessionFactory.getCurrentSession();
		List<Assignment> assignments = course.getAssignments();
		if(!assignments.contains(assignment)){
			assignments.add(assignment);
			session.saveOrUpdate(course);
		}
	}

	@Override
	public void removeAssignment(Course course, Assignment assignment) {
		Session session = sessionFactory.getCurrentSession();
		List<Assignment> assignments = course.getAssignments();
		
		assignments.remove(assignment);
		
		session.saveOrUpdate(course);
	}

}
