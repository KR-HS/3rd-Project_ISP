package kr.human.ISP.service;


import java.util.List;

import kr.human.ISP.vo.BoardCommentVO;
import kr.human.ISP.vo.BoardVO;
import kr.human.ISP.vo.CommVO;
import kr.human.ISP.vo.PagingVO;
import kr.human.ISP.vo.UpFileVO;

public interface BoardService {
	//목록보기(공지사항)
	PagingVO<BoardVO> selectBoardList(CommVO commVO,String board_category);
	//내용보기 (대형수정함 idx로만 판별)
	BoardVO selectByIdx(int notice_idx);
	//저장하기
	void insertBoard(BoardVO boardVO);
	//수정하기
	void updateBoard(BoardVO BoardVO);
	//삭제하기
	void deleteBoard(BoardVO BoardVo);
	
	BoardVO selectByUserIdx(int user_idx);
	
	//문의내역()
	PagingVO<BoardVO> selectInquiryList(CommVO commVO,int user_idx);
	//댓글추가
	void insertBoardComment(BoardCommentVO boardCommentVO);
	// 게시글 번호에 따른 답변갯수
	String selectCommentCountByBoardIdx(int board_idx);
	// 댓글 가져오기
	BoardCommentVO selectCommentBoard(int board_idx,int user_idx);

	// 답변 있는지 확인하기
	String selectCommentCount(int board_idx, int user_idx);
	
	
	//게시판 파일 업로드
	void uploadProfileImg(UpFileVO upFileVO); // 파일업로드 저장하기. 
	List<UpFileVO> selectListByBoard(int board_idx); // 파일업로드 정보가져오기
	int selectCurrentBoardIdx(); // 현재저장된 게시판 idx 가져오기
	
}
