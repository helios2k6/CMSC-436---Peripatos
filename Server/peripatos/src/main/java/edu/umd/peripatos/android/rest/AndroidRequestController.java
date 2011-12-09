package edu.umd.peripatos.android.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
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
@RequestMapping("/android")
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

	private Logger logger = Logger.getLogger(AndroidRequestController.class);

	@RequestMapping(value="/request", method=RequestMethod.POST)
	@ResponseBody
	public String getUserInformation(@RequestBody String body) throws UnsupportedEncodingException {
		String responseString = "";
		try {
			logger.info("Android App requested user info");
			logger.info("Payload is: " + body);

			String decodedString = URLDecoder.decode(body, "UTF-8");
			String cleanedDecodedString = decodedString.trim();
			
			logger.info("Decoded String is " + decodedString);
			logger.info("Cleaned String is " + cleanedDecodedString);
			
			try {
				logger.info("Getting document");
				Namespace ns = Namespace.getNamespace(XMLResponseFactory.PERIPATOS_NAMESPACE);
				Document doc = getDocumentFromPayload(cleanedDecodedString); 

				logger.info("Got document. Analyzing");

				Element root = doc.getRootElement();

				logger.info("Gathering credentials");

				String username = root.getChild("user_credentials", ns).getChild("username", ns).getText();
				logger.info("User = " + username);

				String password = root.getChild("user_credentials", ns).getChild("password", ns).getText();
				logger.info("Password = " + password);

				boolean isAuthenticated = checkLogin(username, password);

				Document response;

				if(isAuthenticated){
					logger.info("User is authenticated");
					User user = userDao.findUserByName(username);

					response = XMLResponseFactory.generateResponseForRequest(userDao.getCoursesByUser(user));
				}else{
					logger.info("User is not authenticated");
					response = XMLResponseFactory.generateErrorResponse(ErrorResponseCode.INVALID_LOGIN);
				}

				responseString = convertDocumentToString(response);

			} catch (JDOMException e) {
				logger.error("JDOM Error", e);
			} catch (IOException e) {
				logger.error("IO Error", e);
			}
		} catch (UnsupportedEncodingException e2) {
			logger.error("Could not decode string", e2);
		}
		return URLEncoder.encode(responseString, "UTF-8");
	}

	@RequestMapping(value="/submit", method=RequestMethod.POST)
	@ResponseBody
	public String saveAnswerSubmission(@RequestBody String body) throws UnsupportedEncodingException{
		String responseString = "";
		try {
			logger.info("Android app requesting submission service");
			
			String decodedString = URLDecoder.decode(body, "UTF-8").trim();
			
			logger.info("Payload String: " + body);
			logger.info("Decoded String: " + decodedString);
			
			Namespace ns = Namespace.getNamespace(XMLResponseFactory.PERIPATOS_NAMESPACE);
			Document doc = getDocumentFromPayload(decodedString);
			Element root = doc.getRootElement();
			
			String username = root.getChild("user_credentials", ns).getChild("username", ns).getText();
			logger.info("Username = " + username);
			String password = root.getChild("user_credentials", ns).getChild("password", ns).getText();
			logger.info("Password = " + password);
			
			
			boolean isAuthenticated = checkLogin(username, password);

			Document response = null;

			if(isAuthenticated){
				logger.info("User is authenticated");
				User user = userDao.findUserByName(username);
				
				logger.info("Username: " + user.getUsername());
				
				Element courses = root.getChild("courses", ns);
				
				logger.info("Processing response");
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
			logger.error("JDOM Error", e);
		} catch (IOException e) {
			logger.error("IO Exception", e);
		}
		return URLEncoder.encode(responseString, "UTF-8");

	}

	private String convertDocumentToString(Document doc) throws IOException{
		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		outputter.output(doc, outStream);

		return outStream.toString();

	}

	private Document getDocumentFromPayload(String payload) throws JDOMException, IOException{
		StringReader stringReader = new StringReader(payload);
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(stringReader);
		stringReader.close();

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
		Namespace ns = Namespace.getNamespace(XMLResponseFactory.PERIPATOS_NAMESPACE);
		long courseId = Long.parseLong(parent.getChild("course_id", ns).getText());

		Course course = courseDao.findCourseById(courseId);

		if(course == null){
			return;
		}
		Element assignments = parent.getChild("assignments", ns);

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
		Namespace ns = Namespace.getNamespace(XMLResponseFactory.PERIPATOS_NAMESPACE);
		long assignmentId = Long.parseLong(parent.getChild("assignment_id", ns).getText());
		Element questions = parent.getChild("questions", ns);

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
		Namespace ns = Namespace.getNamespace(XMLResponseFactory.PERIPATOS_NAMESPACE);
		long questionId = Long.parseLong(parent.getChild("question_id", ns).getText());
		String answerBody = parent.getChild("answer_body", ns).getText();

		Question question = questionDao.getQuestionById(questionId);

		if(question == null){
			return null;
		}

		answer.setCourse(course);
		answer.setAssignment(assignment);
		answer.setQuestion(question);
		answer.setBody(answerBody);
		answer.setSubmissionDate(new Date());
		answer.setUser(user);

		return answer;
	}
}
