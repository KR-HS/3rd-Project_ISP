package kr.human.ISP.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoardCommentVO {
	public int comment_idx;
	public int board_idx;
	public int user_idx;
	public Date comment_regdate;
	public String comment_content;
}
