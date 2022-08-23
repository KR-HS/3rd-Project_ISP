package kr.human.ISP.MoimCreateService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.human.ISP.vo.SignUpVO;

public interface SignUpService {
	//모임신청하기 
	@Autowired
	public void insert(SignUpVO signUpVO);
	
	
	//모임신청 삭제하기
	@Autowired
	public void delete(int signUp_idx);
		
}
