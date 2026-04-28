package br.jus.tjrn.consulta;

import org.springframework.boot.SpringApplication;

public class TestConsultaApplication {

	public static void main(String[] args) {
		SpringApplication.from(ConsultaApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
