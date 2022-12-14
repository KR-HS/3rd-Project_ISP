package kr.human.ISP.controller;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import kr.human.ISP.service.UserServiceImpl;
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
	
	// application.properties??? ????????? ????????? ????????? ????????????.
	String filePath=null;
	{
		try {
			filePath = new ClassPathResource("/static/upload/").getFile().getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	// ?????? ????????? ????????? 
	@RequestMapping(value = "/MoimCreate")
	public String createMoim(Model model, HttpSession session) {
		// ????????? ????????? ????????? ????????? ??????
		List<String> LC_list = moimCreateService.selectAllLcname();
		model.addAttribute("LC_list", LC_list);
		
		
	
		UserVO userVO = (UserVO) session.getAttribute("userVO");
		
		/*
		if(session.getAttribute("o_fileName") != null && ) {
			UpFileVO profileImg=moimCreateService.selectByOfileName((String) session.getAttribute("o_fileName"));
			if(profileImg != null) {
				System.out.println("??????????????????"+profileImg.toString());
			model.addAttribute("path",filePath);
			model.addAttribute("profileImg",profileImg);
			System.out.println("?????????"+profileImg);		
			}
		}		
		*/
		
		return "moim/MoimCreate";
	}
	
	
	
	// ?????? ?????? ?????? ????????? ???????????? insert
	@ResponseBody
	@RequestMapping(value = "/moimInsertOk")
	public ResponseEntity<?> createMoimInsert(@ModelAttribute MoimVO moimVO, @ModelAttribute UpFileVO upFileVO,HttpSession session) {
		log.info("?????? VO : {}", moimVO);
		String str = moimVO.getMoim_time();
		str = str.replace('T', ' ');
		moimVO.setMoim_time(str);
		
		// ??????????????? DB??? ??????
		moimCreateService.insert(moimVO);
		
		
		//String str2 = moimVO.getMoim_deadline();
		//Date str2 = moimVO.getMoim_deadline();
		//str2 = str2.replace('T', ' ');
		//moimVO.setMoim_deadline(str2);
		System.out.println("-".repeat(200));
		log.info("????????? ????????? form???????????? ?????? upFileVO : {}", upFileVO);
		System.out.println("-".repeat(200));
		
		
		MoimVO idxYong = moimCreateService.selectByNewOneMoim();

		System.out.println("-".repeat(200));
		log.info("?????? ????????? ??????VO : {}", idxYong);
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
	
	
	
	
	
	
	

	// ??????????????? ??????????????? category ????????? ???????????? ??????
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
				log.info("?????? ?????? ?????? : {}", filePath);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			long sizeInBytes=uploadfile.getSize();
			if(sizeInBytes>0){ // ??????????????? ????????????
				String oriName = uploadfile.getOriginalFilename();
		        String saveName = UUID.randomUUID().toString() + "_" + oriName ;  // ??????????????? ID??? ???????????????. 
		        
		        UserVO uVO = (UserVO) session.getAttribute("userVO");
				UpFileVO upfileVO = new UpFileVO();
				upfileVO.setUser_idx(uVO.getUser_idx());
				upfileVO.setO_fileName(oriName);
				upfileVO.setS_fileName(saveName);
				
				/* UpFileVO profile=moimCreateService.getProfile(uVO.getUser_idx()); */
				
				// UpFileVO profile=moimCreateService.getMoimImg(oriName);
				// ?????????????????? db??? ????????????????????? ??????
		        // if(profile==null) {
				// System.out.println(moimCreateService.uploadProfileImg(upfileVO)); 
				// db??????
		        	
		        	// ?????? ??????		        	
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
	// ?????? ????????? ????????? ????????????
	
	


	@RequestMapping(value="signUp2")
	public String signUp(Model model) {
		return "signUp2";
	}
	
	//????????? idx ????????? ??????. ????????????????????? ?????????????????? ????????? ?????? ??????!
			//???????????? ????????? ?????? ??????????????? ????????? ?????????.1.
			@RequestMapping("/moim1")
			public String view(
					@RequestParam Map<String, String> params,HttpSession session,
					@ModelAttribute MoimVO moimVO,UserVO userVO,ReviewVO reviewVO,SignUpVO signUpVO,Model model, @RequestParam int indexMoimidx) {
				//????????? ?????? ??????????????? ?????????????????? ????????? ???????????? ???????????????!
				
				MoimVO vo = null;
				try {
					//vo = moimViewService.selectByIdx(1);
					vo = moimViewService.selectByIdx(indexMoimidx);
					UserVO user = userService.selectByIdx(vo.getUser_idx());
					List<ReviewVO> review = reviewService.selectByMoimPlusName(vo.moim_idx);
					List<UserVO> userSignUp_list = userService.selectSignUpList(vo.moim_idx);
					List<SignUpVO> alluserSignUp_list = signUpService.selectByMoimIdx(vo.moim_idx);
					
					UserVO uvo=(UserVO) session.getAttribute("userVO");
					for(SignUpVO svo : alluserSignUp_list) {
						if(svo.getUser_idx()==uvo.getUser_idx() && svo.getSignUp_isApply()!="R") {
							model.addAttribute("sign_apply","????????????");
						}
						
					}
					model.addAttribute("vo",vo);
					model.addAttribute("userVO",user);
					model.addAttribute("review",review);
					model.addAttribute("userSignUp_list",userSignUp_list);
				
				} catch (SQLException e) {
					e.printStackTrace();
				}
						

				return "moim/MoimView";
				
		}
			
			
		//??????????????????
		@RequestMapping(value="signUp_insert",method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
		@ResponseBody
		public void signUp_insert(@ModelAttribute SignUpVO signUpVO) { 
			signUpService.insert(signUpVO);		
		}
		//????????????????????????
		@RequestMapping(value="signUp_delete",method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
		@ResponseBody
		public void signUp_delete(@ModelAttribute SignUpVO signUpVO,int signUp_idx) { 
			signUpService.delete(signUp_idx);
		}
		
		
		//??????????????????
		@RequestMapping(value="commentInsert")
		@ResponseBody
		public void commentInsert(@ModelAttribute ReviewVO reviewVO) {	
			reviewService.insert(reviewVO);		
		}

		//ajax ?????????????????? 
		@RequestMapping(value = "commentDelete", method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
		@ResponseBody
		public void commentDelete(@ModelAttribute ReviewVO reviewVO,int review_idx) {
			reviewService.updateIsDelete(review_idx);		
		}

		//ajax ?????????????????? 
		@RequestMapping(value = "commentUpdate", method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
		@ResponseBody
		public void commentUpdate(@ModelAttribute ReviewVO reviewVO) {
			System.out.println(reviewVO);
			reviewService.update(reviewVO);		
		}

		
		//ajax ????????? 
		@RequestMapping(value = "likeInsert", method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
		@ResponseBody
		public void likeInsert(@ModelAttribute LikeMoimVO likeMoimVO) {
			likeMoimService.insert(likeMoimVO);
			
		}
		//ajax ??? ????????????
		@RequestMapping(value = "likeDelete", method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
		@ResponseBody
		public void likeDelete(@RequestParam int user_idx) {
			likeMoimService.delete(user_idx);
		}
		
		
	
	
	
	
	
}
