package fr.greta91.productapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {
	@GetMapping("/client/nom")
	public ModelAndView client(ModelAndView mv) {
		mv.setViewName("client");
		mv.addObject("nom", "toto");
		return mv;
	}
}
