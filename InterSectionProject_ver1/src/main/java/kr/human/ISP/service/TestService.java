package kr.human.ISP.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.human.ISP.dao.TestDAO;

@Service
public class TestService {

	@Autowired
	private TestDAO testDAO;
	
	public String selectToday() {
		return testDAO.selectToday();
	}
	
}
