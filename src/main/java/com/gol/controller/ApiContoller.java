package com.gol.controller;

import java.util.List;

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
		int[][] currentGen = generationService.getFirstGen("src/main/resources/static/files/ak47.lif");
		generationService.setCurrentGen(currentGen);
		return currentGen;
	}
	
	@RequestMapping("/moveToNext")
	public int[][] moveToNextGen() {
		String fileName = "src/main/resources/static/files/ak47.lif";
		List<String> example = generationService.getFile(fileName);
		String rules = generationService.getRules(example);
		List<Integer> cellsToLive = generationService.getCellToLive(rules);
		List<Integer> cellsToRise = generationService.getCellToRise(rules); 
		
		generationService.setBeforeGen(generationService.getCurrentGen());
		int[][] currentGen = generationService.moveToNextGen(generationService.getCurrentGen(), cellsToLive, cellsToRise);
		generationService.setCurrentGen(currentGen);
		
		return currentGen;
	}
	
	@RequestMapping("/moveBack")
	public int[][] moveBack() {
		return generationService.getBeforeGen();
	}


}
