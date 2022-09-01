package kr.human.ISP.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.human.ISP.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MailController {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private  UserService userService;
	
	// 이메일 보내기
	@RequestMapping(value="psFind", method=RequestMethod.POST)
	@ResponseBody
	public int checkPs(HttpServletRequest request, Model model) throws MessagingException, UnsupportedEncodingException {
    	String mail = request.getParameter("mail");
    	String name = request.getParameter("name");
    	int checkOk = userService.userInfoCheck(mail, name);
    	String tempPassword = getTempPassword();
    	MailHandler mailHandler=null;
		if(checkOk == 1) {
    		System.out.println("정보있으면 1 ->" +checkOk);
    			userService.UserTempPassword(mail, name, tempPassword);
    			mailHandler = new MailHandler(javaMailSender);
    			mailHandler.setFrom("shoogal17@naver.com", "ISP사이트 관리자");
    			mailHandler.setTo(mail); // 받는사람
    			mailHandler.setSubject("안녕하세요.ISP에서 임시비밀번호를 보내드립니다."); // 제목
    			mailHandler.setText("안녕하세요."+name+"님의 임시 비밀번호 안내 관련 이메일입니다. 임시 비밀번호는 ["+tempPassword+"]입니다.<br><a href='http://localhost:8080/login'>로그인하러가기</a>"); // 내용
    			mailHandler.send(); // 메일 보내기
    	}else {
    		System.out.println("정보가 다르면 0 -> " + checkOk);
    	}
		return checkOk;
	}
	
	//임시비밀번호 생성
	public String getTempPassword() {
		char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
		
		String str = "";
		int idx =0;
		for(int i=0; i<10; i++) {
			idx = (int)(charSet.length * Math.random());
			str += charSet[idx];
		}
		
		return str;
	}
}
