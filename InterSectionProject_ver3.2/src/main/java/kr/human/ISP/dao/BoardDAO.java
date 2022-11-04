package kr.human.ISP.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.human.ISP.vo.BoardVO;

@Mapper
public interface BoardDAO {
	public int selectCount() throws SQLException; // 전체 개수 얻기
	public int selectCountByCategory(String board_category) throws SQLException;
	public BoardVO selectByIdx(int board_idx) throws SQLException; // 1개얻기
	public void insert(BoardVO boardVO) throws SQLException; // 저장
	public void noticeupdate(BoardVO boardVO) throws SQLException; //수정
	public void delete(int board_idx) throws SQLException; //삭제
	public List<BoardVO> selectList(HashMap<String,Object> map) throws SQLException;
	public List<BoardVO> selectListByCategory(HashMap<String,Object> map) throws SQLException;
	public List<BoardVO> selectMailList() throws SQLException;
	public List<BoardVO> selectMailPageList(HashMap<String,Integer> map) throws SQLException;
	public int selectCountMail() throws SQLException;

	
	
	//대형 추가
	public int selectDeleteByCategory(String board_isDelete, String board_category) throws SQLException;
	public void boardNoticeInsert(int user_idx, String board_subject, String board_content, String board_category) throws SQLException; //대형 글쓰기 저장
	public int selectByUserIdxCount(int user_idx) throws SQLException; // 1개얻기
	public List<BoardVO> selectListByUserIdx(HashMap<String,Object> map) throws SQLException;
	public int selectCurrentBoardIdx() throws SQLException;
}
