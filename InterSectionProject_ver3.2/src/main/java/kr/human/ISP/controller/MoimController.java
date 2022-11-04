package kr.human.ISP.controller;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.human.ISP.MoimCreateService.LikeMoimServiceImpl;
import kr.human.ISP.MoimCreateService.MoimCreateService;
import kr.human.ISP.MoimCreateService.MoimViewService;
import kr.human.ISP.MoimCreateService.ReviewServiceImpl;
import kr.human.ISP.MoimCreateService.SingUpServiceImpl;
import kr.human.ISP.service.MoimService;
import kr.human.ISP.service.UserServiceImpl;
import kr.human.ISP.vo.CategoryVO;
import kr.human.ISP.vo.LikeMoimVO;
import kr.human.ISP.vo.MoimVO;
import kr.human.ISP.vo.ReviewVO;
import kr.human.ISP.vo.SignUpVO;
import kr.human.ISP.vo.UpFileVO;
import kr.human.ISP.vo.UserVO;
import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
public class MoimController {

	@Autowired
	private MoimViewService moimViewService;
	
	@Autowired
	private SingUpServiceImpl signUpService;
	
	@Autowired
	private ReviewServiceImpl reviewService;
	
	@Autowired
	private LikeMoimServiceImpl likeMoimService;
	
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private MoimCreateService moimCreateService;
	
	@Autowired
	private MoimService moimService;
	
	
	// application.properties에 등록된 파일의 경로를 가져온다.
	String filePath=null;
	{
		try {
			filePath = new ClassPathResource("/static/upload/").getFile().getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	// 모임 만들기 페이지 
	@RequestMapping(value = "/MoimCreate")
	public String createMoim(Model model, HttpSession session) {
		// 대분류 리스트 담아서 모델에 저장
		List<String> LC_list = moimCreateService.selectAllLcname();
		model.addAttribute("LC_list", LC_list);
		
		
	
		UserVO userVO = (UserVO) session.getAttribute("userVO");
		
		/*
		if(session.getAttribute("o_fileName") != null && ) {
			UpFileVO profileImg=moimCreateService.selectByOfileName((String) session.getAttribute("o_fileName"));
			if(profileImg != null) {
				System.out.println("하하하하하하"+profileImg.toString());
			model.addAttribute("path",filePath);
			model.addAttribute("profileImg",profileImg);
			System.out.println("프로필"+profileImg);		
			}
		}		
		*/
		
		return "moim/MoimCreate";
	}
	
	// 모임 만들기 페이지 
		@RequestMapping(value = "/MoimUpdate")
		public String updateMoim(Model model, HttpSession session,@RequestParam int moim_idx) {
			// 대분류 리스트 담아서 모델에 저장
			List<String> LC_list = moimCreateService.selectAllLcname();
			model.addAttribute("LC_list", LC_list);
			
			
		
			UserVO userVO = (UserVO) session.getAttribute("userVO");
			
			/*
			if(session.getAttribute("o_fileName") != null && ) {
				UpFileVO profileImg=moimCreateService.selectByOfileName((String) session.getAttribute("o_fileName"));
				if(profileImg != null) {
					System.out.println("하하하하하하"+profileImg.toString());
				model.addAttribute("path",filePath);
				model.addAttribute("profileImg",profileImg);
				System.out.println("프로필"+profileImg);		
				}
			}		
			*/
			
			return "moim/MoimUpdate";
		}
	
	// 모임 생성 버튼 누르면 진행하는 insert
	@ResponseBody
	@RequestMapping(value = "/moimInsertOk")
	public ResponseEntity<?> createMoimInsert(@ModelAttribute MoimVO moimVO, @ModelAttribute UpFileVO upFileVO,HttpSession session) {
		log.info("받은 VO : {}", moimVO);
		/*
		String str = moimVO.getMoim_time();
		str = str.replace('T', ' ');
		moimVO.setMoim_time(str);
		*/
		
		// 모임만들기 DB에 저장
		moimCreateService.insert(moimVO);
		
		
		//String str2 = moimVO.getMoim_deadline();
		//Date str2 = moimVO.getMoim_deadline();
		//str2 = str2.replace('T', ' ');
		//moimVO.setMoim_deadline(str2);
		System.out.println("-".repeat(200));
		log.info("데이터 삽입전 form전송으로 받은 upFileVO : {}", upFileVO);
		System.out.println("-".repeat(200));
		
		
		MoimVO idxYong = moimCreateService.selectByNewOneMoim();

		System.out.println("-".repeat(200));
		log.info("가장 최신의 모임VO : {}", idxYong);
		System.out.println("-".repeat(200));
		
		upFileVO.setMoim_idx(idxYong.getMoim_idx());
		upFileVO.setBoard_idx(0);
		upFileVO.setUser_idx(0);
		System.out.println("upFileVO: "+upFileVO);
		
		moimCreateService.moimCategoryInsert(idxYong.getMoim_idx(), moimVO.getLc_name(), moimVO.getSc_name());	
		
		
		moimCreateService.profileImgInsert(upFileVO);
		
		
		HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/"));
        session.removeAttribute("o_fileName");
        session.removeAttribute("s_fileName");
		return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
	}
	
	
	
	
	
	
	

	// 모임만들기 페이지에서 category 선택을 가져오는 부분
	@RequestMapping(value = "/Category")
	@ResponseBody
	public List<String> scList(@RequestParam(required = false, name = "lc_name") String lc_name) {
		List<String> SC_list = null;
		if(lc_name != null || lc_name.trim().length() >0) {
			SC_list = moimCreateService.selectByScname(lc_name);
		}
		System.out.print(SC_list);
		return SC_list;
	}
	
	
	@RequestMapping(value = "/profileUploadForm")
	public String profileUploadForm() {
		return "moim/profileUpload";
	}
	
	
	@GetMapping(value = "/profileUploadFormOk")
	public String moimImage_get() {
		return "redirect:moim/profileUpload";
	}
	
	@PostMapping(value = "/profileUploadFormOk")
	@ResponseBody
	public String moimImage_Post(@RequestParam("profileImg") MultipartFile uploadfile, HttpSession session, Model model) {
		if(uploadfile!=null) {
			try {
				filePath = new ClassPathResource("/static/upload/").getFile().getAbsolutePath();
				log.info("서버 절대 경로 : {}", filePath);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			long sizeInBytes=uploadfile.getSize();
			if(sizeInBytes>0){ // 파일크기가 있을때만
				String oriName = uploadfile.getOriginalFilename();
		        String saveName = UUID.randomUUID().toString() + "_" + oriName ;  // 겹치지않는 ID를 만들어준다. 
		        
		        /*
		        UserVO uVO = (UserVO) session.getAttribute("userVO");
				UpFileVO upfileVO = new UpFileVO();
				upfileVO.setUser_idx(uVO.getUser_idx());
				upfileVO.setO_fileName(oriName);
				upfileVO.setS_fileName(saveName);
				*/
				/* UpFileVO profile=moimCreateService.getProfile(uVO.getUser_idx()); */
				
				// UpFileVO profile=moimCreateService.getMoimImg(oriName);
				// 프로필사진이 db에 저장안되있으면 저장
		        // if(profile==null) {
				// System.out.println(moimCreateService.uploadProfileImg(upfileVO)); 
				// db저장
		        	
		        	// 파일 저장		        	
		        	File newFileName = new File(filePath +"/"+ saveName);
			        try {
						uploadfile.transferTo(newFileName);
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
			        
			        
		        //}
		        
		        session.setAttribute("o_fileName", oriName);
		        session.setAttribute("s_fileName", saveName);
		        
		        // return filePath+profile.getS_fileName();
				return filePath+saveName;
			}
		} 
		  return "error";
	}
	
	
	// ---------------------------------------------------------------------
	// 여기 밑으로 새롬이 컨트롤러
	
	


	@RequestMapping(value="signUp2")
	public String signUp(Model model) {
		return "signUp2";
	}
	
	//게시글 idx 가지고 오기. 게시물만든사람 정보유저정보 참여자 정보 까지!
	//참석자가 있을떄 버튼 참여완료로 보이게 해야됨.1.
	@RequestMapping("/moim1")
	public String view(
			@RequestParam Map<String, String> params,HttpSession session,
			@ModelAttribute MoimVO moimVO,UserVO userVO, ReviewVO reviewVO,SignUpVO signUpVO,CategoryVO categoryVO,Model model, @RequestParam(required = false) Integer indexMoimidx) {
		
		//게시글 한개 가지고오기 다른사람이랑 합치고 동적으로 수정해야됨!
		MoimVO vo = null;
		try {
			//vo = moimViewService.selectByIdx(1);
			if(indexMoimidx == null) {
				return "redirect:/index"; 
			}
			vo = moimViewService.selectByIdx(indexMoimidx);				
			
			if(vo.moim_isDelete.equals("Y")) {
				return "redirect:/index"; 
			}
				
			UserVO user = userService.selectByIdx(vo.getUser_idx());
			List<ReviewVO> review = reviewService.selectByMoimPlusName(vo.moim_idx);
			List<UserVO> userSignUp_list = userService.selectSignUpList(vo.moim_idx);
			List<SignUpVO> alluserSignUp_list = signUpService.selectByMoimIdx(vo.moim_idx);
			categoryVO= moimService.selectCategoryByMoimIdx(vo.moim_idx);
			
			
			
			UserVO uvo=(UserVO) session.getAttribute("userVO");
			for(SignUpVO svo : alluserSignUp_list) {
				if(svo.getUser_idx()==uvo.getUser_idx() && svo.getSignUp_isApply()!="R") {
					model.addAttribute("sign_apply","신청완료");
				}
				
			}
			model.addAttribute("vo",vo);
			model.addAttribute("userVO",user);
			model.addAttribute("review",review);
			model.addAttribute("userSignUp_list",userSignUp_list);
			model.addAttribute("categoryVO",categoryVO);
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
				

		return "moim/MoimView";
		
	}
		//참여신청하기
		@RequestMapping(value="moim_delete",method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
		public void moim_delete(@RequestParam int moim_idx) {
			moimService.delete(moim_idx);
		}
			
		//참여신청하기
		@RequestMapping(value="signUp_insert",method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
		@ResponseBody
		public void signUp_insert(@ModelAttribute SignUpVO signUpVO) { 
			signUpService.insert(signUpVO);		
		}
		
		
		
		//참여신청취소하기
		@RequestMapping(value="signUp_delete",method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
		@ResponseBody
		public void signUp_delete(@ModelAttribute SignUpVO signUpVO,int signUp_idx) { 
			signUpService.delete(signUp_idx);
		}
		
		
		//후기저장하기
		@RequestMapping(value="commentInsert")
		@ResponseBody
		public void commentInsert(@ModelAttribute ReviewVO reviewVO) {	
			reviewService.insert(reviewVO);		
		}

		//ajax 후기삭제하기 
		@RequestMapping(value = "commentDelete", method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
		@ResponseBody
		public void commentDelete(@ModelAttribute ReviewVO reviewVO,int review_idx) {
			reviewService.updateIsDelete(review_idx);		
		}

		//ajax 후기수정하기 
		@RequestMapping(value = "commentUpdate", method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
		@ResponseBody
		public void commentUpdate(@ModelAttribute ReviewVO reviewVO) {
			System.out.println(reviewVO);
			reviewService.update(reviewVO);		
		}

		
		//ajax 찜하기 
		@RequestMapping(value = "likeInsert", method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
		@ResponseBody
		public void likeInsert(@ModelAttribute LikeMoimVO likeMoimVO,HttpServletRequest request, UserVO userVO) {
			userVO = (UserVO) request.getSession().getAttribute("userVO");
			likeMoimVO.setUser_idx(userVO.getUser_idx());
			likeMoimService.insert(likeMoimVO);
			
		}
		//ajax 찜 취소하기
		@RequestMapping(value = "likeDelete", method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
		@ResponseBody
		public void likeDelete(@RequestParam int user_idx) {
			likeMoimService.delete(user_idx);
		}
		
		
		/*
		@RequestMapping
		@ResponseBody
		public void fileUploadCkeditor() {
			
			
			
			// 이미지를 업로드하여 서버에 저장하고 서버에 저장된 이미지 경로를 출력해주면 된다.

			// json 데이터로 등록
			// {"uploaded" : 1, "fileName" : "test.jpg", "url" : "/img/test.jpg"}
			// 이런 형태로 리턴이 나가야함.

			// 이미지 업로드
			// 1) 서버에 저장할 경로가 없으면 경로를 만들어 준다.
			File file = new File(application.getRealPath("/ckeditor/"));
			if(!file.exists()){
				file.mkdirs();
			}
			String realPath = application.getRealPath("/ckeditor/"); // 서버 절대 경로
			// 2) 이미지를 업로드 한다.
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if(isMultipart){
				DiskFileItemFactory factory = new DiskFileItemFactory(); // DiskFileItemFactory 객체 생성
				ServletFileUpload upload = new ServletFileUpload(factory); // ServletFileUpload 객체 생성
				upload.setSizeMax(1024*1024*10); // 최대 10MB지정
				List<FileItem> items = upload.parseRequest(request); // 파싱
				if(items!=null && items.size()>0){ // 파일이 있다면
					for(FileItem item : items){ // 반복
						if(item!=null && item.getSize()>0){ // 파일의 크기가 0보다 크다면
							String originalFileName = item.getName(); // 원본이름
							System.out.println("[" + originalFileName + "]");
							if(originalFileName!=null){
								// IE의 경우 원본파일 이름에 경로가 들어간다. 경로가 있으면 경로를 지워줘야 한다.
								if(originalFileName.contains(File.separator)){ // 경로 구분자가 있다면
									int index = originalFileName.lastIndexOf(File.separator); // 뒤에서부터 경로구분자의 위치를 찾는다.
									// 뒷부분만 가지면 된다.
									originalFileName = originalFileName.substring(index+1);
								}
							
								// 겹치지않는 ID를 만들어준다. -- 저장파일명으로 쓰자!!
								String saveFilleName = UUID.randomUUID().toString() + "_" + originalFileName; 
						        item.write(new File(realPath + File.separator + saveFilleName)); // 파일 저장
						        
						        // 리턴할 데이터를 만들어 주어야 한다.
						        // {"uploaded" : 1, "fileName" : "test.jpg", "url" : "/img/test.jpg"}
						        JsonObject json = new JsonObject();
						        json.addProperty("uploaded", 1);
						        json.addProperty("fileName", originalFileName);
						        json.addProperty("url", request.getContextPath()+"/ckeditor/" + saveFilleName);
						        System.out.println("[" + json.toString() + "]");
								out.print(json);
							}
						}
					}
				}
			}
			%>
			
			
			
		}
		*/
	
	
	
	
}
