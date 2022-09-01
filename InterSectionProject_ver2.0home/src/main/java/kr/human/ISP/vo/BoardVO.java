package kr.human.ISP.vo;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoardVO {
	public int board_idx;
	public int user_idx;
	public String board_subject;
	public String board_content;
	public Date board_regdate;
	public String board_category;
	public String board_isPublic;
	public String board_isDelete;
	
	private List<UpFileVO> fileList;
	
	
	private int commentCount;	
	private List<BoardCommentVO> commentList;	
}
