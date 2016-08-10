package srt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import srt.data.domain.management.User;
import srt.data.repository.management.IUserRepository;

@Controller
public class RegisterController {

	private IUserRepository userRepository;

	@Autowired
	public RegisterController(IUserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public String showRegisterPage(Model model){
		User user=new User();
		model.addAttribute("user",user);
		return "register";
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String register(User user){
		userRepository.addUser(user);
		return "redirect:/result/register/successful";
	}
	
}
