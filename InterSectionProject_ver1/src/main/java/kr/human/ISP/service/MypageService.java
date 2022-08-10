package kr.human.ISP.service;

import java.util.List;

import kr.human.ISP.vo.CommVO;
import kr.human.ISP.vo.MoimVO;
import kr.human.ISP.vo.PagingVO;
import kr.human.ISP.vo.UpFileVO;

public interface MypageService {

	List<Integer> ApplyAgreeList(int user_idx,String sortMenu);
	PagingVO<MoimVO> createMoimList(CommVO commVO,int user_idx);
	PagingVO<MoimVO> applyMoimList(CommVO commVO,int user_idx);
	PagingVO<MoimVO> joinMoimList(CommVO commVO,int user_idx);
	PagingVO<MoimVO> likeMoimList(CommVO commVO,int user_idx);
	Boolean uploadProfileImg(UpFileVO upFileVO);
	Boolean updateProfileImg(UpFileVO upFileVO);
	UpFileVO getProfile(int user_idx);
}
