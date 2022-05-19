package com.uni.spring.board.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.uni.spring.board.model.dto.Board;
import com.uni.spring.board.model.dto.PageInfo;
import com.uni.spring.board.model.service.BoardService;
import com.uni.spring.common.Pagination;

// 1 '.jsp'안붙는이유? -> Resolver
// 2 @RequestParam(value="currentPage") int currentPage //값이 넘어오지 않아서 에러 발생 
//	Required int parameter 'currentPage' is not present 
//  -> ->파라미터가 필수인지 아닌지 체크 ->해결? 속성값 : required=false //필수인 파라미터가 아니다(그래서 이제 빈값이 들어옴)
// 3. 또 에러 : Optional int parameter 'currentPage' is present but cannot be...
//	int형인데 null이 들어와서 에러 생기는것 -> 해결? default값 지정 

@Controller
public class BoardController {
	
	@Autowired
	public BoardService boardService;
	
	@RequestMapping("listBoard.do")
	public String selectList(@RequestParam(value="currentPage", required=false, defaultValue="1") int currentPage, Model model) {
		// 1 @RequestParam(value="currentPage") int currentPage -> 값이 넘어오지 않아서 에러발생
		
		// 2 @RequestParam(value="currentPage", required=false) int currentPage
		// required : 해당 파라미터가 필수인지 여부 (기본값 true)
		// required=false : 값을 꼭 받아줄 필요가 없다고 선언.

		//--> null은 기본형 int에 들어갈 수 없기 때문에
		
		// 3 @RequestParam(value="currentPage", required=false, defaultValue="1") int currentPage
		// defaultValue : 넘어오는 값이 null인 경우 해당 파라미터 기본값을 지정
		
		// 4 Model model
		
		//게시글 전체 수
		int listCount = boardService.selectListCount(); 
		System.out.println(listCount);
		
		int boardLimit = 5;
		int pageLimit = 10;
		
		PageInfo pi = Pagination.getPageInfo(listCount, currentPage, pageLimit, boardLimit);
		
		ArrayList<Board> list = boardService.selectList(pi);
		
		model.addAttribute("list", list);
		model.addAttribute("pi", pi);
		
		return "board/boardListView";
	}

}
