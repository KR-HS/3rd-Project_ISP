package kr.human.ISP.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.human.ISP.dao.MoimCategoryDAO;
import kr.human.ISP.dao.MoimDAO;
import kr.human.ISP.vo.CategoryVO;
import kr.human.ISP.vo.MoimVO;

@Service("moimService")
@Transactional
public class MoimServiceImpl implements MoimService{
	
	@Autowired
	private MoimDAO moimDAO;
	
	@Autowired
	private MoimCategoryDAO moimCategoryDAO;
	
	
	@Override
	public MoimVO selectByIdx(int moim_idx) {
		MoimVO moimVO = null;
		try {
			moimVO = moimDAO.selectByIdx(moim_idx);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return moimVO;
	}
	
	
	@Override
	public CategoryVO selectCategoryByMoimIdx(int moim_idx) {
		CategoryVO categoryVO=null;
		try {
			categoryVO=moimCategoryDAO.selectCategoryByMoimIdx(moim_idx);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		return categoryVO;
	}
	
	
}
