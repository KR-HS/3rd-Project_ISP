package kr.human.ISP.controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.RequestContextUtils;



import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;




import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;




import kr.human.ISP.service.BoardService;
import kr.human.ISP.service.UserService;
import kr.human.ISP.vo.BoardCommentVO;
import kr.human.ISP.vo.BoardVO;
import kr.human.ISP.vo.CommVO;
import kr.human.ISP.vo.FileVO;
import kr.human.ISP.vo.PagingVO;
import kr.human.ISP.vo.UpFileVO;
import kr.human.ISP.vo.UserVO;
import lombok.extern.slf4j.Slf4j;

@Controller 
@Slf4j
public class BoardController {
	
	@Autowired
	private UserService userService;

	
	@Autowired
	BoardService boardService;
	
	
	@Value("${spring.servlet.multipart.location}")
	String filePath;
	
	
	// Board Page 
	@GetMapping("board/boardPage")
	public String boardPage() {
		return "board/boardPage";
	}
	
	// 공지사항 목록 페이지
	@GetMapping("/noticeList")
	public String boardList() {
		return "board/noticeList";
	}
	
	@GetMapping(value="/inquiryResult")
	public String inquiryResult_P(HttpServletRequest request, @ModelAttribute BoardVO boardVO) {

		return "board/inquiryResult";
	}
	
	
	// 공지사항 목록 조회 Ajax
	@SuppressWarnings("unchecked")
	@PostMapping(value="board/boardSubject")
	@ResponseBody
	public Map<String, Object> noticeBoardShow(HttpServletRequest request,Model model,HttpSession session,
			@ModelAttribute CommVO commVO, @RequestParam Map<String,String> map) {
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if(flashMap!=null) {
			map = (Map<String, String>) flashMap.get("map");
			commVO.setP(Integer.parseInt(map.get("p")));
			commVO.setS(Integer.parseInt(map.get("s")));
			commVO.setB(Integer.parseInt(map.get("b")));
		}
		UserVO userVO = (UserVO) session.getAttribute("userVO");
		Map<String,Object> resultMap = new HashMap<String, Object>();
		String boardName = map.get("boardName");
		
		if(boardName.equals("inquiryResult")) {
			PagingVO<BoardVO> pagingVO =boardService.selectInquiryList(commVO,userVO.getUser_idx());
			for(BoardVO vo : pagingVO.getList()) {
				vo.setAnswer(boardService.selectCommentCountByBoardIdx(vo.getBoard_idx()));
			}
			resultMap.put("pv", pagingVO);
		}else {
			PagingVO<BoardVO> pagingVO = boardService.selectBoardList(commVO,boardName);
			resultMap.put("pv", pagingVO);
			
		}
		resultMap.put("boardName", boardName);
		System.out.println("결과확인 : " + resultMap);
		return resultMap;
	}
	
	
	// 공지사항 상세보기 페이지
	@GetMapping("/noticeDetail/{board_idx}")
	public String boardDetail(HttpServletRequest request, @PathVariable("board_idx") int board_idx, Model model) {
		model.addAttribute("board_idx",board_idx);	
		System.out.println(board_idx);
		return "board/noticeDetail";
	}
	
	// 공지사항 상세조회 Ajax
	@RequestMapping(value = "/noticeDetail")
	@ResponseBody 
	public Map<String, Object> boardView(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<>();
		BoardVO boardVO = null;
		
		String idx = request.getParameter("board_idx");
		int board_idx = Integer.parseInt(idx);
		
		boardVO = boardService.selectByIdx(board_idx);
	
		resultMap.put("boardVO", boardVO);
		resultMap.put("UpfileVO", boardService.selectListByBoard(board_idx));
		System.out.println("맵정보 확인하기 "  + resultMap);
		
		return resultMap;
	}
	
	// 공지사항 등록 페이지
	@GetMapping("/noticeWrite")
	public String boardCreate(HttpServletRequest request, Model model) {
		return "board/noticeWrite";
	}
	
	// 공지사항 등록 Ajax
	@RequestMapping(value="/noticeWrite_send", method=RequestMethod.POST)
    public String insertBoard(HttpServletRequest request,
    		Model model,
    		HttpSession session,
    		@ModelAttribute BoardVO boardVO,
    		@RequestParam MultipartFile[] uploadfile)  throws IllegalStateException, IOException{

		
    	//세션 로그인시?
    	UserVO userVO = (UserVO)session.getAttribute("userVO");
    	int user_idx = userVO.getUser_idx();
    	


    	boardVO.setBoard_subject(request.getParameter("noticeWirte_subject"));
    	boardVO.setBoard_content(request.getParameter("noticeWrite_content"));
    	boardVO.setUser_idx(user_idx);
    	boardVO.setBoard_category(request.getParameter("notice"));

    	boardService.insertBoard(boardVO);

    	
    	
		// 파일 업로드추가한부분!!! 
		
		List<FileVO> list = new ArrayList<>();
		for (MultipartFile file : uploadfile) {
			if (!file.isEmpty()) {
				// UUID를 이용해 unique한 파일 이름을 만들어준다.
				FileVO vo = new FileVO(UUID.randomUUID().toString(), file.getOriginalFilename(), file.getContentType());
				list.add(vo);
				
				File newFileName = new File(filePath + vo.getUuid() + "_" + vo.getFileName());
				// 전달된 내용을 실제 물리적인 파일로 저장해준다.
				file.transferTo(newFileName);
				System.out.println(newFileName);
				
				int board_idx = boardService.selectCurrentBoardIdx();

				UpFileVO upFileVO = new UpFileVO();
				upFileVO.setBoard_idx(board_idx);
				upFileVO.setUser_idx(user_idx);
				upFileVO.setO_fileName(vo.getFileName());
				upFileVO.setS_fileName(vo.getUuid());
				upFileVO.setContentType(vo.getContentType());
				boardService.uploadProfileImg(upFileVO);
				System.out.println("잘들어갔니? " + upFileVO);
				
		
			}
		}
		System.out.println(list);

    	return "board/noticeList";
    }
	
	
	// 공지사항 수정 페이지로 가기
	@GetMapping(value = "/noticeUpdate/{board_idx}")
	public String boardUpdate(HttpServletRequest request, @PathVariable("board_idx") int board_idx, Model model,@ModelAttribute BoardVO boardVO) {
		boardVO.setBoard_idx(board_idx);
		boardVO = boardService.selectByIdx(board_idx);
		model.addAttribute(boardVO);
		return "board/noticeUpdateView";
	}
	//공지사항 수정하기(글업데이트)
	@PostMapping(value="/noticeUpdate_send")
    public String updateBoard(HttpServletRequest request, HttpSession session,@ModelAttribute BoardVO boardVO) {

    	//세션 로그인시?
    	//UserVO userVO = (UserVO)session.getAttribute("userVO");
    	//int user_idx = userVO.user_idx;
    	String board_subject = request.getParameter("noticeWirte_subject");
    	String board_content = request.getParameter("noticeWrite_content");
    	String idx = request.getParameter("noticeWrite_idx");
    	int board_idx = Integer.parseInt(idx);


    	boardVO.setBoard_subject(board_subject);
    	boardVO.setBoard_content(board_content);
    	boardVO.setBoard_idx(board_idx);
    	boardVO.setBoard_category(request.getParameter("notice"));

    	boardService.updateBoard(boardVO);
    	return "redirect:noticeList";
    }
	
	
	// 공지사항 삭제하기 Ajax
	@RequestMapping(value = "/boardDelete", method=RequestMethod.POST)
	@ResponseBody 
	public String boardDelete(HttpServletRequest request, @ModelAttribute BoardVO boardVO,@RequestParam String boardidx){
		int board_idx = Integer.parseInt(boardidx);
		boardVO.setBoard_idx(board_idx);
		boardService.deleteBoard(boardVO);
		return "삭제성공";
	}	
	
	// 1:1문의하기페이지
	@GetMapping(value="/inquirySend")
	public String inquirySend(HttpServletRequest request, @ModelAttribute BoardVO boardVO) {
	
		return "board/inquirySend";
	}
	@PostMapping(value="/inquirySend")
	public String inquirySendView(HttpServletRequest request, @ModelAttribute BoardVO boardVO,HttpSession session) {
		 
		return "board/inquirySend";
	}
	//  1:1 문의하기 글쓰기 저장
	@PostMapping(value="/inquiryWrite_send")
	public String inquiryWrite_received(HttpServletRequest request, @ModelAttribute BoardVO boardVO,HttpSession session) {
		String board_subject = request.getParameter("inquiryWrite_subject");
		String board_content = request.getParameter("inquiryWrite_content");
		int user_idx = Integer.parseInt(request.getParameter("user_idx"));
		String inquiry = request.getParameter("inquiry");
		

		boardVO.setBoard_subject(board_subject);
		boardVO.setBoard_content(board_content);
		boardVO.setBoard_category(inquiry);
		boardVO.setUser_idx(user_idx);
		
		boardService.insertBoard(boardVO);
		
		return "/board/inquiryResult";
	}



	// 공지사항 수정 페이지로 가기
	@GetMapping(value = "board/inquiryDetail/{board_idx}")
	public String inquiryView(HttpServletRequest request, @PathVariable("board_idx") int board_idx, Model model,@ModelAttribute BoardVO boardVO) {
		
		boardVO  = boardService.selectByIdx(board_idx);
	
		
	
		model.addAttribute("test",board_idx);
		
		return "board/inquiryDetail";
	}
	// 나의 문의내역 뷰페이지 Ajax
	@PostMapping(value = "/inquiryDetail")
	@ResponseBody
	public Map<String, Object> inquiryDetail(HttpServletRequest request,
			@ModelAttribute BoardVO boardVO,
			@ModelAttribute BoardCommentVO boardCommentVO,
			HttpSession session) {
		Map<String,Object> resultMap = new HashMap<>();
		
		int board_idx = Integer.parseInt(request.getParameter("board_idx"));
		boardVO  = boardService.selectByIdx(board_idx);
		UserVO userVO = (UserVO)session.getAttribute("userVO");
		resultMap.put("boardVO", boardVO);
		String CommentCheck = boardService.selectCommentCount(board_idx, userVO.getUser_idx());
		System.out.println("유저정보확인 : " +userVO);
		if(CommentCheck == "댓글있음") {
			boardCommentVO = boardService.selectCommentBoard(board_idx, userVO.getUser_idx());
			resultMap.put("CommentContent",boardCommentVO.getComment_content());
		}else {
			resultMap.put("CommentContent", CommentCheck);
		}
		return resultMap;
		
	}

	@PostMapping(value = "/inquiryReview_Insert")
	@ResponseBody
	public BoardCommentVO inquiryReview_Insert(HttpServletRequest request, Model model,@ModelAttribute BoardCommentVO boardCommentVO,HttpSession session) {
		
		int board_idx = Integer.parseInt(request.getParameter("board_idx"));
		String content = request.getParameter("content");
		System.out.println(board_idx);
		System.out.println(content);
		UserVO userVO = (UserVO) session.getAttribute("userVO");
		boardCommentVO.setBoard_idx(board_idx);
		boardCommentVO.setComment_content(content);
		boardCommentVO.setUser_idx(userVO.getUser_idx());
		boardService.insertBoardComment(boardCommentVO);
		
		//boardCommentVO =boardService.selectCommentBoard(board_idx, userVO.getUser_idx());
		
		
		return boardCommentVO;
		
	}
	

	
	@GetMapping(value = {"/downLoad/{uuid}&{fileName}"})
	public ResponseEntity<Resource> download(@PathVariable("uuid") String uuid , @PathVariable("fileName") String fileName) throws IOException {
		// Path path = Paths.get(filePath + "/" + vo.getUuid() + "_" + vo.getFileName());
		System.out.println("확인용 " + uuid);
		String[] uuid_Name = uuid.split("=");
		String[] file_Name = fileName.split("=");
		System.out.println(" "  +uuid_Name[1]);
		System.out.println("잘렷니? "  +file_Name[1]);
		String newFileName = filePath + uuid_Name[1].toString() + "_" + file_Name[1].toString();
		Path path = Paths.get(newFileName);
		log.info("파일 절대 경로(download) : " + path.toString());
		String contentType = Files.probeContentType(path);
		// header를 통해서 다운로드 되는 파일의 정보를 설정한다.
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDisposition(ContentDisposition.builder("attachment").filename(file_Name[1].toString(), StandardCharsets.UTF_8).build());
		headers.add(HttpHeaders.CONTENT_TYPE, contentType);

		Resource resource = new InputStreamResource(Files.newInputStream(path));
		
		System.out.println("리소스는? "  + resource);
	
		return new ResponseEntity<>(resource, headers, HttpStatus.OK);
	}

	@GetMapping(value = {"/profileUpdate"})
	public String profileUpdate(HttpServletRequest request, Model model, HttpSession session,@ModelAttribute UserVO userVO) {
		
		userVO = (UserVO) session.getAttribute("userVO");
		model.addAttribute("user_id",userVO.getUser_id());
		model.addAttribute("user_idx",userVO.getUser_idx());
		model.addAttribute("user_name",userVO.getUser_name());
		
		model.addAttribute("user_gender",userVO.getUser_gender());
		model.addAttribute("user_phone",userVO.getUser_phone());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
		String user_birth = simpleDateFormat.format(userVO.getUser_birth());
		model.addAttribute("user_birth",user_birth);
		
		
		return "profile/profileUpdate";
	}
	

	
}
