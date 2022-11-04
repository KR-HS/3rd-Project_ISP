package kr.human.ISP.service;

import java.util.List;
import java.util.Map;

public interface MoimSearchService {
	// 검색기준, 내용으로 모임 검색
	public Map<String,List<?>> seachMoim(String seachCriteria,String searchContent);
	
}
