package kr.human.ISP.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
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
public class MainController {

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
	
	@RequestMapping(value = { "/", "/index" })
	public String index(Model model) {
		LocalDateTime today = LocalDateTime.now();
		Calendar cal  = Calendar.getInstance();
		int date = cal.get(Calendar.DATE);
		int dayofWeek = cal.get(Calendar.DAY_OF_WEEK)-1;
		int dayofMonth= cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		String[] korDayOfWeek={"일","월","화","수","목","금","토"};
		
		model.addAttribute("today", today.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
		model.addAttribute("today_date",date);
		model.addAttribute("today_dayList",korDayOfWeek);
		model.addAttribute("today_day",dayofWeek);
		model.addAttribute("Month_date",dayofMonth);
		log.info("서버 절대 경로 : {}", filePath);
		
		
		return "index";
	}
	
	@RequestMapping(value="mypage")
	public String mypage(Model model) throws SQLException {
		log.info("서버 절대 경로 : {}", filePath);
		
		Map<String,Integer> countMap = new HashMap<>();
		Map<String,Object> listMap = new HashMap<>(); 
		
		UpFileVO profileImg=mypageService.getProfile(1);
		UserVO userVO = mypageService.selectByIdx(1);
		
		int createcount = mypageService.createCount(1);
		int signupCount = mypageService.signupCount(1);
		int reviewCount = mypageService.reviewCount(1);
		
		countMap.put("createcount",createcount );
		countMap.put("signupCount",signupCount );
		countMap.put("reviewCount",reviewCount );

		
		List<String> nameList = mypageService.signUpList(1);
		List<String> categoryList = mypageService.categoryList(1);
		List<ReviewVO> reviewList = mypageService.reviewList(1);
		
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
	public String u_ImagePost(@RequestParam("profileImg") MultipartFile uploadfile) {
		if(uploadfile!=null) {
			log.info("서버 절대 경로 : {}", filePath);
			
			long sizeInBytes=uploadfile.getSize();
			if(sizeInBytes>0){ // 파일크기가 있을때만
				String oriName = uploadfile.getOriginalFilename();
		        String saveName = UUID.randomUUID().toString() + "_" + oriName ;  // 겹치지않는 ID를 만들어준다. 
		        
       
				UpFileVO upfileVO = new UpFileVO();
				upfileVO.setUser_idx(1);
				upfileVO.setO_fileName(oriName);
				upfileVO.setS_fileName(saveName);
				UpFileVO profile=mypageService.getProfile(1);
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
			@ModelAttribute CommVO commVO,HttpServletRequest request) throws SQLException{
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if(flashMap!=null) {
			map = (Map<String, String>) flashMap.get("map");
			commVO.setP(Integer.parseInt(map.get("p")));
			commVO.setS(Integer.parseInt(map.get("s")));
			commVO.setB(Integer.parseInt(map.get("b")));
		}
		List<ReviewVO> reviewList = mypageService.reviewList(1);
		PagingVO<ReviewVO> pagingVO = null;
		pagingVO=mypageService.reviewPaging(commVO, 1);
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
			@ModelAttribute CommVO commVO,HttpServletRequest request){
		System.out.println("--------------"+map + ": " +commVO);
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if(flashMap!=null) {
			map = (Map<String, String>) flashMap.get("map");
			commVO.setP(Integer.parseInt(map.get("p")));
			commVO.setS(Integer.parseInt(map.get("s")));
			commVO.setB(Integer.parseInt(map.get("b")));
		}
		
		
		
		UserVO userVO=null;
		String sortMenu=(String)map.get("sortMenu");
		PagingVO<MoimVO> pagingVO = null;
		Map<String,Object> resultMap = new HashMap<>(); // 결과 리턴 맵
		int user_idx=Integer.parseInt((String)map.get("user_idx"));
		List<Integer> list=mypageService.ApplyAgreeList(user_idx,sortMenu); // 신청테이블의 승인완료 인원수
		switch(sortMenu) {
			case "개설":
				userVO=userService.selectByIdx(user_idx);
				pagingVO=mypageService.createMoimList(commVO, user_idx);
				System.out.println(pagingVO);
				break;
			case "신청":
				userVO=userService.selectByIdx(user_idx);
				pagingVO=mypageService.applyMoimList(commVO, user_idx);
				break;
			case "참여중":
				userVO=userService.selectByIdx(user_idx);
				pagingVO=mypageService.joinMoimList(commVO, user_idx);
				break;
			case "찜한모임":
				userVO=userService.selectByIdx(user_idx);
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
	public String apply(Model model) throws SQLException {
		List<MoimVO> list = null;
		// 유저 아이디 세션값에서 가져오는 걸로 변경
		list = applyService.createMoimList(1);
		model.addAttribute("moimList",list);
		return "applyMember";
	}
	
	@RequestMapping(value="admin")
	public String admin(Model model) throws SQLException{
		return "admin";
	}
	@RequestMapping(value="admin2")
	public String admin2(Model model) throws SQLException{
		return "admin2";
	}
	@RequestMapping(value="admin3")
	public String admin3(Model model) throws SQLException{
		return "admin3";
	}
	@RequestMapping(value="admin4")
	public String admin4(Model model) throws SQLException{
		return "admin4";
	}
	@RequestMapping(value="admin5")
	public String admin5(Model model) throws SQLException{
		return "admin5";
	}
	@RequestMapping(value="admin5view")
	public String admin5_view(Model model) throws SQLException{
		return "admin5view";
	}
	@RequestMapping(value = "/decorators/deco.html")
	   public String deco() {
	      return "decorators/deco";
	}
	
	
}
