package kr.human.ISP.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.human.ISP.vo.LikeMoimVO;

@Mapper
public interface LikeMoimDAO {

	public void insert(LikeMoimVO likeMoimVO) throws SQLException;
	public List<LikeMoimVO> selectByUser(int user_idx) throws SQLException;
	public int selectCountByUser(int user_idx) throws SQLException;
	public int selectCountByMoim(int moim_idx) throws SQLException;
	public void delete(int user_idx) throws SQLException;	// 0819 새롬 수정
	public List<LikeMoimVO> selectList(HashMap<String, Integer> map) throws SQLException;

}
