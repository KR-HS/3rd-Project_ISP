package kr.human.ISP.MoimCreateService;


import java.sql.SQLException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.human.ISP.dao.MoimDAO;

import kr.human.ISP.vo.MoimVO;


@Service("moimViewService")
public class MoimViewServiceImpl implements MoimViewService {

	@Autowired
	private MoimDAO moimDAO;

	
	@Override
	public MoimVO selectByIdx(int moim_idx){
		MoimVO vo =null;
		try {
			vo= moimDAO.selectByIdx(moim_idx);
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
		return vo;
	}

}
