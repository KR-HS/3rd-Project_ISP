package kr.human.ISP.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContextUtils;

import kr.human.ISP.service.SupervisorService;
import kr.human.ISP.service.UserService;
import kr.human.ISP.vo.CommVO;
import kr.human.ISP.vo.MoimVO;
import kr.human.ISP.vo.PagingVO;
import kr.human.ISP.vo.UserVO;

@Controller
public class SupervisorController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	SupervisorService supervisorService;
	
	@RequestMapping(value = "/admin")
	public String admin() {
		return "admin";
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "HomeMenu")
	@ResponseBody
	public Map<String,?> homeMenu(@RequestParam Map<String, String> map,
			@ModelAttribute CommVO commVO, HttpServletRequest request){
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if(flashMap!=null) {
			map = (Map<String, String>) flashMap.get("map");
		}
		Map<String,Object> resultMap = new HashMap<String, Object>();
		String page = map.get("page");
		switch (page) {
		case "17":
			List<UserVO> userList = supervisorService.selectFiveUser();
			List<MoimVO> moimList = supervisorService.selectFiveMoim();
			resultMap.put("fiveUserList",userList);
			resultMap.put("fiveMoimList",moimList);
			break;
		case "18":
			int selectTotalCountUser = supervisorService.selectTotalCountUser();
			int selectCountDeleteUser = supervisorService.selectCountDeleteUser();
			commVO.setP(1);
			commVO.setS(10);
			commVO.setB(10);
			PagingVO<UserVO> userPagingVO = userService.selectList(commVO);
			resultMap.put("totalCount", selectTotalCountUser);
			resultMap.put("countDeleteUser", selectCountDeleteUser);
			resultMap.put("pv", userPagingVO);
			break;
		case "19":
			int selectTotalCountMoim = supervisorService.selectTotalCountMoim();
			commVO.setP(1);
			commVO.setS(10);
			commVO.setB(10);
			PagingVO<MoimVO> moimPagingVO = supervisorService.selectMoimList(commVO);
			resultMap.put("totalCount", selectTotalCountMoim);
			resultMap.put("pv", moimPagingVO);
			break;
		case "20":
			
			break;
		case "21":
			
			break;
		default:
			break;
		}
		resultMap.put("page",page);
		return resultMap;
	}
}
