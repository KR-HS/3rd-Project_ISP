package kr.human.ISP.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.human.ISP.vo.CategoryVO;

@Mapper
public interface CategoryDAO {
	public List<CategoryVO> selectByLcname(CategoryVO categoryVO) throws SQLException;
	
	// public CategoryVO selectByCategory(HashMap<String, String> map) throws SQLException;
	
	public CategoryVO selectByIdx(int category_idx) throws SQLException;
	public int selectCategoryIdxByScName(String sc_name) throws SQLException;
	
	// 동원 수정
	public CategoryVO selectByCategory(CategoryVO categoryVO) throws SQLException;
	//동원추가
	public List<String> selectAllLcname() throws SQLException;
	//동원추가 
	public List<String> selectByScname(String lc_name) throws SQLException;
	
	
}
