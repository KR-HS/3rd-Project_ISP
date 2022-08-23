package kr.human.ISP.MoimCreateService;

import java.sql.SQLException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.human.ISP.vo.MoimVO;

public interface MoimViewService {
	
	//모임 1개 내용보기
	@Autowired
	public MoimVO selectByIdx(int moim_idx) throws SQLException;
	
}
