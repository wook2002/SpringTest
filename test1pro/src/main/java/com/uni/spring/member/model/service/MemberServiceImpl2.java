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
public class MemberServiceImpl2 {

	@Autowired
	private SqlSessionTemplate sqlSession;

	@Autowired
	private MemberDao memberDao;

	public Member updateMember(Member m) throws Exception {

		int result = memberDao.updateMember(sqlSession, m);

//		밑에꺼 오류 => update됐는데 오류->rollback되어야함 proxy종류 2가지 -> spring: cglibProxy(target이 되는 proxy를, lagacy : impl단에서 구현체/구현체 아닐 떄 , 
//		memberDao.insertMember(sqlSession, m);

		if (result < 0) {
			Member loginUser = memberDao.loginMember(sqlSession, m);
			return loginUser;
		} else {
			throw new Exception("회원수정 실패");
		}

	}

}
