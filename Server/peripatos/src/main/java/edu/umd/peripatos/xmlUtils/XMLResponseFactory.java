package edu.umd.peripatos.xmlUtils;

import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;

import edu.umd.peripatos.Assignment;
import edu.umd.peripatos.Course;
import edu.umd.peripatos.ErrorResponseCode;
import edu.umd.peripatos.GeoLocation;
import edu.umd.peripatos.Question;
import edu.umd.peripatos.SubmissionReceiptStatusCode;

public class XMLResponseFactory {
	
	public static final String PERIPATOS_NAMESPACE = "http://www.cs.umd.edu/class/fall2011/cmsc436/peripatos";
	public static final String PERIPATOS_NAMESPACE_NAME = "peripatos";
	
	
	public static Document generateResponseForRequest(List<Course> courses){
		Namespace defaultNamespace = Namespace.getNamespace(PERIPATOS_NAMESPACE);
		Element rootElement = new Element("courses_and_assignments", defaultNamespace);
		
		//Add everything to root
		addCourses(rootElement, courses);
		
		Document doc = new Document(rootElement);
		return doc;
	}
	
	private static void addCourses(Element parent, List<Course> courses){
		Namespace defaultNamespace = Namespace.getNamespace(PERIPATOS_NAMESPACE);
		for(Course c : courses){
			//parent
			Element course = new Element("course", defaultNamespace);
			
			//children
			//Course id
			Element courseId = new Element("course_id", defaultNamespace);
			courseId.setText(c.getId().toString());
			
			//Course name
			Element courseName = new Element("course_name", defaultNamespace);
			courseName.setText(c.getName());
			
			//Assignments
			Element assignments = new Element("assignments", defaultNamespace);
			
			//Attach stuff
			course.addContent(courseId);
			course.addContent(courseName);
			
			//Call addAssignments to put all assignments of this course in xml file
			addAssignments(assignments, c.getAssignments());
			
			//Add assignments
			course.addContent(assignments);
			
			//Add to parent
			parent.addContent(course);
		}
		
		
	}
	
	private static void addAssignments(Element parent, List<Assignment> assignments){
		Namespace defaultNamespace = Namespace.getNamespace(PERIPATOS_NAMESPACE);
		for(Assignment a : assignments){
			//Parent
			Element assignment = new Element("assignment", defaultNamespace);
			
			//Children
			//Assignment id
			Element assignmentId = new Element("assignment_id", defaultNamespace);
			assignmentId.setText(a.getId().toString());
			
			//Assignment name
			Element assignmentName = new Element("assignment_name", defaultNamespace);
			assignmentName.setText(a.getName());
			
			//Questions
			Element questions = new Element("questions", defaultNamespace);
			
			//Attach stuff
			assignment.addContent(assignmentId);
			assignment.addContent(assignmentName);
			assignment.addContent(questions);
			
			addQuestions(questions, a.getQuestions());
			
			//Attach everything to parent
			parent.addContent(assignment);
		}
	}
	
	private static void addQuestions(Element parent, List<Question> questions){
		Namespace defaultNamespace = Namespace.getNamespace(PERIPATOS_NAMESPACE);
		for(Question q : questions){
			//Parent
			Element question = new Element("question", defaultNamespace);
			
			//Children
			//Question id
			Element questionId = new Element("question_id", defaultNamespace);
			questionId.setText(q.getId().toString());
			
			//Question title
			Element questionTitle = new Element("question_title", defaultNamespace);
			questionTitle.setText(q.getTitle());
			
			//Question body
			Element questionBody = new Element("question_body", defaultNamespace);
			questionBody.setText(q.getBody());
			
			//Attach stuff
			question.addContent(questionId);
			question.addContent(questionTitle);
			question.addContent(questionBody);
			
			//Add geolocation
			addGeolocation(question, q.getLocation());
			
			parent.addContent(question);
		}
	}
	
	private static void addGeolocation(Element parent, GeoLocation location){
		Namespace defaultNamespace = Namespace.getNamespace(PERIPATOS_NAMESPACE);
		Element geoLocation = new Element("geolocation", defaultNamespace);
		Element latitude = new Element("latitude", defaultNamespace);
		Element longitude = new Element("longitude", defaultNamespace);
		
		latitude.setText(location.getLatitude().toString());
		longitude.setText(location.getLongitude().toString());
		
		geoLocation.addContent(latitude);
		geoLocation.addContent(longitude);
		
		parent.addContent(geoLocation);
	}
	
	public static Document generateSubmissionReceipt(SubmissionReceiptStatusCode statusCode){
		Namespace defaultNamespace = Namespace.getNamespace(PERIPATOS_NAMESPACE);
		Element rootElement = new Element("submission_receipt", defaultNamespace);
		
		Element status = new Element("status", defaultNamespace);
		status.setText(statusCode.toString());
		
		Document doc = new Document(rootElement);
		return doc;
	}
	
	public static Document generateErrorResponse(ErrorResponseCode code){
		Namespace defaultNamespace = Namespace.getNamespace(PERIPATOS_NAMESPACE);
		
		Element rootElement = new Element("error_response", defaultNamespace);
		
		Element status = new Element("error_code", defaultNamespace);
		status.setText(code.toString());
		
		rootElement.addContent(status);
		
		Document doc = new Document(rootElement);
		return doc;
	}
}
