package kr.human.ISP.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthorityVO {	
	public int auth_idx;
	public int user_idx;
	public String role;
}
