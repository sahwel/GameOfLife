package com.gol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
public class GameOfLifeApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameOfLifeApplication.class, args);
	}

}
