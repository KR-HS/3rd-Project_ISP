package kr.human.ISP.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.human.ISP.vo.UserVO;
@Mapper
public interface UserDAO {
	
	public int selectCount() throws SQLException;
	public UserVO selectByIdx(int user_idx) throws SQLException;
	public UserVO selectByPhone(String user_phone) throws SQLException;
	public UserVO selectByUserid(int user_id) throws SQLException;
	public List<UserVO> selectList(HashMap<String, Integer> map) throws SQLException;
	public void insert(UserVO userVO) throws SQLException;
	public void update(UserVO userVO) throws SQLException;
	public void updatePassword(UserVO userVO) throws SQLException;
	public void updateUse(int user_idx) throws SQLException;
	public void supervisorUpdate(UserVO userVO) throws SQLException;
	public int selectUseridCount(String user_id) throws SQLException;
	public void delete(int user_idx) throws SQLException;
}
