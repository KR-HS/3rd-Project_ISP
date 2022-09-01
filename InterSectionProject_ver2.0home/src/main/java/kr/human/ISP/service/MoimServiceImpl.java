package kr.human.ISP.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.human.ISP.dao.MoimDAO;
import kr.human.ISP.vo.MoimVO;

@Service("moimService")
@Transactional
public class MoimServiceImpl implements MoimService{
	
	@Autowired
	private MoimDAO moimDAO;
	
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
	

}
