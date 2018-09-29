package com.banxue;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@MapperScan("com.banxue.*.mapper")
public class BanxueFrontendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BanxueFrontendApplication.class, args);
	}
}
