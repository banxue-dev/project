package com.banxue;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.banxue.*.mapper")
public class BanxueBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BanxueBackendApplication.class, args);
	}
}
