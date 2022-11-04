package kr.human.ISP.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContextUtils;

import kr.human.ISP.MoimCreateService.MoimCreateService;
import kr.human.ISP.service.IndexMoimViewService;
import kr.human.ISP.service.MoimSearchService;
import kr.human.ISP.service.UserService;
import kr.human.ISP.vo.MoimVO;
import kr.human.ISP.vo.UserVO;


@Controller
public class MainController {

	
	@Autowired
	private MoimCreateService moimCreateService;
	
	@Autowired
	private IndexMoimViewService indexMoimViewService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MoimSearchService moimSearchService;
	
	
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
	public Map<String,List<?>> moimOfDate(@RequestParam String anotherDay) {
		System.out.println(anotherDay);
		Map<String,List<?>> map = indexMoimViewService.selectByTodayMoim(anotherDay);
				
		return map;
	}
	
//	@PostMapping(value = "/moimOfDate")
//	@ResponseBody
//	public Map<String,Object> moimOfDate(@RequestParam String anotherDay) {
//		System.out.println(anotherDay);
//		Map<String,Object> map = indexMoimViewService.selectByTodayMoim(anotherDay);
//		
//		
//		return map;
//	}
	
	
	@RequestMapping(value = "/moimOfDate/lcName", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,List<?>> moimOfDateOfLcName(@RequestParam String moimDate, @RequestParam String lc_name){
		Map<String,List<?>> map = indexMoimViewService.selectByDayPlusLcName(moimDate, lc_name);
		
		
		return map;
	}

//	@RequestMapping(value = "/moimOfDate/lcName", method = RequestMethod.GET)
//	@ResponseBody
//	public Map<String,Object> moimOfDateOfLcName(@RequestParam String moimDate, @RequestParam String lc_name){
//		Map<String,Object> map = indexMoimViewService.selectByDayPlusLcName(moimDate, lc_name);
//		
//		
//		return map;
//	}
	
//	
	@RequestMapping(value = "/moimOfDate/scName", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,List<?>> moimOfDateOfScName(@RequestParam String moimDate, @RequestParam String sc_name){
		Map<String,List<?>> map = indexMoimViewService.selectByDayPlusScName(moimDate, sc_name);
		
		
		return map;
	}
	
//	@RequestMapping(value = "/moimOfDate/scName", method = RequestMethod.GET)
//	@ResponseBody
//	public Map<String,Object> moimOfDateOfScName(@RequestParam String moimDate, @RequestParam String sc_name){
//		Map<String,Object> map = indexMoimViewService.selectByDayPlusScName(moimDate, sc_name);
//		
//		
//		return map;
//	}
	
	
	
	@RequestMapping(value = "/moimOfDate/ffff", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,List<?>> moimOf(@RequestParam String moimDate, @RequestParam(required = false) String lc_name, @RequestParam(required = false) String sc_name, @RequestParam(required = false) String area_name){
		Map<String,List<?>> map = indexMoimViewService.dynamicSelectByAreaLcSc(moimDate, area_name, lc_name, sc_name);
		
		
		return map;
	}
	
	
//	@PostMapping(value = "/moimOfDate/ffff")
//	@ResponseBody
//	public Map<String,Object> moimOf(@RequestParam Map<String,String> map,HttpServletRequest request){
//		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
//		if(flashMap!=null) {
//			map = (Map<String, String>) flashMap.get("map");
//		}
//		System.out.println(map.get("moimDate"));
//		Map<String,Object> resultmap = new HashMap<String, Object>();
//		resultmap.put("list", indexMoimViewService.dynamicSelectByAreaLcSc(map));
//		
//		
//		return resultmap;
//	}
	
	
	   @RequestMapping(value="/popup/chatBot")
	   public String chatBot() {
	      return "/popup/chatBot";
	   }
	   
	   
	   // 0902 현수 추가
	   @GetMapping(value = "searchMoim")
	   public String searchMoimGet(Model model) {
	      String searchCriteria="Subject";
	      String searchContent="";
	      Map<String,List<?>> map=null;
	      map= moimSearchService.seachMoim(searchCriteria, searchContent);
	      model.addAttribute("map",map);
	      return "searchMoim";
	   }
	   
	   @PostMapping(value = "/searchMoim")
	   public String searchMoimPost(Model model,HttpServletRequest request) {
	      String searchCriteria=request.getParameter("searchCriteria");
	      String searchContent=request.getParameter("content");
	      Map<String,List<?>> map=null;
	      map= moimSearchService.seachMoim(searchCriteria, searchContent);
	      model.addAttribute("map",map);
	      return "searchMoim";
	   }
	   
	
}
