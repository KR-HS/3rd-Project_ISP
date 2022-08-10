package kr.human.ISP.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.human.ISP.vo.MailSendVO;

@Mapper
public interface MailSendDAO {

	public void insert(HashMap<String, Integer> map) throws SQLException;
	public List<MailSendVO> selectByBoard(int board_idx) throws SQLException;
	public void selectCountByBoard(int board_idx) throws SQLException;
}
