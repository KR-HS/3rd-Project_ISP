package kr.human.ISP.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.human.ISP.vo.UserCategoryVO;
@Mapper
public interface UserCategoryDAO {
	public void insert(HashMap<String, Integer> map) throws SQLException;
	public List<UserCategoryVO> selectByCategory(int user_idx) throws SQLException;
	public int selectCountByCategory(int user_idx) throws SQLException;
	public void delete(int user_category_idx) throws SQLException;
	
}
