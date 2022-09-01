package kr.human.ISP.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.human.ISP.vo.ReviewVO;


@Mapper
public interface MypageDAO {

	public int createCount(int user_idx)throws SQLException;
	public int signupCount(int user_idx)throws SQLException;
	public int reviewCount(int user_idx)throws SQLException;
	public List<String> signUpList(int user_idx)throws SQLException;
	public List<String> categoryList(int user_idx)throws SQLException;
	public List<ReviewVO> reviewList(int user_idx)throws SQLException;
	public List<ReviewVO> reviewPagingList(Map<String,Integer> map)throws SQLException;

}
