package edu.umd.RestTest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public abstract class Test {
	public static final String XML_STRING_REQUEST ="<?xml version=\"1.0\" ?><fetch_assignments xmlns=\"http://www.cs.umd.edu/class/fall2011/cmsc436/peripatos\">" +
			"<user_credentials><username>student</username><password>password</password></user_credentials></fetch_assignments>";

	public static final String XML_STRING_SUBMISSION = 
			"<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" +
			"<answer_submission xmlns=\"http://www.cs.umd.edu/class/fall2011/cmsc436/peripatos\">" +
			"<user_credentials><username>student</username><password>password</password></user_credentials>" +
			"<courses><course><course_id>1</course_id><assignments><assignment>" +
			"<assignment_id>-2805145705193037786</assignment_id>" +
			"<questions><question>" +
			"<question_id>352498644237012063</question_id>" +
			"<answer_body>Test Answer</answer_body></question>" +
			"</questions></assignment></assignments></course></courses></answer_submission>";

	public static final String URL_FOR_REQUESTS = "http://vestigial.cs.umd.edu:8080/peripatos-1.0-SNAPSHOT/peripatos/android/request";
	
	public static final String URL_FOR_SUBMISSIONS = "http://vestigial.cs.umd.edu:8080/peripatos-1.0-SNAPSHOT/peripatos/android/submit";

	public static String convertDocumentToString(Document doc) throws IOException{
		XMLOutputter outputter = new XMLOutputter(Format.getCompactFormat());

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		outputter.output(doc, outStream);

		return outStream.toString();

	}

}
