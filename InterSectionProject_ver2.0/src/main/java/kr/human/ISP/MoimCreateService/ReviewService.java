package kr.human.ISP.MoimCreateService;


import java.sql.SQLException;
import java.util.List;

import kr.human.ISP.vo.ReviewVO;

public interface ReviewService {
	//댓글 하나가져오기
	public ReviewVO selectByIdx(int review_idx);
	//모임별 댓글 리스트 테이블 조인해서 유저정보도 같이 들어있음.
	public List<ReviewVO> selectByMoim(int moim_idx);
	
	//저장
	public void insert(ReviewVO reviewVO);
	//수정
	public void update(ReviewVO reviewVO);
	//삭제
	public void updateIsDelete(int review_idx);
	
	
	
	// 0819 새롬 추가
	public List<ReviewVO> selectByMoimPlusName(int moim_idx);
	
}
