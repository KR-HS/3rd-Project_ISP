package kr.human.ISP.MoimCreateService;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.human.ISP.dao.LikeMoimDAO;
import kr.human.ISP.vo.LikeMoimVO;

@Service("likeMoimService")
public class LikeMoimServiceImpl implements LikeMoimService {

	@Autowired
	LikeMoimDAO likeMoimDAO;
	
	@Override
	public void insert(LikeMoimVO likeMoimVO) {
		try {
			//user_idx 비교해서 같은 값이 없으면 저장하기 
			likeMoimDAO.insert(likeMoimVO);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void delete(int user_idx) {
		try {
			//user_idx가 있다면 삭제하기
			likeMoimDAO.delete(user_idx);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
