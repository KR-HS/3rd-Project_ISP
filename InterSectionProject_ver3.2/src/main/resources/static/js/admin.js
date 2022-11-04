
	$(function(){	
		$('.HomeMenu').trigger('click');
	})
	
	//커서 위치 설정 함수
	$.fn.setCursorPosition = function( pos )
	{
	  this.each( function( index, elem ) {
	    if( elem.setSelectionRange ) {
	      elem.setSelectionRange(pos, pos);
	    } else if( elem.createTextRange ) {
	      var range = elem.createTextRange();
	      range.collapse(true);
	      range.moveEnd('character', pos);
	      range.moveStart('character', pos);
	      range.select();
	    }
	  });
	  
	  return this;
	};
	function selectMenu(e){
		$(".MainMenu").css("background","lightgray");
		$(e).css("background","white");
		
		var obj = {
				"page" : e.id
		}
		$.ajax({
			url : "HomeMenu",
			type : "POST",  
			data : obj,
			async: false,
			success : function(res){
				$(".mainContent").empty();
				$(".pagingVO").empty();
				if(res!=null){
					if(res["page"]=="17"){
						$(".mainBannerContent").text("관리자 메인");
						var userList = res["fiveUserList"];
						var moimList = res["fiveMoimList"];
						if(userList.length>0){
							
							$(".mainContent").append(function(){
								var userth ="<p class='recentJoin'>신규 회원가입 목록 (최신 5건)</p>"
								userth += "<div class='RecentUserTableWrap'><table class='RecentTable RecentJoinTable'>"
								userth += "<tr class='recentUserType'>"
								userth += "<th class='recentUserId'>유저 아이디</th>"
								userth += "<th class='recentUserName'>유저 이름</th>"
								userth += "<th class='recentUserBirth'>유저 생년월일</th>"
								userth += "<th class='recentUserGender'>유저 성별</th>"
								userth += "<th class='recentUserPhone'>유저 번호</th>"
								userth += "<th class='recentUseruse'>유저 인증여부</th>"
								userth += "<th class='recentUserIsdeleted'>유저 삭제여부</th></tr></table></div>"
								return userth;
							});
							
							for(vo of userList){
								$(".RecentJoinTable").append(function(){
									var user = "<tr class='recentUserInput'>";
									user += "<td class='recentUserIdInput'>"+vo.user_id+"</td>"
									user += "<td class='recentUserNameInput'>"+vo.user_name+"</td>"
									user += "<td class='recentUserBirthInput'>"+vo.user_birth+"</td>"
									user += "<td class='recentUserGenderInput'>"+vo.user_gender+"</td>"
									user += "<td class='recentUserPhoneInput'>"+vo.user_phone+"</td>"
									user += "<td class='recentUseruseInput'>"+vo.user_use+"</td>"
									user += "<td class='recentUserIsdeletedInput'>"+vo.user_isDeleted+"</td></tr>"
									return user;
								})
							}
						}
						if(moimList.length>0){
							
							$(".mainContent").append(function(){
								var moimth = "<p class='recentMoim'>최근 모임개설 목록 (최신 5건)</p>"
								moimth += "<div class='RecentMoimTableWrap'><table class='RecentTable RecentMoimTable'>"
								moimth += "<tr class='recentMoimType'>"
								moimth += "<th class='recentMoimName'>모임 이름</th>"
								moimth += "<th class='recentMoimCategory'>모임 카테고리</th>"
								moimth += "<th class='recentMoimTime'>모임 일자</th>"
								moimth += "<th class='recentMoimAddr'>모임 주소</th>"
								moimth += "<th class='recentMoimAge'>모임 나이제한</th>"
								moimth += "<th class='recentMoimPersonnel'>모임 정원</th>"
								moimth += "<th class='recentMoimIsdelete'>모임 삭제여부</th>"
								moimth += "<th class='recentMoimIsend'>모임 종료여부</th></tr></table></div>"
								return moimth;
							});
							
							for(vo of moimList){
								$(".RecentMoimTable").append(function(){
									var moim = "<tr class='recentMoimInput'>";
									moim += "<td class='recentMoimNameInput'>"+vo.moim_name+"</td>"
									moim += "<td class='recentMoimCategoryInput'>"+vo.moim_category+"</td>"
									moim += "<td class='recentMoimTimeInput'>"+vo.moim_time+"</td>"
									moim += "<td class='recentMoimAddrInput'>"+vo.moim_addr+"</td>"
									moim += "<td class='recentMoimAgeInput'>"+vo.moim_age+"</td>"
									moim += "<td class='recentMoimPersonnelInput'>"+vo.moim_personnel+"</td>"
									moim += "<td class='recentMoimIsdeleteInput'>"+vo.moim_isDelete+"</td>"
									moim += "<td class='recentMoimIsendInput'>"+vo.moim_isEnd+"</td></tr>"
									return moim;
								})
							}
						}
					}
					if(res["page"]=="18"){
						$(".mainBannerContent").text("회원관리");
						var userList=res['pv'].list;
						var pv = res['pv'];
						var totalCount = res['totalCount'];
						var countDeleteUser = res['countDeleteUser']
						if(userList.length>0){
							$(".mainContent").append(function(){
								var header = "<div class='userCountBtnWrap'>"
								header += "<div class='TotalContent'>전체목록</div>"
								header += "<div class='countBtn TotalCount'>"
								header += "<div class='countBtnDummy'>전체목록</div>"
								header += "<div class='countBtnInput TotalCountInput'>"+totalCount+"명</div></div>"
								header += "<div class='countBtn Resign'>"
								header += "<span class='countBtnDummy'>탈퇴</span>"
								header += "<span class='countBtnInput ResignInput'>"+countDeleteUser+"명</span></div></div>"
								header += "<div class='searchBtnWrap'>"
								header += "<form><input type='text' class='searchUserInput' placeholder='회원아이디 검색'>"
								header += "<i class='fa fa-search fa-1x searchBtn' aria-hidden='true' onclick='searchUser();'></i></form></div>"
								header += "<div class='UserTableWrap'>"
								header += "<form onsubmit='return false;'>"
								header += "<table class='userTable'>"
								header += "<tr class='topRow'>"
								header += "<th class='userId'>유저 아이디</th>"
								header += "<th class='userName'>유저 이름</th>"
								header += "<th class='userBirth'>유저 생일</th>"
								header += "<th class='userGender'>유저 성별</th>"
								header += "<th class='userPhone'>유저 번호</th>"
								header += "<th class='useruse'>유저 인증여부</th>"
								header += "<th class='userIsdeleted'>유저 삭제여부</th>"
								header += "<th class='userManage'>관리</th></tr>"
								return header;
							})
							
							
							for(vo of userList){
								$(".userTable").append(function(){
									var user = "<tr class='userInput'>";
									user += "<td class='userIdInput'><input type='text' value='"+vo.user_id+"' disabled></td>"
									user += "<td class='userNameInput'><input type='text' value='"+vo.user_name+"' disabled></td>"
									user += "<td class='userBirthInput'><input type='text' value='"+vo.user_birth+"' disabled></td>"
									user += "<td class='userGenderInput'><input type='text' value='"+vo.user_gender+"' disabled></td>"
									user += "<td class='userPhoneInput'><input type='text' value='"+vo.user_phone+"' disabled></td>"
									user += "<td class='useruseInput'><input type='text' value='"+vo.user_use+"' disabled></td>"
									user += "<td class='userIsdeletedInput'><input type='text' value='"+vo.user_isDeleted+"' disabled></td>"
									user += "<td class='userManageInput'><button class='userManageBtn' onclick='userFormChange(this);'>변경</button></td></tr>"
									return user;
								})
							}
							$(".mainContent").append(function(){
								var footer = "</table></form></div>"
							})
						}
						$(".mainContent").append(function(){
							var paging = "<div class='pagingVO'></div>";
							return paging;
						})
						$(".pagingVO").append(pv.pageList);
					}
					if(res["page"]=="19"){
						$(".mainBannerContent").text("모임관리");
						var moimList=res['pv'].list;
						var pv = res['pv'];
						var totalCount = res['totalCount'];
						if(moimList.length>0){
							$(".mainContent").append(function(){
								var header = "<div class='moimCountBtnWrap'>"
								header += "<div class='TotalContent'>전체목록</div>"
								header += "<div class='moimTotalCount'>"
								header += "<div class='countBtnDummy'>생성 모임수</div>"
								header += "<div class='countBtnInput TotalCountInput'>"+totalCount+"개</div></div></div>"
								header += "<div class='searchBtnWrap'>"
								header += "<form><input type='text' class='searchMoimInput' placeholder='모임이름 검색'>"
								header += "<i class='fa fa-search fa-1x searchBtn' aria-hidden='true' onclick='searchMoim();'></i></form></div>"
								header += "<div class='MoimTableWrap'>"
								header += "<form onsubmit='return false;'>"
								header += "<table class='MoimTable'>"
								header += "<tr class='moimType'>"
								header += "<th class='moimName'>모임 이름</th>"
								header += "<th class='moimCategory'>모임 카테고리</th>"
								header += "<th class='moimTime'>모임 일자</th>"
								header += "<th class='moimAddr'>모임 주소</th>"
								header += "<th class='moimAge'>모임 나이제한</th>"
								header += "<th class='moimPersonnel'>모임 정원</th>"
								header += "<th class='moimIsdelete'>모임 삭제여부</th>"
								header += "<th class='moimIsend'>모임 종료여부</th>"
								header += "<th class='MoimManage'>관리</th></tr>"
								return header;
							})
							
							for(vo of moimList){
								$(".MoimTable").append(function(){
									var moim = "<tr class='moimInput'>";
									moim += "<td class='moimNameInput'><input type='text' value='"+vo.moim_name+"' disabled></td>"
									moim += "<td class='moimCategoryInput'><input type='text' value='"+vo.moim_category+"' disabled></td>"
									moim += "<td class='moimTimeInput'><input type='text' value='"+vo.moim_time+"' disabled></td>"
									moim += "<td class='moimAddrInput'><input type='text' value='"+vo.moim_addr+"' disabled></td>"
									moim += "<td class='moimAgeInput'><input type='text' value='"+vo.moim_age+"' disabled></td>"
									moim += "<td class='moimPersonnelInput'><input type='text' value='"+vo.moim_personnel+"' disabled></td>"
									moim += "<td class='moimIsdeleteInput'><input type='text' value='"+vo.moim_isDelete+"' disabled></td>"
									moim += "<td class='moimIsendInput'><input type='text' value='"+vo.moim_isEnd+"' disabled></td>"
									moim += "<td class='MoimManageInput'><button class='moimManageBtn' onclick='moimFormChange(this);'>변경</button></td></tr>"
									return moim;
								})
							}
							
							$(".mainContent").append(function(){
								var footer = "</table></form></div>"
							})
						}
						
						$(".mainContent").append(function(){
							var paging = "<div class='pagingVO'></div>";
							return paging;
						})
						$(".pagingVO").append(pv.pageList);
					}
				}
			},
			error : function(err){
				alert("실행 실패");
			}
		});
	}
	
	function userFormChange(e){
		$(e).parent().siblings().children('input').css("color","blue");
		$(e).parent().siblings().children('input').attr("disabled",false);
		$(e).parent().siblings('.userIdInput').children('input').focus().setCursorPosition($(e).parent().siblings('.userIdInput').children('input').val().length);
		
		$(e).removeAttr("onclick");
		$(e).attr("onclick","userManage(this);");
		$(e).text("수정");
	}
	
	function userManage(e){
		$(e).parent().siblings().children('input').css("color","rgb(84, 84, 84)");
		$(e).parent().siblings().children('input').attr("disabled",true);
		$(e).parent().siblings('.userIdInput').children('input').blur();
		
		$(e).removeAttr("onclick");
		$(e).attr("onclick","userFormChange(this);");
		$(e).text("변경");
		
		/* Ajax 여기에 추가 */
		
	}
	

	function moimFormChange(e){
		$(e).parent().siblings().children('input').css("color","blue");
		$(e).parent().siblings().children('input').attr("disabled",false);
		$(e).parent().siblings('.moimNameInput').children('input').focus().setCursorPosition($(e).parent().siblings('.moimNameInput').children('input').val().length);
		
		$(e).removeAttr("onclick");
		$(e).attr("onclick","moimManage(this);");
		$(e).html("수정");
	}
	
	function moimManage(e){
		$(e).parent().siblings().children('input').css("color","rgb(84, 84, 84)");
		$(e).parent().siblings().children('input').attr("disabled",true);
		$(e).parent().siblings('.moimNameInput').children('input').blur();
		
		$(e).removeAttr("onclick");
		$(e).attr("onclick","moimFormChange(this);");
		$(e).html("변경");
		
		/* Ajax 여기에 추가 */
		
	}
	
	function searchUser(){
		var user_id = $(".searchUserInput").val();
		var category = "user"
		var obj = {
				"category" : category, 
				"user_id" : user_id
		}
		$.ajax({
			url : "search",
			type : "POST",  
			data : obj,
			async: false,
			success : function(res){
				var userVO = res["userVO"]
				$(".userInput").empty();
				$(".userTable").append(function(){
					var user = "<tr class='userInput'>";
					user += "<td class='userIdInput'><input type='text' value='"+userVO.user_id+"' disabled></td>"
					user += "<td class='userNameInput'><input type='text' value='"+userVO.user_name+"' disabled></td>"
					user += "<td class='userBirthInput'><input type='text' value='"+userVO.user_birth+"' disabled></td>"
					user += "<td class='userGenderInput'><input type='text' value='"+userVO.user_gender+"' disabled></td>"
					user += "<td class='userPhoneInput'><input type='text' value='"+userVO.user_phone+"' disabled></td>"
					user += "<td class='useruseInput'><input type='text' value='"+userVO.user_use+"' disabled></td>"
					user += "<td class='userIsdeletedInput'><input type='text' value='"+userVO.user_isDeleted+"' disabled></td>"
					user += "<td class='userManageInput'><button class='userManageBtn' onclick='userFormChange(this);'>변경</button></td></tr>"
					return user;
				})
			},
			error : function(err){
				alert('에러')
			}
		})
	}
	
	function searchMoim(){
		var moim_name = $(".searchMoimInput").val();
		var category = "moim"
		var obj = {
				"category" : category, 
				"moim_name" : moim_name
		}
		$.ajax({
			url : "search",
			type : "POST",  
			data : obj,
			async: false,
			success : function(res){
				var moimVO = res["moimVO"]
				$(".moimInput").empty();
				$(".MoimTable").append(function(){
					var moim = "<tr class='moimInput'>";
					moim += "<td class='moimNameInput'><input type='text' value='"+moimVO.moim_name+"' disabled></td>"
					moim += "<td class='moimCategoryInput'><input type='text' value='"+moimVO.moim_category+"' disabled></td>"
					moim += "<td class='moimTimeInput'><input type='text' value='"+moimVO.moim_time+"' disabled></td>"
					moim += "<td class='moimAddrInput'><input type='text' value='"+moimVO.moim_addr+"' disabled></td>"
					moim += "<td class='moimAgeInput'><input type='text' value='"+moimVO.moim_age+"' disabled></td>"
					moim += "<td class='moimPersonnelInput'><input type='text' value='"+moimVO.moim_personnel+"' disabled></td>"
					moim += "<td class='moimIsdeleteInput'><input type='text' value='"+moimVO.moim_isDelete+"' disabled></td>"
					moim += "<td class='moimIsendInput'><input type='text' value='"+moimVO.moim_isEnd+"' disabled></td>"
					moim += "<td class='MoimManageInput'><button class='moimManageBtn' onclick='moimFormChange(this);'>변경</button></td></tr>"
					return moim;
				})
			},
			error : function(err){
				alert('에러')
			}
		})
	}