 
 $(function() {
		CKEDITOR.replace('content', {
			// 이것을 써줘야 로컬파일 업로드가 가능하다.
			filebrowserUploadUrl : '${pageContext.request.contextPath }/freeBoard/fileupload.jsp',
			height : 290
		});
		
	// --------------------------------------------------------------------------------------
	
	
	
	
	$('input[name="moim_onoff"]').change(function() {
		if($('input[name="moim_onoff"]:checked').val()=='Y'){
			$('#moim_info4').show();
		}else{
			$('#moim_info4').hide();
		}
	})
	
	
	
	$('#flexSwitchCheckChecked').change(function() {
		if($(this).is(':checked')) {
			$('.moimCost').show();		
		}
		else{
			$('.moimCost').hide();		
			
		}
	})
	// --------------------------------------------------------------------------------------
	
	
		$('#lc_category').change(function(){
		var result = $('#lc_category option:selected').text();
		if(result == '대분류'){
			$('#sc').hide();
		}else{
			$('#sc').show();
			$.ajax("Category",{
				type:"GET",
				data:{
					"lc_name" : result
				},
				success : function(data){
					// alert(typeof(data) + " : " + data );
					$("#sc_category").empty();
					$("#sc_category").append("<option value='소분류'>소분류</option>");
					for(index in data){
						// alert(data[index]);
						$("#sc_category").append("<option value='" + data[index] + "'>" + data[index] + "</option>");
					}
				},
				error : function(data){
					alert(data + "먼가 안됌");
					
				}
			})
		
		}
	})	
	
	// 
	// --------------------------------------------------------------------------------------
	$("#check_limit_age_on").change(function(){
        if($("#check_limit_age_on").is(":checked")){
            $('.slider').show()
        }
    });
	
	$("#check_limit_age_off").change(function(){
        if($("#check_limit_age_off").is(":checked")){
            $('.slider').hide()       
        }
    });


	
	// --------------------------------------------------------------------------------------
	const inputLeft = document.getElementById("input-left");
	const inputRight = document.getElementById("input-right");

	const thumbLeft = document.querySelector(".thumb.left");
	const thumbRight = document.querySelector(".thumb.right");

	const range = document.querySelector(".range");

	const setLeftValue = e => {
	  const _this = e.target;
	  const { value, min, max } = _this;

	  if (+inputRight.value - +value < 10) {
	    _this.value = +inputRight.value - 10;
	 }

	  const percent = ((+_this.value - +min) / (+max - +min)) * 100;

	  thumbLeft.style.left = `${percent}%`;
	  range.style.left = `${percent}%`;
	};

	const setRightValue = e => {
	  const _this = e.target;
	  const { value, min, max } = _this;

	  if (+value - +inputLeft.value < 10) {
	    _this.value = +inputLeft.value + 10;
	  }

	  const percent = ((+_this.value - +min) / (+max - +min)) * 100;

	  thumbRight.style.right = `${100 - percent}%`;
	  range.style.right = `${100 - percent}%`;
	};

	if (inputLeft && inputRight) {
	  inputLeft.addEventListener("input", setLeftValue);
	  inputRight.addEventListener("input", setRightValue);
	}
	
	});
	
	// --------------------------------------------------------------------------------------
	
	
	
	
	
	function daumPostcode() {
		new daum.Postcode(
				{
					oncomplete : function(data) {
						var addr = ''; // 주소 변수

						//사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
						if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
							addr = data.roadAddress;
						} else { // 사용자가 지번 주소를 선택했을 경우(J)
							addr = data.jibunAddress;
						}
						// 우편번호와 주소 정보를 해당 필드에 넣는다.
						document.getElementById('postCode').value = data.zonecode;
						document.getElementById("addr1").value = addr;
						// 커서를 상세주소 필드로 이동한다.
						// document.getElementById("addr2").focus();

						// 주소를 읽어서 맵을 찍는다.
						// 
						//
						var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
						mapOption = {
							center : new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
							level : 3 // 지도의 확대 레벨
						};

						// 지도를 생성합니다    
						var map = new kakao.maps.Map(mapContainer, mapOption);

						// 주소-좌표 변환 객체를 생성합니다
						var geocoder = new kakao.maps.services.Geocoder();

						var marker = new kakao.maps.Marker(), // 클릭한 위치를 표시할 마커입니다
						infowindow = new kakao.maps.InfoWindow({ zindex : 1 }); // 클릭한 위치에 대한 주소를 표시할 인포윈도우입니다
						
						// -------------------------------------------------------------------------------
						// 주소로 좌표를 검색합니다
						geocoder.addressSearch(addr, function(result, status) {

							// 정상적으로 검색이 완료됐으면 
							if (status === kakao.maps.services.Status.OK) {

								var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

								marker.setMap(map);
								marker.setPosition(coords);
								document.getElementById("addr2").value = coords;
								// 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
								map.setCenter(coords);
							}
						});
						// -------------------------------------------------------------------------------		
					
						
						// -------------------------------------------------------------------------------
						// 현재 지도 중심좌표로 주소를 검색해서 지도 좌측 상단에 표시합니다
						searchAddrFromCoords(map.getCenter(), displayCenterInfo);

						// 지도를 클릭했을 때 클릭 위치 좌표에 대한 주소정보를 표시하도록 이벤트를 등록합니다
						kakao.maps.event.addListener(map, 'click', function(mouseEvent) {
							searchDetailAddrFromCoords(mouseEvent.latLng, function(result, status) {
								if (status === kakao.maps.services.Status.OK) {
									var detailAddr = !!result[0].road_address ? '<div>도로명주소 : '
																	+ result[0].road_address.address_name
																	+ '</div>'
																	: '';
										detailAddr += '<div>지번 주소 : '
																	+ result[0].address.address_name
																	+ '</div>';

									var content = '<div class="bAddr">'
																	+ '<span class="title">법정동 주소정보</span>'
																	+ detailAddr
																	+ '</div>';

						// 마커를 클릭한 위치에 표시합니다 
								marker.setPosition(mouseEvent.latLng);
								marker.setMap(map);
								document.getElementById("addr2").value = mouseEvent.latLng;
								
						// 인포윈도우에 클릭한 위치에 대한 법정동 상세 주소정보를 표시합니다
								infowindow.setContent(content);
								infowindow.open(map, marker);
								}
							});
						});

						// 중심 좌표나 확대 수준이 변경됐을 때 지도 중심 좌표에 대한 주소 정보를 표시하도록 이벤트를 등록합니다
						kakao.maps.event.addListener(map, 'idle', function() {
							searchAddrFromCoords(map.getCenter(),
									displayCenterInfo);
						});

						function searchAddrFromCoords(coords, callback) {
							// 좌표로 행정동 주소 정보를 요청합니다
							geocoder.coord2RegionCode(coords.getLng(), coords
									.getLat(), callback);
						}

						function searchDetailAddrFromCoords(coords, callback) {
							// 좌표로 법정동 상세 주소 정보를 요청합니다
							geocoder.coord2Address(coords.getLng(), coords
									.getLat(), callback);
						}

						// 지도 좌측상단에 지도 중심좌표에 대한 주소정보를 표출하는 함수입니다
						function displayCenterInfo(result, status) {
							if (status === kakao.maps.services.Status.OK) {
								var infoDiv = document
										.getElementById('centerAddr');

								for (var i = 0; i < result.length; i++) {
									// 행정동의 region_type 값은 'H' 이므로
									if (result[i].region_type === 'H') {
										infoDiv.innerHTML = result[i].address_name;
										break;
									}
								}
							}
						}
						// -------------------------------------------------------------------------------
						

					}
				}).open();

	}
	