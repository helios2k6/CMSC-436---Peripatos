package edu.umd.peripatos.android.rest;

import java.io.IOException;
import java.io.StringReader;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/android/")
public class AndroidRequestController {
	
	@RequestMapping(value="/request", method=RequestMethod.POST)
	@ResponseBody
	public String getUserInformation(@RequestBody String body){
		SAXBuilder builder = new SAXBuilder();
		try {
			Document doc = builder.build(new StringReader(body));
			
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	@RequestMapping(value="/submit", method=RequestMethod.POST)
	@ResponseBody
	public String saveAnswerSubmission(@RequestBody String body){
		return "";
	}
	
	private boolean checkLogin(String userName, String password){
		
		return false;
	}
}
