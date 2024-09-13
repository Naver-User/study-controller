package org.zerock.myapp.controller;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.myapp.domain.SampleDTO;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@Slf4j
@NoArgsConstructor

// 이 컨트롤러가 처리할 요청URI의 Base URI를 지정하는 역할을 하기도 합니다.
//@RequestMapping("/은행/")	// Base URI
@RequestMapping("/sample/")	// Base URI


/**
 * -----------------
 * Important
 * -----------------
 * The spring keyword "redirect" does work only when @Controller annotation attached to this controller
 * But if @RestController annotation attached, the "redirect" keyword doesn't work. (***)
 * -----------------
 */

//@RestController
@Controller
public class SampleController {	// POJO
	
	// 1. @RequestMapping OR @RequestMapping("")
//	@RequestMapping("")		// OK: Base URI + No detail URI => "/sample/" with Any HTTP method
	@RequestMapping			// OK: 상동                          상동
	public String h1() {
		log.info("h1() invoked.");
		
		return "H1";
	} // h1
	
	// 2. @RequestMapping(path, method)
	// 처리할 URI = Base URI + Detail URI = "/sample/*" + "/h2" = "/sample/h2"
	@RequestMapping(path="/h2", method = RequestMethod.GET)	// 상세URI: "/h2"
	public String h2() {
		log.info("h2() invoked.");
		
		return "H2";
	} // h2

	// 3. @RequestMapping(path, method)
//	@PutMapping				// OK
//	@DeleteMapping			// OK
//	@TraceMapping			// XX
//	@HeadMapping			// XX
	
	@RequestMapping(
		path= {"/h3-1", "/h3-2"},
		
		// 전송방식(method)의 생략의 의미:
		// 어떤 전송방식이든 처리하지 않겠다는 의미가 아니라,
		// 반대로, 어떤 전송방식이든 다 처리하겠습니다라는 의미가 됩니다.
		method= {RequestMethod.GET, RequestMethod.POST}
	)
//	public
	String h3() {
		log.info("h3() invoked.");
		
		return "H3";
	} // h3
	
	// 4. @RequestMapping(path)
	//    전송방식을 지정하지 않으면 -> 모든 전송방식을 지원하겠다 라는 의미
//	@RequestMapping("/h4")		// Using value element.
	@RequestMapping(path="/h4")	// Using path element.
	String h4() {
		log.info("h4() invoked.");
		
		return "H4";
	} // h4
	
	// 5. @RequestMapping 어노테이션에 대한 단축형 어노테이션이 준비되어 있습니다.
	//    (1) @GetMapping(path)  : GET, path
	//    (2) @PostMapping(path) : POST, path
	//    (3) @PutMapping(path) : PUT, path
	//    (4) @DeleteMapping(path) : DELETE, path
	//    (5) @PatchMapping(path) : PATCH, path
	
	@GetMapping("/h5")			// 권장
//	@GetMapping(path="/h5")		// 비추
	String h5Get() {
		log.info("h5Get() invoked.");
		
		return "H5";
	} // h5Get

	@PostMapping("/h5")			// 권장
	String h5Post() {
		log.info("h5Post() invoked.");
		
		return "h5Post";
	} // h5
	
	
	// 6. @GetMapping or @PostMapping with DTO
	@GetMapping("/h6")
//	@PostMapping("/h6")
	SampleDTO h6(SampleDTO dto) { // DTO 로 전송파라미터 수집
		log.info("h6({}) invoked.", dto);
		
		return dto;
	} // h6
	
	
	// 7. Get All Request Parameters without DTO
	
	// 필수 전송파라미터 중에 하나라도 들어오지 않으면, 400 Bad Request 가
	// 발생하게 되어있는데도 불구하고, 개발자가 직접 아래 어노테이션으로
	// 정상처리되었다(200 OK)라고 지정해준다면, 이건 "모순"이죠!?...
	// 이럴때는 어떻게 응답이 나갈까요!?
//	@ResponseStatus(HttpStatus.OK)	// 이런 모순에서는, 정석대로 오류코드가 나감
	
	@PostMapping(path="/h7", params = { "name", "age" })
	SampleDTO h7(String name, Integer age) {
		log.info("h7({}, {}) invoked.", name, age);
		
		SampleDTO dto = new SampleDTO();
		dto.setName(name);
		dto.setAge(age);
		log.info("\t+ dto: {}", dto);
		
		return dto;
	} // h7
	
	
	// 8. With primitive type parameters
	@ResponseStatus(HttpStatus.OK)	// 200 OK
	@PutMapping("/h8")
	SampleDTO h8(String name, int age) {	// age is a primitive type 
		log.info("h8({}, {}) invoked.", name, age);
		
		SampleDTO dto = new SampleDTO();
		dto.setName(name);
		dto.setAge(age);
		
		log.info("\t+ dto: {}", dto);
		
		return dto;
	} // h8
	
	
	// 9. With @RequestParam annotation
	@DeleteMapping("/h9")
	SampleDTO h9(@RequestParam("name") String name2, @RequestParam("age") Integer age2) {
		log.info("h9({}, {}) invoked.", name2, age2);
		
		SampleDTO dto = new SampleDTO();
		dto.setName(name2);
		dto.setAge(age2);
		
		log.info("\t+ dto: {}", dto);
		
		return dto;
	} // h9
	
	
	// 10. 하나의 전송파라미터는 무조건 "한개"의 값만 전송가능한게 아니라,
	//     기본설계가 하나의 전송파라미터에 한개 이상의 다중값이 전송가능한게
	//     전송파라미터입니다. 이럴때는 컨트롤러의 핸들러가 이 다중값을
	//     어떻게 받아낼수가 있을까!?
	@PatchMapping("/h10")
//	String h10(String[] phoneNumbers) {	// 1, OK but if no request parameters, 500 error
	String h10(
		@RequestParam("phoneNumbers") 	// 5, OK
		List<String> phoneNumbers) {	// 2, XX
//	String h10(Vector<String> phoneNumbers) {	// 3, 절반: 빈 벡터만 생성
//	String h10(
//		@RequestParam("phoneNumbers")
//		Vector<String> phoneNumbers) {	// 4, OK
		
		log.info("h10({}, type: {}) invoked.",
				phoneNumbers, 
				(phoneNumbers != null)? phoneNumbers.getClass().getName() : null);
		
		StringBuffer result = new StringBuffer(); 
		
		for(String phoneNumber : phoneNumbers) {
			result.append(phoneNumber);
		} // enhanced for
		
		return result.toString();
	} // h10
	
	
	// 11. 전송파라미터로, "날짜(시간정보도포함)" 데이터가
	//     수신되었을 때에, 그냥 받으면 무조건 "문자열"인데....
	//     이러한 날짜형식의 문자열로 된 전송파라미터의 값으로,
	//     핸들러가 수신할 때에, 아예 진짜 "날짜(Date같은)"타입의 객체로
	//     변환해서 받을 수가 없을까!!!?? <--- **** : 마지막 종류의 데이터
	
	@GetMapping("/h11")
	Date h11(
//		@DateTimeFormat(iso = ISO.DATE)		// 1, OK : 세계표준시각(시차8시간발생)
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		Date birthDay) {	// 전송파라미터로 날짜형식의 문자열이 들어올때
		log.info("h11({}) invoked.", birthDay);
		
		return birthDay;
	} // h11
	
	
	// 12. Model 타입의 Map 객체에 대해서 알아야 한다!!
	//     Spring Boot 는, 개발자가 만든 컨트롤러를 통해서 처리된 비지니스 로직의
	//     결과물인 Model 데이터를 담을 수 있는 "상자(Box)"를 이미
	//     준비해 놓았습니다. 이 "상자"가 바로, Model 타입의 객체입니다.
	//     이번에는 이 Model 상자를 사용하는 방법을 배웁니다.
	@PutMapping(path="/h12", params= {"name", "age"})
	String h12(String name, Integer age, Model model) {
		log.info("h12({}, {}, model: {}) invoked.", name, age, model);
		log.info("\t+ model type: {}", model.getClass().getName());
		
		// 아래의 메소드로 새로운 Key/Value 쌍을 모델상자안에 추가할 때마다,
		// 실제로는 Request Scope 공유영역에 새로운 "공유속성"을 추가하는 것과
		// 같은 효과를 냅니다.
//		model.addAttribute(KEY, VALUE);
		
		model.addAttribute("name", name);
		model.addAttribute("age", age);
		
		return name + age;
	} // h12
	
	
	// 13. Spring Boot의 FC(즉, DispatcherServlet)에게, Model 상자를 "달라!"라고
	//     하지 않아도, 아래의 어노테이션으로 Model 상자안에 넣어주는 어노테이션인
	//     @ModelAttribute(KEY)에 대해서 배웁니다.
	@DeleteMapping(path="/h13", params= {"name", "age"})
	String h13(
		// "웹 뷰에 노출된 명명된 모델 속성에, 
		// 메서드 매개변수나 메서드 반환 값을 바인딩하는 주석입니다. 
		// @RequestMapping 메서드를 가진 컨트롤러 클래스에서 지원됩니다."			
		
		/**
		 * -----------------
		 * Important
		 * -----------------
		 * The spring keyword "redirect" does work only when @Controller annotation attached to this controller
		 * But if @RestController annotation attached, the "redirect" keyword doesn't work. (***)
		 * -----------------
		 */
			
		@ModelAttribute("name") String name,
		@ModelAttribute("age") Integer age) {
		log.info("h13({}, {}) invoked.", name, age);
		
		return name + age;
	} // h13
	
	
	// 14. Model 타입의 Map 객체에 대해서 알아야 한다!!
	//     Spring Boot 는, 개발자가 만든 컨트롤러를 통해서 처리된 비지니스 로직의
	//     결과물인 Model 데이터를 담을 수 있는 "상자(Box)"를 이미
	//     준비해 놓았습니다. 이 "상자"가 바로, Model 타입의 객체입니다.
	//     이번에는 이 Model 상자를 사용하는 방법을 배웁니다.
	@GetMapping(path="/h14", params= {"name", "age"})
	String h14(String name, Integer age, RedirectAttributes rttrs) {	// 임시상자
		log.info("h14({}, {}, rttrs: {}) invoked.", name, age, rttrs);
		log.info("\t+ rttrs type: {}", rttrs.getClass().getName());
		
		// 이 상자는 임시 상자로, 현재의 핸들러 메소드에서,
		// Re-Direction 을 수행할 때에, Target URI로 함께 전송해줄
		// 전송파라미터를 만들어 주는 역할을 하는 "상자"입니다.
//		rttrs.addAttribute(KEY, VALUE);
		
		rttrs.addAttribute("name", name);
		rttrs.addAttribute("age", age);

//		return "forward:";			// Request Forwarding
		
		// 리다이렉션 수행
//		return "redirect:https://naver.com";				// 1, OK
//		return "redirect:http://localhost:8008/";			// 2, OK
//		return "redirect:/h6";								// 3, XX : 베이스URI가 빠졌으니까
//		return "redirect:/sample/h6";						// 4, OK
		return "redirect:http://localhost:8008/sample/h6";	// 5, OK
	} // h14
	
	
	
	
	
	
	

} // end class
