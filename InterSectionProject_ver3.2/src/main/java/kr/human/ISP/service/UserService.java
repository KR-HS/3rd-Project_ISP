package kr.human.ISP.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import kr.human.ISP.vo.CategoryVO;
import kr.human.ISP.vo.CommVO;
import kr.human.ISP.vo.MoimVO;
import kr.human.ISP.vo.PagingVO;
import kr.human.ISP.vo.SignUpVO;
import kr.human.ISP.vo.UserVO;

public interface UserService extends UserDetailsService {

	// 저장(회원가입)
	public boolean insertUser(UserVO userVO);

	// 수정(정보수정)
	void update(UserVO userVO, String newPassword);

	// 삭제(회원탈퇴)
	void delete(UserVO userVO);

	// 목록보기(관리자)
	PagingVO<UserVO> selectList(CommVO commVO);

	// 아이디 찾기
	String searchUserid(String user_name, String user_phone);

	// 비번 찾기
	UserVO searchPassword(String user_id);
	
	// 인증하기
	void emailConfirm(int user_idx);

	// 아이디 중복확인
	boolean idCheck(String user_id);
	
	// 비밀번호 찾을때 mailController에서 검증할때 사용함
	int userInfoCheck(String user_id, String user_name);
	
	// 임시비밀번호 생성
	void UserTempPassword(String user_id, String user_name, String tempPassword);
	
	// categoryIdx 소분류로 가져오기
	int selectCategoryIdxByScName(String sc_name);
	
	// 제일 최근 가입한 user_idx 가져오기
	int findCurrentUserIdx();
	
	// 회원가입시 유저 카테고리 저장
	void insertUserCategory(HashMap<String, Integer> map);
	
	// 유저 로그인 확인
	boolean checkUserLogin(HashMap<String, String> map);
	
	// user_id로 회원한명 정보 가져오기
	UserVO selectById(String user_id);

	// 관리자 삭제 (삭제)
	void supervisorDelete(UserVO userVO);

	// 관리자 수정
	void supervisorUpdate(UserVO userVO);

	// 찜한 모임 개수 구해오기
	int likeMoimCount(UserVO userVO);

	// 작성한 모임수 구해오기
	int moimCount(UserVO userVO);

	// 참여한 모임수 구해오기
	int joinMoimCount(UserVO userVO);

	// 유저 카테고리 가져오기
	List<CategoryVO> selectCategory(UserVO userVO);

	// 한명 불러오기
	UserVO selectByIdx(int user_idx);
	

	// ----------------------------------- 신청자페이지 관련 --------------------------------------
	
	// 내가 개설한 모임들 가져오기
	public List<MoimVO> selectMoimList(int user_idx);

	// 내가 개설한 모임의 신청자 리스트 받아오기(페이징)
	public PagingVO<UserVO> selectUserList(MoimVO moimVO, CommVO commVO);

	// 승낙하기
	public void apply(int signUp_idx);

	// 거절하기
	public void refuse(int signUp_idx);

	// 신청하기(Insert)
	public void insertSignUp(UserVO userVO, MoimVO moimVO, SignUpVO signUpVO);

	// 모임에 따른 신청인원수 구하기
	public int selectCountByMoim(MoimVO moimVO);
	
	// 모임에 따른 신청자리스트 구하기
	public List<UserVO> selectByMoimIdx(MoimVO moimVO);
	
	// 유저와 모임idx를 통해 signUpIdx 구하기
	public int selectSignUpIdx(HashMap<String, Integer> map);
	
	// 모임 idx를 통해 signUpVO List 가져오기
	public List<SignUpVO> selectSignUpVOByMoimIdx(int moim_idx);

	
	// 0819 새롬 추가 
	//모임별 신청승인된 유저정보 가지고오기
	public List<UserVO> selectSignUpList(int moim_idx);
	

	// 0901 대형추가(회원정보수정)
	public void updateUser(UserVO userVO);
	
	
}
