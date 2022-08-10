package kr.human.ISP.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserVO {
	public int user_idx;
	public String user_id;
	public String user_pwd;
	public String user_name;
	public Date user_birth;
	public String user_gender;
	public String user_phone;
	public String user_use;
	public String user_UUID;
	public String user_isPublic;
	public String user_isDeleted;
}
