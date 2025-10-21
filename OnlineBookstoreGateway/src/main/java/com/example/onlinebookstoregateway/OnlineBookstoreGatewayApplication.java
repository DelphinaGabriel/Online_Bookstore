package com.example.onlinebookstoregateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class OnlineBookstoreGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineBookstoreGatewayApplication.class, args);
	}

}
