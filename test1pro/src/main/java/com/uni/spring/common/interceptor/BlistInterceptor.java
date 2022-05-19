package com.uni.spring.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.uni.spring.member.model.dto.Member;

import ch.qos.logback.classic.Logger;

public class BlistInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = (Logger)LoggerFactory.getLogger(BlistInterceptor.class);

	//인터셉터
	// 1 override에서 preHandle 클릭
	// 2 return false; // 컨트롤러가 실행되면 안되니까
	// 3 return true; // 컨트롤러로 감
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession();
		
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			logger.info("비로그인 상태에서[ " + request.getRequestURI() + " ] 접근시도 !! ");
			
			session.setAttribute("msg", "로그인후 이용하세요!");
			
			response.sendRedirect("/spring");
			return false;
		}
		
		return true;
		
	}
	
	

}
