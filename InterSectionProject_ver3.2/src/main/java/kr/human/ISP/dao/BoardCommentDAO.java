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
	public BoardCommentVO selectCommentBoard(int board_idx, int user_idx) throws SQLException; //  user_idx,board_idx로 댓글 가져오기
	public int selectCommentCount(int board_idx, int user_idx) throws SQLException; // 댓글 있는지 확인
}
