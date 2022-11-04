package kr.human.ISP.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.human.ISP.vo.MoimVO;

@Mapper
public interface MoimDAO {
	
	public int selectCount() throws SQLException;
	public List<MoimVO> selectByTime(String moim_time) throws SQLException;
	public MoimVO selectByIdx(int moim_idx) throws SQLException;
	public void insert(MoimVO moimVO) throws SQLException;
	public void update(MoimVO moimVO) throws SQLException;
	public void delete(int moim_idx) throws SQLException;
	public void supervisorDelete(int moim_idx) throws SQLException;
	public List<MoimVO> selectList(HashMap<String, Integer> map) throws SQLException;
	public int selectCountByUser(int user_idx) throws SQLException;
	public List<MoimVO> selectByUser(int user_idx) throws SQLException;
	public List<MoimVO> selectFiveMoim() throws SQLException;
	
	// 동원 추가 - 모임 생성시 생성하자마자 생긴 idx 가져오는 용도
	public MoimVO selectByNewOneMoim() throws SQLException;
	
	
	// 현수꺼 추가 시작 08/19
	public List<MoimVO> selectCreatePage(HashMap<String, Integer> map) throws SQLException;
	public List<MoimVO> selectCreateList(int user_idx) throws SQLException;
	public int selectCreateListCount(int user_idx) throws SQLException;
	
	public List<MoimVO> selectApplyPage(HashMap<String, Integer> map) throws SQLException;
	public List<MoimVO> selectApplyList(int user_idx) throws SQLException;
	public int selectApplyListCount(int user_idx) throws SQLException;
	
	public List<MoimVO> selectJoinPage(HashMap<String, Integer> map) throws SQLException;
	public List<MoimVO> selectJoinList(int user_idx) throws SQLException;
	public int selectJoinListCount(int user_idx) throws SQLException;
	
	public List<MoimVO> selectLikePage(HashMap<String, Integer> map) throws SQLException;
	public List<MoimVO> selectLikeList(int user_idx) throws SQLException;
	public int selectLikeListCount(int user_idx) throws SQLException;
	// 현수꺼 추가 종료 08/19

	
	// 동원 추가0825
	public List<MoimVO> selectByTodayMoim(String moim_time) throws SQLException;

	// 동원 추가 주말
	public List<MoimVO> selectByDayPlusLcName(HashMap<String, String> map) throws SQLException;
	
	// 동원 추가 주말
	public List<MoimVO> selectByDayPlusScName(HashMap<String, String> map) throws SQLException;
	
	// 동원 추가 0829
	public List<MoimVO> selectByDayPlusArea(HashMap<String, String> map) throws SQLException;
	
	// 동원 추가 0829
	public List<MoimVO> selectByDayPlusAreaPlusLc(HashMap<String, String> map) throws SQLException;
	
	// 동원 추가 0829
	public List<MoimVO> selectByDayPlusAreaPlusSc(HashMap<String, String> map) throws SQLException;
	
	// 현수 추가 0902
	public List<MoimVO> searchMoimBySubject(String searchSubject) throws SQLException;
	public List<MoimVO> searchMoimByContent(String searchContent) throws SQLException;
	
	// 상현 추가 0829
	public MoimVO selectById(String moim_name) throws SQLException;
}
