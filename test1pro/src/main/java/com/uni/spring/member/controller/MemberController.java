package com.uni.spring.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.uni.spring.common.CommException;
import com.uni.spring.member.model.dto.Member;
import com.uni.spring.member.model.service.MemberService;

//+_+Model에다가추가하면 자동으로 추가해라(--응답페이지에 응답할 데이터가 있는경우(3)--)
@SessionAttributes("loginUser") //Model에 Attribute 추가할 때 자동으로 설정된 키값을 세션에 등록시키는 기능.
@Controller
public class MemberController {
	
	//+_+이게 의존성주입(DI) ( => MemberService memberservice = new MemberServiceImpl();)
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MemberService memberServiceImpl2;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder; 

	/*
	//ExceptionHandler(인터페이스) 이용한
	//Exception.class 
	@ExceptionHandler(value = Exception.class)
	public ModelAndView controllerExceptionHandler (Exception e) {
		e.printStackTrace();
		return new ModelAndView("common/errorPageServer").addObject("msg", e.getMessage());
	}
	*/
	
	
	
	
	
	//-- 뷰에서 보내온 값 받기 --
	/*
	//1.HttpServletRequest를 통해 전송받기(기존 jsp/servlet 방식)
	@RequestMapping(value="login.do", method=RequestMethod.POST) //@RequestMapping을 붙여 줌으로써 HandlerMapping으로 등록이  돼서 url이 들어오면 알아서 찾아서 실행해줌(url매핑)
	public String loginMember(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		
		System.out.println(userId);
		System.out.println(userPwd);
		return "main"; //리턴되는 문자열을 servlet-context.xml의 viewResolver(뷰리졸버)에 의해서 사용자가 보게 될 뷰로 포워딩
	}
  */
 

	/*
	//2.@RequestParam - 스프링에서 제공하는 파라미터를 받아오는 방식
	@RequestMapping(value="login.do", method=RequestMethod.POST)
	public String loginMember(@RequestParam("userId") String userId, @RequestParam("userPwd") String userPwd) {
		
		System.out.println(userId);
		System.out.println(userPwd);
		return "main"; 
	}
  */
 

	//3.@RequestParam 생략가능 - 매개변수를 (파라미터로 넘어온 )name과 동일하게 작성해야 자동으로 값이 주어진다.
//	@RequestMapping(value="login.do", method=RequestMethod.POST)
//	public String loginMember(String userId, String userPwd) {
//		System.out.println(userId);
//		System.out.println(userPwd);
//		return "main"; 
//	}

	/*
	//ModelAttribute
	
	//4. @ModelAttribute 를 이용한 방법 - 요청 파라미터가 많은경우 객체 타입으로 넘겨 받는데 기본 생성자와 setter 를 이용한 주입 방식 이므로 둘중하나라도 
	//없으면 에러 . 반드시 name속성에 있는 값과 필드명이 동일 해야 하고 setter 작성하는 규칙에 맞게 작성되어야 한다.
	@RequestMapping(value="login.do", method=RequestMethod.POST) //@RequestMapping을 붙여줌으로써 HandlerMapping으로 등록 ★
	public String loginMember(@ModelAttribute Member m) {
		
		System.out.println("Id : " + m.getUserId());
		System.out.println("Pwd : " + m.getUserPwd());
		return "main"; 
	}
	*/
	
	//5.@ModelAttribute 생략가능, 객체를 바로 전달 받는 방식.
	/*
	@RequestMapping(value="login.do", method=RequestMethod.POST) 
	public String loginMember(Member m, HttpSession session) {
		
		
//		MemberService memberservice = new MemberServiceImpl();
		
		try {
			Member loginUser = memberService.loginMember(m);

			session.setAttribute("loginUser", loginUser);
			
			//redirect: 이거하면 리졸브 설정된거 무시됨
			
			return "redirect:/";
		} catch (Exception e) {
			e.printStackTrace();
			return "common/errorPage";
		}
		
		
	}
	*/
	
	
	 // 로그아웃(1)
//	@RequestMapping("logout.do")
//	public String logout(HttpSession session) {
//		session.invalidate();
//		return "redirect:/";
//	}
	
	
	//--응답페이지에 응답할 데이터가 있는경우(1)--
	//1.Model 객체 사용 방법 -> 응답뷰로 전달하고자 하는 Map(키,value) 형식으로 값을 담을 수 있다.(+_+return타입 String(리졸브붙여줌))
//	@RequestMapping(value="login.do", method=RequestMethod.POST)
//	public String loginMember(Member m, HttpSession session, Model model) {
//		try {
//			Member loginUser = memberService.loginMember(m);
//			session.setAttribute("loginUser", loginUser);
//			
//			return "redirect:/";
//		} catch (Exception e) {
//			e.printStackTrace();
//			model.addAttribute("msg", "로그인실패");
//			return "common/errorPage";
//		}
//	}
	
	
	//--응답페이지에 응답할 데이터가 있는경우(2)--
	//2.ModelAndView 객체를 사용하는 방법 -> 값과 '뷰' 모두 지정 (+_+return타입으로 ModelAndView 보내줌)(값 + url(화면전환))
//	@RequestMapping(value="login.do", method=RequestMethod.POST) //@RequestMapping을 붙여줌으로써 HandlerMapping으로 등록
//	public ModelAndView loginMember(Member m, HttpSession session, ModelAndView mv) {
//		
//		
//		try {
//			Member loginUser = memberService.loginMember(m);
//			session.setAttribute("loginUser", loginUser);
//			
//			
//			mv.setViewName("redirect:/");
//		} catch (Exception e) {
//			e.printStackTrace();
//			mv.addObject("msg", "로그인실패");
//			mv.setViewName("common/errorPage");
//		}
//		
//		return mv;
//	}
	
	
	//@SessionAttribute(1)
	//--응답페이지에 응답할 데이터가 있는경우(3)--(뷰로 보내주는거)
	//3.session에 loginUser를 저장할 때 @SessionAttribute어노테이션 사용하기
	/*
	@RequestMapping(value = "login.do", method = RequestMethod.POST)
	public String loginMember(Member m, Model model){
		
		System.out.println("인코딩확인" + m.getUserId());//필터(인코딩) 확인

		try {
			Member loginUser = memberService.loginMember(m);
			model.addAttribute("loginUser", loginUser);
			return "redirect:/";
		} catch (Exception e) {
			e.printStackTrace();
			return "common/errorPage";
		}
	}
	*/
	
	//@SessionAttribute(2)
	//로그아웃(2)(바로 위에 @SessionAttributes 때문에)+_+(HttpSession=>SessionStatus)(invalidate->setComplete)
	@RequestMapping("logout.do")
	public String logout(SessionStatus status) {
		status.setComplete(); //현재 SessionAttributes에 의해 저장된 오브젝트를 제거  <-- +_+HttpSession session.invalidate();
		return "redirect:/";
	}
	

	//화면전환(입력페이지로)
	@RequestMapping("enrollForm.do")
	public String enrollForm() {
		return "member/memberEnrollForm";
	}
	
	//+_+암호화(salt(~랜덤값) : 설팅기법)
	@RequestMapping("insertMember.do")
	public String insertMember(@ModelAttribute Member m, @RequestParam("post") String post,
		@RequestParam("address1") String address1, @RequestParam("address2") String address2, HttpSession session) {
		//+_+address에 하나로 합쳐서 담아줌(setter = post/address1/address2)
		
		m.setAddress(post+"/"+address1+"/"+address2);

		System.out.println("암호화 전 : " + m.getUserPwd());
		String encPwd = bCryptPasswordEncoder.encode(m.getUserPwd());
		System.out.println("암호화 후 : " + encPwd);
		
		m.setUserPwd(encPwd);
		
		//+_+@Autowired => private MemberService memberService; => 때문에 MemberService(x), memberService(o)
		//MemberService.insertMember(m)(에러)
		memberService.insertMember(m);
		
		session.setAttribute("msg", "회원가입성공");
		return "redirect:/";
	}

	// 암호화 처리 후 로그인(bCryptPasswordEncoder는 주입이 된 상태)
	@RequestMapping(value = "login.do", method = RequestMethod.POST)
	public String loginMember(Member m, Model model) {
		Member loginUser = memberService.loginMember(bCryptPasswordEncoder, m);
		model.addAttribute("loginUser", loginUser);
		return "redirect:/";
	}

	@RequestMapping("myPage.do")
	public String myPage() {
		return "member/myPage";
	}
	
	@RequestMapping("updateMember.do")
	public String updateMember(@ModelAttribute Member m, @RequestParam("post") String post,
			@RequestParam("address1") String address1, @RequestParam("address2") String address2, Model model) throws Exception {
		
		m.setAddress(post+"/"+address1+"/"+address2);
		
		Member userInfo = memberService.updateMember(m);
		
//		Member userInfo = memberServiceImpl2.updateMember(m);
		
		model.addAttribute("loginUser", userInfo);
		
		return "member/myPage";
	}
	
	//updatePwd.do
	@RequestMapping(value="updatePwd.do", method=RequestMethod.POST)
	public String updatePwd(String originPwd, String updatePwd, Model model, HttpServletRequest request){
		
		Member m = (Member)request.getSession().getAttribute("loginUser");
		
		//암호화
		String encPwd = bCryptPasswordEncoder.encode(updatePwd);
		
		//다보냄
		Member userInfo;
		try {
			userInfo = memberService.updatePwd(bCryptPasswordEncoder, originPwd, encPwd, m);
			model.addAttribute("msg", "성공적으로 변경하였습니다");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "실패했습니다");
			return "member/myPage";
		}
		model.addAttribute("loginUser", userInfo);
		
		
		return "member/myPage";
	}
	
}
