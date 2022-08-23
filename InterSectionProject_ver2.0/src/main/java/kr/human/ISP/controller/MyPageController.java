package kr.human.ISP.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.RequestContextUtils;

import kr.human.ISP.service.ApplyService;
import kr.human.ISP.service.MypageService;
import kr.human.ISP.service.UserService;
import kr.human.ISP.vo.CommVO;
import kr.human.ISP.vo.MoimVO;
import kr.human.ISP.vo.PagingVO;
import kr.human.ISP.vo.ReviewVO;
import kr.human.ISP.vo.UpFileVO;
import kr.human.ISP.vo.UserVO;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MyPageController {
	
	// application.properties에 등록된 파일의 경로를 가져온다.
	String filePath = null;
	{
		try {
			filePath = new ClassPathResource("static/upload/").getFile().getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Autowired
	private MypageService mypageService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ApplyService applyService;
	
	
	
	@RequestMapping(value="mypage")
	public String mypage(Model model,HttpServletRequest request,UserVO userVO) throws SQLException {
		log.info("서버 절대 경로 : {}", filePath);
		log.info("userVO : {}",(UserVO) request.getSession().getAttribute("userVO"));
		Map<String,Integer> countMap = new HashMap<>();
		Map<String,Object> listMap = new HashMap<>(); 
		
		userVO = (UserVO) request.getSession().getAttribute("userVO");
		UpFileVO profileImg=mypageService.getProfile(userVO.getUser_idx());
		
		int createcount = mypageService.createCount(userVO.getUser_idx());
		int signupCount = mypageService.signupCount(userVO.getUser_idx());
		int reviewCount = mypageService.reviewCount(userVO.getUser_idx());
		
		countMap.put("createcount",createcount );
		countMap.put("signupCount",signupCount );
		countMap.put("reviewCount",reviewCount );

		
		List<String> nameList = mypageService.signUpList(userVO.getUser_idx());
		List<String> categoryList = mypageService.categoryList(userVO.getUser_idx());
		List<ReviewVO> reviewList = mypageService.reviewList(userVO.getUser_idx());
		
		listMap.put("nameList", nameList);
		listMap.put("categoryList", categoryList);
		listMap.put("reviewList", reviewList);
		
		model.addAttribute("countMap",countMap);
		model.addAttribute("listMap",listMap);
		model.addAttribute("userVO",userVO);
		
		model.addAttribute("path",filePath);
		model.addAttribute("profileImg",profileImg);
		return "mypage";
	}
	
	
	//이미지 업로드 화면
	@RequestMapping(value="/popup/profileImg_upload")
	public String profileImg_upload(Model model) throws SQLException{
		return "/popup/profileImg_upload";
	}
	// 이미지 업로드 기능
	@GetMapping(value="profileImg_uploadOk")
	public String u_ImageGet() {
		return "redirect:/popup/profileImg_upload";
	}
	
	// 이미지 업로드 기능
	@PostMapping(value="profileImg_uploadOk")
	@ResponseBody
	public String u_ImagePost(@RequestParam("profileImg") MultipartFile uploadfile,HttpServletRequest request,UserVO userVO) {
		if(uploadfile!=null) {
			log.info("서버 절대 경로 : {}", filePath);
			userVO = (UserVO) request.getSession().getAttribute("userVO");
			long sizeInBytes=uploadfile.getSize();
			if(sizeInBytes>0){ // 파일크기가 있을때만
				String oriName = uploadfile.getOriginalFilename();
		        String saveName = UUID.randomUUID().toString() + "_" + oriName ;  // 겹치지않는 ID를 만들어준다. 
			        
	       
				UpFileVO upfileVO = new UpFileVO();
				upfileVO.setUser_idx(userVO.getUser_idx());
				upfileVO.setO_fileName(oriName);
				upfileVO.setS_fileName(saveName);
				UpFileVO profile=mypageService.getProfile(userVO.getUser_idx());
				// 프로필사진이 db에 저장안되있으면 저장
		        if(profile==null) {
		        	System.out.println(mypageService.uploadProfileImg(upfileVO)); // db저장
		        	// 파일 저장
		        	File newFileName = new File(filePath +"/"+ saveName);
			        try {
						uploadfile.transferTo(newFileName);
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
		        }// 프로필사진이 db에 저장되어있으면 수정
		        else {
		        	// 기존 프로필파일 로컬에서 삭제
		        	File file=new File(filePath+"/"+profile.getS_fileName());
		        	file.delete();
		        	// 새로운 프로필사진 로컬에 저장
		        	File newFileName = new File(filePath +"/"+ saveName);
			        try {
						uploadfile.transferTo(newFileName);
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
		        	System.out.println(mypageService.updateProfileImg(upfileVO));
		        }
		        
				return filePath+"/"+saveName;
			}
		} 
		  return "error";
	}
	
	//리뷰 리스트 화면
	@RequestMapping(value="/popup/profile_ReviewList")
	public String profile_ReviewList(Model model,@RequestParam Map<String,String> map,
	@ModelAttribute CommVO commVO,HttpServletRequest request,UserVO userVO) throws SQLException{
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if(flashMap!=null) {
			map = (Map<String, String>) flashMap.get("map");
			commVO.setP(Integer.parseInt(map.get("p")));
			commVO.setS(Integer.parseInt(map.get("s")));
			commVO.setB(Integer.parseInt(map.get("b")));
		}
		userVO = (UserVO) request.getSession().getAttribute("userVO");
		PagingVO<ReviewVO> pagingVO = null;
		pagingVO=mypageService.reviewPaging(commVO, userVO.getUser_idx());
		model.addAttribute("pv",pagingVO);
		return "/popup/profile_ReviewList";
	}

		@GetMapping(value="myMoim")
		public String MymoimGet() {
			return "redirect:/mypage";
		}
		
		@SuppressWarnings("unchecked")
		@PostMapping(value="myMoim")
		@ResponseBody
		public Map<String,?> myMoimPost(Model model,@RequestParam Map<String,String> map,
				@ModelAttribute CommVO commVO,HttpServletRequest request,UserVO userVO){
			System.out.println("--------------"+map + ": " +commVO);
			Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
			if(flashMap!=null) {
				map = (Map<String, String>) flashMap.get("map");
				commVO.setP(Integer.parseInt(map.get("p")));
				commVO.setS(Integer.parseInt(map.get("s")));
				commVO.setB(Integer.parseInt(map.get("b")));
			}
			
			
			
			userVO = (UserVO) request.getSession().getAttribute("userVO");
			String sortMenu=(String)map.get("sortMenu");
			PagingVO<MoimVO> pagingVO = null;
			Map<String,Object> resultMap = new HashMap<>(); // 결과 리턴 맵
			int user_idx=userVO.getUser_idx();
			List<Integer> list=mypageService.ApplyAgreeList(user_idx,sortMenu); // 신청테이블의 승인완료 인원수
			switch(sortMenu) {
				case "개설":
					pagingVO=mypageService.createMoimList(commVO, user_idx);
					break;
				case "신청":
					pagingVO=mypageService.applyMoimList(commVO, user_idx);
					break;
				case "참여중":
					pagingVO=mypageService.joinMoimList(commVO, user_idx);
					break;
				case "찜한모임":
					pagingVO=mypageService.likeMoimList(commVO, user_idx);
					break;
			}
			
			resultMap.put("user", userVO);
			resultMap.put("pv", pagingVO);
			resultMap.put("cv",commVO);
			resultMap.put("approve", list);
			resultMap.put("sortMenu", sortMenu);
			System.out.println(list);
			return resultMap;
		}
		@RequestMapping(value="apply")
		public String apply(Model model,HttpServletRequest request,UserVO userVO) throws SQLException {
			List<MoimVO> list = null;
			userVO = (UserVO) request.getSession().getAttribute("userVO");
			// 유저 아이디 세션값에서 가져오는 걸로 변경
			list = applyService.createMoimList(userVO.getUser_idx());
			model.addAttribute("moimList",list);
			return "applyMember";
		}
		
}
