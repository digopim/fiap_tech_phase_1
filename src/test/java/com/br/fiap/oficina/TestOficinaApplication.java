package com.br.fiap.oficina;

import org.springframework.boot.SpringApplication;

public class TestOficinaApplication {

	static void main(String[] args) {
		SpringApplication.from(Application::main).with(TestcontainersConfiguration.class).run(args);
	}

}
