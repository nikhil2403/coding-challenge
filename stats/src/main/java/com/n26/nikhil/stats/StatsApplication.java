package com.n26.nikhil.stats;

import com.n26.nikhil.stats.service.impl.AsyncAdd;
import com.n26.nikhil.stats.service.impl.AsyncEvict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StatsApplication implements CommandLineRunner {
	@Autowired
	private AsyncAdd asyncAdd;
	@Autowired
	private AsyncEvict asyncEvict;

	public static void main(String[] args) {
		SpringApplication.run(StatsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		asyncAdd.handleAddToDS();
		asyncEvict.handleRemoveFromDS();
	}
}

