package com.jumooong.forms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class FormsApplication {

	public static void main(String[] args) {
		run();
		SpringApplication.run(FormsApplication.class, args);
	}

	public static void run(){
		String plainPassword = "secret";
		String hash = new BCryptPasswordEncoder().encode(plainPassword);
		System.out.println(hash);
	}


}
