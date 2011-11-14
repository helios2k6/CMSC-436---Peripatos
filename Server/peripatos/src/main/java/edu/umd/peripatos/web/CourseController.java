package edu.umd.peripatos.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.umd.peripatos.Course;
import edu.umd.peripatos.User;
import edu.umd.peripatos.dao.CourseDao;
import edu.umd.peripatos.dao.UserDao;

@Controller
public class CourseController {
	
	@Autowired
	private CourseDao courseDao;
	
	@Autowired
	private UserDao userDao;
	
	@RequestMapping("/courses")
	public String listAllCourses(@ModelAttribute("user") User user, Model model){
		List<Course> courses = userDao.getCoursesByUser(user);
		
		model.addAttribute("courses", courses);
		
		return "courses/listAllCourses";
	}
	
	@ModelAttribute("user")
	public User getUser(){
		return new User();
	}
	
	@RequestMapping(value = "/courses/{course_id}", method = RequestMethod.GET)
	public String getCourseDetails(@PathVariable("course_id") Long id, Model model){
		Course course = courseDao.getCourseById(id);
		
		model.addAttribute("course", course);
		
		return "courses/courseDetails";
	}
	
}