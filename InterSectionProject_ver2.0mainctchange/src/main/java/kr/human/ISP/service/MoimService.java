package kr.human.ISP.service;

import kr.human.ISP.vo.CategoryVO;
import kr.human.ISP.vo.MoimVO;

public interface MoimService {
	// 모임 하나 가져오기
	public MoimVO selectByIdx(int moim_idx);
	
	// 0829 새롬 추가
	public CategoryVO selectCategoryByMoimIdx(int moim_idx);
	
}
