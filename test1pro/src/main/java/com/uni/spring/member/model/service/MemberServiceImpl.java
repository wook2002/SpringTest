package com.uni.spring.member.model.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uni.spring.common.CommException;
import com.uni.spring.member.model.dao.MemberDao;
import com.uni.spring.member.model.dto.Member;

//
//+_+@Transactional(rollbackFor=Exception.class) //Exception도 적용하겠음(추가하겠음)
//+_+@Transactional(rollbackFor={Exception.class, RuntimeException.class}) //여러개 적용도 가능
//@Transactional(noRollbackFor=Exception.class) //Exception도 롤백 적용안하겠음

//밑에껏도 각각 xml에 가능
//@EnableAspectJAutoProxy
//@Transactional(RollbackFor=Exception.class)
@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Autowired
	private MemberDao memberDao;
	
	@Override
	public Member loginMember(Member m) throws Exception {

		Member loginUser = memberDao.loginMember(sqlSession, m);
		System.out.println("loginUser : " + loginUser);
		
		if(loginUser == null) {
			throw new Exception("loginUser확인");
		}
		
		return m;
	}

	@Override
	public void insertMember(Member m) {
		
		int result = memberDao.insertMember(sqlSession, m);
		
		if(result < 0) {
			throw new CommException("회원가입에 실패 하였습니다.");
		}
		
		
	}

	@Override
	public Member loginMember(BCryptPasswordEncoder bCryptPasswordEncoder, Member m) {
		Member loginUser = memberDao.loginMember(sqlSession, m);
		if(loginUser == null) {
			throw new CommException("loginUser확인");
		}
		
		//matches(평문, 암호문) --> boolean
		if(!bCryptPasswordEncoder.matches(m.getUserPwd(), loginUser.getUserPwd())) {
			throw new CommException("암호 불일치");
		}
			
		return loginUser;
	}

	//CommException , Exception , ? 
	@Override
	public Member updateMember(Member m) throws Exception {
		
		int result = memberDao.updateMember(sqlSession, m);
		
//		밑에꺼 오류 => update됐는데 오류->rollback되어야함
//		memberDao.insertMember(sqlSession, m);
		
		if(result > 0 ) {
			Member loginUser = memberDao.loginMember(sqlSession, m);
			return loginUser;
		}else{
			throw new Exception("회원수정 실패");
		}
		
	}

	@Override
	public Member updatePwd(BCryptPasswordEncoder bCryptPasswordEncoder, String originPwd, String encPwd, Member m)
			throws Exception {
		
		int result = 0;
		
		//비번비교 후 변경
		if(bCryptPasswordEncoder.matches(originPwd, m.getUserPwd())) {
			m.setUserPwd(encPwd);
			result = memberDao.updatePwd(sqlSession, m);
		}else {
			throw new CommException("비번틀림");
		}
		
		//update성공유무
		if(result > 0 ) {
			return m;
		}else{
			throw new CommException("비번수정 실패");
		}
		
	}

	

	

	
	
	

	

}
