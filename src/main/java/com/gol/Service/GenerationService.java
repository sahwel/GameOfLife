package com.gol.service;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

@Service
public class GenerationService {
	private int gridSize = 500;

	ClassLoader loader = GenerationService.class.getClassLoader();
	ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(loader);

	private int[][] beforeGen = new int[gridSize][gridSize];
	private int[][] currentGen = new int[gridSize][gridSize];

	public List<String> getResources() {
		List<String> filesName = new ArrayList<>();
		try {
			Resource[] resources = resolver.getResources("static/files/*.lif");
			for (int i = 0; i < resources.length; i++) {
				filesName.add(resources[i].getFilename());
			}
		} catch (IOException e) {
			System.out.println("Cannot find path");
		}
		return filesName;
	}

	public int getGridSize() {
		return gridSize;
	}

	public ClassLoader getLoader() {
		return loader;
	}

	public void setLoader(ClassLoader loader) {
		this.loader = loader;
	}

	public ResourcePatternResolver getResolver() {
		return resolver;
	}

	public void setResolver(ResourcePatternResolver resolver) {
		this.resolver = resolver;
	}

	public int[][] getBeforeGen() {
		return beforeGen;
	}

	public void setBeforeGen(int[][] beforeGen) {
		this.beforeGen = beforeGen;
	}

	public int[][] getCurrentGen() {
		return currentGen;
	}

	public void setCurrentGen(int[][] currentGen) {
		this.currentGen = currentGen;
	}

	public void setGridSize(int gridSize) {
		this.gridSize = gridSize;
	}

	public int[][] getBefore(){
		int[][] before = new int[gridSize][gridSize];
		
		for (int i = 0; i < getCurrentGen().length; i++) {
			for (int j = 0; j < getCurrentGen().length; j++) {
				before[i][j] = getCurrentGen()[i][j];
			}
		}
		
		return before;
	}
	
	public int[][] moveToNextGen(int grid[][], List<Integer> cellsToLive, List<Integer> cellsToRise) {
		
		setBeforeGen(grid); 
		
		List<String> cellsDie = new ArrayList<String>();
		List<String> cellsRise = new ArrayList<String>();
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {

				int cellAround = 0;
				for (int k = -1; k < 2; k++) {
					for (int l = -1; l < 2; l++) {
						try {
							if (!(k == 0 && l == 0)) {
								if (grid[i + k][j + l] == 1) {
									cellAround++;
								}
							}
						} catch (Exception e) {
						}
					}
				}
				boolean isCellWillLive = false;
				if (grid[i][j] == 1) {

					for (int k = 0; k < cellsToRise.size(); k++) {
						if (cellAround == cellsToRise.get(k)) {
							isCellWillLive = true;
							break;
						}
					}
					if (!isCellWillLive) {
						if (cellAround < 2 || cellAround > 3) {
							cellsDie.add(i + " " + j);
						}
					}

				} else {

					if (cellsToRise.size() == 1) {
						if (cellAround == cellsToRise.get(0)) {
							cellsRise.add(i + " " + j);
						}
					} else {
						for (int k = 0; k < cellsToRise.size(); k++) {
							if (cellAround == cellsToRise.get(k)) {
								cellsRise.add(i + " " + j);
							}
						}
					}
				}

				cellAround = 0;
			}
		}
		for (int j = 0; j < cellsRise.size(); j++) {
			String array[] = cellsRise.get(j).split(" ");
			int num1 = Integer.parseInt(array[0]);
			int num2 = Integer.parseInt(array[1]);
			try {
				grid[num1][num2] = 1;
			} catch (Exception e) {
			}

		}
		for (int j = 0; j < cellsDie.size(); j++) {
			String array[] = cellsDie.get(j).split(" ");
			int num1 = Integer.parseInt(array[0]);
			int num2 = Integer.parseInt(array[1]);
			try {
				grid[num1][num2] = 0;
			} catch (Exception e) {
			}

		}
		
		return grid;
	}

	public List<String> getFile(String fileName) {
		List<String> examples = new ArrayList<>();
		String ext = fileName.substring(fileName.length() - 4);
		if (ext.equals((".lif"))) {
			try {
				RandomAccessFile raf = new RandomAccessFile(fileName, "r");
				for (String line = raf.readLine(); line != null; line = raf.readLine()) {
					examples.add(line);
				}
				raf.close();
			} catch (IOException ex) {
				// hiba kezelés
			}
		}
		return examples;
	}

	public List<Integer> getCellToRise(String rules) {
		List<Integer> cellsToLive = new ArrayList<>();
		String cells = rules.substring(rules.indexOf("/") + 1);
		for (int i = 0; i < cells.length(); i++) {
			int number = Integer.parseInt(cells.substring(i, i + 1));
			cellsToLive.add(number);
		}
		return cellsToLive;
	}

	public List<Integer> getCellToLive(String rules) {
		List<Integer> cellsToLive = new ArrayList<>();
		String cells = rules.substring(0, rules.indexOf("/"));
		for (int i = 0; i < cells.length(); i++) {
			int number = Integer.parseInt(cells.substring(i, i + 1));
			cellsToLive.add(number);
		}
		return cellsToLive;
	}

	public String getRules(List<String> example) {
		String rules = "23/3";
		for (int i = 0; i < example.size(); i++) {
			if (example.get(i).startsWith("#R")) {
				rules = example.get(i);
				break;
			}
		}
		return rules;
	}

	public int[][] generateGrid() {
		int[][] array = new int[gridSize][gridSize];
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length; j++) {
				array[i][j] = 0;
			}
		}
		return array;
	}

	public static int[][] getFirstGenSix(int grid[][], List<String> example) {
		int origoX = grid.length / 2;
		int origoY = grid.length / 2;
		int x = 0;
		int y = 0;
		for (int i = 0; i < example.size(); i++) {
			if (!(example.get(i).startsWith("#"))) {
				String[] line = example.get(i).split(" ");
				x = origoX + Integer.parseInt(line[0]);
				y = origoY + Integer.parseInt(line[1]);
				grid[y - 1][x - 1] = 1;
			}
		}
		return grid;
	}

	public static String getVersion(List<String> example) {
		return example.get(0);
	}

	public static int[][] getFirstGenFive(int grid[][], List<String> example) {
		int origoX = grid.length / 2;
		int origoY = grid.length / 2;
		int startX = origoX;
		int x = 0;
		int y = 0;
		for (int i = 0; i < example.size(); i++) {
			if (example.get(i).startsWith("#P")) {
				String[] line = example.get(i).split(" ");
				startX = origoX + Integer.parseInt(line[1]);
				y = origoY + Integer.parseInt(line[2]);
				x = startX;
			}

			if (!(example.get(i).startsWith("#"))) {
				String line = example.get(i);
				for (int j = 0; j < line.length(); j++) {
					if (line.charAt(j) == '*') {
						grid[y - 1][x - 1] = 1;
					}
					x++;
				}
				x = startX;
				y++;
			}
		}
		return grid;
	}

	public int[][] getFirstGen(String fileName) {
		int[][] grid = generateGrid();
		List<String> example = getFile(fileName);
		String version = getVersion(example);
		String v5 = "#Life 1.05";
		String v6 = "#Life 1.06";
		if (version.equals(v5)) {
			grid = getFirstGenFive(grid, example);
		}
		if (version.equals(v6)) {
			grid = getFirstGenSix(grid, example);
		}
		return grid;
	}

}
