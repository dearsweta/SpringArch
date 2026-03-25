package com.spring.internal_working.internal_work;


import com.spring.internal_working.internal_work.controller.UserController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication public class InternalWorkApplication {

	public static void main(String[] args) {
		ApplicationContext context =  SpringApplication.run(InternalWorkApplication.class, args);
		var b = context.getBean(UserController.class);
//27.1.0
	}
}
