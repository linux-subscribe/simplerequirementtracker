package srt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ResultController {

	@RequestMapping("/result")
	public String showResultPage(){
		return "result";
	}
	
}
