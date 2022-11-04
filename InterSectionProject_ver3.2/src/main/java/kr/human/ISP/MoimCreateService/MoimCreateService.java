package kr.human.ISP.MoimCreateService;

import java.sql.SQLException;
import java.util.List;

import kr.human.ISP.vo.MoimVO;
import kr.human.ISP.vo.UpFileVO;


public interface MoimCreateService {
	
	
	public List<String> selectAllLcname();	
	public List<String> selectByScname(String lc_name);
	public Boolean uploadProfileImg(UpFileVO upFileVO) ;
	public Boolean updateProfileImg(UpFileVO upFileVO) ;
	public UpFileVO getProfile(int user_idx) ;
	public UpFileVO getMoimImg(String ofileName);
	// 모임 생성시 모임테이블에 저장
	public void insert(MoimVO moimVO);
	
	// 모임 생성시 모임카테고리에 저장
	public void moimCategoryInsert(int moim_idx, String lc_name, String sc_name);
	
	
	// 모임 생성시 넣자마자 생긴 idx 가져올 용도
	public MoimVO selectByNewOneMoim();
		
	
	// 모임 생성시 모임 프로필 사진 DB저장하는 서비스
	public void profileImgInsert(UpFileVO upFileVO);
	
	
	
		
}
