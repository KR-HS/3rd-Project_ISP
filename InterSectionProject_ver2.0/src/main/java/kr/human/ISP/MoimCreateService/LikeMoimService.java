package kr.human.ISP.MoimCreateService;

import kr.human.ISP.vo.LikeMoimVO;

public interface LikeMoimService {
	//찜하기 
	public void insert(LikeMoimVO likeMoimVO);
	//찜삭제
	public void delete(int user_idx);

}
