package kr.human.ISP.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.human.ISP.vo.CategoryVO;

@Mapper
public interface CategoryDAO {
	public List<CategoryVO> selectByLcname(CategoryVO categoryVO) throws SQLException;
	public List<CategoryVO> selectByCategory(CategoryVO categoryVO) throws SQLException;
	
}
