package com.example.onlinebookstoreeurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class OnlineBookstoreEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineBookstoreEurekaServerApplication.class, args);
	}

}
