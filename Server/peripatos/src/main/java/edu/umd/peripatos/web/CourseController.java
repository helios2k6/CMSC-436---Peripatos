package edu.umd.peripatos.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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

	@InitBinder
	public void initBinder(WebDataBinder binder){
		logger.info("Binding Custom Editor");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		CustomDateEditor editor = new CustomDateEditor(df, true);
		binder.registerCustomEditor(Date.class, editor);
		logger.info("Binding Complete");
	}

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

	private void removeDuplicateQuestions(List<Question> questionsOnAssignment, List<Question> allAvailableQuestions){
		List<Question> duplicateOfAllQuestions = new ArrayList<Question>();

		//Duplicate stuff
		for(Question q : allAvailableQuestions){
			duplicateOfAllQuestions.add(q);
		}

		for(Question q : duplicateOfAllQuestions){
			for(Question u : questionsOnAssignment){
				if(q.getId().equals(u.getId())){
					allAvailableQuestions.remove(q);
				}
			}
		}
	}

	@RequestMapping(value = "/courses/{course_id}/assignments/{assignment_id}", method = RequestMethod.GET)
	public String getAssignmentDetails(
			@PathVariable("assignment_id")Long id, 
			@PathVariable("course_id")Long cid,
			HttpServletRequest request,
			Model model){

		logger.info("Fetching Assignment");

		Assignment assignment = assignmentDao.findAssignmentById(id);

		List<Question> questions = questionDao.getQuestionsByUser(userDao.findUserByName(request.getRemoteUser()));

		removeDuplicateQuestions(assignment.getQuestions(), questions);

		model.addAttribute("assignment", assignment);
		model.addAttribute("availableQuestions", questions);

		return "courses/assignments/assignmentDetails";
	}

	@RequestMapping(value = "/courses/{course_id}/assignments/{assignment_id}", method = RequestMethod.POST)
	public String editAssignment(@PathVariable("course_id") Long id,
			@PathVariable("assignment_id")Long assignmentId,
			@ModelAttribute("assignment") @Valid Assignment assignment, 
			BindingResult result,
			@RequestParam("selectedQuestions") String selectedQuestionsList){

		logger.info("Editing assignment " + assignmentId);
		Assignment oldAssignment = assignmentDao.findAssignmentById(assignmentId);

		if(result.hasErrors()){
			logger.error("Form has errors");
			return "courses/assignments/assignmentDetails";
		}

		oldAssignment.setQuestions(getListOfQuestionsFromPostList(selectedQuestionsList));

		assignmentDao.store(oldAssignment);

		return "redirect:/peripatos/courses/"+id;
	}

	@RequestMapping(value = "/courses/{course_id}/assignments/{assignment_id}", method = RequestMethod.DELETE)
	public String deleteAssignment(@PathVariable("course_id")Long courseId, @PathVariable("assignment_id")Long assignmentId){
		logger.info("Deleting assignment");
		Assignment assignment = assignmentDao.findAssignmentById(assignmentId);
		logger.info("Deleting Assignment: " + assignment.getId());

		assignmentDao.delete(assignment);

		logger.info("Assignment successfully deleted");

		return "redirect:/peripatos/courses/"+courseId;
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
		model.addAttribute("assignment", assignment);
		model.addAttribute("answers", answers);

		return "courses/assignments/answers/answerDetails";
	}

	@RequestMapping(value = "/courses/{course_id}/assignments/createAssignment", method = RequestMethod.GET)
	public String createAssignmentForm(HttpServletRequest request, Model model){
		List<Question> questions = questionDao.getQuestionsByUser(userDao.findUserByName(request.getRemoteUser()));

		model.addAttribute("availableQuestions", questions);
		model.addAttribute("newAssignment", true);

		return "courses/assignments/assignmentDetails";
	}

	@RequestMapping(value = "/courses/{course_id}/assignments/createAssignment", method = RequestMethod.POST)
	public String saveAssignmentForm(@PathVariable("course_id")Long courseId, 
			@ModelAttribute("assignment") @Valid Assignment assignment, 
			BindingResult result, 
			HttpServletRequest request,
			@RequestParam("selectedQuestions") String selectedQuestionsList, Model model){

		logger.info("Creating new assignment");

		logger.info("Due Date found is: " + assignment.getDueDate().toString());

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

		logger.info("Adding assignment to course");
		Course course = courseDao.findCourseById(courseId);
		courseDao.addAssignment(course, assignment);

		return "redirect:/peripatos/courses/" + course.getId();
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
		logger.info("Parsing question ID's from: " + listOfQuestions);

		List<Question> questions = new ArrayList<Question>();

		if(listOfQuestions.equals("")){
			return questions;
		}

		String[] splitQuestions = listOfQuestions.split(",;");
		for(int i = 0; i < splitQuestions.length; i++){
			logger.info("Working on Question ID: " + splitQuestions[i]);
			try{
				Question question = questionDao.getQuestionById(Long.parseLong(splitQuestions[i]));
				questions.add(question);
			}catch(NumberFormatException e){
				logger.error("Couldn't parse question", e);
			}

		}

		return questions;
	}

}