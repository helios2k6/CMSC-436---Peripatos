package edu.umd.peripatos.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.umd.peripatos.Course;
import edu.umd.peripatos.User;
import edu.umd.peripatos.dao.UserDao;

@Controller
public class AdministrationController {
	
	@Autowired
	private UserDao userDao;
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String getHomePage(HttpServletRequest request, Model model){
		String username = request.getRemoteUser();
		
		User user = userDao.findUserByName(username);
		
		List<Course> courses = userDao.getCoursesByUser(user);
		
		model.addAttribute("user", user);
		model.addAttribute("courses", courses);
		
		return "admin/home";
	}
}
