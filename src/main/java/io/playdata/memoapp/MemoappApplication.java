package io.playdata.memoapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MemoappApplication {
	// 1. port 2. mysql
	public static void main(String[] args) {
		SpringApplication.run(MemoappApplication.class, args);
	}

}
