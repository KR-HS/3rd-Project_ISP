package kr.human.ISP.MoimCreateService;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.human.ISP.dao.SignUpDAO;
import kr.human.ISP.vo.SignUpVO;


@Service("signUpService")
public class SingUpServiceImpl implements SignUpService {

	@Autowired
	private SignUpDAO signUpdao;
	
	@Override
	public void insert(SignUpVO signUpVO) {
		try {
			//유저정보 중복 안되게 할까말까.. 
			
			signUpdao.insert(signUpVO);	
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}


	@Override
	public void delete(int signUp_idx) {
		try {
			signUpdao.delete(signUp_idx);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}


	@Override
	public List<SignUpVO> selectByMoimIdx(int moim_idx) {
		List<SignUpVO> list=null;
		try {
			list=signUpdao.selectByMoimIdx(moim_idx);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}	
}
