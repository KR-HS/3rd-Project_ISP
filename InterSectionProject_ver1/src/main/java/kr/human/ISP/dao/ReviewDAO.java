package kr.human.ISP.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.human.ISP.vo.ReviewVO;

@Mapper
public interface ReviewDAO {
	public ReviewVO selectByIdx(int review_idx) throws SQLException;
	public int selectCount() throws SQLException;
	public List<ReviewVO>  selectByUser(int user_idx) throws SQLException;
	public int selectCountByUser(int user_idx) throws SQLException;
	public List<ReviewVO> selectByMoim(int moim_idx) throws SQLException;
	public int selectCountByMoim(int moim_idx) throws SQLException;
	public void insert(ReviewVO reviewVO) throws SQLException;
	public void update(ReviewVO reviewVO) throws SQLException;
	public void updateIsDelete(int review_idx) throws SQLException;
}
