package kr.human.ISP.controller;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContextUtils;

import kr.human.ISP.service.MypageService;
import kr.human.ISP.service.UserService;
import kr.human.ISP.vo.CommVO;
import kr.human.ISP.vo.MoimVO;
import kr.human.ISP.vo.PagingVO;
import kr.human.ISP.vo.UserVO;

public class MainController_ori {

	@Autowired
	private MypageService mypageService;
	
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = { "/", "/index" })
	public String index(Model model) {
		LocalDateTime today = LocalDateTime.now();
		Calendar cal  = Calendar.getInstance();
		int date = cal.get(Calendar.DATE);
		int dayofWeek = cal.get(Calendar.DAY_OF_WEEK)-1;
		
		String[] korDayOfWeek={"일","월","화","수","목","금","토"};
		
		model.addAttribute("today", today.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
		model.addAttribute("today_date",date);
		model.addAttribute("today_dayList",korDayOfWeek);
		model.addAttribute("today_day",dayofWeek);
		return "index";
	}
	
	@RequestMapping(value="mypage")
	public String mypage(Model model) throws SQLException {
		return "mypage";
	}
	
	@GetMapping(value="myMoim")
	public String MymoimGet() {
		return "redirect:/mypage";
	}
	
	@PostMapping(value="myMoim")
	@ResponseBody
	public Map<String,Object> myMoimPost(Model model,@RequestParam String sortMenu,
			@RequestParam int user_idx,
			@ModelAttribute CommVO commVO,HttpServletRequest request){
		Map<String,Object> map= new HashMap<>();
		UserVO userVO=null;
		List<MoimVO> list = null;
		switch(sortMenu) {
			case "개설":
				list=mypageService.moimList(user_idx);
				userVO=userService.selectByIdx(user_idx);
				break;
			case "신청":
				
				break;
			case "참여중":
				list=mypageService.moimList(user_idx);
				userVO=userService.selectByIdx(user_idx);
				break;
			case "찜한모임":
				list=mypageService.likeMoimList(user_idx);
				userVO=userService.selectByIdx(user_idx);
				break;
		}
		map.put("list",list);
		map.put("user", userVO);
		
		return map;
	}
	@RequestMapping(value = "/decorators/deco.html")
	   public String deco() {
	      return "decorators/deco";
	}
}
