package com.gol.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gol.service.GenerationService;

@RestController
public class ApiContoller {
	private GenerationService generationService;

	@Autowired
	public void setGenerationService(GenerationService generationService) {
		this.generationService = generationService;
	}

	@RequestMapping("/grids")
	public int[][] grids() {
		return generationService.getFirstGen("src/main/resources/static/files/ak47.lif");
	}

}
