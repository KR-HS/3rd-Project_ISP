package kr.human.ISP.dao;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import kr.human.ISP.vo.UserCategoryVO;
@Mapper
public interface UserCategoryDAO {
	public void insert(UserCategoryVO userCategoryVO) throws SQLException;
	public UserCategoryVO selectByCategory(int user_idx) throws SQLException;
	public int selectCountByCategory(int user_idx) throws SQLException;
	public int delete(int user_category_idx) throws SQLException;
}
