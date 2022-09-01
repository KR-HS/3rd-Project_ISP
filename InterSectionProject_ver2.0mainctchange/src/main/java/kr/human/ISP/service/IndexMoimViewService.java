package kr.human.ISP.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import kr.human.ISP.vo.MoimVO;

public interface IndexMoimViewService {
	// 날짜로만 모임 가져오기
	public Map<String,List<?>> selectByTodayMoim(String moim_time);
	//public Map<String,Object> selectByTodayMoim(String moim_time);
	
	// 날짜 + 대분류로 모임 가져오기
	public Map<String, List<?>> selectByDayPlusLcName(String moim_time, String lc_name);
	//public Map<String, Object> selectByDayPlusLcName(String moim_time, String lc_name);
	
	// 날짜 + 소분류로 모임 가져오기
	public Map<String, List<?>> selectByDayPlusScName(String moim_time, String sc_name);
	//public Map<String, Object> selectByDayPlusScName(String moim_time, String sc_name);
	
	// 날짜 + 지역으로 모임 가져오기
	
	// 날짜 + 대분류 + 지역
	
	// 날짜 + 소분류 + 지역
	
	
	// 갈아 엎어보는 방법
	public Map<String, List<?>> dynamicSelectByAreaLcSc(String moim_time, String area_name, String lc_name, String sc_name); 
	// public Map<String, Object> dynamicSelectByAreaLcSc(Map<String,String> map); 
	
	
	
}
