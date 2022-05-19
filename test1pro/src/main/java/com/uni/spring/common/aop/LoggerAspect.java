package com.uni.spring.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import ch.qos.logback.classic.Logger;

public class LoggerAspect {
	private static final Logger logger=(Logger) LoggerFactory.getLogger(LoggerAspect.class); //로그찍을 떄 필요
	
	
	
	
	public Object aroundLogAdvice(ProceedingJoinPoint join) throws Throwable {
		
		Signature sig = join.getSignature();
		String type = sig.getDeclaringTypeName();
		String methodName = sig.getName();

		System.out.println("type : " + type);
		System.out.println("methodName : " + methodName);
//		type : com.uni.spring.member.model.dao.MemberDao
//		methodName : loginMember
		
//		근데 순서가 왜지?
//		AspectTest-> obj1 : org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder@7915435b
//		AspectTest-> obj1 : Member(userId=asdf5, userPwd=asdf, userName=null, email=null, gender=null, age=null, phone=null, address=null, enrollDate=null, modifyDate=null, status=null)
//		AspectTest-> 메서드 호출 전 확인 : com.uni.spring.member.model.service.MemberService : loginMember
//		LoggerAspect-> type : com.uni.spring.member.model.dao.MemberDao
//		LoggerAspect-> methodName : loginMember
		
		String cName = "";
		if(type.indexOf("Controller")>-1) { //Controller 포함하면 -1보다 큼
			cName="Controller : ";
		}else if(type.indexOf("Service")>-1) { //Service 포함하면 -1보다 큼
			cName="Service : ";
		}else if(type.indexOf("Dao")>-1) { //Dao 포함하면 -1보다 큼
			cName="Dao : ";
		}
		
		
		logger.info("[Logger + Aspect : Before] " + cName + type + "." + methodName + "()");
		
		Object obj = join.proceed(); //전과 후를 나누는 '기준'+_+

		logger.info("[Logger + Aspect : After] " + cName + type + "." + methodName + "()");
		
		
		return obj;
	}
}
