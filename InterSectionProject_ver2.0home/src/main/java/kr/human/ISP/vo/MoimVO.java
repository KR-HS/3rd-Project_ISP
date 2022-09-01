package kr.human.ISP.vo;

import java.util.Date;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MoimVO {
	public int moim_idx;
	public int user_idx;
	public String moim_name;
	public String moim_content;
	public Date moim_regdate; // 작성일시
	public String moim_time; // 만남시간  String or Date 문제
	public String moim_onoff;
	public String moim_postCode;
	public String moim_addr1;
	public String moim_addr2;
	public int moim_startAge; // 나이제한 
	public int moim_limitAge; // 나이제한
	public int moim_personnel; // 정원(인원수제한)
	public int moim_cost;
	public String moim_isDelete;
	public String moim_isEnd;
	private Date moim_deadline;		// 동원 추가
	
	private String lc_name;			// 동원 추가
	private String sc_name;			// 동원 추가
	
	public UpFileVO file;
	
	private int reviewCount;	
	private List<ReviewVO> reviewList;	
	
}
