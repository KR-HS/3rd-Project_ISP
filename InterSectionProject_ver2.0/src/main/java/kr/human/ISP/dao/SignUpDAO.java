package kr.human.ISP.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.human.ISP.vo.SignUpVO;
import kr.human.ISP.vo.UserVO;
@Mapper
public interface SignUpDAO {
	public List<UserVO> selectByMoim(int moim_idx) throws SQLException;
	public List<SignUpVO> selectByMoimApply(int moim_idx) throws SQLException;
	public List<SignUpVO> selectByUser(int user_idx) throws SQLException;
	public List<SignUpVO> selectByUserApply(int user_idx) throws SQLException;
	public int selectCountByUser(int user_idx) throws SQLException;
	public int selectCountByMoim(int moim_idx) throws SQLException;
	public int selectCountByMoimApply(int moim_idx) throws SQLException;
	public int selectCountByUserApply(int user_idx) throws SQLException;
	public void insert(SignUpVO signUpVO) throws SQLException;	
	public void updateApply(int signUp_idx) throws SQLException;
	public void updateRefuse(int signUp_idx) throws SQLException;
	public int selectSignUpIdx(HashMap<String, Integer> map) throws SQLException;
	public List<SignUpVO> selectByMoimIdx(int moim_idx) throws SQLException;
	
	
	//참여삭제하기   0819 새롬 수정 구현은 안함
	public void delete(int signUp_idx) throws SQLException;
}
