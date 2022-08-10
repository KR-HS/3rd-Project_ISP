package kr.human.ISP.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@Data
@NoArgsConstructor
public class UpFileVO {
	public int file_idx;
	public int moim_idx;
	public int user_idx;
	public int board_idx;
	public String o_fileName;
	public String s_fileName;
	public String contentType; // 추가했음 파일 형식
}
