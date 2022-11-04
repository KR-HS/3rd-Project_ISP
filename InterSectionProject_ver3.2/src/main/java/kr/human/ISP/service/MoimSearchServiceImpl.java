package kr.human.ISP.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.human.ISP.dao.MoimCategoryDAO;
import kr.human.ISP.dao.MoimDAO;
import kr.human.ISP.dao.SignUpDAO;
import kr.human.ISP.vo.CategoryVO;
import kr.human.ISP.vo.MoimVO;
import lombok.extern.slf4j.Slf4j;

@Service("MoimSearchService")
@Slf4j
@Transactional
public class MoimSearchServiceImpl implements MoimSearchService{

	@Autowired
	private MoimDAO moimDAO;

	@Autowired
	private SignUpDAO signUpDAO;
	
	@Override
	public Map<String,List<?>> seachMoim(String seachCriteria, String searchContent) {
		Map<String,List<?>> map=new HashMap<>();
		List<MoimVO> moimList=null;
		List<Integer> MoimSignUpCountList = new ArrayList<Integer>();
		log.info("검색기준 : {} // 검색내용 : {}",seachCriteria,searchContent);
		try {
			switch(seachCriteria) {
				case "Subject":
					moimList=moimDAO.searchMoimBySubject(searchContent);
					break;
				case "Content":
					moimList=moimDAO.searchMoimByContent(searchContent);
					break;
			}
			
			for(MoimVO vo : moimList) {
				MoimSignUpCountList.add(signUpDAO.selectCountByMoimApply(vo.getMoim_idx()));
			}
			
			map.put("moimList", moimList);
			map.put("MoimSignUpCountList", MoimSignUpCountList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		log.info("searchMoim 실행 완료: {}",map);
		return map;
	}

}
