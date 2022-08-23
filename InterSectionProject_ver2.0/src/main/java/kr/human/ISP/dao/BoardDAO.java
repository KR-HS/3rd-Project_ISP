package kr.human.ISP.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.human.ISP.vo.BoardVO;

@Mapper
public interface BoardDAO {
	public int selectCount() throws SQLException;
	public int selectCountByCategory(String board_category) throws SQLException;
	public BoardVO selectByIdx(int board_idx) throws SQLException;
	public void insert(BoardVO boardVO) throws SQLException;
	public void update(BoardVO boardVO) throws SQLException;
	public void delete(int board_idx) throws SQLException;
	public List<BoardVO> selectList(HashMap<String,Integer> map) throws SQLException;
	public List<BoardVO> selectListByCategory(HashMap<String,String> map) throws SQLException;
	public List<BoardVO> selectMailList() throws SQLException;
	public List<BoardVO> selectMailPageList(HashMap<String,Integer> map) throws SQLException;
	public int selectCountMail() throws SQLException;
}
