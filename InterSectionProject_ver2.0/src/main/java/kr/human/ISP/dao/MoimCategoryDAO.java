package kr.human.ISP.dao;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import kr.human.ISP.vo.MoimCategoryVO;

@Mapper
public interface MoimCategoryDAO {

	public void insert(MoimCategoryVO moimCategoryVO) throws SQLException;
	public int selectCountByCategory(int category_idx) throws SQLException;
	public void update(MoimCategoryVO moimCategoryVO) throws SQLException;
}
