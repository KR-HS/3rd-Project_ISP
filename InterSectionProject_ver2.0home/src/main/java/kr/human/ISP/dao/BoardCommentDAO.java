package kr.human.ISP.dao;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import kr.human.ISP.vo.BoardCommentVO;

@Mapper
public interface BoardCommentDAO {

	public int selectCount(int board_idx) throws SQLException;
	public BoardCommentVO selectByIdx(int comment_idx) throws SQLException;
	public void insert(BoardCommentVO boardCommentVO) throws SQLException;
	public void update(BoardCommentVO boardCommentVO) throws SQLException;
	public void delete(int comment_idx) throws SQLException;
}
