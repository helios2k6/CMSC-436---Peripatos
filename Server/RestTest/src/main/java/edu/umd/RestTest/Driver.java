package edu.umd.RestTest;

import java.io.IOException;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class Driver {
	public static void main(String[] args) throws JDOMException, IOException{
		TestRequest testRequest = new TestRequest();
		TestSubmission testSubmission = new TestSubmission();
		
		Document doc = testRequest.testRequestURLValid();
		Document docSub = testSubmission.testSubmission();
		
		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
		
		System.out.println("Request Response");
		outputter.output(doc, System.out);
		System.out.println("Submission Response");
		outputter.output(docSub, System.out);
	}
}
