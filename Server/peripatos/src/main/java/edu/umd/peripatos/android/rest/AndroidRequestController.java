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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.umd.peripatos.Answer;
import edu.umd.peripatos.Assignment;
import edu.umd.peripatos.Course;
import edu.umd.peripatos.ErrorResponseCode;
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
	
	@Autowired
	private static ProviderManager manager;
	
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
			
			XMLOutputter outputter = new XMLOutputter(Format.getCompactFormat());
			
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			
			outputter.output(response, outStream);
			
			responseString = new String(outStream.toByteArray());
			
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
			
			Document response;
			
			if(isAuthenticated){
				
			}else{
				response = XMLResponseFactory.generateErrorResponse(ErrorResponseCode.INVALID_LOGIN);
			}
			
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return responseString;
	}
	
	private Document getDocumentFromPayload(String payload) throws JDOMException, IOException{
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(new StringReader(payload));
		
		return doc;
	}
	
	private boolean checkLogin(String username, String password){
		Authentication request = new UsernamePasswordAuthenticationToken(username, password);
		Authentication result = manager.doAuthentication(request);
		return result.isAuthenticated();
	}
	
	private List<Answer> processCourses(Element parent, String username){
		@SuppressWarnings("unchecked")
		List<Element> children = parent.getChildren();
		List<Answer> answers = new ArrayList<Answer>();
		
		for(Element e : children){
			if(e.getName().equalsIgnoreCase("course")){
				long courseId = Long.parseLong(e.getChild("course_id").getText());
				
				Course course = courseDao.getCourseById(courseId);
				
				if(course == null){
					continue;
				}
				
				Element assignments = e.getChild("assignments");
				
				answers.addAll(processAssignments(assignments, course));
			}
		}
		
		return answers;
	}
	
	private List<Answer> processAssignments(Element parent, Course course){
		
		return null;
	}
	
	private List<Answer> processQuestions(Element parent, Course course, Assignment assignment){
		return null;
	}
}
