package edu.umd.peripatos.android.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/android/")
public class AndroidRequestController {
	
	@RequestMapping(value="/request", method=RequestMethod.POST)
	@ResponseBody
	public String getUserInformation(Model model){
		
		return "";
	}
}
