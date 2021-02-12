package com.gol.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gol.service.GenerationService;


@Controller
public class HomeController {
	private GenerationService generationService;
	
	@Autowired
	public void setGenerationService(GenerationService generationService) {
		this.generationService = generationService;
	}
	@RequestMapping("/")
	public String home(Model model) {
		//model.addAttribute("grids", generationService.getFirstGen("src/main/resources/static/files/acorn.lif"));
		//System.out.print(generationService.getFirstGen("src/main/resources/static/files/acorn.lif").length);
		return "index";
	}
}
