package kr.human.ISP.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContextUtils;

import kr.human.ISP.service.MoimService;
import kr.human.ISP.service.UserService;
import kr.human.ISP.vo.CommVO;
import kr.human.ISP.vo.MoimVO;
import kr.human.ISP.vo.PagingVO;
import kr.human.ISP.vo.SignUpVO;
import kr.human.ISP.vo.UserVO;

@Controller
public class ApplyController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private MoimService moimService;
	
	@RequestMapping(value="applyPage")
	public String applyPage(Model model, HttpServletRequest request, UserVO userVO) throws SQLException {
		userVO = (UserVO) request.getSession().getAttribute("userVO");
		List<MoimVO> moimList = userService.selectMoimList(userVO.getUser_idx());
		model.addAttribute("moimList", moimList);
		return "applyPage";
	}
	
	@GetMapping(value="userList")
	public String MymoimGet() {
		return "redirect:/applyPage";
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value="userList")
	@ResponseBody
	public Map<String,?> applyPost(Model model,@RequestParam Map<String,String> map,
	@ModelAttribute CommVO commVO,HttpServletRequest request){
		System.out.println("--------------"+map + ": " +commVO);
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if(flashMap!=null) {
			map = (Map<String, String>) flashMap.get("map");
			commVO.setP(Integer.parseInt(map.get("p")));
			commVO.setS(Integer.parseInt(map.get("s")));
			commVO.setB(Integer.parseInt(map.get("b")));
		}
		//UserVO userVO = userService.selectByIdx(Integer.parseInt((String)map.get("user_idx")));
		MoimVO moimVO = moimService.selectByIdx(Integer.parseInt((String)map.get("moim_idx")));
		PagingVO<UserVO> pagingVO = userService.selectUserList(moimVO, commVO);
		Map<String,Object> resultMap = new HashMap<>(); // 결과 리턴 맵
		List<UserVO> userList = userService.selectByMoimIdx(moimVO);
		List<SignUpVO> signUpList = userService.selectSignUpVOByMoimIdx(moimVO.getMoim_idx());
		resultMap.put("pv", pagingVO);
		resultMap.put("userList", userList);
		resultMap.put("cv", commVO);
		resultMap.put("signUpList", signUpList);
		return resultMap;
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "isApply")
	@ResponseBody
	public Map<String,?> isApply(@RequestParam Map<String, String> map, HttpServletRequest request) {
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if(flashMap!=null) {
			map = (Map<String, String>) flashMap.get("map");
		}
		String isApply = map.get("isApply");
		System.out.println(map);
		HashMap<String, Integer> param = new HashMap<String, Integer>();
		Map<String,String> resultMap = new HashMap<String, String>();
		param.put("user_idx", Integer.parseInt(map.get("user_idx")));
		param.put("moim_idx", Integer.parseInt(map.get("moim_idx")));
		if(isApply.equals("승인수락")) {
			userService.apply(userService.selectSignUpIdx(param));
			resultMap.put("apply", "승인완료");
		}else {
			userService.refuse(userService.selectSignUpIdx(param));
			resultMap.put("apply", "승인거절");
		}
		return resultMap;
	}
}
