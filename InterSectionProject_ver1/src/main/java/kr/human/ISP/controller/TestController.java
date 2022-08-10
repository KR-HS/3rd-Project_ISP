package kr.human.ISP.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.human.ISP.service.TestService;

@Controller
public class TestController {

	
	@Autowired
	private TestService testService;
	
	@RequestMapping("/test")
	public String home(Model model) {
		model.addAttribute("today",testService.selectToday());
		return "test";
	}
}
