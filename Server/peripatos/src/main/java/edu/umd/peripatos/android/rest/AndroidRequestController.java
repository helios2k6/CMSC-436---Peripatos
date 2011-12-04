package edu.umd.peripatos.android.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.umd.peripatos.Answer;
import edu.umd.peripatos.Assignment;
import edu.umd.peripatos.Course;
import edu.umd.peripatos.ErrorResponseCode;
import edu.umd.peripatos.Question;
import edu.umd.peripatos.SubmissionReceiptStatusCode;
import edu.umd.peripatos.User;
import edu.umd.peripatos.dao.AnswerDao;
import edu.umd.peripatos.dao.AssignmentDao;
import edu.umd.peripatos.dao.CourseDao;
import edu.umd.peripatos.dao.QuestionDao;
import edu.umd.peripatos.dao.UserDao;
import edu.umd.peripatos.xmlUtils.XMLResponseFactory;

@Controller
@RequestMapping("/android/")
public class AndroidRequestController {

	@Autowired @Qualifier("org.springframework.security.authenticationManager")
	private ProviderManager manager;

	@Autowired
	private UserDao userDao;

	@Autowired
	private AnswerDao answerDao;

	@Autowired
	private AssignmentDao assignmentDao;

	@Autowired
	private QuestionDao questionDao;

	@Autowired
	private CourseDao courseDao;

	@RequestMapping(value="/request", method=RequestMethod.POST)
	@ResponseBody
	public String getUserInformation(@RequestBody String body){
		String responseString = "";

		try {
			Document doc = getDocumentFromPayload(body);
			Element root = doc.getRootElement();

			String username = root.getChild("user_credentials").getChild("username").getText();
			String password = root.getChild("user_credentials").getChild("password").getText();

			boolean isAuthenticated = checkLogin(username, password);

			Document response;

			if(isAuthenticated){
				User user = userDao.findUserByName(username);

				response = XMLResponseFactory.generateResponseForRequest(userDao.getCoursesByUser(user));
			}else{
				response = XMLResponseFactory.generateErrorResponse(ErrorResponseCode.INVALID_LOGIN);
			}

			responseString = convertDocumentToString(response);

		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return responseString;
	}

	@RequestMapping(value="/submit", method=RequestMethod.POST)
	@ResponseBody
	public String saveAnswerSubmission(@RequestBody String body){
		String responseString = "";
		try {
			Document doc = getDocumentFromPayload(body);
			Element root = doc.getRootElement();

			String username = root.getChild("user_credentials").getChild("username").getText();
			String password = root.getChild("user_credentials").getChild("password").getText();

			boolean isAuthenticated = checkLogin(username, password);

			Document response = null;

			if(isAuthenticated){
				User user = userDao.findUserByName(username);
				Element courses = root.getChild("courses");

				List<Answer> answers = processCourses(courses, user);

				for(Answer a : answers){
					answerDao.store(a);
				}

				response = XMLResponseFactory.generateSubmissionReceipt(SubmissionReceiptStatusCode.OK);

			}else{
				response = XMLResponseFactory.generateErrorResponse(ErrorResponseCode.INVALID_LOGIN);
			}

			responseString = convertDocumentToString(response);
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return responseString;
	}

	private String convertDocumentToString(Document doc) throws IOException{
		XMLOutputter outputter = new XMLOutputter(Format.getCompactFormat());

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		outputter.output(doc, outStream);

		return new String(outStream.toByteArray());

	}

	private Document getDocumentFromPayload(String payload) throws JDOMException, IOException{
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(new StringReader(payload));

		return doc;
	}

	private boolean checkLogin(String username, String password){
		Authentication request = new UsernamePasswordAuthenticationToken(username, password);
		try{
			manager.authenticate(request);
		}catch(AuthenticationException exception){
			return false;
		}
		return true;
	}

	private List<Answer> processCourses(Element parent, User user){
		List<Answer> answers = new ArrayList<Answer>();

		@SuppressWarnings("unchecked")
		List<Element> courses = parent.getChildren();

		for(Element e : courses){
			if(e.getName().equalsIgnoreCase("course")){
				processCourse(e, user, answers);
			}
		}

		return answers;
	}

	private void processCourse(Element parent, User user, List<Answer> answers){
		long courseId = Long.parseLong(parent.getChild("course_id").getText());

		Course course = courseDao.findCourseById(courseId);

		if(course == null){
			return;
		}

		Element assignments = parent.getChild("assignments");

		processAssignments(assignments, user, course, answers);
	}

	private void processAssignments(Element parent, User user, Course course, List<Answer> answers){
		@SuppressWarnings("unchecked")
		List<Element> assignments = parent.getChildren();

		for(Element e : assignments){
			if(e.getName().equalsIgnoreCase("assignment")){
				processAssignment(e, user, course, answers);
			}
		}
	}

	private void processAssignment(Element parent, User user, Course course, List<Answer> answers){
		long assignmentId = Long.parseLong(parent.getChild("assignment_id").getText());
		Element questions = parent.getChild("questions");

		Assignment assignment = assignmentDao.findAssignmentById(assignmentId);

		if(assignment == null){
			return;
		}

		processQuestions(questions, user, course, assignment, answers);
	}

	private void processQuestions(Element parent, User user, Course course, Assignment assignment, List<Answer> answers){
		@SuppressWarnings("unchecked")
		List<Element> questions = parent.getChildren();

		for(Element e : questions){
			if(e.getName().equalsIgnoreCase("question")){
				Answer answer = processQuestion(e, user, course, assignment);
				if(answer != null){
					answers.add(answer);
				}
			}
		}

	}

	private Answer processQuestion(Element parent, User user, Course course, Assignment assignment){
		Answer answer = new Answer();

		long questionId = Long.parseLong(parent.getChild("question_id").getText());
		String answerBody = parent.getChild("answer_body").getText();

		Question question = questionDao.getQuestionById(questionId);

		if(question == null){
			return null;
		}

		answer.setCourse(course);
		answer.setAssignment(assignment);
		answer.setQuestion(question);
		answer.setBody(answerBody);
		answer.setSubmissionDate(new DateTime());
		answer.setUser(user);

		return answer;
	}
}
