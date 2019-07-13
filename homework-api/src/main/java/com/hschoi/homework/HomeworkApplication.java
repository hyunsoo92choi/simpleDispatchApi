package com.hschoi.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * <pre>
 * com.hschoi.homework_HomeworkApplication.java
 * </pre>
 * @date : 2019. 7. 12.
 * @author : hychoi
 */
@ComponentScan("com.hschoi")
@SpringBootApplication
public class HomeworkApplication {
	
	public static void main(String[] args) {
        SpringApplication.run(HomeworkApplication.class, args);
    }

}
