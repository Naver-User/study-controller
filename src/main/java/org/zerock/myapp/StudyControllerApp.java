package org.zerock.myapp;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import lombok.extern.slf4j.Slf4j;


//@Log4j2
@Slf4j


//@ServletComponentScan(basePackages = {"org.zerock.myapp"})
@ServletComponentScan

@SpringBootApplication
public class StudyControllerApp {

	public static void main(String[] args) {
		SpringApplication.run(StudyControllerApp.class, args);
		log.trace("main({}) invoked.", Arrays.toString(args));
	} // main

} // end class
