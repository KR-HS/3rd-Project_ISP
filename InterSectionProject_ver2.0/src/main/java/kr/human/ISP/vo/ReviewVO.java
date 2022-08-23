package kr.human.ISP.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ReviewVO {
	public int review_dix;
	public int user_idx;
	public int moim_idx;
	public Date review_regdate;
	public String review_content;
	public String review_isPublic;
	public String review_isDelete;
	
	//조인하고 받아와야해서 만듬
	public String user_name;

}
