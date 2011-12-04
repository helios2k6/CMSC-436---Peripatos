package edu.umd.peripatos.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.umd.peripatos.Question;
import edu.umd.peripatos.User;
import edu.umd.peripatos.dao.GeoLocationDao;
import edu.umd.peripatos.dao.QuestionDao;
import edu.umd.peripatos.dao.UserDao;

@Controller
@RequestMapping("/questions")
public class QuestionBankController {

	@Autowired
	private QuestionDao questionDao;

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private GeoLocationDao geoLocationDao;
	
	private static Logger logger = Logger.getLogger(QuestionBankController.class);
	
	@RequestMapping(method = RequestMethod.GET)
	public String getListOfQuestions(Model model, HttpServletRequest request){
		User user = userDao.findUserByName(request.getRemoteUser());

		List<Question> questions = questionDao.getQuestionsByUser(user);

		model.addAttribute("questions", questions);

		return "questions/listQuestions";	
	}

	@RequestMapping(value = "/{question_id}", method = RequestMethod.GET)
	public String getQuestionDetails(@PathVariable("question_id") Long id, Model model){
		logger.info("Gathering Question Details");
		Question question = questionDao.getQuestionById(id);
		logger.info("Got Question: " + question.getId());
		model.addAttribute("question", question);

		return "questions/questionDetails";
	}

	@RequestMapping(value = "/{question_id}", method = RequestMethod.POST)
	public String postQuestionEdits(@PathVariable("question_id")Long id, 
			@ModelAttribute("question") @Valid Question question, 
			BindingResult result){
		
		logger.info("Saving edit to question");
		if(result.hasErrors()){
			logger.error("There were errors in the question");
			return "questions/questionDetails";
		}
			
		logger.info("Updating GeoLocation");
		geoLocationDao.store(question.getLocation());
		
		logger.info("Updating Question");
		questionDao.store(question);
		
		logger.info("Redirecting");
		return "redirect:/questions";
	}
	
	@RequestMapping(value = "/createQuestion", method = RequestMethod.GET)
	public String createNewQuestionForm(Model model, HttpServletRequest request){
		return "questions/questionDetails";
	}
	
	@RequestMapping(value = "/createQuestion", method = RequestMethod.POST)
	public String postNewQuestionForm(@ModelAttribute("question") @Valid Question question,
			BindingResult result, HttpServletRequest request){
		
		logger.info("Creating new Question");
		if(result.hasErrors()){
			logger.error("Question form has errors");
			return "questions/questionDetails";
		}
		
		logger.info("Checking User object dependency");
		if(question.getUser() == null){
			logger.info("Attempting to hydrate user");
			question.setUser(userDao.findUserByName(request.getRemoteUser()));
			
			logger.info("Hydrated question with: " + question.getUser().getUsername());
		}
		
		logger.info("Inserting Geolocation");
		geoLocationDao.store(question.getLocation());
		
		logger.info("Inserting Question");
		questionDao.store(question);
		
		logger.info("Redirecting");
		return "redirect:/questions";
	}
	
	@RequestMapping(value = "/{question_id}", method = RequestMethod.DELETE)
	public String deleteQuestion(@PathVariable("question_id")Long id){
		logger.info("Deleting Question: " + id);
		questionDao.delete(questionDao.getQuestionById(id));
		
		return "redirect:/questions";
	}
	
	
	@ModelAttribute("question")
	public Question getQuestion(){
		Question question = new Question();
		
		return question;
	}
}
