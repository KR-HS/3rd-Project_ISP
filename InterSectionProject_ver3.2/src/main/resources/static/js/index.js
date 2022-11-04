$(function(){
		
		
		
		$('#slider-div').slick({
			slide: 'p',		//슬라이드 되어야 할 태그 ex) div, li 
			rows:1,
			infinite : false, 	//무한 반복 옵션	 
			slidesToShow : 7,		// 한 화면에 보여질 컨텐츠 개수
			slidesToScroll : 1,		//스크롤 한번에 움직일 컨텐츠 개수
			speed : 100,	 // 다음 버튼 누르고 다음 화면 뜨는데까지 걸리는 시간(ms)
			arrows : true, 		// 옆으로 이동하는 화살표 표시 여부
			dots : false, 		// 스크롤바 아래 점으로 페이지네이션 여부
			autoplay : false,			// 자동 스크롤 사용 여부
			autoplaySpeed : 10000, 		// 자동 스크롤 시 다음으로 넘어가는데 걸리는 시간 (ms)
			pauseOnHover : false,		// 슬라이드 이동	시 마우스 호버하면 슬라이더 멈추게 설정
			vertical : false,		// 세로 방향 슬라이드 옵션
			prevArrow : "<button type='button' class='slick-prev'>Previous</button>",		// 이전 화살표 모양 설정
			nextArrow : "<button type='button' class='slick-next'>Next</button>",		// 다음 화살표 모양 설정
			dotsClass : "slick-dots", 	//아래 나오는 페이지네이션(점) css class 지정
			draggable : true, 	//드래그 가능 여부 
			variableWidth: true,
			variableHeight: true,
			initialSlide:0

		});
		
		$(".slider-element-day").each(function(){
			var yoil = $(this).text();
			if(yoil=="일"){
				$(this).css("color","red");
				$(this).siblings().css("color","red");
			}
		})
		var width=parseInt(($("#slider-div").width()-35)/7)+"px";
		$(".slider-element").css("width",width);
		
		
		
		// 동원 필터 부분 
		$('.lc_filter').on('click', function(e){
			document.getElementById('sc_select').value = null;	
			$('.lc_filter').css("background-color", "").css("color", "");
			$('#'+e.target.id).css("background-color", "#015958").css("color", "white");
			
			if($('#lc_select').val() == $('#'+e.target.id).text()){
				$('.lc_filter').css("background-color", "").css("color", "");
				document.getElementById('lc_select').value = null;							
				$('#lc_select').focus();							
			}else{
				$('#lc_select').val($('#'+e.target.id).text());
				$('#lc_select').focus();							
			}
			
			
			var date = new Date();
			var year = date.getFullYear();
			var month = date.getMonth()+1;
			var day = $(e).children(".slider-element-date").text();
			
			var today=new Date(date.getYear()+1900,date.getMonth(),date.getDate(),date.getHours(),date.getMinutes(),date.getSeconds());
			
			if(day<date.getDate()){
				month=month+1;
				if(month>12){
					month=month%12;
					year=year+1;
				}
			}
			
			
			var area_name = $('#area_select').val();
			var lc_name = $('#lc_select').val();
			var sc_name = $('#sc_select').val();
			
			// alert(e.target.id);
			
				// alert();
				var moimDate = $('#moim_select').val();
				$.ajax("moimOfDate/ffff",{
					type:"GET",
					data:{
						"moimDate" : moimDate,
						"area_name" : area_name,
						"lc_name" : lc_name,
						"sc_name" : sc_name
					},
					success : function(res){
						alert(res);
						//alert(//JSON.)   
						$('.moimContentList').empty();
			               var moimList=res["todayMoimList"];
			               var moimFounderList=res["todayMoimListFounder"];
			               var moimSignUpCountList=res["MoimSignUpAgreeList"];
			               var moimCategoryList=res["moimCategoryList"];
			               
			                     if(moimList.length<1){
			                        $('.moimContentList').append("<p class='moimEmpty'>오늘 모임이 없습니다.</p>");
			                     }
			                     
			               for(var i=0;i<moimList.length;i++){
			            	   
				               $('.moimContentList').append("<li class='moimContent moimContent"+i+"' onclick='sendMoim(this)'>");
				               $('.moimContent'+i+'').append("<input class='moimContentIdx' type='hidden' value="+moimList[i].moim_idx+">");
				               $('.moimContent'+i+'').append("<p class='moimSubject"+i+"'>");
				               $('.moimSubject'+i+'').append("<span class='moimSubjectInput'>"+moimList[i].moim_name+"</span>")
				               //$('.moimSubject'+i+'').append("<span class='moimCategory moimLC'>"+moimCategoryList[i].lc_name+"</span>")
				               $('.moimSubject'+i+'').append("<span class='moimCategory moimLC'>"+moimList[i].lc_name+"</span>")
				               $('.moimSubject'+i+'').append("<span class='moimCategoryArrow'>▶</span>")
				               //$('.moimSubject'+i+'').append("<span class='moimCategory moimSC'>"+moimCategoryList[i].sc_name+"</span>")
				               $('.moimSubject'+i+'').append("<span class='moimCategory moimSC'>"+moimList[i].sc_name+"</span>")
				               $('.moimContent'+i+'').append("</p>")
				               
				               /* $('.moimContent').append("<p class='moimInform moimCreator'>개설자");
				               $('.moimCreator').append("<span class='moimInformInput moimCreatorInput'>"+moimFounderList[i]+"</span>");
				               $('.moimContent').append("</p>"); */
				               
				               $('.moimContent'+i+'').append("<p class='moimInform moimPersonCount moimPersonCount"+i+"'>모임인원");
				               $('.moimPersonCount'+i+'').append("<span class='moimInformInput moimPersonCountInput moimPersonCountInput"+i+"'>"+moimSignUpCountList[i]+"명 / "+moimList[i].moim_personnel+"명</span>");
				               
				               
				               if(moimSignUpCountList[i]<moimList[i].moim_personnel){
				                  $('.moimPersonCountInput'+i+'').append("<span class='moimProceeding'>모집중</span>")
				               }
				               else{
				                  $('.moimPersonCountInput'+i+'').append("<span class='moimProceeding'>모집완료</span>")
				               }
				               
				               
			               $('.moimContent'+i+'').append("</p>");
			               
			               
			               $('.moimContent'+i+'').append("<p class='moimInform moimLocation moimLocation"+i+"'>모임장소");
			               $('.moimLocation'+i+'').append("<span class='moimInformInput moimLocationInput'>("+moimList[i].moim_postCode+") "+moimList[i].moim_addr1+"</span>");
			               $('.moimContent'+i+'').append("</p>");
			               
			               $('.moimContent'+i+'').append("<p class='moimInform moimTime moimTime"+i+"'>모임시간");
				          
			               var  mt = moimList[i].moim_time;
			               mt = mt.replace('T', ' ');
			               $('.moimTime'+i+'').append("<span class='moimInformInput moimTimeInput moimTimeInput"+i+"'>"+mt+"</span>");
						/*	              
			               var moimDateSum = moimList[i].moim_time.split(" ")[0];
			               var moimDate=moimDateSum.split("-");
			               var moimTimeSum = moimList[i].moim_time.split(" ")[1];
			               var moimTime=moimTimeSum.split(":");
			               */
			               var moimDateSum = mt.split(" ")[0];
			               var moimDate=moimDateSum.split("-");
			               var moimTimeSum = mt.split(" ")[1];
			               var moimTime=moimTimeSum.split(":");
			               
			               var moimTimeAll=new Date(Number(moimDate[0]),Number(moimDate[1])-1,Number(moimDate[2]),Number(moimTime[0]),Number(moimTime[1]),Number(moimTime[2]));
			               
			               if(today<moimTimeAll){
			                  $('.moimTime'+i+'').append("<span class='moimProceeding'>진행중</span>")
			               }
			               else{
			                  $('.moimTime'+i+'').append("<span class='moimProceeding'>종료</span>")
			               }
			               
			               
			               $('.moimContent'+i+'').append("</p>");
			               
			               $('.moimContentList'+i+'').append("</li>");
			               
			               
			              
			               }
			                   
			               $(".moimContent").each(function(){
			                  var moimApplyEnd = $(this).children(".moimPersonCountInput").children(".moimProceeding").text();
			                  var moimEnd = $(this).children(".moimTime").children(".moimProceeding").text();
			                  switch(moimApplyEnd){
			                     case "모집중":
			                        $(this).children(".moimPersonCountInput").children(".moimProceeding").css("background-color","#1ABC9C");
			                     break;
			                     case "모집완료":
			                        $(this).children(".moimPersonCountInput").children(".moimProceeding").css("background-color","#1570FF");
			                     break;
			                  }
			                  switch(moimEnd){
			                     case "종료":
			                        $(this).children(".moimTime").children(".moimProceeding").css("background-color","#A9A9A9");
			                     break;
			                     case "진행중":
			                        $(this).children(".moimTime").children(".moimProceeding").css("background-color","#1570FF");
			                     break;
			                  }
			               })
					},
					error : function(data){
						alert("에러"+ data);						
					}
					
				})  //ajax 끝 
				
				$('#sc').show();
				$.ajax("Category",{
					type:"GET",
					data:{
						"lc_name" : lc_name
					},
					success : function(data){
						// alert(typeof(data) + " : " + data );
						$("#sc").empty();
						for(index in data){
							// alert(data[index]);
							$("#sc").append("<button class='sc_filter' id='sc"+index+"' value='" + data[index] + "' onclick='sc_filter_click(this)'  >" + data[index] + "</button>");

						}
					},
					error : function(data){
						alert(data + "먼가 안됌");
						
					}
				})
			
			
			
		});	
		
		
		
	
		
		
		

	
			
		// 동원 필터 부분 종료		
		$(".date_sort_start").trigger('click');
	
		
		
		
		
		
	});		// <-- 윈도우 온로드
	
	
	
	
	function area_filter_click(e){
		
		var area_name = $(e).val();	
		$('.area_filter').css("background-color", "").css("color", "");
		$(e).css("background-color", "red").css("color", "white");
		//$('#area_select').val() = area_name;
		
		
		if($('#area_select').val() == $(e).val()){
			$('.area_filter').css("background-color", "").css("color", "");
			document.getElementById('area_select').value = null;							
			$('#area_select').focus();
			
		}else{
			$('#area_select').val($(e).val());
			$('#area_select').focus();							
		}
		
		area_name = $('#area_select').val();
		var lc_name = $('#lc_select').val();
		var sc_name = $('#sc_select').val();
		
		/*
		alert(area_name);
		alert(lc_name);
		alert(sc_name);
		*/
		
		var moimDate = $('#moim_select').val();
		
		
		
		var date = new Date();
		var year = date.getFullYear();
		var month = date.getMonth()+1;
		var day = $(e).children(".slider-element-date").text();
		
		var today=new Date(date.getYear()+1900,date.getMonth(),date.getDate(),date.getHours(),date.getMinutes(),date.getSeconds());
		
		if(day<date.getDate()){
			month=month+1;
			if(month>12){
				month=month%12;
				year=year+1;
			}
		}
		
		
		
		$.ajax("moimOfDate/ffff",{
			type:"GET",
			data:{
				"moimDate" : moimDate,
				"area_name" : area_name,
				"lc_name" : lc_name,
				"sc_name" : sc_name
			},
			success : function(res){
				
				//alert(//JSON.)   
				$('.moimContentList').empty();
	               var moimList=res["todayMoimList"];
	               var moimFounderList=res["todayMoimListFounder"];
	               var moimSignUpCountList=res["MoimSignUpAgreeList"];
	               var moimCategoryList=res["moimCategoryList"];
	               alert(moimList.length);
	                     if(moimList.length<1){
	                        $('.moimContentList').append("<p class='moimEmpty'>오늘 모임이 없습니다.</p>");
	                     }
	                     
	               for(var i=0;i<moimList.length;i++){
	            	   
		               $('.moimContentList').append("<li class='moimContent moimContent"+i+"' onclick='sendMoim(this)'>");
		               $('.moimContent'+i+'').append("<input class='moimContentIdx' type='hidden' value="+moimList[i].moim_idx+">");
		               $('.moimContent'+i+'').append("<p class='moimSubject"+i+"'>");
		               $('.moimSubject'+i+'').append("<span class='moimSubjectInput'>"+moimList[i].moim_name+"</span>")
		               //$('.moimSubject'+i+'').append("<span class='moimCategory moimLC'>"+moimCategoryList[i].lc_name+"</span>")
		               $('.moimSubject'+i+'').append("<span class='moimCategory moimLC'>"+moimList[i].lc_name+"</span>")
		               $('.moimSubject'+i+'').append("<span class='moimCategoryArrow'>▶</span>")
		               //$('.moimSubject'+i+'').append("<span class='moimCategory moimSC'>"+moimCategoryList[i].sc_name+"</span>")
		               $('.moimSubject'+i+'').append("<span class='moimCategory moimSC'>"+moimList[i].sc_name+"</span>")
		               $('.moimContent'+i+'').append("</p>")
		               
		               /* $('.moimContent').append("<p class='moimInform moimCreator'>개설자");
		               $('.moimCreator').append("<span class='moimInformInput moimCreatorInput'>"+moimFounderList[i]+"</span>");
		               $('.moimContent').append("</p>"); */
		               
		               $('.moimContent'+i+'').append("<p class='moimInform moimPersonCount moimPersonCount"+i+"'>모임인원");
		               $('.moimPersonCount'+i+'').append("<span class='moimInformInput moimPersonCountInput moimPersonCountInput"+i+"'>"+moimSignUpCountList[i]+"명 / "+moimList[i].moim_personnel+"명</span>");
		               
		               
		               if(moimSignUpCountList[i]<moimList[i].moim_personnel){
		                  $('.moimPersonCountInput'+i+'').append("<span class='moimProceeding'>모집중</span>")
		               }
		               else{
		                  $('.moimPersonCountInput'+i+'').append("<span class='moimProceeding'>모집완료</span>")
		               }
		               
		               
	               $('.moimContent'+i+'').append("</p>");
	               
	               
	               $('.moimContent'+i+'').append("<p class='moimInform moimLocation moimLocation"+i+"'>모임장소");
	               $('.moimLocation'+i+'').append("<span class='moimInformInput moimLocationInput'>("+moimList[i].moim_postCode+") "+moimList[i].moim_addr1+"</span>");
	               $('.moimContent'+i+'').append("</p>");
	               
	               $('.moimContent'+i+'').append("<p class='moimInform moimTime moimTime"+i+"'>모임시간");
		          
	               var  mt = moimList[i].moim_time;
	               mt = mt.replace('T', ' ');
	               $('.moimTime'+i+'').append("<span class='moimInformInput moimTimeInput moimTimeInput"+i+"'>"+mt+"</span>");
				/*	              
	               var moimDateSum = moimList[i].moim_time.split(" ")[0];
	               var moimDate=moimDateSum.split("-");
	               var moimTimeSum = moimList[i].moim_time.split(" ")[1];
	               var moimTime=moimTimeSum.split(":");
	               */
	               var moimDateSum = mt.split(" ")[0];
	               var moimDate=moimDateSum.split("-");
	               var moimTimeSum = mt.split(" ")[1];
	               var moimTime=moimTimeSum.split(":");
	               
	               var moimTimeAll=new Date(Number(moimDate[0]),Number(moimDate[1])-1,Number(moimDate[2]),Number(moimTime[0]),Number(moimTime[1]),Number(moimTime[2]));
	               
	               if(today<moimTimeAll){
	                  $('.moimTime'+i+'').append("<span class='moimProceeding'>진행중</span>")
	               }
	               else{
	                  $('.moimTime'+i+'').append("<span class='moimProceeding'>종료</span>")
	               }
	               
	               
	               $('.moimContent'+i+'').append("</p>");
	               
	               $('.moimContentList'+i+'').append("</li>");
	               
	               
	              
	               }
	                   
	               $(".moimContent").each(function(){
	                  var moimApplyEnd = $(this).children(".moimPersonCountInput").children(".moimProceeding").text();
	                  var moimEnd = $(this).children(".moimTime").children(".moimProceeding").text();
	                  switch(moimApplyEnd){
	                     case "모집중":
	                        $(this).children(".moimPersonCountInput").children(".moimProceeding").css("background-color","#1ABC9C");
	                     break;
	                     case "모집완료":
	                        $(this).children(".moimPersonCountInput").children(".moimProceeding").css("background-color","#1570FF");
	                     break;
	                  }
	                  switch(moimEnd){
	                     case "종료":
	                        $(this).children(".moimTime").children(".moimProceeding").css("background-color","#A9A9A9");
	                     break;
	                     case "진행중":
	                        $(this).children(".moimTime").children(".moimProceeding").css("background-color","#1570FF");
	                     break;
	                  }
	               })
			},
			error : function(data){
				alert(data + "먼가 안됌");
				
			}
		
			
		})
		
	}
	
	
	
	

	
	function sc_filter_click(e){
		var sc_name = $('#'+e.id).val();	
		
		$('.sc_filter').css("background-color", "").css("color", "");
		$('#'+e.id).css("background-color", "#0CABA8").css("color", "white");
		
		
		if($('#sc_select').val() == $('#'+e.id).val()){
			$('.sc_filter').css("background-color", "").css("color", "");
			document.getElementById('sc_select').value = null;							
		}else{
			$('#sc_select').val(sc_name);
		}
		
			
		
		var area_name = $('#area_select').val();
		var lc_name = $('#lc_select').val();
		sc_name = $('#sc_select').val();
		
		
		var moimDate = $('#moim_select').val();
		
		
		
		var date = new Date();
		var year = date.getFullYear();
		var month = date.getMonth()+1;
		var day = $(e).children(".slider-element-date").text();
		
		var today=new Date(date.getYear()+1900,date.getMonth(),date.getDate(),date.getHours(),date.getMinutes(),date.getSeconds());
		
		if(day<date.getDate()){
			month=month+1;
			if(month>12){
				month=month%12;
				year=year+1;
			}
		}
		
		
		
		
		
		$.ajax("moimOfDate/ffff",{
			type:"GET",
			data:{
				"moimDate" : moimDate,
				"area_name" : area_name,
				"lc_name" : lc_name,
				"sc_name" : sc_name
			},
			success : function(res){
				alert(res);
				$('.moimContentList').empty();
	               var moimList=res["todayMoimList"];
	               var moimFounderList=res["todayMoimListFounder"];
	               var moimSignUpCountList=res["MoimSignUpAgreeList"];
	               var moimCategoryList=res["moimCategoryList"];
	               
	               alert(moimList.length);
            
	               
	                     if(moimList.length<1){
	                        $('.moimContentList').append("<p class='moimEmpty'>오늘 모임이 없습니다.</p>");
	                     }
	                     
	               for(var i=0;i<moimList.length;i++){
	            	   
		               $('.moimContentList').append("<li class='moimContent moimContent"+i+"' onclick='sendMoim(this)'>");
		               $('.moimContent'+i+'').append("<input class='moimContentIdx' type='hidden' value="+moimList[i].moim_idx+">");
		               $('.moimContent'+i+'').append("<p class='moimSubject"+i+"'>");
		               $('.moimSubject'+i+'').append("<span class='moimSubjectInput'>"+moimList[i].moim_name+"</span>")
		               //$('.moimSubject'+i+'').append("<span class='moimCategory moimLC'>"+moimCategoryList[i].lc_name+"</span>")
		               $('.moimSubject'+i+'').append("<span class='moimCategory moimLC'>"+moimList[i].lc_name+"</span>")
		               $('.moimSubject'+i+'').append("<span class='moimCategoryArrow'>▶</span>")
		               //$('.moimSubject'+i+'').append("<span class='moimCategory moimSC'>"+moimCategoryList[i].sc_name+"</span>")
		               $('.moimSubject'+i+'').append("<span class='moimCategory moimSC'>"+moimList[i].sc_name+"</span>")
		               $('.moimContent'+i+'').append("</p>")
		               
		               /* $('.moimContent').append("<p class='moimInform moimCreator'>개설자");
		               $('.moimCreator').append("<span class='moimInformInput moimCreatorInput'>"+moimFounderList[i]+"</span>");
		               $('.moimContent').append("</p>"); */
		               
		               $('.moimContent'+i+'').append("<p class='moimInform moimPersonCount moimPersonCount"+i+"'>모임인원");
		               $('.moimPersonCount'+i+'').append("<span class='moimInformInput moimPersonCountInput moimPersonCountInput"+i+"'>"+moimSignUpCountList[i]+"명 / "+moimList[i].moim_personnel+"명</span>");
		               
		               
		               if(moimSignUpCountList[i]<moimList[i].moim_personnel){
		                  $('.moimPersonCountInput'+i+'').append("<span class='moimProceeding'>모집중</span>")
		               }
		               else{
		                  $('.moimPersonCountInput'+i+'').append("<span class='moimProceeding'>모집완료</span>")
		               }
		               
		               
	               $('.moimContent'+i+'').append("</p>");
	               
	               
	               $('.moimContent'+i+'').append("<p class='moimInform moimLocation moimLocation"+i+"'>모임장소");
	               $('.moimLocation'+i+'').append("<span class='moimInformInput moimLocationInput'>("+moimList[i].moim_postCode+") "+moimList[i].moim_addr1+"</span>");
	               $('.moimContent'+i+'').append("</p>");
	               
	               $('.moimContent'+i+'').append("<p class='moimInform moimTime moimTime"+i+"'>모임시간");
		          
	               var  mt = moimList[i].moim_time;
	               mt = mt.replace('T', ' ');
	               $('.moimTime'+i+'').append("<span class='moimInformInput moimTimeInput moimTimeInput"+i+"'>"+mt+"</span>");
				/*	              
	               var moimDateSum = moimList[i].moim_time.split(" ")[0];
	               var moimDate=moimDateSum.split("-");
	               var moimTimeSum = moimList[i].moim_time.split(" ")[1];
	               var moimTime=moimTimeSum.split(":");
	               */
	               var moimDateSum = mt.split(" ")[0];
	               var moimDate=moimDateSum.split("-");
	               var moimTimeSum = mt.split(" ")[1];
	               var moimTime=moimTimeSum.split(":");
	               
	               var moimTimeAll=new Date(Number(moimDate[0]),Number(moimDate[1])-1,Number(moimDate[2]),Number(moimTime[0]),Number(moimTime[1]),Number(moimTime[2]));
	               
	               if(today<moimTimeAll){
	                  $('.moimTime'+i+'').append("<span class='moimProceeding'>진행중</span>")
	               }
	               else{
	                  $('.moimTime'+i+'').append("<span class='moimProceeding'>종료</span>")
	               }
	               
	               
	               $('.moimContent'+i+'').append("</p>");
	               
	               $('.moimContentList'+i+'').append("</li>");
	               
	               
	              
	               }
	                   
	               $(".moimContent").each(function(){
	                  var moimApplyEnd = $(this).children(".moimPersonCountInput").children(".moimProceeding").text();
	                  var moimEnd = $(this).children(".moimTime").children(".moimProceeding").text();
	                  switch(moimApplyEnd){
	                     case "모집중":
	                        $(this).children(".moimPersonCountInput").children(".moimProceeding").css("background-color","#1ABC9C");
	                     break;
	                     case "모집완료":
	                        $(this).children(".moimPersonCountInput").children(".moimProceeding").css("background-color","#1570FF");
	                     break;
	                  }
	                  switch(moimEnd){
	                     case "종료":
	                        $(this).children(".moimTime").children(".moimProceeding").css("background-color","#A9A9A9");
	                     break;
	                     case "진행중":
	                        $(this).children(".moimTime").children(".moimProceeding").css("background-color","#1570FF");
	                     break;
	                  }
	               })		
			},
			error : function(res){
				alert(res+"실패");

				
			}
		});
		
		
	}
	

	
	
	
	
	
	
	function sendMoim(e){
		 var form = document.createElement('form');
		    form.setAttribute('method', 'post'); //POST 메서드 적용
		    form.setAttribute('action', "/moim1");	// 데이터를 전송할 url
		    document.charset = "utf-8";
		   
		        var hiddenField = document.createElement('input');
		        hiddenField.setAttribute('type', 'hidden'); //값 입력
		        hiddenField.setAttribute('name', 'indexMoimidx');
		        hiddenField.setAttribute('value', $(e).children(".moimContentIdx").val());
		        form.appendChild(hiddenField);
		    
		        
		    document.body.appendChild(form);
		    form.submit();	// 전송~
	}
	
	
	$(window).resize(function(){
		var width=parseInt(($("#slider-div").width()-35)/7)+"px";
		$(".slider-element").css("width",width);
	})

	
	
	
	
	
	function click_date(e){
      $(".slider-element").css("background-color","#FFFFFF");
      $(".slider-element span").css("color","#000000");
      $(".slider-element-day").each(function(){
         var yoil = $(this).text();
         if(yoil=="일"){
            $(this).css("color","red");
            $(this).siblings().css("color","red");
         }
      })
      $(e).css("background-color","#1570FF");
      $(e).children("span").css("color","#FFFFFF");
      
      
      
      
      
      var date = new Date();
      var year = date.getFullYear();
      var month = date.getMonth()+1;
      var day = $(e).children(".slider-element-date").text();
      
      var today=new Date(date.getYear()+1900,date.getMonth(),date.getDate(),date.getHours(),date.getMinutes(),date.getSeconds());
      
      if(day<date.getDate()){
         month=month+1;
         if(month>12){
            month=month%12;
            year=year+1;
         }
      }
      
      var getDate = year+"-"+("0"+month).slice(-2)+"-"+("0"+day).slice(-2);
      
      
      document.getElementById('moim_select').value = getDate;
      // alert(document.getElementById('moim_select').value);
      
      area_name = $('#area_select').val();
      var lc_name = $('#lc_select').val();
      var sc_name = $('#sc_select').val();
      var moimDate = $('#moim_select').val();
      
      var obj = {
            "anotherDay" : getDate
      };
      
      $.ajax("moimOfDate/ffff",{
         type:"GET",
         data:{
            "moimDate" : moimDate,
            "area_name" : area_name,
            "lc_name" : lc_name,
            "sc_name" : sc_name
         },
            success : function(res){
               //alert(//JSON.)   
               
               $('.moimContentList').empty();
                     var moimList=res["todayMoimList"];
                     var moimFounderList=res["todayMoimListFounder"];
                     var moimSignUpCountList=res["MoimSignUpAgreeList"];
                     var moimCategoryList=res["moimCategoryList"];
                     
                           if(moimList.length<1){
                              $('.moimContentList').append("<p class='moimEmpty'>오늘 모임이 없습니다.</p>");
                           }
                           
                     for(var i=0;i<moimList.length;i++){
                        
                        $('.moimContentList').append("<li class='moimContent moimContent"+i+"' onclick='sendMoim(this)'>");
                        $('.moimContent'+i+'').append("<input class='moimContentIdx' type='hidden' value="+moimList[i].moim_idx+">");
                        $('.moimContent'+i+'').append("<p class='moimSubject"+i+"'>");
                        $('.moimSubject'+i+'').append("<span class='moimSubjectInput'>"+moimList[i].moim_name+"</span>")
                        $('.moimSubject'+i+'').append("<span class='moimCategory moimLC'>"+moimList[i].lc_name+"</span>")
                        $('.moimSubject'+i+'').append("<span class='moimCategoryArrow'>▶</span>")
                        $('.moimSubject'+i+'').append("<span class='moimCategory moimSC'>"+moimList[i].sc_name+"</span>")
                        $('.moimContent'+i+'').append("</p>")
                        
                        /* $('.moimContent').append("<p class='moimInform moimCreator'>개설자");
                        $('.moimCreator').append("<span class='moimInformInput moimCreatorInput'>"+moimFounderList[i]+"</span>");
                        $('.moimContent').append("</p>"); */
                        
                        $('.moimContent'+i+'').append("<p class='moimInform moimPersonCount moimPersonCount"+i+"'>모임인원");
                        $('.moimPersonCount'+i+'').append("<span class='moimInformInput moimPersonCountInput moimPersonCountInput"+i+"'>"+moimSignUpCountList[i]+"명 / "+moimList[i].moim_personnel+"명</span>");
                        
                        
                        if(moimSignUpCountList[i]<moimList[i].moim_personnel){
                           $('.moimPersonCountInput'+i+'').append("<span class='moimProceeding'>모집중</span>")
                        }
                        else{
                           $('.moimPersonCountInput'+i+'').append("<span class='moimProceeding'>모집완료</span>")
                        }
                        
                        
                     $('.moimContent'+i+'').append("</p>");
                     
                     
                     $('.moimContent'+i+'').append("<p class='moimInform moimLocation moimLocation"+i+"'>모임장소");
                     $('.moimLocation'+i+'').append("<span class='moimInformInput moimLocationInput'>("+moimList[i].moim_postCode+") "+moimList[i].moim_addr1+"</span>");
                     $('.moimContent'+i+'').append("</p>");
                     
                     $('.moimContent'+i+'').append("<p class='moimInform moimTime moimTime"+i+"'>모임시간");
                   
                     var  mt = moimList[i].moim_time;
                     mt = mt.replace('T', ' ');
                     $('.moimTime'+i+'').append("<span class='moimInformInput moimTimeInput moimTimeInput"+i+"'>"+mt+"</span>");
               /*                 
                     var moimDateSum = moimList[i].moim_time.split(" ")[0];
                     var moimDate=moimDateSum.split("-");
                     var moimTimeSum = moimList[i].moim_time.split(" ")[1];
                     var moimTime=moimTimeSum.split(":");
                     */
                     var moimDateSum = mt.split(" ")[0];
                     var moimDate=moimDateSum.split("-");
                     var moimTimeSum = mt.split(" ")[1];
                     var moimTime=moimTimeSum.split(":");
                     
                     var moimTimeAll=new Date(Number(moimDate[0]),Number(moimDate[1])-1,Number(moimDate[2]),Number(moimTime[0]),Number(moimTime[1]),Number(moimTime[2]));
                     
                     if(today<moimTimeAll){
                        $('.moimTime'+i+'').append("<span class='moimProceeding'>진행중</span>")
                     }
                     else{
                        $('.moimTime'+i+'').append("<span class='moimProceeding'>종료</span>")
                     }
                     
                     
                     $('.moimContent'+i+'').append("</p>");
                     
                     $('.moimContentList'+i+'').append("</li>");
                     
                     
                    
                     }
                         
                     $(".moimContent").each(function(){
                        var moimApplyEnd = $(this).children(".moimPersonCountInput").children(".moimProceeding").text();
                        var moimEnd = $(this).children(".moimTime").children(".moimProceeding").text();
                        switch(moimApplyEnd){
                           case "모집중":
                              $(this).children(".moimPersonCountInput").children(".moimProceeding").css("background-color","#1ABC9C");
                           break;
                           case "모집완료":
                              $(this).children(".moimPersonCountInput").children(".moimProceeding").css("background-color","#1570FF");
                           break;
                        }
                        switch(moimEnd){
                           case "종료":
                              $(this).children(".moimTime").children(".moimProceeding").css("background-color","#A9A9A9");
                           break;
                           case "진행중":
                              $(this).children(".moimTime").children(".moimProceeding").css("background-color","#1570FF");
                           break;
                        }
                     })
            },
            error : function(err){
               alert("ajax 보낼 파일명 바꾸세요" + err);
               
            }
      });
   }
	