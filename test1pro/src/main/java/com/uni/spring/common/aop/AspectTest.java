package com.uni.spring.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

//https://docs.spring.io/spring-framework/docs/5.2.9.RELEASE/spring-framework-reference/core.html#aop-introduction-proxies

@Aspect//(pointCut + Advice)
@Component
public class AspectTest {
	//* 임의의 문자열 1개를 의미
	//.. 임의의 문자열 0개이상을 의미
	//*(..) 모든 메서드의미
	
	@Pointcut("execution(* com.uni.spring..*ServiceImpl.*(..))")
	public void pointCut() {}

	//@Before("execution(* com.uni.spring..*ServiceImpl.*(..))") //이렇게도 가능
	@Before("pointCut()")// 메서드가 실행되기 전에 (공통적으로 처리할 작업을 위해 사용)
	public void before(JoinPoint join) {
		Signature sig = join.getSignature(); //AOP가 적용된 메서드의 정보를 반환(Signature)
		
		Object[] params = join.getArgs(); //파라미터값 반환(Object타입)
		for(Object obj : params) {
			System.out.println("obj1 : " + obj); //두 개 뜨는데 암호화 시킨거랑 아닌거 
		}
//		obj1 : org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder@7915435b
//		obj1 : Member(userId=asdf5, userPwd=asdf, userName=null, email=null, gender=null, age=null, phone=null, address=null, enrollDate=null, modifyDate=null, status=null)
		
		
		System.out.println("메서드 호출 전 확인 : " + sig.getDeclaringTypeName() + " : " + sig.getName());//풀네임(sig.), 메서드명(sig.) 
//		sig.getDeclaringLTypeName() : 메서드가 있는 클래스의 풀네임
		//sig.getName() : 타겟 객체의 메서드명
		//메서드 호출 전 확인 : com.uni.spring.member.model.service.MemberService : loginMember
		
	}
	
	@After("pointCut()") //'예외 발생여부에 상관없이' 비즈니스 로직 후에 무조건 수행하는 기능을 작성하는 advice. 
	public void after(JoinPoint join) {
		Signature sig = join.getSignature(); 
		System.out.println("메서드 호출 후 확인 : " + sig.getDeclaringTypeName() + " : " + sig.getName());
	}
	
	@AfterReturning(pointcut="pointCut()", returning="returnObj") //정상적으로 비즈니스 메서드가 리턴한 결과 데이터를 다른 용도로 처리할 때.
	public void returningPoint(JoinPoint join, Object returnObj) {
		Signature sig = join.getSignature(); 
		System.out.println("메서드 리턴 후 확인 : " + sig.getDeclaringTypeName() + " : " + sig.getName());
		System.out.println("returnObj 확인 : " + returnObj);
	}
	
	@AfterThrowing(pointcut="pointCut()", throwing="exceptionObj") //예외발생 시 수행
	public void throwingPoint(JoinPoint join, Exception exceptionObj) {
		Signature sig = join.getSignature(); 
		System.out.println("예외처리 후 확인 : " + sig.getDeclaringTypeName() + " : " + sig.getName());
		
		if(exceptionObj instanceof IllegalAccessException) {
			System.out.println("부적합한 값이 입력되었습니다. ");
		}else {
			System.out.println("예외발생 메시지 : " + exceptionObj.getMessage());
			System.out.println("예외발생 종류 : " + exceptionObj.getClass().getName());
		}
	}
	
	@Around("pointCut()")
	public Object aroundLog(ProceedingJoinPoint join) throws Throwable {
		
		String methodName = join.getSignature().getName();
		
		StopWatch stopwatch = new StopWatch();
		
		stopwatch.start();
		
		Object obj = join.proceed(); //전과 후를 나누는 '기준'+_+
		
		stopwatch.stop();
		
		System.out.println("초 : " + methodName + " 소요시간(ms) : " + stopwatch.getTotalTimeMillis() + " 초");
		
		return obj;
	}
	
//	메서드 리턴 후 확인 : com.uni.spring.member.model.service.MemberService : loginMember
//	returnObj 확인 : Member(userId=asdf5, userPwd=$2a$10$3GLj.EAfbxbQgsqGRRLIBeIy1rm2qtEvFmRwtMOGJE9GNYznHIB3e, userName=이재욱, email=null, gender=M, age=null, phone=null, address=//, enrollDate=2022-05-04, modifyDate=2022-05-04, status=Y)
//	메서드 호출 후 확인 : com.uni.spring.member.model.service.MemberService : loginMember
//	초 : loginMember 소요시간(ms) : 734 초
	
}
