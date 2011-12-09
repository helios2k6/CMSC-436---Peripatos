package edu.umd.RestTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.Scanner;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class TestSubmission extends Test{
	private Document doc;
	
	public TestSubmission() throws JDOMException, IOException{
		SAXBuilder builder = new SAXBuilder();
		
		doc = builder.build(new StringReader(XML_STRING_SUBMISSION));
	}
	
	public Document testSubmission() throws IOException, JDOMException{
		URL url = new URL(URL_FOR_SUBMISSIONS);
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);
		
		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		
		String xmlOutput = convertDocumentToString(doc);
		
		wr.write(xmlOutput);
		wr.flush();
		
		//Get Response
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		Scanner scanner = new Scanner(reader);
		
		StringBuffer buffer = new StringBuffer();
		
		while(scanner.hasNext()){
			buffer.append(scanner.next());
		}
		
		scanner.close();
		
		String readString = URLDecoder.decode(buffer.toString(), "UTF-8");
		
		StringReader feedBack = new StringReader(readString);
		
		SAXBuilder builder = new SAXBuilder();
		
		return builder.build(feedBack);
	}
}
