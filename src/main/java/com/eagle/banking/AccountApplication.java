package com.eagle.banking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootConfiguration
@EnableFeignClients
@SpringBootApplication
public class AccountApplication {

  public static void main(String [] args) {
    ConfigurableApplicationContext context = SpringApplication.run(AccountApplication.class, args);
  }

}
