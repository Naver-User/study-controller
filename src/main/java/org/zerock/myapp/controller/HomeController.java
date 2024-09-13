package org.zerock.myapp.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;


//@Log4j2
@Slf4j

// (1) MVC 패턴에서 Controller 역할을 하는 클래스임을 명시
// (2) 스프링 컨텍스트(Spring Context) == 스프링 Beans Container 에
//     자동등록되는 빈으로도 됩니다.


// (1) 요청처리에 대한 응답을 클라이언트가 원하는 XML 또는 JSON으로 주는 컨트롤러
@RestController

// (2) 요청처리에 대한 응답을 뷰(JSP, Thymeleaf, ...)를 통해서, HTML문서로 주는 컨트롤러
//@Controller
public class HomeController {	// 컨트롤러

	
	// 1st. Handler	
	// 어떤요청을 수신해서, 처리하지를 지정하는 어노테이션
	@RequestMapping(path = "/", method = RequestMethod.GET)
	public String home(Locale locale) {
		log.info("home({}) invoked.", locale);
		
		Date now = new Date();
		DateFormat dateFormatter = 
			DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedNow = dateFormatter.format(now);
		log.info("\t+ formattedNow: {}", formattedNow);
		
		return formattedNow;
	} // home
	

} // end class
