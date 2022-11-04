package kr.human.ISP.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.human.ISP.dao.LikeMoimDAO;
import kr.human.ISP.dao.MoimDAO;
import kr.human.ISP.dao.MypageDAO;
import kr.human.ISP.dao.ReviewDAO;
import kr.human.ISP.dao.SignUpDAO;
import kr.human.ISP.dao.UpFileDAO;
import kr.human.ISP.dao.UserDAO;
import kr.human.ISP.vo.CommVO;
import kr.human.ISP.vo.MoimVO;
import kr.human.ISP.vo.PagingVO;
import kr.human.ISP.vo.ReviewVO;
import kr.human.ISP.vo.UpFileVO;
import kr.human.ISP.vo.UserVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service("mypageService")
public class MypageServiceImpl implements MypageService{
	
	@Autowired
	private MoimDAO moimDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private SignUpDAO signUpDAO;

	@Autowired
	private UpFileDAO upFileDAO;
	
	@Autowired
	private ReviewDAO reviewDAO;
	
	@Autowired
	private MypageDAO mypageDAO;
	@Override
	public PagingVO<MoimVO> createMoimList(CommVO commVO,int user_idx) {
		PagingVO<MoimVO> pagingVO = null;
		//전체 개수
		int totalCount;
		try {
			totalCount = moimDAO.selectCreateListCount(user_idx);
			log.info("totalCount : "+totalCount);
			// 페이징 객체 생성 : 계산 완료
			pagingVO = new PagingVO<>(totalCount, commVO.getCurrentPage(), commVO.getPageSize(), commVO.getBlockSize());
			log.info(commVO.toString());
			log.info(pagingVO.toString());
			// 목록 가져오기
			HashMap<String, Integer> map = new HashMap<>();
			map.put("startNo", pagingVO.getStartNo());
			map.put("endNo", pagingVO.getEndNo());
			map.put("user_idx",user_idx);
			List<MoimVO> list = moimDAO.selectCreatePage(map);
			pagingVO.setList(list);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

		return pagingVO;
	}

	@Override
	public PagingVO<MoimVO> applyMoimList(CommVO commVO, int user_idx) {
		PagingVO<MoimVO> pagingVO = null;
		//전체 개수
		int totalCount;
		try {
			totalCount = moimDAO.selectApplyListCount(user_idx);
			log.info("totalCount : "+totalCount);
			// 페이징 객체 생성 : 계산 완료
			pagingVO = new PagingVO<>(totalCount, commVO.getCurrentPage(), commVO.getPageSize(), commVO.getBlockSize());
			log.info(commVO.toString());
			log.info(pagingVO.toString());
			// 목록 가져오기
			HashMap<String, Integer> map = new HashMap<>();
			map.put("startNo", pagingVO.getStartNo());
			map.put("endNo", pagingVO.getEndNo());
			map.put("user_idx",user_idx);
			List<MoimVO> list = moimDAO.selectApplyPage(map);
			pagingVO.setList(list);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

		return pagingVO;
	}

	@Override
	public PagingVO<MoimVO> joinMoimList(CommVO commVO, int user_idx) {
		PagingVO<MoimVO> pagingVO = null;
		//전체 개수
		int totalCount;
		try {
			totalCount = moimDAO.selectJoinListCount(user_idx);
			log.info("totalCount : "+totalCount);
			// 페이징 객체 생성 : 계산 완료
			pagingVO = new PagingVO<>(totalCount, commVO.getCurrentPage(), commVO.getPageSize(), commVO.getBlockSize());
			log.info(commVO.toString());
			log.info(pagingVO.toString());
			// 목록 가져오기
			HashMap<String, Integer> map = new HashMap<>();
			map.put("startNo", pagingVO.getStartNo());
			map.put("endNo", pagingVO.getEndNo());
			map.put("user_idx",user_idx);
			List<MoimVO> list = moimDAO.selectJoinPage(map);
			pagingVO.setList(list);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

		return pagingVO;
	}

	@Override
	public PagingVO<MoimVO> likeMoimList(CommVO commVO, int user_idx) {
		PagingVO<MoimVO> pagingVO = null;
		//전체 개수
		int totalCount;
		try {
			totalCount = moimDAO.selectLikeListCount(user_idx);
			log.info("totalCount : "+totalCount);
			// 페이징 객체 생성 : 계산 완료
			pagingVO = new PagingVO<>(totalCount, commVO.getCurrentPage(), commVO.getPageSize(), commVO.getBlockSize());
			log.info(commVO.toString());
			log.info(pagingVO.toString());
			// 목록 가져오기
			HashMap<String, Integer> map = new HashMap<>();
			map.put("startNo", pagingVO.getStartNo());
			map.put("endNo", pagingVO.getEndNo());
			map.put("user_idx",user_idx);
			List<MoimVO> list = moimDAO.selectLikePage(map);
			pagingVO.setList(list);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

		return pagingVO;
	}

	
	@Override
	public List<Integer> ApplyAgreeList(int user_idx,String sortMenu) {
		List<MoimVO> moimList = null;
		List<Integer> list=new ArrayList<>();
		try {
			switch(sortMenu) {
			case "개설":
				moimList=moimDAO.selectCreateList(user_idx);
				break;
			case "신청":
				moimList=moimDAO.selectApplyList(user_idx);
				break;
			case "참여중":
				moimList=moimDAO.selectJoinList(user_idx);
				break;
			case "찜한모임":
				moimList=moimDAO.selectLikeList(user_idx);
				break;
			}
			System.out.println("---------------");
			System.out.println(moimList);
			for(MoimVO vo : moimList ) {
				if(vo!=null) {
		              list.add(signUpDAO.selectCountByMoimApply(vo.getMoim_idx()));
				}
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public Boolean uploadProfileImg(UpFileVO upFileVO) {
		boolean result=false;
		try {
			upFileDAO.insert(upFileVO);
			result=true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Boolean updateProfileImg(UpFileVO upFileVO) {
		boolean result=false;
		try {
			upFileDAO.update(upFileVO);
			result=true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	@Override
	public UpFileVO getProfile(int user_idx) {
		UpFileVO upfile = new UpFileVO();
		try {
			upfile=upFileDAO.selectProfileImg(user_idx);
			System.out.println("upfile : "+upfile);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return upfile;
	}

	@Override
	public List<ReviewVO> getReview(int user_idx) {
		List<ReviewVO> list = null;
		try {
			list=reviewDAO.selectByUser(user_idx);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public UserVO selectByIdx(int user_idx) {
		UserVO userVO = null;
		try {
			userVO = userDAO.selectByIdx(user_idx);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userVO;
	}

	@Override
	public int createCount(int user_idx) {
		int createcount = 0;
		try {
			createcount = mypageDAO.createCount(user_idx);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return createcount;
	}

	@Override
	public int signupCount(int user_idx){
		int signupcount = 0;
		try {
			signupcount = mypageDAO.signupCount(user_idx);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return signupcount;
	}

	@Override
	public int reviewCount(int user_idx) {
		int reviewcount=0;
		try {
			reviewcount=mypageDAO.reviewCount(user_idx);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reviewcount;
	}

	@Override
	public List<String> signUpList(int user_idx) {
		List<String> nameList = null;
		try {
			nameList = mypageDAO.signUpList(user_idx);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nameList;
	}

	@Override
	public List<String> categoryList(int user_idx) {
		List<String> categoryList = null;
		try {
			categoryList = mypageDAO.categoryList(user_idx);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categoryList;
	}

	@Override
	public List<ReviewVO> reviewList(int user_idx) {
		List<ReviewVO> reviewList = null;
		try {
			reviewList = mypageDAO.reviewList(user_idx);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reviewList;
	}

	@Override
	public PagingVO<ReviewVO> reviewPaging(CommVO commVO, int user_idx) {
		PagingVO<ReviewVO> pagingVO = null;
		//전체 개수
		int totalCount;
		try {
			totalCount = mypageDAO.reviewCount(user_idx);
			log.info("totalCount : "+totalCount);
			// 페이징 객체 생성 : 계산 완료
			pagingVO = new PagingVO<>(totalCount, commVO.getCurrentPage(), commVO.getPageSize(), commVO.getBlockSize());
			log.info(commVO.toString());
			log.info(pagingVO.toString());
			// 목록 가져오기
			HashMap<String, Integer> map = new HashMap<>();
			map.put("startNo", pagingVO.getStartNo());
			map.put("endNo", pagingVO.getEndNo());
			map.put("user_idx",user_idx);
			List<ReviewVO> list = mypageDAO.reviewPagingList(map);
			pagingVO.setList(list);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pagingVO;
	}

	@Override
	public List<UserVO> getCreatorList(int user_idx,String sortMenu) {
		List<MoimVO> moimList = null;
		List<UserVO> list=new ArrayList<>();
		try {
			switch(sortMenu) {
			case "개설":
				moimList=moimDAO.selectCreateList(user_idx);
				break;
			case "신청":
				moimList=moimDAO.selectApplyList(user_idx);
				break;
			case "참여중":
				moimList=moimDAO.selectJoinList(user_idx);
				break;
			case "찜한모임":
				moimList=moimDAO.selectLikeList(user_idx);
				break;
			}
			System.out.println("---------------");
			System.out.println(moimList);
			for(MoimVO vo : moimList ) {
				if(vo!=null) {
		              list.add(userDAO.selectByIdx( vo.getUser_idx() ) );
				}
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	
}
