package kr.human.ISP.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import kr.human.ISP.vo.MoimVO;

public interface IndexMoimViewService {
	public Map<String,List<?>> selectByTodayMoim(String moim_time);

}
