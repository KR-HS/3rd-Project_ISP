package kr.human.ISP.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SignUpVO {
	public int signUp_idx;
	public int moim_idx;
	public int user_idx;
	public String signUp_isApply;
}
