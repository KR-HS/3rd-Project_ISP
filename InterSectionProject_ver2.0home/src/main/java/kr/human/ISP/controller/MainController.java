package kr.human.ISP.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.human.ISP.MoimCreateService.MoimCreateService;
import kr.human.ISP.service.IndexMoimViewService;
import kr.human.ISP.service.UserService;
import kr.human.ISP.vo.MoimVO;
import kr.human.ISP.vo.UserVO;
import lombok.extern.java.Log;

@Controller
public class MainController {

	
	@Autowired
	private MoimCreateService moimCreateService;
	
	@Autowired
	private IndexMoimViewService indexMoimViewService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = { "/", "/index" })
	public String index(Model model) {
		LocalDateTime today = LocalDateTime.now();
		Calendar cal  = Calendar.getInstance();
		int date = cal.get(Calendar.DATE);
		int dayofWeek = cal.get(Calendar.DAY_OF_WEEK)-1;
		int dayofMonth= cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		String[] korDayOfWeek={"일","월","화","수","목","금","토"};
		
		/*String formatToday = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).substring(0, 10);*/
		
		
		model.addAttribute("today", today.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
		model.addAttribute("today_date",date);
		model.addAttribute("today_dayList",korDayOfWeek);
		model.addAttribute("today_day",dayofWeek);
		model.addAttribute("Month_date",dayofMonth);
		
		
		/*
		 * Map<String,Object> map= indexMoimViewService.selectByTodayMoim(formatToday);
		 * List<MoimVO> todayMoimList = (List<MoimVO>) map.get("todayMoimList");
		 * List<String> todayMoimFounderList=(List<String>)
		 * map.get("todayMoimListFounder"); if(todayMoimList != null) {
		 * model.addAttribute("todayMoimList", todayMoimList);
		 * model.addAttribute("todayMoimFounderList", todayMoimFounderList); }
		 */
		
		/*
	 	System.out.println(today);
		System.out.println(formatToday);
		System.out.println(today.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
		System.out.println(date);
		System.out.println(korDayOfWeek);
		System.out.println(dayofWeek);
		System.out.println(dayofMonth);
		 */
		
		// 동원 추가-DB에 저장된 대분류를 중복 제거하고 이름만 전부 가져오는 서비스 호출
				List<String> LC_list = moimCreateService.selectAllLcname();
				// 동원 추가-서비스로 담은 List를 모델에 저장
				model.addAttribute("LC_list", LC_list);
		
		
		return "index";
	}
	
	
	@RequestMapping(value = "/decorators/deco.html")
	   public String deco(Model model,HttpServletRequest request, UserVO userVO) {
		  userVO = (UserVO) request.getSession().getAttribute("userVO");
		  model.addAttribute("userVO",userVO);
	      return "decorators/deco";
	}
	
	
	@RequestMapping(value = "/moimOfDate" ,method = RequestMethod.GET)
	@ResponseBody
	public Map<String,List<?>> moimOfDate(@RequestParam String anotherDay, Model model) {
		System.out.println(anotherDay);
		Map<String,List<?>> map = indexMoimViewService.selectByTodayMoim(anotherDay);
		
		return map;
	}
	
}
