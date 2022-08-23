package kr.human.ISP.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.human.ISP.dao.BoardDAO;
import kr.human.ISP.dao.MailSendDAO;
import kr.human.ISP.dao.MoimDAO;
import kr.human.ISP.dao.UserDAO;
import kr.human.ISP.vo.BoardVO;
import kr.human.ISP.vo.CommVO;
import kr.human.ISP.vo.MoimVO;
import kr.human.ISP.vo.PagingVO;
import kr.human.ISP.vo.UserVO;

@Service("supervisorService")
@Transactional
public class SupervisorServiceImpl implements SupervisorService{

	@Autowired
	UserDAO userDAO;
	
	@Autowired
	MoimDAO moimDAO;
	
	@Autowired
	BoardDAO boardDAO;
	
	@Autowired
	MailSendDAO mailSendDAO;

	@Override
	public List<UserVO> selectFiveUser() {
		List<UserVO> list = null;
		try {
			list = userDAO.selectFiveUser();	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<MoimVO> selectFiveMoim() {
		List<MoimVO> list = null;
		try {
			list = moimDAO.selectFiveMoim();	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int selectTotalCountUser() {
		int count = 0;
		try {
			count = userDAO.selectCount();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int selectCountDeleteUser() {
		int count = 0;
		try {
			count = userDAO.selectCountDeleteUser();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public List<BoardVO> selectMailList() {
		List<BoardVO> list = null;
		try {
			list = boardDAO.selectMailList();	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public PagingVO<UserVO> selectUserList(CommVO commVO) {
		PagingVO<UserVO> pagingVO = null;
		try {
			int totalCount = userDAO.selectCount();
			pagingVO = new PagingVO<>(totalCount, commVO.getP(), commVO.getS(), commVO.getB());
			HashMap<String, Integer> hashMap = new HashMap<>();
			hashMap.put("startNo", pagingVO.getStartNo());
			hashMap.put("pageSize", pagingVO.getPageSize());
			List<UserVO> list = userDAO.selectList(hashMap);
			pagingVO.setList(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pagingVO;
	}

	@Override
	public PagingVO<UserVO> selectDeleteUserList(CommVO commVO) {
		PagingVO<UserVO> pagingVO = null;
		try {
			int totalCount = userDAO.selectCountDeleteUser();
			pagingVO = new PagingVO<>(totalCount, commVO.getP(), commVO.getS(), commVO.getB());
			HashMap<String, Integer> hashMap = new HashMap<>();
			hashMap.put("startNo", pagingVO.getStartNo());
			hashMap.put("pageSize", pagingVO.getPageSize());
			List<UserVO> list = userDAO.selectDeleteList(hashMap);
			pagingVO.setList(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pagingVO;
	}

	@Override
	public void supervisorUpdate(List<UserVO> list) {
		try {
			if(list != null) {
				for(UserVO userVO : list) {
					userDAO.supervisorUpdate(userVO);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public int selectTotalCountMoim() {
		int count = 0;
		try {
			count = moimDAO.selectCount();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	

	@Override
	public PagingVO<MoimVO> selectMoimList(CommVO commVO) {
		PagingVO<MoimVO> pagingVO = null;
		try {
			int totalCount = moimDAO.selectCount();
			pagingVO = new PagingVO<>(totalCount, commVO.getP(), commVO.getS(), commVO.getB());
			HashMap<String, Integer> hashMap = new HashMap<>();
			hashMap.put("startNo", pagingVO.getStartNo());
			hashMap.put("endNo", pagingVO.getEndNo());
			List<MoimVO> list = moimDAO.selectList(hashMap);
			pagingVO.setList(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pagingVO;
	}

	@Override
	public PagingVO<BoardVO> selectMailList(CommVO commVO) {
		PagingVO<BoardVO> pagingVO = null;
		try {
			int totalCount = boardDAO.selectCountMail();
			pagingVO = new PagingVO<>(totalCount, commVO.getP(), commVO.getS(), commVO.getB());
			HashMap<String, Integer> hashMap = new HashMap<>();
			hashMap.put("startNo", pagingVO.getStartNo());
			hashMap.put("pageSize", pagingVO.getPageSize());
			List<BoardVO> list = boardDAO.selectMailPageList(hashMap);
			pagingVO.setList(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pagingVO;
	}

	@Override
	public PagingVO<UserVO> selectMailSendList(CommVO commVO) {
		PagingVO<UserVO> pagingVO = null;
		try {
			int totalCount = userDAO.selectCountDeleteUser();
			pagingVO = new PagingVO<>(totalCount, commVO.getP(), commVO.getS(), commVO.getB());
			HashMap<String, Integer> hashMap = new HashMap<>();
			hashMap.put("startNo", pagingVO.getStartNo());
			hashMap.put("pageSize", pagingVO.getPageSize());
			List<UserVO> list = mailSendDAO.selectMailSendList(hashMap);
			pagingVO.setList(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pagingVO;
	}
	
	
	
}
