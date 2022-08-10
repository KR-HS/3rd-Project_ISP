package kr.human.ISP.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.human.ISP.dao.MoimDAO;
import kr.human.ISP.dao.UserDAO;
import kr.human.ISP.vo.MoimVO;
import kr.human.ISP.vo.UserVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service("userService")
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDAO userDAO;

	@Override
	public UserVO selectByIdx(int user_idx) {
		UserVO userVO=null;
		try {
			userVO = userDAO.selectByIdx(user_idx);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userVO;
	}
	
	
	
}
