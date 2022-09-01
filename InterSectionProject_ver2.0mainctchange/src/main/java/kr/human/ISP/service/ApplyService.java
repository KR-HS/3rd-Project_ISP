package kr.human.ISP.service;

import java.util.List;

import kr.human.ISP.vo.MoimVO;

public interface ApplyService {


	List<MoimVO> createMoimList(int user_idx);	
}
