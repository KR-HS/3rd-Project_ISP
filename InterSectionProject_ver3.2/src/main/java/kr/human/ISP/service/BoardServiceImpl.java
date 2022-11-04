package kr.human.ISP.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.human.ISP.dao.BoardCommentDAO;
import kr.human.ISP.dao.BoardDAO;
import kr.human.ISP.dao.UpFileDAO;
import kr.human.ISP.vo.BoardCommentVO;
import kr.human.ISP.vo.BoardVO;
import kr.human.ISP.vo.CommVO;
import kr.human.ISP.vo.PagingVO;
import kr.human.ISP.vo.UpFileVO;

@Service("boardService")
@Transactional
public class BoardServiceImpl implements BoardService{

	@Autowired
	private BoardDAO boardDAO;
	
	@Autowired
	private BoardCommentDAO boardCommentDAO;
	
	@Autowired
	private UpFileDAO upFileDAO;
	
	@Override
	public PagingVO<BoardVO> selectBoardList(CommVO commVO,String board_category) {
		System.out.println("확인할게요 : " + commVO);
		PagingVO<BoardVO> pagingVO = null;
		String board_isDelete = "Y";
		int deleteCount =0;
		try {
			
			int totalCount = boardDAO.selectCountByCategory(board_category);
			deleteCount = boardDAO.selectDeleteByCategory(board_isDelete,board_category);
			//삭제된 게시판도 있기때문에 전체 게시판갯수에서 삭제된 갯수 삭제해줘야함.
			System.out.println("카테고리 삭제된 갯수 : " + deleteCount);
			System.out.println("게시판갯수 : " + totalCount);
			totalCount = totalCount - deleteCount;
			pagingVO = new PagingVO<>(totalCount, commVO.getP(), commVO.getS(), commVO.getB());
			HashMap<String, Object> hashMap = new HashMap<>();
			hashMap.put("startNo", pagingVO.getStartNo());
			hashMap.put("endNo", pagingVO.getEndNo());
			hashMap.put("board_category", board_category);
			List<BoardVO> list = boardDAO.selectListByCategory(hashMap);
			System.out.println("확인222222 : " + list);
			pagingVO.setList(list);
			System.out.println("최종결과  : " + pagingVO);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pagingVO;
	}

	//대형 수정
	@Override
	public BoardVO selectByIdx(int notice_idx) {
		BoardVO boardVO = null;
		try {
			boardVO = boardDAO.selectByIdx(notice_idx);
			return boardVO;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return boardVO;

	}
	//게시판 글쓰기 저장하기
	@Override
	public void insertBoard(BoardVO boardVO) {
		int a = 0;
		
		try {
			boardDAO.insert(boardVO);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void updateBoard(BoardVO BoardVO) {
		try {
			boardDAO.noticeupdate(BoardVO);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//게시판 삭제하기
	@Override
	public void deleteBoard(BoardVO noticeVo) {
		try {
			boardDAO.delete(noticeVo.getBoard_idx());
			System.out.println("삭제할게요 : " +noticeVo.getBoard_idx());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public BoardVO selectByUserIdx(int user_idx) {
		
		BoardVO boardVO =null;
		try {
			boardVO =boardDAO.selectByIdx(user_idx);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return boardVO;
	}

	@Override
	public PagingVO<BoardVO> selectInquiryList(CommVO commVO, int user_idx) {
		PagingVO<BoardVO> pagingVO = null;
	
		try {
			int totalCount = boardDAO.selectByUserIdxCount(user_idx);
			System.out.println("총갯수는 ? " + totalCount);
			pagingVO = new PagingVO<>(totalCount, commVO.getP(), commVO.getS(), commVO.getB());
			HashMap<String, Object> hashMap = new HashMap<>();
			hashMap.put("startNo", pagingVO.getStartNo());
			hashMap.put("endNo", pagingVO.getEndNo());
			hashMap.put("user_idx", user_idx);
			List<BoardVO> list = boardDAO.selectListByUserIdx(hashMap);
			System.out.println("리스트 확인 : " + list);
			pagingVO.setList(list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pagingVO;
	}

	//댓글 저장하기
	@Override
	public void insertBoardComment(BoardCommentVO boardCommentVO) {
		// TODO Auto-generated method stub
		try {
			boardCommentDAO.insert(boardCommentVO);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public String selectCommentCountByBoardIdx(int board_idx) {
		String answer = "";
		try {
			if(boardCommentDAO.selectCount(board_idx)==0) {
				answer = "답변대기";
			}else {
				answer = "답변완료";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}				
		return answer;
	}

	//문의내역 댓글 가져오기.
	@Override
	public BoardCommentVO selectCommentBoard(int board_idx, int user_idx) {
		
		BoardCommentVO boardCommentVO= null;
		try {
			 boardCommentVO = boardCommentDAO.selectCommentBoard(board_idx, user_idx);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return boardCommentVO;
		
	}

	@Override
	public String selectCommentCount(int board_idx, int user_idx) {
		int count = 0;
		String CommentCount = "댓글없음";
		try {
			count = boardCommentDAO.selectCommentCount(board_idx, user_idx);
			if(count > 0) {
				CommentCount = "댓글있음";
				return CommentCount;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return CommentCount;
	}

	@Override
	public void uploadProfileImg(UpFileVO upFileVO) {
		
		try {
			upFileDAO.insert(upFileVO);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	@Override
	public int selectCurrentBoardIdx() {
		
		int board_idx = 0;
		try {
			board_idx = boardDAO.selectCurrentBoardIdx();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return board_idx;
	}

	@Override
	public List<UpFileVO> selectListByBoard(int board_idx) {
		List<UpFileVO> upFileList = null;
	
		
		try {
			 upFileList = upFileDAO.selectListByBoard(board_idx);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return upFileList;
	}




	
	
	

}
