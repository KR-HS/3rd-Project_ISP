

	$(function(){	
		$('.firstItem').trigger('click'); // 트리거 다시 선택해져있게 만들어야함. 
	});

	$(window).resize(function(){
		
	})
	function sendPost(urlPage,params){
		var moim_idx = $("#moim_select option:selected").val();
		var obj={
				"moim_idx" : moim_idx,
		};
	    for (var key in params) {	// key, value로 이루어진 객체 params
	       obj[key]=params[key]+"";
	    }
	    $.ajax({
			url : "userList",
			type : "POST",  
			data : obj,
			async: false,
			success : function(res){
				$(".underSection").empty();		
				$(".pagingVO").empty();
				if(res["pv"].totalCount!=0){
					var userList=res['pv'].list;
					var pv = res['pv'];
					var cv=res["cv"];
					if(userList.length>0){
						for(vo of userList){
							$(".underSection").append(function(){
								var user = "<div class='item'>";
								user += "<div class='user_idx'>"+vo.user_idx+"</div>"
								user += "<div class='item_name'>"
								user += "<span class='user_name'>"+vo.user_name+"</span>"
								user += "<span class='user_detail'><a href='#' onclick='window.open(\"/\",\"유저상세보기\", \"width=500,height=250,fullscreen=no,location=no,toolbar=no,menubar=no,scrollbars=no,resizable=no,status=no\");return false;'>상세보기</a></span>"
								user += "</div>"
								user += "<div class='item_detail_wrap'>"
								user += "<div class='user_email'>이메일"
								user += "<span class='item_detail user_email_input'>"+vo.user_id+"</span>"
								user += "</div>"
								user += "<div class='user_phone'>전화번호"
								user += "<span class='item_detail user_email_input'>"+vo.user_phone+"</span>"
								user += "</div>"
								user += "</div>"
								user += "<div class='item_button_wrap'>"
								user += "<div class='agree_button' onclick='apply(this)' >승인수락</div>"
								user += "<div class='disagree_button' onclick='apply(this)'>승인거절</div>"
								user += "</div>"
								return user;
							})
						}
					}else{
						$(".underSection").append("<div class='user'>신청인원이 없습니다.</div>");
					}
					$(".pagingVO").append(pv.pageList);
				}
			},
			error : function(err){
				alert("실행 실패");
			}
		});
	}
	
	
	function clickMoim(e){
		var clickMoim_idx=e;
		var obj = {
				"moim_idx" : clickMoim_idx,
				"s" : 5,
				"b" : 5
		};
		$.ajax({
			url : "userList",
			type : "POST",  
			data : obj,
			async: false,
			success : function(res){
				$(".underSection").empty();	
				$(".pagingVO").empty();
				if(res["pv"].totalCount!=0){
					var userList=res['pv'].list;
					var signUpList=res['signUpList'].list;
					var pv = res['pv'];
					var cv=res["cv"];
					if(userList.length>0){
						for(vo of userList){
							$(".underSection").append(function(){
								var user = "<div class='item'>";
								user += "<div class='user_idx'>"+vo.user_idx+"</div>"
								user += "<div class='item_name'>"
								user += "<span class='user_name'>"+vo.user_name+"</span>"
								user += "<span class='user_detail'><a href='#' onclick='window.open(\"userDetail?user_idx="+vo.user_idx+"\",\"유저상세보기\", \);return false;'>상세보기</a></span>"
								user += "</div>"
								user += "<div class='item_detail_wrap'>"
								user += "<div class='user_email'>📧"
								user += "<span class='item_detail user_email_input'>"+vo.user_id+"</span>"
								user += "</div>"
								user += "<div class='user_phone'>📞"
								user += "<span class='item_detail user_email_input'>"+vo.user_phone+"</span>"
								user += "</div>"
								user += "</div>"
								user += "<div class='item_button_wrap'>"
								user += "<div class='agree_button' onclick='apply(this)' >승인수락</div>"
								user += "<div class='disagree_button' onclick='apply(this)'>승인거절</div>"
								user += "</div>"
								return user;
							})
						}
					}else{
						$(".underSection").append("<div class='user'>신청인원이 없습니다.</div>");
					}
					$(".pagingVO").append(pv.pageList);
				}
			},
			error : function(err){
				alert("실행 실패");
			}
		});
	}
	
	function apply(e){
		var isApply = $(e).text();
		var moim_idx = $("#moim_select option:selected").val();
		var user_idx = $(e).parent(".item_button_wrap").siblings(".user_idx").text();
		var obj = {
				"isApply" : isApply,
				"moim_idx" : moim_idx+"",
				"user_idx" : user_idx+""
		}
		$.ajax({
			url : "isApply",
			type : "POST",  
			data : obj,
			async: false,
			success : function(res){
				alert(res["apply"])
				if(res["apply"]=="승인완료"){
					$(e).text("승인완료");
					$(e).removeAttr("onclick");
					$(e).css("cursor","none");
					$(e).siblings(".disagree_button").hide(); // 눌렀을때 변경되게 해야하는거 못하겠음.
				}else{
					$(e).text("거절된 승인");
					$(e).removeAttr("onclick");
					$(e).css("cursor","none");
					$(e).siblings(".agree_button").hide();
				}
				//location.reload();
			},
			error : function(){
				alert("승인 작동 실패")
			}
		});
	}