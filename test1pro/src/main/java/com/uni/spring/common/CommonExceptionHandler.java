package com.uni.spring.common;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

//여기 패키지(com.uni.spring) 내에서 예외가 발생하면 2.빈으로 등록해야됨(servlet-context에 "commonExceptionHandler"로 등록했음) 3.img파일도 뜨네?->common/errorPage
@ControllerAdvice("com.uni.spring")
public class CommonExceptionHandler {

	//e.getMessage 띄움
	@ExceptionHandler(value = Exception.class)
	public ModelAndView controllerExceptionHandler (Exception e) {
		e.printStackTrace();
		return new ModelAndView("common/errorPage").addObject("msg", e.getMessage());
	}
	
}
