package com.villwaa.backend;

import org.springframework.boot.SpringApplication;

public class TestVillwaaBackendApplication {

	public static void main(String[] args) {
		SpringApplication.from(VillwaaBackendApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
