package kr.human.ISP.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MailSendVO {
	public int mail_send_idx;
	public int board_idx;
	public int user_idx;
}
