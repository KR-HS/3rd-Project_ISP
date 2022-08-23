package kr.human.ISP.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.human.ISP.service.UserService;
import kr.human.ISP.vo.UserVO;

@Controller
public class LoginController {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	UserService userService;

	@GetMapping("login/login")
	public String loginPage() {
		return "login/login";
	}

	// 로그인 실패시.
	@RequestMapping(value = "/login/loginFail")
	public void loginFali(HttpServletRequest request) {
	}

	// 회원가입부분
	@GetMapping("login/signUp")
	public String signUpForm() {
		return "login/signUp";
	}

	@RequestMapping(value = "login/signUp")
	public String signUp(UserVO userVO) throws MessagingException, UnsupportedEncodingException {
		userService.insertUser(userVO);
		MailHandler mailHandler = null;
		mailHandler = new MailHandler(javaMailSender);
		mailHandler.setFrom("ckdlsktkdgus@naver.com", "ISP사이트 관리자");
		mailHandler.setTo(userVO.getUser_id()); // 받는사람
		mailHandler.setSubject("안녕하세요." + userVO.getUser_name() + "님 ISP에 가입하신 것을 환영합니다."); // 제목
		mailHandler.setText("안녕하세요." + userVO.getUser_name() + "님의 인증번호 안내 관련 이메일입니다. 인증번호는 [" + userVO.getUser_UUID()
				+ "]입니다.<br><form action='http://localhost:8080/login/confirm' method='post'><input type='hidden' name='user_id' value='"
				+ userVO.getUser_id() + "'><input type='submit' value='인증하러가기'></form>"); // 내용
		mailHandler.send(); // 메일 보내기
		return "login/signUp_Ct";
	}

	// 회원가입시 카테고리 선택
	@GetMapping("login/signUp_Ct")
	public String signUpForm2() {
		return "login/signUp_Ct";
	}

	@RequestMapping(value = "login/signUp_Ct")
	public String signUp_Ct(Model model) {
		return "login/signUp_Ct";
	}

	// 카테고리 선택된값들 전달받기
	@RequestMapping(value = "login/sendCt")
	@ResponseBody
	public List<String> check(HttpServletRequest request, Model model) {
		String[] selectItem = request.getParameterValues("selectItem");
		int size = selectItem.length;

		List<String> data = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			System.out.println("js에서 받은 msg: " + i + ": " + selectItem[i]);
			data.add(selectItem[i]);
		}
		model.addAttribute("data", data);
		System.out.println("-----------------------");

		return data;
	}

	// 회원가입 여부확인
	@RequestMapping(value = "login/emailCheck", method = RequestMethod.POST)
	@ResponseBody
	public boolean checkMail(HttpServletRequest request, Model model) {

		String eamil = request.getParameter("user_id");
		System.out.println(eamil);
		boolean flag;
		flag = userService.idCheck(eamil);
		return flag;
	}

	// 아이디 찾기
	@RequestMapping(value = "login/idCheck", method = RequestMethod.POST)
	@ResponseBody
	public String checkId(HttpServletRequest request, Model model) {
		String idOk = "";
		String eamil = request.getParameter("mail");
		String phone = request.getParameter("phone");
		idOk = userService.searchUserid(eamil, phone);
		if (idOk == null)
			return "fail";
		return idOk;
	}

	@RequestMapping(value = "login/infoFind")
	public String infoFind(Model model) {
		return "login/infoFind";
	}

	// 회원가입하기
	@RequestMapping(value = "login/signUp_Ok", method = RequestMethod.POST)
	public String signUp_Ok(HttpServletRequest request, Model model) {
		// 유저 idx 받아오기
		int user_idx;
		user_idx = userService.findCurrentUserIdx();
		// 소분류 카테로기 선택값 가져오기.
		String scString = request.getParameter("scSelectResult");
		List<String> list = Arrays.asList(scString.split(",")); // 소분류 스트링
		HashMap<String, Integer> map = new HashMap<>();
		map.put("user_idx", user_idx);
		for (String i : list) {
			map.put("category_idx", (userService.selectCategoryIdxByScName(i)));
			userService.insertUserCategory(map);
		} // categoryIdx가 전체 들어감
		return "login/signUp_Finish";
	}

	// 회원가입완료시 로그인화면으로 이동.
	@RequestMapping(value = "login/signUp_Finish", method = RequestMethod.POST)
	public String signUp_Finish(HttpServletRequest request, Model model) {
		return "redirect:/login/login";
	}

	@PostMapping(value = "/login/confirm")
	public String cofirmPage(HttpServletRequest request, Model model) {
		String user_id = request.getParameter("user_id");
		model.addAttribute("user_id", user_id);
		return "/login/confirm";
	}

	@PostMapping(value = "/login/confirmOk")
	@ResponseBody
	public String cofirmOkPage(HttpServletRequest request) {
		String user_id = request.getParameter("user_id");
		String user_UUID = request.getParameter("UUID");
		UserVO userVO = userService.selectById(user_id);
		if (userVO.getUser_UUID().equals(user_UUID)) {
			userService.emailConfirm(userVO.getUser_idx());
		}
		;
		return "ok";
	}
	
	/*
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "redirect:/";
	}
*/
}
