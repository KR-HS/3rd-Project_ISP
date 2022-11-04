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
	

}
