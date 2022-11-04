package kr.human.ISP.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.human.ISP.vo.UserVO;

@Mapper
public interface UserDAO {

	public int selectCount() throws SQLException;

	public UserVO selectByIdx(int user_idx) throws SQLException;

	public UserVO selectByUserid(String user_id) throws SQLException;

	public List<UserVO> selectList(HashMap<String, Integer> map) throws SQLException;

	public void insert(UserVO userVO) throws SQLException;

	public void update(UserVO userVO) throws SQLException;

	public void updatePassword(HashMap<String, String> map) throws SQLException;

	public void updateUse(int user_idx) throws SQLException;

	public void supervisorUpdate(UserVO userVO) throws SQLException;

	public int selectUseridCount(String user_id) throws SQLException;

	public void delete(int user_idx) throws SQLException;

	public void updateIsDelete(int user_idx) throws SQLException;

	public List<UserVO> selectSignUpUser(HashMap<String, Integer> map) throws SQLException;

	public List<UserVO> selectFiveUser() throws SQLException;

	public int selectCountDeleteUser() throws SQLException;;

	public List<UserVO> selectDeleteList(HashMap<String, Integer> map) throws SQLException;

	// 로그인 부분
	public int selectCurrentUserIdx() throws SQLException; // 유저 idx 찾기

	public String selectByPhone(String user_name, String user_phone) throws SQLException; // 유저아이디찾기 - 이름,휴대폰번호로

	public int userInfoCheck(String user_id, String user_name) throws SQLException; // 유저정보확인(비번찾기) - 이메일,이름으로찾기

	public void updateTempPassword(String user_id, String user_name, String user_pwd) throws SQLException; // 유저정보확인(비번찾기)
																											// -
																											// 이메일,이름으로찾기
	
	
	// 새롬 0819금 추가 시작
	//승인된 유저정보얻기
		public List<UserVO> selectSignUpList(int moim_idx)throws SQLException;
	// 새롬 0819금 추가 종료 
	
		// 현수 0826
		// user_idx로 이름 가져오기
			public List<String> getUserNameList(String moim_time)throws SQLException;
	
			

			
	// 0901 대형추가 개인정보수정
	public void updateUser(HashMap<String, Object> map) throws SQLException;
			
	
}
