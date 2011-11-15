package edu.umd.peripatos.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.umd.peripatos.Answer;
import edu.umd.peripatos.Assignment;
import edu.umd.peripatos.Course;
import edu.umd.peripatos.User;
import edu.umd.peripatos.dao.AnswerDao;
import edu.umd.peripatos.dao.AssignmentDao;
import edu.umd.peripatos.dao.CourseDao;
import edu.umd.peripatos.dao.UserDao;

@Controller
public class CourseController {
	
	@Autowired
	private CourseDao courseDao;
	
	@Autowired
	private AssignmentDao assignmentDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private AnswerDao answerDao;
	
	@RequestMapping("/courses")
	public String listAllCourses(@ModelAttribute("user") User user, Model model){
		List<Course> courses = userDao.getCoursesByUser(user);
		
		model.addAttribute("courses", courses);
		
		return "courses/listAllCourses";
	}
	
	
	@RequestMapping(value = "/courses/{course_id}", method = RequestMethod.GET)
	public String getCourseDetails(@PathVariable("course_id") Long id, Model model){
		Course course = courseDao.getCourseById(id);
		
		model.addAttribute("course", course);
		
		return "courses/courseDetails";
	}
	
	@RequestMapping(value = "/courses/{course_id}/assignments/{assignment_id}", method = RequestMethod.GET)
	public String getAssignmentDetails(@PathVariable("assignment_id")Long id, @PathVariable("course_id")Long cid, Model model){
		Assignment assignment = assignmentDao.findAssignmentById(id);
		Course course = courseDao.getCourseById(cid);
		
		model.addAttribute("assignment", assignment);
		model.addAttribute("students", course.getStudents());
		
		return "courses/assignments/assignmentDetails";
	}
	
	@RequestMapping(value = "/courses/{course_id}/assignments/{assignment_id}", method = RequestMethod.POST)
	public String editAssignment(@ModelAttribute("assignment") @Valid Assignment assignment, BindingResult result){
		
		if(result.hasErrors()){
			return "courses/assignments/assignmentDetails";
		}
		
		assignmentDao.store(assignment);
		
		return "courses/courseDetails";
	}
	
	@RequestMapping(value = "/courses/{course_id}/assignments/{assignment_id}", method = RequestMethod.DELETE)
	public String deleteAssignment(@ModelAttribute("assignment") @Valid Assignment assignment, BindingResult result){
		if(result.hasErrors()){
			return "courses/assignments/assignmentDetails";
		}
		
		assignmentDao.delete(assignment);
		
		return "courses/courseDetails";
	}
	
	@RequestMapping(value = "/courses/{course_id}/assignments/{assignment_id}/answers/{user_id}", method = RequestMethod.GET)
	public String getAnswersFromStudent(
			@PathVariable("course_id") Long cid, 
			@PathVariable("assignment_id") Long aid, 
			@PathVariable("user_id")Long uid, 
			Model model){
		Assignment assignment = assignmentDao.findAssignmentById(aid);
		User user = userDao.findUserById(uid);
		Course course = courseDao.getCourseById(cid);
		
		List<Answer> answers = answerDao.getAnswerByAssignmentAndUserAndCourse(assignment, user, course);
		
		model.addAttribute("answers", answers);
		
		return "courses/assignments/answers/userAnswers";
	}
	
	@RequestMapping(value = "/courses/{course_id}/assignments/createAssignment", method = RequestMethod.GET)
	public String createAssignmentForm(){
		return "courses/assignments/createAssignment";
	}
	
	@RequestMapping(value = "/courses/{course_id}/assignments/createAssignment", method = RequestMethod.POST)
	public String saveAssignmentForm(@ModelAttribute("assignment") @Valid Assignment assignment, BindingResult result){
		if(result.hasErrors()){
			return "courses/assignments/createAssignment";
		}
		
		assignmentDao.store(assignment);
		
		return "courses/courseDetails";
	}
	
	@ModelAttribute("user")
	public User getUser(){
		return new User();
	}
	
	@ModelAttribute("course")
	public Course getCourse(){
		return new Course();
	}
	
	@ModelAttribute("assignment")
	public Assignment getAssignment(){
		return new Assignment();
	}
	
}