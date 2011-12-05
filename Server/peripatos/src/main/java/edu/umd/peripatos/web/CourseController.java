package edu.umd.peripatos.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.umd.peripatos.Answer;
import edu.umd.peripatos.Assignment;
import edu.umd.peripatos.Authority;
import edu.umd.peripatos.Course;
import edu.umd.peripatos.Question;
import edu.umd.peripatos.User;
import edu.umd.peripatos.dao.AnswerDao;
import edu.umd.peripatos.dao.AssignmentDao;
import edu.umd.peripatos.dao.CourseDao;
import edu.umd.peripatos.dao.QuestionDao;
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

	@Autowired
	private QuestionDao questionDao;
	
	private static Logger logger = Logger.getLogger(CourseController.class);
	
	/*	
	@RequestMapping("/courses")
	public String listAllCourses(HttpServletRequest request, Model model){
		User user = userDao.findUserByName(request.getRemoteUser());

		List<Course> courses = userDao.getCoursesByUser(user);

		model.addAttribute("courses", courses);
		model.addAttribute("user_id", request.getRemoteUser());

		return "courses/listCourses";
	}
	 */
	
	@SuppressWarnings("unused")
	private List<User> getProfessors(Course course){
		List<User> professors = new ArrayList<User>();
		List<User> allUsers = course.getUsers();

		for(User u : allUsers){
			if(u.getAuthority() == Authority.ROLE_PROFESSOR){
				professors.add(u);
			}
		}

		return professors;
	}

	private List<User> getStudents(Course course){
		List<User> students = new ArrayList<User>();
		List<User> allUsers = course.getUsers();

		for(User u : allUsers){
			if(u.getAuthority() == Authority.ROLE_STUDENT){
				students.add(u);
			}
		}

		return students;
	}

	@RequestMapping(value = "/courses/{course_id}", method = RequestMethod.GET)
	public String getCourseDetails(@PathVariable("course_id") Long id, Model model){
		Course course = courseDao.findCourseById(id);

		model.addAttribute("course", course);
		model.addAttribute("students", getStudents(course));

		return "courses/courseDetails";
	}

	@RequestMapping(value = "/courses/{course_id}/assignments/{assignment_id}", method = RequestMethod.GET)
	public String getAssignmentDetails(
			@PathVariable("assignment_id")Long id, 
			@PathVariable("course_id")Long cid,
			HttpServletRequest request,
			Model model){
		Assignment assignment = assignmentDao.findAssignmentById(id);

		List<Question> questions = questionDao.getQuestionsByUser(userDao.findUserByName(request.getRemoteUser()));
		questions.removeAll(assignment.getQuestions());

		model.addAttribute("assignment", assignment);
		model.addAttribute("availableQuestions", questions);

		return "courses/assignments/assignmentDetails";
	}

	@RequestMapping(value = "/courses/{course_id}/assignments/{assignment_id}", method = RequestMethod.POST)
	public String editAssignment(
			@ModelAttribute("assignment") @Valid Assignment assignment, 
			BindingResult result,
			@RequestParam("selectedQuestions") String selectedQuestionsList){

		logger.info("Editing assignment " + assignment.getId());
		
		if(result.hasErrors()){
			logger.error("Form has errors");
			return "courses/assignments/assignmentDetails";
		}
		
		logger.info("Gathering selected questions list");
		List<Question> questions = getListOfQuestionsFromPostList(selectedQuestionsList);
		
		assignment.setQuestions(questions);
		
		logger.info("Saving assignment");
		assignmentDao.store(assignment);

		return "redirect:/courses/{course_id}";
	}

	@RequestMapping(value = "/courses/{course_id}/assignments/{assignment_id}", method = RequestMethod.DELETE)
	public String deleteAssignment(@ModelAttribute("assignment") @Valid Assignment assignment, BindingResult result){
		if(result.hasErrors()){
			return "courses/assignments/assignmentDetails";
		}

		assignmentDao.delete(assignment);

		return "courses/courseDetails";
	}

	@RequestMapping(value = "/courses/{course_id}/assignments/{assignment_id}/answers", method = RequestMethod.GET)
	public String getStudentListForAnswers(
			@PathVariable("course_id") Long cid, 
			@PathVariable("assignment_id") Long aid,
			Model model){
		
		Course course = courseDao.findCourseById(cid);
		Assignment assignment = assignmentDao.findAssignmentById(aid);
		
		model.addAttribute("students", getStudents(course));
		model.addAttribute("assignment", assignment);
		
		return "courses/assignments/answers/listStudents";
	}
	
	@RequestMapping(value = "/courses/{course_id}/assignments/{assignment_id}/answers/{username}", method = RequestMethod.GET)
	public String getAnswersFromStudent(
			@PathVariable("course_id") Long cid, 
			@PathVariable("assignment_id") Long aid, 
			@PathVariable("username")String username, 
			Model model){
		Assignment assignment = assignmentDao.findAssignmentById(aid);
		User user = userDao.findUserByName(username);
		Course course = courseDao.findCourseById(cid);

		List<Answer> answers = answerDao.getAnswerByAssignmentAndUserAndCourse(assignment, user, course);
		
		model.addAttribute("user", user);
		model.addAttribute("course", course);
		model.addAttribute("assignment", assignment);
		model.addAttribute("answers", answers);

		return "courses/assignments/answers/answerDetails";
	}

	@RequestMapping(value = "/courses/{course_id}/assignments/createAssignment", method = RequestMethod.GET)
	public String createAssignmentForm(HttpServletRequest request, Model model){
		List<Question> questions = questionDao.getQuestionsByUser(userDao.findUserByName(request.getRemoteUser()));

		model.addAttribute("createForm", true);
		model.addAttribute("availableQuestions", questions);

		return "courses/assignments/assignmentDetails";
	}

	@RequestMapping(value = "/courses/{course_id}/assignments/createAssignment", method = RequestMethod.POST)
	public String saveAssignmentForm(@ModelAttribute("assignment") @Valid Assignment assignment, 
			BindingResult result, 
			HttpServletRequest request,
			@RequestParam("selectedQuestions") String selectedQuestionsList, Model model){
		
		logger.info("Creating new assignment");
		
		if(result.hasErrors()){
			logger.error("Form has errors");
			List<ObjectError> errors = result.getAllErrors();
			
			for(ObjectError er : errors){
				logger.error("Error is: " + er.toString());
			}
			
			List<Question> questions = questionDao.getQuestionsByUser(userDao.findUserByName(request.getRemoteUser()));
			model.addAttribute("availableQuestions", questions);
			return "courses/assignments/assignmentDetails";
		}
		
		logger.info("Getting user");
		
		User user = userDao.findUserByName(request.getRemoteUser());
		
		logger.info("Setting user to " + user.getUsername());
		assignment.setUser(user);
		
		logger.info("Gathering selected questions list");
		List<Question> questions = getListOfQuestionsFromPostList(selectedQuestionsList);
		
		assignment.setQuestions(questions);
		
		logger.info("Saving assignment");
		assignmentDao.store(assignment);

		return "redirect:/courses/{course_id}";
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
	
	private List<Question> getListOfQuestionsFromPostList(String listOfQuestions){
		List<Question> questions = new ArrayList<Question>();
		String[] splitQuestions = listOfQuestions.split(",;");
		
		for(int i = 0; i < splitQuestions.length; i++){
			String currentString = splitQuestions[i];
			int indexOfFirstBracket = currentString.indexOf('[');
			int indexOfSecondBracket = currentString.indexOf(']');
			
			long idOfQuestion = Long.parseLong(currentString.substring(indexOfFirstBracket+1, indexOfSecondBracket));
			
			Question question = questionDao.getQuestionById(idOfQuestion);
			
			questions.add(question);
		}
		
		return questions;
	}
	
}