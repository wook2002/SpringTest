package com.uni.spring.board.model.service;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uni.spring.board.model.dao.BoardDao;
import com.uni.spring.board.model.dto.Board;
import com.uni.spring.board.model.dto.PageInfo;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Autowired
	private BoardDao boardDao;
	
	@Override
	public int selectListCount() {
		return boardDao.selectListCount(sqlSession);
	}

	@Override
	public ArrayList<Board> selectList(PageInfo pi) {
		return boardDao.selectList(sqlSession, pi);
	}

}
