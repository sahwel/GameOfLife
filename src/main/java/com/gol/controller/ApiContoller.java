package com.gol.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gol.service.GenerationService;

@RestController
public class ApiContoller {
	private GenerationService generationService;
	private String fileName = "src/main/resources/static/files/relay.lif";

	@Autowired
	public void setGenerationService(GenerationService generationService) {
		this.generationService = generationService;
	}

	@RequestMapping("/getFilesName")
	public List<String> getFilesName() {
		return generationService.getResources();
	}

	@RequestMapping(path = "/newExample/{name}", method = RequestMethod.POST)
	public int[][] activation(@PathVariable("name") String name) {
		fileName = "src/main/resources/static/files/" + name + ".lif";
		int[][] currentGen = generationService.getFirstGen(fileName);
		generationService.setCurrentGen(currentGen);
		return currentGen;
	}

	@RequestMapping("/grids")
	public int[][] grids() {
		int[][] currentGen = generationService.getFirstGen(fileName);
		generationService.setCurrentGen(currentGen);
		return currentGen;
	}

	@RequestMapping("/moveToNext")
	public int[][] moveToNextGen() {
		int[][] before = generationService.getBefore();
		List<String> example = generationService.getFile(fileName);
		String rules = generationService.getRules(example);
		List<Integer> cellsToLive = generationService.getCellToLive(rules);
		List<Integer> cellsToRise = generationService.getCellToRise(rules);

		
		int[][] currentGen = generationService.moveToNextGen(generationService.getCurrentGen(), cellsToLive,
				cellsToRise);
		generationService.setCurrentGen(currentGen);

		
		generationService.setBeforeGen(before);
		return currentGen;
	}

	@RequestMapping("/moveBack")
	public int[][] moveBack() {
		return generationService.getBeforeGen();
	}

}
