package kr.human.ISP.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.human.ISP.dao.MoimCategoryDAO;
import kr.human.ISP.dao.MoimDAO;
import kr.human.ISP.dao.SignUpDAO;
import kr.human.ISP.dao.UserDAO;
import kr.human.ISP.vo.CategoryVO;
import kr.human.ISP.vo.MoimVO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IndexMoimViewServiceImpl implements IndexMoimViewService{

	@Autowired
	private MoimDAO moimDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private SignUpDAO signUpDAO;
	
	@Autowired
	private MoimCategoryDAO moimCategoryDAO;
	// 오늘 날짜로 모임 가져오기	
	@Override
	public Map<String,List<?>> selectByTodayMoim(String moim_time) {
		log.info("IndexMoimViewServiceImpl 서비스 selectByTodayMoim 호출중 넘겨받은 오늘 날짜 : {}", moim_time);
		Map<String,List<?>> map = new HashMap<>();
		List<MoimVO> todayMoimList = null;	
		/* List<String> MoimFounderNameList = null; */
		List<Integer> MoimSignUpAgreeList = new ArrayList<Integer>();
		List<CategoryVO> moimCategoryList = null;
		try {
			todayMoimList = moimDAO.selectByTodayMoim(moim_time);
			/* MoimFounderNameList=userDAO.getUserNameList(moim_time); */
			for(MoimVO vo : todayMoimList) {
				MoimSignUpAgreeList.add(signUpDAO.selectCountByMoimApply(vo.getMoim_idx()));
			}
			moimCategoryList=moimCategoryDAO.selectByMoim(moim_time);
			
			map.put("todayMoimList", todayMoimList);
			/* map.put("todayMoimListFounder", MoimFounderNameList); */
			map.put("MoimSignUpAgreeList", MoimSignUpAgreeList);
			map.put("moimCategoryList", moimCategoryList);
			
			log.info("IndexMoimViewServiceImpl 서비스 selectByTodayMoim 호출중 오늘날짜로 조회한 모임 리스트 : {}", todayMoimList);
			log.info("IndexMoimViewServiceImpl 서비스 selectByTodayMoim 호출중 오늘날짜로 조회한 모임승인수 리스트 : {}", MoimSignUpAgreeList);
			log.info("IndexMoimViewServiceImpl 서비스 selectByTodayMoim 호출중 오늘날짜로 조회한 모임카테고리 리스트 : {}", moimCategoryList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	// 오늘 날짜로 모임 가져오기	
	/*
	 * @Override public List<MoimVO> selectByTodayMoim(String moim_time) {
	 * log.info("IndexMoimViewServiceImpl 서비스 selectByTodayMoim 호출중 넘겨받은 오늘 날짜 : {}"
	 * , moim_time); List<MoimVO> todayMoimList = null; List<String>
	 * MoimFounderNameList = null; try { todayMoimList =
	 * moimDAO.selectByTodayMoim(moim_time); List<Integer> todayMoimUserIdxList =
	 * moimDAO.getTodayMoimUserIdx(moim_time);
	 * MoimFounderNameList=userDAO.getUserNameList(todayMoimUserIdxList); log.
	 * info("IndexMoimViewServiceImpl 서비스 selectByTodayMoim 호출중 오늘날짜로 조회한 모임 리스트 : {}"
	 * , todayMoimList); } catch (SQLException e) { e.printStackTrace(); } return
	 * todayMoimList; }
	 */

	
}
