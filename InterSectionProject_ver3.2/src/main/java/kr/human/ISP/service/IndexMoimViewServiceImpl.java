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
import kr.human.ISP.dao.UserDAO;
import kr.human.ISP.vo.CategoryVO;
import kr.human.ISP.vo.MoimVO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
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
		List<String> MoimFounderNameList = null;
		List<Integer> MoimSignUpAgreeList = new ArrayList<Integer>();
		List<CategoryVO> moimCategoryList = null;
		
		try {
			todayMoimList = moimDAO.selectByTodayMoim(moim_time);
			MoimFounderNameList=userDAO.getUserNameList(moim_time);
			moimCategoryList=moimCategoryDAO.selectByMoim(moim_time);
			
			for(MoimVO vo : todayMoimList) {
				MoimSignUpAgreeList.add(signUpDAO.selectCountByMoimApply(vo.getMoim_idx()));
			}

			moimCategoryList=moimCategoryDAO.selectByMoim(moim_time);
	        map.put("moimCategoryList", moimCategoryList);   
			map.put("todayMoimList", todayMoimList);
			map.put("todayMoimListFounder", MoimFounderNameList);
			map.put("MoimSignUpAgreeList", MoimSignUpAgreeList);
			
			log.info("IndexMoimViewServiceImpl 서비스 selectByTodayMoim 호출중 오늘날짜로 조회한 모임 리스트 : {}", todayMoimList);
			log.info("IndexMoimViewServiceImpl 서비스 selectByTodayMoim 호출중 오늘날짜로 조회한 모임개설자 리스트 : {}", moim_time);
			log.info("IndexMoimViewServiceImpl 서비스 selectByTodayMoim 호출중 오늘날짜로 조회한 모임승인수 리스트 : {}", MoimSignUpAgreeList);
			log.info("IndexMoimViewServiceImpl 서비스 selectByTodayMoim 호출중 오늘날짜로 조회한 모임 카테고리 리스트 : {}", moimCategoryList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	
//	@Override
//	public Map<String,Object> selectByTodayMoim(String moim_time) {
//		log.info("IndexMoimViewServiceImpl 서비스 selectByTodayMoim 호출중 넘겨받은 오늘 날짜 : {}", moim_time);
//		Map<String,Object> map = new HashMap<>();
//		List<MoimVO> todayMoimList = null;	
//		List<String> MoimFounderNameList = null;
//		List<Integer> MoimSignUpAgreeList = new ArrayList<Integer>();
//		List<CategoryVO> moimCategoryList = null;
//		
//		try {
//			todayMoimList = moimDAO.selectByTodayMoim(moim_time);
//			MoimFounderNameList=userDAO.getUserNameList(moim_time);
//			moimCategoryList=moimCategoryDAO.selectByMoim(moim_time);
//			
//			for(MoimVO vo : todayMoimList) {
//				MoimSignUpAgreeList.add(signUpDAO.selectCountByMoimApply(vo.getMoim_idx()));
//			}
//			
//			moimCategoryList=moimCategoryDAO.selectByMoim(moim_time);
//			map.put("moimCategoryList", moimCategoryList);   
//			map.put("todayMoimList", todayMoimList);
//			map.put("todayMoimListFounder", MoimFounderNameList);
//			map.put("MoimSignUpAgreeList", MoimSignUpAgreeList);
//			
//			log.info("IndexMoimViewServiceImpl 서비스 selectByTodayMoim 호출중 오늘날짜로 조회한 모임 리스트 : {}", todayMoimList);
//			log.info("IndexMoimViewServiceImpl 서비스 selectByTodayMoim 호출중 오늘날짜로 조회한 모임개설자 리스트 : {}", moim_time);
//			log.info("IndexMoimViewServiceImpl 서비스 selectByTodayMoim 호출중 오늘날짜로 조회한 모임승인수 리스트 : {}", MoimSignUpAgreeList);
//			log.info("IndexMoimViewServiceImpl 서비스 selectByTodayMoim 호출중 오늘날짜로 조회한 모임 카테고리 리스트 : {}", moimCategoryList);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return map;
//	}
	
	
	
	
	
	
	@Override
	public Map<String, List<?>> selectByDayPlusLcName(String moim_time, String lc_name) {
		log.info("IndexMoimViewServiceImpl 서비스 selectByDayPlusLcName 호출중 넘겨받은 오늘 날짜 : {}, 넘겨받은 대분류명 : {}", moim_time, lc_name);
		Map<String,List<?>> map = new HashMap<>();
		HashMap<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("moim_time", moim_time);
		parameterMap.put("lc_name", lc_name);
		
		List<MoimVO> todayMoimList = null;	
		List<String> MoimFounderNameList = null;
		List<Integer> MoimSignUpAgreeList = new ArrayList<Integer>();
		List<CategoryVO> moimCategoryList = null;
		try {
			todayMoimList = moimDAO.selectByDayPlusLcName(parameterMap);	
			
			MoimFounderNameList=userDAO.getUserNameList(moim_time);
			for(MoimVO vo : todayMoimList) {
				MoimSignUpAgreeList.add(signUpDAO.selectCountByMoimApply(vo.getMoim_idx()));
			}

			
			
			
			moimCategoryList=moimCategoryDAO.selectByMoim(moim_time);
	        map.put("moimCategoryList", moimCategoryList);			
			map.put("todayMoimList", todayMoimList);
			map.put("todayMoimListFounder", MoimFounderNameList);
			map.put("MoimSignUpAgreeList", MoimSignUpAgreeList);
			
			log.info("IndexMoimViewServiceImpl 서비스 selectByDayPlusLcName 호출중 오늘날짜로 조회한 모임 리스트 : {}", todayMoimList);
			log.info("IndexMoimViewServiceImpl 서비스 selectByDayPlusLcName 호출중 오늘날짜로 조회한 모임개설자 리스트 : {}", MoimFounderNameList);
			log.info("IndexMoimViewServiceImpl 서비스 selectByDayPlusLcName 호출중 오늘날짜로 조회한 모임승인수 리스트 : {}", MoimSignUpAgreeList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}
	
//	@Override
//	public Map<String, Object> selectByDayPlusLcName(String moim_time, String lc_name) {
//		log.info("IndexMoimViewServiceImpl 서비스 selectByDayPlusLcName 호출중 넘겨받은 오늘 날짜 : {}, 넘겨받은 대분류명 : {}", moim_time, lc_name);
//		Map<String,Object> map = new HashMap<>();
//		HashMap<String, String> parameterMap = new HashMap<String, String>();
//		parameterMap.put("moim_time", moim_time);
//		parameterMap.put("lc_name", lc_name);
//		
//		List<MoimVO> todayMoimList = null;	
//		List<String> MoimFounderNameList = null;
//		List<Integer> MoimSignUpAgreeList = new ArrayList<Integer>();
//		List<CategoryVO> moimCategoryList = null;
//		try {
//			todayMoimList = moimDAO.selectByDayPlusLcName(parameterMap);	
//			
//			MoimFounderNameList=userDAO.getUserNameList(moim_time);
//			for(MoimVO vo : todayMoimList) {
//				MoimSignUpAgreeList.add(signUpDAO.selectCountByMoimApply(vo.getMoim_idx()));
//			}
//			
//			
//			
//			
//			moimCategoryList=moimCategoryDAO.selectByMoim(moim_time);
//			map.put("moimCategoryList", moimCategoryList);			
//			map.put("todayMoimList", todayMoimList);
//			map.put("todayMoimListFounder", MoimFounderNameList);
//			map.put("MoimSignUpAgreeList", MoimSignUpAgreeList);
//			
//			log.info("IndexMoimViewServiceImpl 서비스 selectByDayPlusLcName 호출중 오늘날짜로 조회한 모임 리스트 : {}", todayMoimList);
//			log.info("IndexMoimViewServiceImpl 서비스 selectByDayPlusLcName 호출중 오늘날짜로 조회한 모임개설자 리스트 : {}", MoimFounderNameList);
//			log.info("IndexMoimViewServiceImpl 서비스 selectByDayPlusLcName 호출중 오늘날짜로 조회한 모임승인수 리스트 : {}", MoimSignUpAgreeList);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return map;
//	}






	@Override
	public Map<String, List<?>> selectByDayPlusScName(String moim_time, String sc_name) {
		log.info("IndexMoimViewServiceImpl 서비스 selectByDayPlusScName 호출중 넘겨받은 오늘 날짜 : {}, 넘겨받은 대분류명 : {}", moim_time, sc_name);
		Map<String,List<?>> map = new HashMap<>();
		HashMap<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("moim_time", moim_time);
		parameterMap.put("sc_name", sc_name);
		
		List<MoimVO> todayMoimList = null;	
		List<String> MoimFounderNameList = null;
		List<Integer> MoimSignUpAgreeList = new ArrayList<Integer>();
		List<CategoryVO> moimCategoryList = null;
		try {
			todayMoimList = moimDAO.selectByDayPlusScName(parameterMap);	
			
			MoimFounderNameList=userDAO.getUserNameList(moim_time);
			for(MoimVO vo : todayMoimList) {
				MoimSignUpAgreeList.add(signUpDAO.selectCountByMoimApply(vo.getMoim_idx()));
			}

			
			
			
			moimCategoryList=moimCategoryDAO.selectByMoim(moim_time);
	        map.put("moimCategoryList", moimCategoryList);			
			map.put("todayMoimList", todayMoimList);
			map.put("todayMoimListFounder", MoimFounderNameList);
			map.put("MoimSignUpAgreeList", MoimSignUpAgreeList);
			
			log.info("IndexMoimViewServiceImpl 서비스 selectByDayPlusScName 호출중 오늘날짜로 조회한 모임 리스트 : {}", todayMoimList);
			log.info("IndexMoimViewServiceImpl 서비스 selectByDayPlusScName 호출중 오늘날짜로 조회한 모임개설자 리스트 : {}", MoimFounderNameList);
			log.info("IndexMoimViewServiceImpl 서비스 selectByDayPlusScName 호출중 오늘날짜로 조회한 모임승인수 리스트 : {}", MoimSignUpAgreeList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}
	
//	@Override
//	public Map<String, Object> selectByDayPlusScName(String moim_time, String sc_name) {
//		log.info("IndexMoimViewServiceImpl 서비스 selectByDayPlusScName 호출중 넘겨받은 오늘 날짜 : {}, 넘겨받은 대분류명 : {}", moim_time, sc_name);
//		Map<String,Object> map = new HashMap<>();
//		HashMap<String, String> parameterMap = new HashMap<String, String>();
//		parameterMap.put("moim_time", moim_time);
//		parameterMap.put("sc_name", sc_name);
//		
//		List<MoimVO> todayMoimList = null;	
//		List<String> MoimFounderNameList = null;
//		List<Integer> MoimSignUpAgreeList = new ArrayList<Integer>();
//		List<CategoryVO> moimCategoryList = null;
//		try {
//			todayMoimList = moimDAO.selectByDayPlusScName(parameterMap);	
//			
//			MoimFounderNameList=userDAO.getUserNameList(moim_time);
//			for(MoimVO vo : todayMoimList) {
//				MoimSignUpAgreeList.add(signUpDAO.selectCountByMoimApply(vo.getMoim_idx()));
//			}
//			
//			
//			
//			
//			moimCategoryList=moimCategoryDAO.selectByMoim(moim_time);
//			map.put("moimCategoryList", moimCategoryList);			
//			map.put("todayMoimList", todayMoimList);
//			map.put("todayMoimListFounder", MoimFounderNameList);
//			map.put("MoimSignUpAgreeList", MoimSignUpAgreeList);
//			
//			log.info("IndexMoimViewServiceImpl 서비스 selectByDayPlusScName 호출중 오늘날짜로 조회한 모임 리스트 : {}", todayMoimList);
//			log.info("IndexMoimViewServiceImpl 서비스 selectByDayPlusScName 호출중 오늘날짜로 조회한 모임개설자 리스트 : {}", MoimFounderNameList);
//			log.info("IndexMoimViewServiceImpl 서비스 selectByDayPlusScName 호출중 오늘날짜로 조회한 모임승인수 리스트 : {}", MoimSignUpAgreeList);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return map;
//	}






	@Override
	public Map<String, List<?>> dynamicSelectByAreaLcSc(String moim_time, String area_name, String lc_name, String sc_name) {
		log.info("IndexMoimViewServiceImpl 서비스 dynamicSelectByAreaLcSc 호출시 넘겨받은 모임날짜, 지역 : {}, {}", moim_time, area_name);
		log.info("IndexMoimViewServiceImpl 서비스 dynamicSelectByAreaLcSc 호출시 넘겨받은 대분류, 소분류 : {}, {}", lc_name, sc_name);
		
		Map<String,List<?>> mapi = null;
		
		if(area_name == "" && area_name.length() < 2) { // 지역명이 없을때
			
			
			
			if(sc_name == "" && sc_name.length() < 2 && lc_name=="" && lc_name.length() < 2) { // 모임시간
				log.info("dynamicSelectByAreaLcSc 호출중 모임시간만 실행 : {}",moim_time);
				Map<String, List<?>> map = selectByTodayMoim(moim_time);
				return map;
				
			}else if(sc_name != "" && sc_name.length() > 1) {// 모임시간 + 소분류
				
				log.info("dynamicSelectByAreaLcSc 호출중 모임시간 + 소분류 실행 : {}, {}",moim_time, sc_name);
				Map<String, List<?>> map = selectByDayPlusScName(moim_time, sc_name);
				return map;
			}else if(sc_name == "" && sc_name.length() < 2) {
				
				
				log.info("dynamicSelectByAreaLcSc 호출중 모임시간 + 대분류 실행 : {}, {}",moim_time, lc_name);
				
				Map<String, List<?>> map = selectByDayPlusLcName(moim_time, lc_name);
				return map;
			}
			
			
			
			
		}else if(area_name != "" && area_name.length() > 1) { // 지역명이 있을때
			if(sc_name == "" && sc_name.length() < 2) {	// 소분류가 없다면

				if(lc_name != "" && lc_name.length() > 1) { // 대분류가 있다면  --> 모임시간 + 지역명 + 대분류
					
					log.info("dynamicSelectByAreaLcSc 호출중 지역명 + 대분류 실행 : {}, {}", area_name, lc_name);
					Map<String,List<?>> map = new HashMap<>();
					HashMap<String, String> parameterMap = new HashMap<String, String>();
					parameterMap.put("moim_time", moim_time);
					parameterMap.put("area_name", area_name);
					parameterMap.put("lc_name", lc_name);
					
					
					
					
					List<MoimVO> todayMoimList = null;	
					List<String> MoimFounderNameList = null;
					List<Integer> MoimSignUpAgreeList = new ArrayList<Integer>();
	
					try {
						todayMoimList = moimDAO.selectByDayPlusAreaPlusLc(parameterMap);	
						
						MoimFounderNameList=userDAO.getUserNameList(moim_time);
						for(MoimVO vo : todayMoimList) {
							MoimSignUpAgreeList.add(signUpDAO.selectCountByMoimApply(vo.getMoim_idx()));
						}
	
									
						
						map.put("todayMoimList", todayMoimList);
						map.put("todayMoimListFounder", MoimFounderNameList);
						map.put("MoimSignUpAgreeList", MoimSignUpAgreeList);
						
						log.info("IndexMoimViewServiceImpl 서비스 dynamicSelectByAreaLcSc 호출중 날짜 + 지역 + 대분류로 조회한 모임 리스트 : {}", todayMoimList);
						log.info("IndexMoimViewServiceImpl 서비스 dynamicSelectByAreaLcSc 호출중 날짜 + 지역 + 대분류로 조회한 모임개설자 리스트 : {}", MoimFounderNameList);
						log.info("IndexMoimViewServiceImpl 서비스 dynamicSelectByAreaLcSc 호출중 날짜 + 지역 + 대분류로 조회한 모임승인수 리스트 : {}", MoimSignUpAgreeList);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return map;
					
				}else if(lc_name == "" && lc_name.length() < 2) { // 대분류가 없다면  --> 모임시간 + 지역명
					log.info("dynamicSelectByAreaLcSc 호출중 모임시간 + 지역명 실행 : {}, {}", moim_time, area_name);
					Map<String,List<?>> map = new HashMap<>();
					HashMap<String, String> parameterMap = new HashMap<String, String>();
					parameterMap.put("moim_time", moim_time);
					parameterMap.put("area_name", area_name);
	
					
					
					
					List<MoimVO> todayMoimList = null;	
					List<String> MoimFounderNameList = null;
					List<Integer> MoimSignUpAgreeList = new ArrayList<Integer>();
	
					try {
						todayMoimList = moimDAO.selectByDayPlusArea(parameterMap);	
						
						MoimFounderNameList=userDAO.getUserNameList(moim_time);
						for(MoimVO vo : todayMoimList) {
							MoimSignUpAgreeList.add(signUpDAO.selectCountByMoimApply(vo.getMoim_idx()));
						}
	
									
						
						map.put("todayMoimList", todayMoimList);
						map.put("todayMoimListFounder", MoimFounderNameList);
						map.put("MoimSignUpAgreeList", MoimSignUpAgreeList);
						
						log.info("IndexMoimViewServiceImpl 서비스 dynamicSelectByAreaLcSc 호출중 날짜 + 지역으로 조회한 모임 리스트 : {}", todayMoimList);
						log.info("IndexMoimViewServiceImpl 서비스 dynamicSelectByAreaLcSc 호출중 날짜 + 지역으로 모임개설자 리스트 : {}", MoimFounderNameList);
						log.info("IndexMoimViewServiceImpl 서비스 dynamicSelectByAreaLcSc 호출중 날짜 + 지역으로 모임승인수 리스트 : {}", MoimSignUpAgreeList);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return map;
					
				}
				
			}else if(sc_name != "" && sc_name.length() > 1) { // 지역명 + 소분류가 있다면  --> 모임시간 + 지역명 + 소분류
				
				log.info("dynamicSelectByAreaLcSc 호출중 지역명 + 대분류 실행 : {}, {}", area_name, sc_name);
				Map<String,List<?>> map = new HashMap<>();
				HashMap<String, String> parameterMap = new HashMap<String, String>();
				parameterMap.put("moim_time", moim_time);
				parameterMap.put("area_name", area_name);
				parameterMap.put("sc_name", sc_name);
				
				
				
				
				List<MoimVO> todayMoimList = null;	
				List<String> MoimFounderNameList = null;
				List<Integer> MoimSignUpAgreeList = new ArrayList<Integer>();

				try {
					todayMoimList = moimDAO.selectByDayPlusAreaPlusSc(parameterMap);	
					
					MoimFounderNameList=userDAO.getUserNameList(moim_time);
					for(MoimVO vo : todayMoimList) {
						MoimSignUpAgreeList.add(signUpDAO.selectCountByMoimApply(vo.getMoim_idx()));
					}

								
					
					map.put("todayMoimList", todayMoimList);
					map.put("todayMoimListFounder", MoimFounderNameList);
					map.put("MoimSignUpAgreeList", MoimSignUpAgreeList);
					
					log.info("IndexMoimViewServiceImpl 서비스 dynamicSelectByAreaLcSc 호출중 날짜 + 지역 + 소분류로 조회한 모임 리스트 : {}", todayMoimList);
					log.info("IndexMoimViewServiceImpl 서비스 dynamicSelectByAreaLcSc 호출중 날짜 + 지역 + 소분류로 조회한 모임개설자 리스트 : {}", MoimFounderNameList);
					log.info("IndexMoimViewServiceImpl 서비스 selectByDayPlusScName 호출중 오늘날짜로 조회한 모임승인수 리스트 : {}", MoimSignUpAgreeList);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return map;
				
			}
			
			
			
		}
		
		
		
		return mapi;
		
	}
	
	
//	@Override
//	public Map<String, Object> dynamicSelectByAreaLcSc(Map<String,String> map) {
//		Map<String,Object> mapi = null;
//		String area_name = map.get("area_name");
//		String sc_name = map.get("sc_name");
//		String moim_time = map.get("moimDate");
//		String lc_name = map.get("lc_name");
//		log.info("IndexMoimViewServiceImpl 서비스 dynamicSelectByAreaLcSc 호출시 넘겨받은 모임날짜, 지역 : {}, {}", moim_time, area_name);
//		log.info("IndexMoimViewServiceImpl 서비스 dynamicSelectByAreaLcSc 호출시 넘겨받은 대분류, 소분류 : {}, {}", lc_name, sc_name);
//		
//		if(area_name == "" && area_name.length() < 2) { // 지역명이 없을때
//			
//			if(sc_name == "" && sc_name.length() < 2) { // 모임시간 + 대분류
//				log.info("dynamicSelectByAreaLcSc 호출중 모임시간 + 대분류 실행 : {}, {}",moim_time, lc_name);
//				
//				Map<String, Object> map1 = selectByDayPlusLcName(moim_time, lc_name);
//				return map1;
//				
//			}else if(sc_name != "" && sc_name.length() > 1) {// 모임시간 + 소분류
//				
//				log.info("dynamicSelectByAreaLcSc 호출중 모임시간 + 소분류 실행 : {}, {}",moim_time, sc_name);
//				Map<String, Object> map1 = selectByDayPlusScName(moim_time, sc_name);
//				return map1;
//			}
//			
//		}else if(area_name != null && area_name.length() > 1) { // 지역명이 있을때
//			if(sc_name == null && sc_name.length() < 2) {	// 소분류가 없다면
//				
//				if(lc_name != null && lc_name.length() > 1) { // 대분류가 있다면  --> 모임시간 + 지역명 + 대분류
//					
//					log.info("dynamicSelectByAreaLcSc 호출중 지역명 + 대분류 실행 : {}, {}", area_name, lc_name);
//					Map<String,Object> map1 = new HashMap<>();
//					HashMap<String, String> parameterMap = new HashMap<String, String>();
//					parameterMap.put("moim_time", moim_time);
//					parameterMap.put("area_name", area_name);
//					parameterMap.put("lc_name", lc_name);
//					
//					
//					
//					
//					List<MoimVO> todayMoimList = null;	
//					List<String> MoimFounderNameList = null;
//					List<Integer> MoimSignUpAgreeList = new ArrayList<Integer>();
//					
//					try {
//						todayMoimList = moimDAO.selectByDayPlusAreaPlusLc(parameterMap);	
//						
//						MoimFounderNameList=userDAO.getUserNameList(moim_time);
//						for(MoimVO vo : todayMoimList) {
//							MoimSignUpAgreeList.add(signUpDAO.selectCountByMoimApply(vo.getMoim_idx()));
//						}
//						
//						
//						
//						map1.put("todayMoimList", todayMoimList);
//						map1.put("todayMoimListFounder", MoimFounderNameList);
//						map1.put("MoimSignUpAgreeList", MoimSignUpAgreeList);
//						
//						log.info("IndexMoimViewServiceImpl 서비스 dynamicSelectByAreaLcSc 호출중 날짜 + 지역 + 대분류로 조회한 모임 리스트 : {}", todayMoimList);
//						log.info("IndexMoimViewServiceImpl 서비스 dynamicSelectByAreaLcSc 호출중 날짜 + 지역 + 대분류로 조회한 모임개설자 리스트 : {}", MoimFounderNameList);
//						log.info("IndexMoimViewServiceImpl 서비스 dynamicSelectByAreaLcSc 호출중 날짜 + 지역 + 대분류로 조회한 모임승인수 리스트 : {}", MoimSignUpAgreeList);
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}
//					return map1;
//					
//				}else if(lc_name == null && lc_name.length() < 2) { // 대분류가 없다면  --> 모임시간 + 지역명
//					log.info("dynamicSelectByAreaLcSc 호출중 모임시간 + 지역명 실행 : {}, {}", moim_time, area_name);
//					Map<String,Object> map1 = new HashMap<>();
//					HashMap<String, String> parameterMap = new HashMap<String, String>();
//					parameterMap.put("moim_time", moim_time);
//					parameterMap.put("area_name", area_name);
//					
//					
//					
//					
//					List<MoimVO> todayMoimList = null;	
//					List<String> MoimFounderNameList = null;
//					List<Integer> MoimSignUpAgreeList = new ArrayList<Integer>();
//					
//					try {
//						todayMoimList = moimDAO.selectByDayPlusArea(parameterMap);	
//						
//						MoimFounderNameList=userDAO.getUserNameList(moim_time);
//						for(MoimVO vo : todayMoimList) {
//							MoimSignUpAgreeList.add(signUpDAO.selectCountByMoimApply(vo.getMoim_idx()));
//						}
//						
//						
//						
//						map1.put("todayMoimList", todayMoimList);
//						map1.put("todayMoimListFounder", MoimFounderNameList);
//						map1.put("MoimSignUpAgreeList", MoimSignUpAgreeList);
//						
//						log.info("IndexMoimViewServiceImpl 서비스 dynamicSelectByAreaLcSc 호출중 날짜 + 지역으로 조회한 모임 리스트 : {}", todayMoimList);
//						log.info("IndexMoimViewServiceImpl 서비스 dynamicSelectByAreaLcSc 호출중 날짜 + 지역으로 모임개설자 리스트 : {}", MoimFounderNameList);
//						log.info("IndexMoimViewServiceImpl 서비스 dynamicSelectByAreaLcSc 호출중 날짜 + 지역으로 모임승인수 리스트 : {}", MoimSignUpAgreeList);
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}
//					return map1;
//					
//				}
//				
//			}else if(sc_name != null && sc_name.length() > 1) { // 지역명 + 소분류가 있다면  --> 모임시간 + 지역명 + 소분류
//				
//				log.info("dynamicSelectByAreaLcSc 호출중 지역명 + 대분류 실행 : {}, {}", area_name, sc_name);
//				Map<String,Object> map1 = new HashMap<>();
//				HashMap<String, String> parameterMap = new HashMap<String, String>();
//				parameterMap.put("moim_time", moim_time);
//				parameterMap.put("area_name", area_name);
//				parameterMap.put("sc_name", sc_name);
//				
//				
//				
//				
//				List<MoimVO> todayMoimList = null;	
//				List<String> MoimFounderNameList = null;
//				List<Integer> MoimSignUpAgreeList = new ArrayList<Integer>();
//				
//				try {
//					todayMoimList = moimDAO.selectByDayPlusAreaPlusSc(parameterMap);	
//					
//					MoimFounderNameList=userDAO.getUserNameList(moim_time);
//					for(MoimVO vo : todayMoimList) {
//						MoimSignUpAgreeList.add(signUpDAO.selectCountByMoimApply(vo.getMoim_idx()));
//					}
//					
//					
//					
//					map1.put("todayMoimList", todayMoimList);
//					map1.put("todayMoimListFounder", MoimFounderNameList);
//					map1.put("MoimSignUpAgreeList", MoimSignUpAgreeList);
//					
//					log.info("IndexMoimViewServiceImpl 서비스 dynamicSelectByAreaLcSc 호출중 날짜 + 지역 + 소분류로 조회한 모임 리스트 : {}", todayMoimList);
//					log.info("IndexMoimViewServiceImpl 서비스 dynamicSelectByAreaLcSc 호출중 날짜 + 지역 + 소분류로 조회한 모임개설자 리스트 : {}", MoimFounderNameList);
//					log.info("IndexMoimViewServiceImpl 서비스 selectByDayPlusScName 호출중 오늘날짜로 조회한 모임승인수 리스트 : {}", MoimSignUpAgreeList);
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//				return map1;
//				
//			}
//			
//			
//			
//		}
//		
//		
//		
//		return mapi;
//		
//	}
	

	
}
