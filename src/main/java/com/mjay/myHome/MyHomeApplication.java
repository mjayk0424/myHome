package com.mjay.myHome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MyHomeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyHomeApplication.class, args);
	}

}
