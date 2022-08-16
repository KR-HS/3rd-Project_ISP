package kr.human.ISP.service;

import java.util.List;

import kr.human.ISP.vo.CommVO;
import kr.human.ISP.vo.MoimVO;
import kr.human.ISP.vo.PagingVO;
import kr.human.ISP.vo.ReviewVO;
import kr.human.ISP.vo.UpFileVO;
import kr.human.ISP.vo.UserVO;

public interface MypageService {

	List<Integer> ApplyAgreeList(int user_idx,String sortMenu);
	PagingVO<MoimVO> createMoimList(CommVO commVO,int user_idx);
	PagingVO<MoimVO> applyMoimList(CommVO commVO,int user_idx);
	PagingVO<MoimVO> joinMoimList(CommVO commVO,int user_idx);
	PagingVO<MoimVO> likeMoimList(CommVO commVO,int user_idx);
	Boolean uploadProfileImg(UpFileVO upFileVO);
	Boolean updateProfileImg(UpFileVO upFileVO);
	UpFileVO getProfile(int user_idx);
	List<ReviewVO> getReview(int user_idx);

	PagingVO<ReviewVO> reviewPaging(CommVO commVO, int user_idx);
	// 한명의 정보 가져오기
	public UserVO selectByIdx(int user_idx);
	// 개설 모임수 갯수찍기
	public int createCount(int user_idx);
	// 참여 모임수 갯수찍기
	public int signupCount(int user_idx);
	// 후기 작성수 갯수찍기
	public int reviewCount(int user_idx);
	// 승인 대기중인 모임 리스트
	public List<String> signUpList(int user_idx);
	// 관심항목 리스트
	public List<String> categoryList(int user_idx);
	// 후기 리스트
	public List<ReviewVO> reviewList(int user_idx);
}
