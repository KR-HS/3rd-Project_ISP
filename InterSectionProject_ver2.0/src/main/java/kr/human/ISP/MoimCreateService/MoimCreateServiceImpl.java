package kr.human.ISP.MoimCreateService;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.human.ISP.dao.CategoryDAO;
import kr.human.ISP.dao.MoimCategoryDAO;
import kr.human.ISP.dao.MoimDAO;
import kr.human.ISP.dao.UpFileDAO;
import kr.human.ISP.vo.CategoryVO;
import kr.human.ISP.vo.MoimCategoryVO;
import kr.human.ISP.vo.MoimVO;
import kr.human.ISP.vo.UpFileVO;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class MoimCreateServiceImpl implements MoimCreateService {
	
	@Autowired
	private CategoryDAO categoryDAO;
	
	@Autowired
	private UpFileDAO upFileDAO;
	
	@Autowired
	private MoimDAO moimDAO;
	
	@Autowired
	private MoimCategoryDAO moimCategoryDAO;
	
	
	
	public List<String> selectAllLcname(){
		List<String> list = null;
		try {
			list = categoryDAO.selectAllLcname();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	public List<String> selectByScname(String lc_name){
		List<String> list = null;
		try {
			list = categoryDAO.selectByScname(lc_name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	public Boolean uploadProfileImg(UpFileVO upFileVO) {
		boolean result=false;
		try {
			upFileDAO.insert(upFileVO);
			result=true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public Boolean updateProfileImg(UpFileVO upFileVO) {
		boolean result=false;
		try {
			upFileDAO.update(upFileVO);
			result=true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	public UpFileVO getProfile(int user_idx) {
		UpFileVO upfile = new UpFileVO();
		try {
			upfile=upFileDAO.selectProfileImg(user_idx);
			System.out.println("upfile : "+upfile);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return upfile;
	}



	@Override
	public void insert(MoimVO moimVO) {
		log.info("MoimCreateService insert 호출 받은 VO : {}", moimVO);
		
		try {
			moimDAO.insert(moimVO);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}



	@Override
	public void moimCategoryInsert(int moim_idx, String lc_name, String sc_name) {
		int idx = 0;	
		 CategoryVO categoryVO = new CategoryVO(0, lc_name, sc_name);
		 
		try {
			categoryVO = categoryDAO.selectByCategory(categoryVO);
			idx = categoryVO.getCategory_idx();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		 MoimCategoryVO moimCategoryVO = new MoimCategoryVO(0, moim_idx, idx);
		try {
			
			moimCategoryDAO.insert(moimCategoryVO);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}



	@Override
	public MoimVO selectByNewOneMoim() {
		MoimVO moimVO = null;
		try {
			moimVO = moimDAO.selectByNewOneMoim();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return moimVO;
	}

	
	
	
	
	
	
}
