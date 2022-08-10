package kr.human.ISP.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.human.ISP.vo.UpFileVO;
@Mapper
public interface UpFileDAO {
	public int selectByIdx(int file_idx) throws SQLException;
	public int selectCountByBoard(int board_idx) throws SQLException;
	public int selectCountByUser(int user_idx) throws SQLException;
	public int selectCountByMoim(int moim_idx) throws SQLException;
	public List<UpFileVO> selectListByBoard(int board_idx) throws SQLException; 
	public List<UpFileVO> selectListByUser(int user_idx) throws SQLException; 
	public List<UpFileVO> selectListByMoim(int moim_idx) throws SQLException; 
	public void insert(UpFileVO upFileVO) throws SQLException;
	public void update(UpFileVO upFileVO) throws SQLException;
	public void delete(int file_idx) throws SQLException;
	
	public UpFileVO selectProfileImg(int user_idx) throws SQLException;
}
