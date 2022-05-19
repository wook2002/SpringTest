package com.uni.spring.common.interceptor;

import java.net.InetAddress;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.uni.spring.member.model.dto.Member;

import ch.qos.logback.classic.Logger;

// HandlerInterceptorAdapter : Interceptor구현
// 이 Abstract Class는 HandlerInterceptor Interface를 상속받아 구현됨
// servlet-context에 interceptor매핑
public class LoginInterceptor extends HandlerInterceptorAdapter {

	// LoginInterceptor클래스의 로거를 가져옴
	private static final Logger logger = (Logger)LoggerFactory.getLogger(LoginInterceptor.class);

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		HttpSession session = request.getSession();
		Member loginUser = (Member)session.getAttribute("loginUser");
		
		if(loginUser != null) {
			InetAddress local = InetAddress.getLocalHost();
			logger.info(loginUser.getUserId() + " " + local.getHostAddress());
			
		}
	}

	

	
	
}
