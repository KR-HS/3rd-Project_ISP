package kr.human.ISP.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.human.ISP.dao.MoimDAO;
import kr.human.ISP.vo.MoimVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service("applyService")
public class ApplyServiceImpl implements ApplyService{
	
	@Autowired
	private MoimDAO moimDAO;

	@Override
	public List<MoimVO> createMoimList(int user_idx) {
		List<MoimVO> list = null;
		try {
			list=moimDAO.selectByUser(user_idx);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	

	

	
	
}
