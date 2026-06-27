package com.br.fiap.oficina;

import org.springframework.boot.SpringApplication;

public class TestOficinaApplication {

	public static void main(String[] args) {
		SpringApplication.from(OficinaApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
