package edu.umd.peripatos.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.umd.peripatos.User;
import edu.umd.peripatos.dao.UserDao;

@Controller
public class AdministrationPage {
	
	@Autowired
	private UserDao userDao;
	
	@RequestMapping("/admin/{user_id}")
	public String getHomePage(@PathVariable("user_id")Long id, Model model){
		User user = userDao.findUserById(id);
		
		model.addAttribute("user", user);
		
		return "admin/home";
	}
}
