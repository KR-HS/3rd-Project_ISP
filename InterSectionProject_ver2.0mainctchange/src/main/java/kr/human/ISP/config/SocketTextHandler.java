package kr.human.ISP.config;

import java.io.IOException;

import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class SocketTextHandler extends TextWebSocketHandler {

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message)
			throws InterruptedException, IOException {

		String payload = message.getPayload();
		JSONObject jsonObject = new JSONObject(payload);
		String str = jsonObject.get("user").toString();
		if(str.contains("회원") || str.contains("멤버")){
			if(str.contains("가입") || str.contains("등록")) {
				if(str.contains("조건")) {
					session.sendMessage(new TextMessage("이름과 이메일,전화번호 입력만으로 간편하게 가입하실수 있습니다."));	
				}else {
					session.sendMessage(new TextMessage("회원가입은 우측상단 메뉴바 클릭하시면 버튼이 보입니다."));
				}
			}else if(str.contains("수정")){
				session.sendMessage(new TextMessage("회원정보 수정은 마이페이지->회원정보수정에서 수정하실수 있습니다."));	
			}else if(str.contains("이메일")) {
				if(str.contains("인증")) {
					session.sendMessage(new TextMessage("회원가입시 이메일 인증 절차가 진행되기때문에 필수로 진행하셔야합니다."));	
				}else {
					session.sendMessage(new TextMessage("저는 멍청이라 이런말은 몰라요"));	
				}
			}else {
				session.sendMessage(new TextMessage("준비되어있는 답변키워드가 아닙니다. 더 많은 키워드를 입력해주세요. 예시 키워드 : 회원, 멤버, 가입, 등록, 참여, 수정, 비밀번호, 탈퇴"));	
			}
		}else if(str.contains("모임")){
			if(str.contains("참여") || str.contains("신청")) {
				session.sendMessage(new TextMessage("로그인 후에 모임에서 신청가능합니다."));	
			}else {
				session.sendMessage(new TextMessage("준비되어있는 답변키워드가 아닙니다. 더 많은 키워드를 입력해주세요. 예시 키워드 : 회원, 멤버, 가입, 등록, 참여, 수정, 비밀번호, 탈퇴"));	
			}
		}else if(str.contains("탈퇴")||str.contains("삭제")) {
			if(str.contains("회원")||str.contains("모임")) {
				session.sendMessage(new TextMessage("안됩니다. 놓아주지 않을거에요."));	
			}else if(str.contains("모임")) {
				session.sendMessage(new TextMessage("모임시간이 지난 후 자동으로 삭제가 진행됩니다. 그럼에도 불구하고 삭제하시려면 내 모임 보기->삭제버튼 클릭 하시면 됩니다."));
			}else {
				session.sendMessage(new TextMessage("저는 멍청이라 이런말은 몰라요"));	
			}
		}else if(str.contains("비밀번호")) {
			if(str.contains("변경")) {
				session.sendMessage(new TextMessage("마이페이지에서 변경가능합니다."));	
			}else if(str.contains("분실")||str.contains("찾기")||str.contains("잃어")||str.contains("잊어")||str.contains("까먹")) {
				session.sendMessage(new TextMessage("비밀번호를 분실하신 경우 비밀번호 찾기를 통해 비밀번호를 재설정하실 수 있습니다."));	
			}else {
				session.sendMessage(new TextMessage("저는 멍청이라 이런말은 몰라요"));	
			}
		}else if(str.contains("아이디")||str.contains("이메일")||str.contains("계정")) {
			if(str.contains("찾기")||str.contains("잃어")||str.contains("잊어")||str.contains("까먹")||str.contains("분실")) {
				session.sendMessage(new TextMessage("아이디(이메일)을 분실하신 경우 '아이디찾기'에서  이름과 휴대전화를 통해 찾으실수 있습니다."));	
			}else if(str.contains("인증")) {
				session.sendMessage(new TextMessage("회원가입시 이메일 인증 절차가 진행되기때문에 필수로 진행하셔야합니다."));	
			}else {
				session.sendMessage(new TextMessage("저는 멍청이라 이런말은 몰라요"));	
			}
		}else if(str.contains("무엇")||str.contains("뭔")) {
			if(str.contains("마이페이지")||str.contains("내 정보")) {
				session.sendMessage(new TextMessage("마이페이지는 ISP 회원에게 생성되는 고유 페이지입니다. 마이페이지에서는 회원정보 수정이나, 모임,개설,신청,댓글 작성등 활동내역을 확인할수 있습니다."));	
			}else if(str.contains("찜")) {
				session.sendMessage(new TextMessage("찜은 관심있는 모임을 표시하는 기능입니다. 로그인 후 모임페이지의 ‘♡’ 버튼을 클릭하여 원하는 모임을 찜할 수 있으며, 찜한 모임은 '마이페이지 → 관심내역 → 찜한 모임'에서 확인할 수 있습니다."));	
			}else {
				session.sendMessage(new TextMessage("저는 멍청이라 이런말은 몰라요"));	
			}
		}else if(str.contains("프로필")) {
			if(str.contains("사진")) {
				session.sendMessage(new TextMessage("프로필 사진의 등록,수정,삭제는 마이페이지에서 가능합니다."));	
			}else {
				session.sendMessage(new TextMessage("저는 멍청이라 이런말은 몰라요"));	
			}
		}
	}

}