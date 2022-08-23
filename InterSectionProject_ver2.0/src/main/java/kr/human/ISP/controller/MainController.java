package kr.human.ISP.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.human.ISP.MoimCreateService.MoimCreateService;
import kr.human.ISP.service.UserService;

@Controller
public class MainController {

	
	@Autowired
	private MoimCreateService moimCreateService;
	
	
	
	@RequestMapping(value = { "/", "/index" })
	public String index(Model model) {
		LocalDateTime today = LocalDateTime.now();
		Calendar cal  = Calendar.getInstance();
		int date = cal.get(Calendar.DATE);
		int dayofWeek = cal.get(Calendar.DAY_OF_WEEK)-1;
		int dayofMonth= cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		String[] korDayOfWeek={"일","월","화","수","목","금","토"};
		
		model.addAttribute("today", today.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
		model.addAttribute("today_date",date);
		model.addAttribute("today_dayList",korDayOfWeek);
		model.addAttribute("today_day",dayofWeek);
		model.addAttribute("Month_date",dayofMonth);
		
		// 동원 추가-DB에 저장된 대분류를 중복 제거하고 이름만 전부 가져오는 서비스 호출
				List<String> LC_list = moimCreateService.selectAllLcname();
				// 동원 추가-서비스로 담은 List를 모델에 저장
				model.addAttribute("LC_list", LC_list);
		
		
		return "index";
	}
	
	
	@RequestMapping(value = "/decorators/deco.html")
	   public String deco() {
	      return "decorators/deco";
	}
	
	
}
