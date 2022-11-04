
 
 //모임글 삭제하기 테스트안해봄
 $(document).ready(function() {
     $('#moim_delete').click(function(event) {
         var moim_idx = $("#moim_idx").val();//넘겨받은 모임 글번호로 바꿔야함. 
         var obj={
 			"moim_idx" : moim_idx	
         };
         $.ajax("moim_delete",{
         	   type : "POST",             
               data : obj,
               success :function(){
             	  alert("삭제성공")
             	  location.href = '/';

               },
               error : function() {                    
                   alert('삭제할수 없습니다.');
                   location.reload(); // 실패시 새로고침하기
               }
        	})
         
     });
     
});


//찜하기
$(document).ready(function() {
        $('#like').click(function(event) {
            var moim_idx = $("#moim_idx").val();
            var obj={
    			"moim_idx" : moim_idx	
            };
            $.ajax("likeInsert",{
            	  type : "POST",             
                  data : obj,
                  success :function(){
                	  alert("찜성공")
                	  if($("#like_ok").css("display") =="none"){
                		  $("#like").hide();	 //버튼숨기기    
		    		      $("#like_ok").show();//찜완료 보이기
                	  }
                  },
                  error : function() {                    
                      alert('찜할 수 없습니다.');
                      location.reload(); // 실패시 새로고침하기
                  }
           	})
            
        }); 
});


//찜삭제
$(document).ready(function() {
        $('#like_ok').click(function(event) {
            var user_idx = 3//로그인되있는 정보로바꿔야함. 
            var obj1={
            	"user_idx" : user_idx    			
            };
            $.ajax("likeDelete",{
            	  type : "POST",             
                  data : obj1,
                  success :function(){
                	  alert("찜 취소 성공")
                	  if($("#like").css("display") =="none"){
                		  $("#like_ok").hide();	  
		    		      $("#like").show();
                	  }
                  },
                  error : function() {                    
                      alert('찜할 수 없습니다.');
                      location.reload(); // 실패시 새로고침하기
                  }
           	})
            
        }); 
});



//모임신청하기 
$(function () {
    //모달사용함. 
    $(document).on("click", "#signUp", function () {
        action_popup.confirm("해당모임에 참여할까요?", function (res) {
            if (res) {
                action_popup.alert("참여 신청완료");
                var signUp = {
                	    "moim_idx" : $('#moim_idx').val(),
                	    "user_idx" : $("#review_user_idx").val(),
                	    "signUp_isApply":'N'
                	};
                $.ajax("signUp_insert",{
              	  type : "POST",             
                    data : signUp,
                    success :function(){
                  	  alert("신청성공")
                    location.reload();
                    },
                    error : function() {                    
                        alert('신청불가!!!');
                        location.reload(); // 실패시 새로고침하기
                    }
             	})
            }
        })
    });

    $(document).on("click", "#signUp_ok", function () {
        action_popup.alert("이미 신청 되었습니다.");
    });

    $(".modal_close").on("click", function () {
        action_popup.close(this);
    });
});

//alert, confirm 대용 팝업 메소드 정의 
var action_popup = {
	//timer : 애니메이션 동작 속도 
    //timer: 500,
    //confirm : 확인창 
    confirm: function (txt, func) {
        if (txt == null || txt.trim() == "") {
        	//txt가 없으면 그냥 끝내기
            return;
        } else if (func == null || typeof func != 'function') {
        	//func가 없으면 그냥 끝내기
            return;
        } else {
        	//매개변수값이 모두 들어왔다면 실행하기 . 
            $(".type-confirm .btn_ok").on("click", function () {
            	//이벤트등록.bind() , 이벤트제거 .unbind()
                $(this).unbind("click");
                func(true);
                action_popup.close(this);
            });
            this.open("type-confirm", txt);
        }
    },
	//alert : 경고창 
    alert: function (txt) {
        if (txt == null || txt.trim() == "") {
            //경고창에 입력값 없으면 그냥 리턴시킴. 
            return;
        } else {
        	//입력값있으면 오픈
            this.open("type-alert", txt);
        }
    },
	//open : 팝업 열기
    open: function (type, txt) {
        var popup = $("." + type);
        //menu_msg <p>태그 클래스임. 
        popup.find(".menu_msg").text(txt);
        $("body").append("<div class='dimLayer'></div>");
        $(".dimLayer").css('height', $(document).height()).attr("target", type);
        popup.fadeIn(this.timer);
    },
	//close : 팝업 닫기
    close: function (target) {
        var modal = $(target).closest(".modal-section");
        var dimLayer;
        if (modal.hasClass("type-confirm")) {
            dimLayer = $(".dimLayer[target=type-confirm]");
            $(".type-confirm .btn_ok").unbind("click");
        } else if (modal.hasClass("type-alert")) {
            dimLayer = $(".dimLayer[target=type-alert]")
        } else {
            console.warn("close unknown target.")
            return;
        }
        modal.fadeOut(this.timer);
        setTimeout(function () {
            dimLayer != null ? dimLayer.remove() : "";
        }, this.timer);
    }
}




//댓글등록하기
$(document).ready(function() {
        $('#commentInsert').click(function(event) {
            var user_idx = $("#review_user_idx").val();		//로그인되있는 정보로바꿔야함.   
            var moim_idx = $("#moim_idx").val()*1; 		//넘겨받은 모임 글번호로 바꿔야함.  
            var review_content = $("#comment_content").val();
            var checkbox = document.getElementById('review_isPublic');
            var review_isPublic = checkbox.checked ? 'N' : 'Y' 		//공개Y,비공개N
			
          	
        	var obj={
            	"user_idx" : user_idx,
    			"moim_idx" : moim_idx,
    			"review_content" : review_content,
    			"review_isPublic" :review_isPublic
            };
        
        	if(review_content.trim().length>0){
	            $.ajax("commentInsert",{
	            	  type : "POST",             
	                  data : obj,
	                  success :function(){
	                	  alert("댓글등록완료");
	                	  $("#comment_content").val("")
	                	  $("#review_isPublic").prop("checked",false);
	              		  
	              		  $("#comment_List").load(location.href + " #comment_List");
	              		  location.reload();
	              		  $("#comment_List").focus();
	              		  	              		  
	                	
	                  },
	                  error : function() {                    
	                      alert('댓글등록실패 !!');
	                      location.reload(); // 실패시 새로고침하기
	                  }
	           	})
        	}else{
        		alert("공란입니다")
        	}
            
        }); 
});


//댓글삭제
	function delete_ok(e){
		var review_idx = $(e).val();
		alert("review_idx값은?:"+review_idx);
    	var obj={
        	"review_idx" : review_idx			
        };
    	//ajax
        $.ajax("commentDelete",{
        	  type : "POST",             
              data : obj,
              success :function(){
            	//댓글 뿌려주기 
            	  alert("댓글삭제완료") 
          		  $("#comment_List").load(location.href + " #comment_List");
            	
              },
              error : function() {                    
                  alert('댓글삭제 실패 !!');
                  //location.reload();  실패시 새로고침하기
              }
       	})      
    };
    
    
//댓글수정 
function update_ok(idx,content,isPublic){

		var review_idx =idx;
		var review_content =content;
		var review_isPublic = isPublic;
		alert(review_idx)
		alert(review_isPublic)
		alert(review_content)
		
		 $("#comment_content").val(review_content);
		 if($("#commentUpdate").css("display") =="none"){
     		  $("#commentInsert").hide();	 //등록버튼버튼숨기기    
 		      $("#commentUpdate").show();//수정버튼 보이기
     	  }
		 //수정버튼클릭시 ajax 실행
		 $('#commentUpdate').click(function(event) {
				 review_content=$("#comment_content").val();
	        	var obj={
	            	"review_content" : review_content,
	    			"review_idx" : review_idx,
	    			"review_isPublic" : review_isPublic
	            };
	        	if(review_content.trim().length>0){
	            $.ajax("commentUpdate",{
	            	  type : "POST",             
	                  data : obj,
	                  success :function(){
	                	  alert("댓글수정완료");
	                	  $("#comment_content").val("")
	                	  if($("#commentInsert").css("display") =="none"){
	                 		  $("#commentUpdate").hide();	 //등록버튼버튼숨기기    
	             		      $("#commentInsert").show();//수정버튼 보이기
	                 	  }
	              		  $("#comment_List").load(location.href + " #comment_List");
	                      location.reload(); 
	                	
	                  },
	                  error : function() {                    
	                      alert('댓글수정실패 !!');
	                      location.reload(); // 실패시 새로고침하기
	                  }
	           	})
	        	}else{
	        		alert("공란입니다")
	        	}
	            
	        }); 
		 
   
    };
    
    
//지도찍기
$(function(){
	//마커위치를 담을 변수임. 
	
	var mar =$("#addr2_Save").val().replace('(','');
	var mar2 = mar.replace(')','');
	var mar3 = mar2.split(',');
	
	
	var markerPosition  = new kakao.maps.LatLng(mar3[0]*1,mar3[1]*1); 
	
	//이미지 지도에 표시할 마커입니다
	//이미지 지도에 표시할 마커는 Object 형태입니다
	var marker = {
	position: markerPosition
	};
	
	var staticMapContainer  = document.getElementById('moim_info3'), // 이미지 지도를 표시할 div  
	staticMapOption = { 
	   center: new kakao.maps.LatLng(mar3[0]*1,mar3[1]*1), // 이미지 지도의 중심좌표
	   level: 2, // 이미지 지도의 확대 레벨
	   marker: marker, // 이미지 지도에 표시할 마커
	  
	};    

	//이미지 지도를 생성합니다
	var staticMap = new kakao.maps.StaticMap(staticMapContainer, staticMapOption);
	
})



$(function(){
	CKEDITOR.replace('content', {
	    // 이것을 써줘야 로컬파일 업로드가 가능하다.
	    toolbar: [],
	    filebrowserUploadUrl : '${pageContext.request.contextPath }/freeBoard/fileupload.jsp',
	    height : 290
	    //removePlugins: 'contextmenu,tabletools' // context menu disable
	 });
	CKEDITOR.editorConfig = function( config ) {
		   // Define changes to default configuration here. For example:
		   config.toolbarStartupExpanded = false; // 툴바 접기
		};
})
