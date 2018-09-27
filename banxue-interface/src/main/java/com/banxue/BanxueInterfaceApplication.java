package com.banxue;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.banxue.*.mapper")
public class BanxueInterfaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BanxueInterfaceApplication.class, args);
	}
}
