package kr.human.ISP.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.human.ISP.vo.SignUpVO;
@Mapper
public interface SignUpDAO {
	public List<SignUpVO> selectByMoim(HashMap<String, Integer> map) throws SQLException;
	public List<SignUpVO> selectByUser(HashMap<String, Integer> map) throws SQLException;
	public List<SignUpVO> selectByApply(HashMap<String, Integer> map) throws SQLException;
	public int selectCountByUser(HashMap<String, Integer> map) throws SQLException;
	public int selectCountByMoim(int moim_idx) throws SQLException;
	public int selectCountByMoimApply(HashMap<String, Integer> map) throws SQLException;
	public void insert(SignUpVO signUpVO) throws SQLException;
	public void udpateApply(int signUp_idx) throws SQLException;
	public void udpateRefuse(int signUp_idx) throws SQLException;
}
