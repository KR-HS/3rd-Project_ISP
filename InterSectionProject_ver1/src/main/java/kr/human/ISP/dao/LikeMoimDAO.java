package kr.human.ISP.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.human.ISP.vo.LikeMoimVO;

@Mapper
public interface LikeMoimDAO {

	public void insert(LikeMoimVO likeMoimVO) throws SQLException;
	public List<Integer> selectByUser(int user_idx) throws SQLException;
	public int selectCountByUser(int user_idx) throws SQLException;
	public int selectCountByMoim(int moim_idx) throws SQLException;
	public void delete(int user_idx) throws SQLException;

}
