package kr.human.ISP.MoimCreateService;

import java.sql.SQLException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.human.ISP.dao.ReviewDAO;
import kr.human.ISP.vo.ReviewVO;


@Service("reviewService")
public class ReviewServiceImpl implements ReviewService{

	@Autowired
	private ReviewDAO reviewDAO;
	
	//댓글하나가지고오기 
	@Override
	public ReviewVO selectByIdx(int review_idx) {
		ReviewVO vo=null;
		try {
			vo=reviewDAO.selectByIdx(review_idx);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return vo;
	
	}
	//모임별 댓글리스트 
	@Override
	public List<ReviewVO> selectByMoim(int moim_idx) {
		List<ReviewVO> list = null;
		try {
			list = reviewDAO.selectByMoim(moim_idx);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	//저장하기 
	@Override
	public void insert(ReviewVO reviewVO) {
		try {
			if(reviewVO.review_content !="" && reviewVO.review_content.trim().length()!=0) {				
				reviewDAO.insert(reviewVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	//수정하기 
	@Override
	public void update(ReviewVO reviewVO) {
		try {
			reviewDAO.update(reviewVO);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	//삭제하기 
	@Override
	public void updateIsDelete(int review_idx) {
		try {			
			ReviewVO vo =null;
			vo=reviewDAO.selectByIdx(review_idx);
			if(vo!=null) {
				reviewDAO.updateIsDelete(review_idx);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public List<ReviewVO> selectByMoimPlusName(int moim_idx) {
		List<ReviewVO> list = null;
		try {
			list = reviewDAO.selectByMoimPlusName(moim_idx);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	


}


