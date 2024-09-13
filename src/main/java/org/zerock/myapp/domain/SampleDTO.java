package org.zerock.myapp.domain;

import lombok.Data;


// DTO: Data Transfer Object

@Data
public class SampleDTO { // 전송파라미터 수집용 클래스
	private String name;
	private Integer age;

	
} // end class
