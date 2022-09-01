package kr.human.ISP.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.human.ISP.vo.CategoryVO;
import kr.human.ISP.vo.MoimCategoryVO;

@Mapper
public interface MoimCategoryDAO {

	public void insert(MoimCategoryVO moimCategoryVO) throws SQLException;
	public int selectCountByCategory(int category_idx) throws SQLException;
	public void update(MoimCategoryVO moimCategoryVO) throws SQLException;
	
	public List<CategoryVO> selectByMoim(String moim_date) throws SQLException;
	
	// 0829 새롬 추가
	public CategoryVO selectCategoryByMoimIdx(int moim_idx) throws SQLException;
	
}
