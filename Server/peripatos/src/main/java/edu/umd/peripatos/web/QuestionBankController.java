package edu.umd.peripatos.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import edu.umd.peripatos.dao.QuestionDao;
import edu.umd.peripatos.dao.UserDao;

@Controller
@RequestMapping("/questions")
public class QuestionBankController {

	@Autowired
	private QuestionDao questionDao;

	@Autowired
	private UserDao userDao;

	@RequestMapping(method = RequestMethod.GET)
	public String getListOfQuestions(Model model, HttpServletRequest request){
		User user = userDao.findUserByName(request.getRemoteUser());

		List<Question> questions = questionDao.getQuestionsByUser(user);

		model.addAttribute("questions", questions);

		return "questions/listQuestions";	
	}

	@RequestMapping(value = "/{question_id}", method = RequestMethod.GET)
	public String getQuestionDetails(@PathVariable("question_id") Long id, Model model){
		Question question = questionDao.getQuestionById(id);
		model.addAttribute("question", question);

		return "questions/questionDetails";
	}

	@RequestMapping(value = "/{question_id}", method = RequestMethod.POST)
	public String postQuestionEdits(@PathVariable("question_id")Long id, 
			@ModelAttribute("question") @Valid Question question, 
			BindingResult result){
		
		if(result.hasErrors()){
			return "questions/questionDetails";
		}
		
		questionDao.store(question);
		
		return "redirect:question/listQuestions";
	}
	
	@RequestMapping(value = "/createQuestion", method = RequestMethod.GET)
	public String createNewQuestionForm(Model model){
		model.addAttribute("createForm", true);
		return "questions/questionDetails";
	}
	
	@RequestMapping(value = "/createQuestion", method = RequestMethod.POST)
	public String postNewQuestionForm(@ModelAttribute("question") @Valid Question question,
			BindingResult result){
		
		if(result.hasErrors()){
			return "questions/questionDetails";
		}
		
		questionDao.store(question);
		
		return "questions/listQuestions";
	}
	
	@RequestMapping(value = "/{question_id}", method = RequestMethod.DELETE)
	public String deleteQuestion(@PathVariable("question_id")Long id){
		questionDao.delete(questionDao.getQuestionById(id));
		return "questions/listQuestions";
	}
	
	
	@ModelAttribute("question")
	public Question getQuestion(){
		return new Question();
	}
}
