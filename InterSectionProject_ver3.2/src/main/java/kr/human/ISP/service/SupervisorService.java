package kr.human.ISP.service;

import java.util.List;

import kr.human.ISP.vo.BoardVO;
import kr.human.ISP.vo.CommVO;
import kr.human.ISP.vo.MoimVO;
import kr.human.ISP.vo.PagingVO;
import kr.human.ISP.vo.UserVO;

public interface SupervisorService {
	
	// 17페이지
	
	// 신규회원가입목록 최신 5건 
	List<UserVO> selectFiveUser();
	
	// 최근 모임개설 목록 최신 5건
	List<MoimVO> selectFiveMoim();
	
	// 18페이지

	// 총 회원수 
	int selectTotalCountUser();
	
	// 탈퇴회원수
	int selectCountDeleteUser();
	
	// 유저 이름으로 한명 가져오기
	UserVO selectByUserId(String user_id);
	
	// 메일 목록 가져오기(리스트)
	List<BoardVO> selectMailList();
	
	// 회원 목록 불러오기(페이징)
	PagingVO<UserVO> selectUserList(CommVO commVO);
	
	// 탈퇴 회원 목록 불러오기 (페이징)
	PagingVO<UserVO> selectDeleteUserList(CommVO commVO);
	
	// 선택한 사람 메일보내기
	
	// 선택한 사람 수정하기
	void supervisorUpdate(List<UserVO> list);

	// 19페이지
	
	// 전체 모임수 
	int selectTotalCountMoim();
	
	// 모임 이름으로 검색해서 하나 가져오기
	MoimVO selectByMoimId(String moim_name);
	
	// 전체 모임 목록 불러오기(페이징)
	PagingVO<MoimVO> selectMoimList(CommVO commVO);
	
	// 20페이지
	
	// 메일목록 불러오기(페이징)
	PagingVO<BoardVO> selectMailList(CommVO commVO);
	
	// 21페이지
	
	// 메일전송한 인원 목록 가져오기(페이징)
	PagingVO<UserVO> selectMailSendList(CommVO commVO);
	
	
	
}
