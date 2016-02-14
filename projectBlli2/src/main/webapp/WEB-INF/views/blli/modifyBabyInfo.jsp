<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    <script>
  //이메일 유효성 변수
	var emailValidity;
	//쌍둥이 선택 시 몇번째 칸 아이인지 저장하는 변수
	var selectBabyNum ;
	//최근 업로드된 사진번호 저장 변수
	var updateBabyPhotoNum;
	
	var regExp = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i; 
	$(document).ready(function(){
		if(!regExp.test($(':input[name="memberEmail"]').val())){
			emailValidity = false;
			$('.email_bg').css("display","block");
		}else{
			emailValidity = true;
		}
	});
	function setUpdateBabyPhotoNum(updateBabyPhotoNum){
		this.updateBabyPhotoNum = updateBabyPhotoNum;
	}
	
	function setChildValue(twinsName){
		if(twinsName=="exit" || twinsName==""){
			$(':input[name="BlliBabyVO['+selectBabyNum+'].babySex"]').val("남자");
			$(':input[name="BlliBabyVO['+selectBabyNum+'].babyName"]').val("");
		}else{
			$(':input[name="BlliBabyVO['+selectBabyNum+'].babyName"]').val(twinsName);
			$(':input[name="BlliBabyVO['+selectBabyNum+'].babyName"]').attr("readonly",true);
		}
	}
	function openAddTwinsNamePage(){
		var popOption = "width=370, height=360, resizable=no, scrollbars=no, status=no;";    //팝업창 옵션(optoin)
		window.open("${initParam.root}addTwinsName.do","popup",popOption);
	}
	$(document).ready(function(){
		//사진 업로드 시 실시간 미리보기 및 용량 체크
		$('.babyPhoto').change(function(){
			var fileName = $(this).val(); 
			//파일을 추가한 input 박스의 값
			fileName = fileName.slice(fileName.indexOf('.') + 1).toLowerCase(); 
			//파일 확장자를 잘라내고, 비교를 위해 소문자로 만듭니다.
			if(fileName != "jpg" && fileName != "png" &&  fileName != "gif" &&  fileName != "bmp")
			{ //확장자를 확인합니다.
				alert('프로필 사진은 이미지 파일(jpg, png, gif, bmp)만 등록 가능합니다.');
				$('#babyPhoto'+updateBabyPhotoNum).attr("src","${initParam.root}img/foto_plus.png");
				$('#babyPhoto'+updateBabyPhotoNum).css("width","");
				$('#babyPhoto'+updateBabyPhotoNum).css("height","");
				return false;
			}else{
				var data = new FormData();
				$.each($('#babyPhotoInput0')[0].files, function(i, file) {          
		   			data.append('file-' + i, file);
		        });
		        $.ajax({
			        url: 'fileCapacityCheck.do',
			        type: "post",
			        dataType: "text",
			        data: data,
			        // cache: false,
			        processData: false,
			        contentType: false,
			        success: function(data, textStatus, jqXHR) {
				        var input = this;
				        if(data=="true"){
				        	
				        }else{
				        	var fileName = $(this).val("");
					        alert('프로필 사진은 2mb 이하로 올려주세요 ^^');
					        $('#babyPhoto'+updateBabyPhotoNum).attr("src","${initParam.root}img/foto_plus.png");
							$('#babyPhoto'+updateBabyPhotoNum).css("width","");
							$('#babyPhoto'+updateBabyPhotoNum).css("height","");

					        return false;
					    }
		            }, 
		            error: function(jqXHR, textStatus, errorThrown) {}
		        });
		        if(fileName!=""){
				var input = this;
				 if (input.files && input.files[0]) {
	                    var reader = new FileReader(); //파일을 읽기 위한 FileReader객체 생성
	                    reader.onload = function (e) {
	                    //파일 읽어들이기를 성공했을때 호출되는 이벤트 핸들러
	                        //$('#babyPhoto0').css({"background":"url("+e.target.result+")"});
	                    	$('#babyPhoto'+updateBabyPhotoNum).attr("src",e.target.result);
	                    	$('#babyPhoto'+updateBabyPhotoNum).css("width","116px");
	                    	$('#babyPhoto'+updateBabyPhotoNum).css("height","116px");
	                    }                   
	                    reader.readAsDataURL(input.files[0]);
	             }
			}
			}
		});
		
		$('.babyName').keyup(function(){
			var userWritingBabyName = $(this).val();
			$(this).val(userWritingBabyName.substring(0,5));
			if($(this).length>5){
				alert('아이이름은 5글자 이하로 작성해주세요 ^^');
				$(this).val(userWritingBabyName.substring(0,5));
			}
		});
		
		//쌍둥이 이름 수정 시 발동되는 메서드
		$('.babyName').click(function(){
			if($(this).attr("readonly")){
				openAddTwinsNamePage();
			}
		});
		//성별에서 쌍둥이 선택시 발동되는 메서드
		$('.genderSelector').change(function(){
			alert($(this).val());
			if($(this).val()=='쌍둥이'){
				selectBabyNum = $(this).index();
				openAddTwinsNamePage();
			}
		});
		$(':input[name="memberId"]').keyup(function(){
			//유저의 입력값
			var userMail = $(this).val();
			//이메일 정규식
			var regExp = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i; 
			if(userMail==""){
				$('#memberIdInsertMSG').text('이메일을 입력해주세요');
			}else if(!regExp.test(userMail)){
				$('#memberIdInsertMSG').text('유효한 이메일을 입력해주세요');
			}else{
				$('#memberIdInsertMSG').text('유효한 이메일입니다 ');
				$.ajax({
		        	type:"get",
		        	url:"findMemberByEmailId.do?memberId="+userMail,
		        	success:function(result){
		        		if(result==true){
		        			$('#memberIdInsertMSG').text('이미 등록한 이메일 주소 입니다.');
		        		}else{
		        			$('#memberIdInsertMSG').text('유효한 이메일입니다 ');
		        			emailValidity = true;
		        		}
		        	}
		        });
			}
			if(userMail.length>=29){
				$('#memberIdInsertMSG').text('이메일 주소는 30글자 이하로 입력해주세요 ^^');
				$(this).val(userMail.substring(0,29));
			}
		});
		//date-picker 키보드 이용 불능으로 만들기
		$('#datepicker').on('keypress', function(e) {
		    e.preventDefault();
		});
		$('#datepicker2').on('keypress', function(e) {
		    e.preventDefault();
		});
		$('#datepicker3').on('keypress', function(e) {
		    e.preventDefault();
		});
		
	});
	
	$(function() {
	    $( "#datepicker" ).datepicker({ minDate: -1095, maxDate: "+10M", changeMonth: true,
	        changeYear: true , dateFormat: "yy-mm-dd" });
	  });
	$(function() {
	    $( "#datepicker2" ).datepicker({ minDate: -1095, maxDate: "+10M", changeMonth: true,
	        changeYear: true , dateFormat: "yy-mm-dd" });
	  });
	$(function() {
	    $( "#datepicker3" ).datepicker({ minDate: -1095, maxDate: "+10M", changeMonth: true,
	        changeYear: true , dateFormat: "yy-mm-dd" });
	  });
	
	function addInfoBg2(){
		$('.email_bg').css('top','20px');
		$('.info_bg').css('top','125px');
		$('.info_bt').css('top','610px');
		$('.info_bg2').css('display','block');
		return false;
	}
	function addInfoBg3(){
		$('.info_bg2').css('top','375px');
		$('.info_bt').css('top','780px');
		$('.info_bg3').css('display','block');
	}
	function cancelInfoBg2(){
		if($('.info_bg3').css("display")=="block"){
			alert('3번째 아이 입력창부터 비활성화 해주세요');
			return false;
		}
		$('.email_bg').css('top','15%');
		$('.info_bg').css('top','30%');
		$('.info_bt').css('top','60%');
		$('.info_bg2').css('display','none');
	}
	function cancelInfoBg3(){
		$('.info_bg2').css('top','375px');
		$('.info_bg3').css('display','none');
		$('.info_bt').css('top','610px');
	}
	
	function insertBabyInfo(){
		//첫번째 아이만 등록하는 경우
		if($('.info_bg2').css('display')=='none'){
			checkBabyInfo(1);			
		}else if($('.info_bg2').css('display')=='block'){
			//두번째 아이까지 등록하는 경우
			if($('.info_bg3').css('display')=='none'){
				checkBabyInfo(2);	
			}else{
				//세번째 아이까지 등록하는 경우
				checkBabyInfo(3);	
			}
		}
	}
	function checkBabyInfo(targetAmount){
		var flag = false;
		//이메일 유효성 검증 실패
		if(!emailValidity){
			alert('이메일을 확인해주세요');
			$(':input[name="memberId"]').focus();
			return false;
		}
		flag = checkFirstBabyInfo(targetAmount);
		$(':input[name="targetAmount"]').val(targetAmount);
		submitBabyInfo(flag);
		
		function checkFirstBabyInfo(targetAmount){
			for(var i=0;i<targetAmount;i++){
				if($(':input[name="BlliBabyVO['+i+'].babyName"]').val()==""){
					alert((i+1)+'번째 아이의 이름을 입력해주세요!');
					$(':input[name="firstBabyName"]').focus();
					return false;
				}else if($(':input[name="BlliBabyVO['+i+'].babyBirthday"]').val()==""){
					alert((i+1)+'번째 아이의 생일을 입력해주세요!');
					$(':input[name="firstBabyBirthday"]').focus();
					return false;
				}
				if(targetAmount>=2){
					for(var j=targetAmount-1;j>0;j--){
						if($(':input[name="BlliBabyVO['+i+'].babyName"]').val()==$(':input[name="BlliBabyVO['+j+'].babyName"]').val()){
							if(i==j){
								
							}else{
								alert('아이의 이름이 중복됩니다. 확인 부탁드려요!');
								$(':input[name="BlliBabyVO['+j+'].babyName"]').val('');
								$(':input[name="BlliBabyVO['+j+'].babyName"]').focus();
								return false;
							}
						};
					}
				}
			}
			return true;
		}
	}
	function submitBabyInfo(flag){
		if(flag){
			document.getElementById("babyInfoForm").submit();
		}
	}
	function backButton(){
		history.back();
	}
		
</script>
	<div class="loginPage_bg" style="height: 1000px;">
<form action="updateBabyInfo.do" id="babyInfoForm" method="post" name="babyInfoInsertForm" enctype="multipart/form-data">
	<div class="info_fr">
			<input type="hidden" name="memberId" value="${sessionScope.blliMemberVO.memberId}">
			<div class="email_bg" style="top: 15%; display : none;">
				이메일 주소 <input type="text" name="memberEmail" placeholder="Email 주소" value="${sessionScope.blliMemberVO.memberEmail}">
			</div>

			<!-- 몇명의 아이를 입력했는지 정보를 넘겨주는 히든 폼 -->
			<input type="hidden" value="1" name="targetAmount"> 
		<c:if test="${requestScope.blliMemberVO.blliBabyVOList.size()==1 }">
			<div class="info_bg" style="top: 28%;">
				<div class="title">
					아이 정보 입력
				</div>
				<div>
					<div class="fl">
						<label>
						<div class="baby_foto">
							<input type="file" class="babyPhoto" name="BlliBabyVO[0].babyPhoto" id="babyPhotoInput0" 
							value="${requestScope.blliMemberVO.blliBabyVOList.get(0).babyPhoto}"
							style="display: none;" accept="image/*" onchange="setUpdateBabyPhotoNum(0)">
	 						<c:if test="${requestScope.blliMemberVO.blliBabyVOList.get(0).babyPhoto!='default'}">
	 							<img src="${initParam.root}babyphoto/${requestScope.blliMemberVO.blliBabyVOList.get(0).babyPhoto}" 
				 						alt="사진추가하기" class="foto_plus" id="babyPhoto0" style="height: 116px; width: 116px;">
	 						</c:if>
	 						<c:if test="${requestScope.blliMemberVO.blliBabyVOList.get(0).babyPhoto=='default'}">
	 							<img src="./img/foto_plus.png" alt="사진추가하기" class="foto_plus" id="babyPhoto0">
	 						</c:if>
						</div>
						</label>
					</div>
					<div class="fr" style="width:190px;">
							<span class="fl" style="margin-bottom: 7px;">
								성별 : 
								<select name="BlliBabyVO[0].babySex" class="genderSelector">
									<option value="${requestScope.blliMemberVO.blliBabyVOList.get(0).babySex}" selected="selected">
										${requestScope.blliMemberVO.blliBabyVOList.get(0).babySex}</option>
									<option value="남자">남자</option>
									<option value="여자">여자</option>
									<option value="쌍둥이">쌍둥이</option>
									<option value="모름">모름</option>
								</select>					
							</span>
						<label><input type="text" placeholder="아이이름" name="BlliBabyVO[0].babyName" class="babyName"
						value="${requestScope.blliMemberVO.blliBabyVOList.get(0).babyName}"></label>
						<label><input type="text" id="datepicker" value="${requestScope.blliMemberVO.blliBabyVOList.get(0).babyBirthday}" 
						name="BlliBabyVO[0].babyBirthday" placeholder="아이생일" readonly="readonly" style="margin-top:5px;" ></label>
						<input type="button" class="fr" onclick="addInfoBg2()" style="margin-top: 10px ;height: 20px;" value="아이 추가 하기">
					</div>
				</div>
			</div>
			<div class="info_bg2" style="display: none;">
					<div>
						<div class="fl">
							<label>
								<div class="baby_foto" >
									<input type="file" class="babyPhoto" name="BlliBabyVO[1].babyPhoto" style="display: none;" accept="image/*" onchange="setUpdateBabyPhotoNum(1)">
			 						<img src="./img/foto_plus.png" alt="사진추가하기" class="foto_plus" id="babyPhoto1">
								</div>
							</label>
						</div>
						<div class="fr" style="width:190px;">
							<span class="fl" style="margin-bottom: 7px; margin-top:11px;">
									성별 : 
									<select name="BlliBabyVO[1].babySex" class="genderSelector">
										<option value="남자">남자</option>
										<option value="여자">여자</option>
										<option value="쌍둥이">쌍둥이</option>
										<option value="모름">모름</option>
									</select>					
							</span>
							<span class="fr" style="margin-top:-15px; margin-left:20px;"><a href="#" onclick="cancelInfoBg2()"><img src="./img/cancle.png" alt="삭제"></a></span>
							<label><input type="text" placeholder="아이이름" name="BlliBabyVO[1].babyName" class="babyName"></label>
							<label><input type="text" id="datepicker2" placeholder="아이생일" name="BlliBabyVO[1].babyBirthday" readonly="readonly" style="margin-top:5px;"></label>
							<input type="button" class="fr" onclick="addInfoBg3()" style="margin-top: 10px; height: 20px;" value="아이 추가 하기">
						</div>
					</div>
				</div>
		<div class="info_bg3" style="display: none;">
			<div>
				<div class="fl">
				<label>
					<div class="baby_foto" >
						<input type="file" class="babyPhoto" name="BlliBabyVO[2].babyPhoto" style="display: none;" accept="image/*" onchange="setUpdateBabyPhotoNum(2)">
 						<img src="./img/foto_plus.png" alt="사진추가하기" class="foto_plus" id="babyPhoto2">
					</div>
				</label>
				</div>
				<div class="fr" style="width:190px;">
					<span class="fl" style="margin-bottom: 7px; margin-top:11px;">
							성별 : 
							<select name="BlliBabyVO[2].babySex" class="genderSelector">
								<option value="남자">남자</option>
								<option value="여자">여자</option>
								<option value="쌍둥이">쌍둥이</option>
								<option value="모름">모름</option>
							</select>					
					</span>
					<span class="fr" style="margin-top:-15px; margin-left:20px;"><a href="#" onclick="cancelInfoBg3()"><img src="./img/cancle.png" alt="삭제"></a></span>
					<label><input type="text" placeholder="아이이름" name="BlliBabyVO[2].babyName" class="babyName"></label>
					<label><input type="text" id="datepicker3" placeholder="아이생일" name="BlliBabyVO[2].babyBirthday" readonly="readonly" style="margin-top:5px;"></label>
				</div>
			</div>
		</div>
		
		</c:if>
		<c:if test="${requestScope.blliMemberVO.blliBabyVOList.size()==2 }">
			<div class="info_bg" style="top: 28%;">
				<div class="title">
					아이 정보 입력
				</div>
				<div class="fl">
					<label>
					<div class="baby_foto">
						<input type="file" class="babyPhoto" name="BlliBabyVO[0].babyPhoto" id="babyPhotoInput0" style="display: none;" 
						value="${requestScope.blliMemberVO.blliBabyVOList.get(0).babyPhoto}"
						accept="image/*" onchange="setUpdateBabyPhotoNum(0)">
 						<c:if test="${requestScope.blliMemberVO.blliBabyVOList.get(0).babyPhoto!='default'}">
 							<img src="${initParam.root}babyphoto/${requestScope.blliMemberVO.blliBabyVOList.get(0).babyPhoto}" 
			 						alt="사진추가하기" class="foto_plus" id="babyPhoto0" style="height: 116px; width: 116px;">
 						</c:if>
 						<c:if test="${requestScope.blliMemberVO.blliBabyVOList.get(0).babyPhoto=='default'}">
 							<img src="./img/foto_plus.png" alt="사진추가하기" class="foto_plus" id="babyPhoto0">
 						</c:if>
					</div>
					</label>
				</div>
				<div class="fr" style="width:190px;">
						<span class="fl" style="margin-bottom: 7px;">
							성별 : 
							<select name="BlliBabyVO[0].babySex" class="genderSelector">
								<option value="${requestScope.blliMemberVO.blliBabyVOList.get(0).babySex}" selected="selected">
									${requestScope.blliMemberVO.blliBabyVOList.get(0).babySex}</option>
								<option value="남자">남자</option>
								<option value="여자">여자</option>
								<option value="쌍둥이">쌍둥이</option>
								<option value="모름">모름</option>
							</select>					
						</span>
					<label><input type="text" placeholder="아이이름" name="BlliBabyVO[0].babyName" class="babyName"
					value="${requestScope.blliMemberVO.blliBabyVOList.get(0).babyName}"></label>
					<label><input type="text" id="datepicker" value="${requestScope.blliMemberVO.blliBabyVOList.get(0).babyBirthday}" 
					name="BlliBabyVO[0].babyBirthday" placeholder="아이생일" readonly="readonly" style="margin-top:5px;" ></label>
					<input type="button" class="fr" onclick="addInfoBg2()" style="margin-top: 10px ;height: 20px;" value="아이 추가 하기">
				</div>
			</div>
				<div class="info_bg2" style="display: block;">
					<div>
						<div class="fl">
							<label>
								<div class="baby_foto" >
									<input type="file" class="babyPhoto" name="BlliBabyVO[1].babyPhoto" style="display: none;" accept="image/*" 
									value="${requestScope.blliMemberVO.blliBabyVOList.get(1).babyPhoto}"
									onchange="setUpdateBabyPhotoNum(1)">
			 						<c:if test="${requestScope.blliMemberVO.blliBabyVOList.get(0).babyPhoto!='default'}">
				 						<img src="${initParam.root}babyphoto/${requestScope.blliMemberVO.blliBabyVOList.get(1).babyPhoto}" 
				 						alt="사진추가하기" class="foto_plus" id="babyPhoto1" style="height: 116px;width: 116px;">
				 					</c:if>
			 						<c:if test="${requestScope.blliMemberVO.blliBabyVOList.get(0).babyPhoto=='default'}">
			 							<img src="./img/foto_plus.png" alt="사진추가하기" class="foto_plus" id="babyPhoto1">
			 						</c:if>
								</div>
							</label>
						</div>
						<div class="fr" style="width:190px;">
							<span class="fl" style="margin-bottom: 7px; margin-top:11px;">
									성별 : 
									<select name="BlliBabyVO[1].babySex" class="genderSelector">
										<option value="${requestScope.blliMemberVO.blliBabyVOList.get(1).babySex}" selected="selected">
										${requestScope.blliMemberVO.blliBabyVOList.get(1).babySex}</option>
										<option value="남자">남자</option>
										<option value="여자">여자</option>
										<option value="쌍둥이">쌍둥이</option>
										<option value="모름">모름</option>
									</select>					
							</span>
							<span class="fr" style="margin-top:-15px; margin-left:20px;"><a href="#" onclick="cancelInfoBg2()"><img src="./img/cancle.png" alt="삭제"></a></span>
							<label><input type="text" placeholder="아이이름" name="BlliBabyVO[1].babyName" class="babyName" value="${requestScope.blliMemberVO.blliBabyVOList.get(1).babyName}"></label>
							<label><input type="text" id="datepicker2" placeholder="아이생일" value="${requestScope.blliMemberVO.blliBabyVOList.get(1).babyBirthday}"
							name="BlliBabyVO[1].babyBirthday" readonly="readonly" style="margin-top:5px;"></label>
							<input type="button" class="fr" onclick="addInfoBg2()" style="margin-top: 10px; height: 20px;" value="아이 추가 하기">
						</div>
					</div>
				</div>
				<div class="info_bg3" style="display: none;">
			<div>
				<div class="fl">
				<label>
					<div class="baby_foto" >
						<input type="file" class="babyPhoto" name="BlliBabyVO[2].babyPhoto" style="display: none;" accept="image/*" onchange="setUpdateBabyPhotoNum(2)">
 						<img src="./img/foto_plus.png" alt="사진추가하기" class="foto_plus" id="babyPhoto2">
					</div>
				</label>
				</div>
				<div class="fr" style="width:190px;">
					<span class="fl" style="margin-bottom: 7px; margin-top:11px;">
							성별 : 
							<select name="BlliBabyVO[2].babySex" class="genderSelector">
								<option value="남자">남자</option>
								<option value="여자">여자</option>
								<option value="쌍둥이">쌍둥이</option>
								<option value="모름">모름</option>
							</select>					
					</span>
					<span class="fr" style="margin-top:-15px; margin-left:20px;"><a href="#" onclick="cancelInfoBg3()"><img src="./img/cancle.png" alt="삭제"></a></span>
					<label><input type="text" placeholder="아이이름" name="BlliBabyVO[2].babyName" class="babyName"></label>
					<label><input type="text" id="datepicker3" placeholder="아이생일" name="BlliBabyVO[2].babyBirthday" readonly="readonly" style="margin-top:5px;"></label>
				</div>
			</div>
		</div>
		
			</c:if>
			
				
		
		<c:if test="${requestScope.blliMemberVO.blliBabyVOList.size()==3 }">
		<div class="info_bg" style="top: 28%;">
			<div class="title">
				아이 정보 입력
			</div>
			<div>
				<div class="fl">
					<label>
					<div class="baby_foto">
						<input type="file" class="babyPhoto" name="BlliBabyVO[0].babyPhoto" id="babyPhotoInput0" style="display: none;"
						 value="${requestScope.blliMemberVO.blliBabyVOList.get(0).babyPhoto}"
						style="display: none;" accept="image/*" onchange="setUpdateBabyPhotoNum(0)">
 						<c:if test="${requestScope.blliMemberVO.blliBabyVOList.get(0).babyPhoto!='default'}">
 							<img src="${initParam.root}babyphoto/${requestScope.blliMemberVO.blliBabyVOList.get(0).babyPhoto}" 
			 						alt="사진추가하기" class="foto_plus" id="babyPhoto0" style="height: 116px; width: 116px;">
 						</c:if>
 						<c:if test="${requestScope.blliMemberVO.blliBabyVOList.get(0).babyPhoto=='default'}">
 							<img src="./img/foto_plus.png" alt="사진추가하기" class="foto_plus" id="babyPhoto0">
 						</c:if>
					</div>
					</label>
				</div>
				<div class="fr" style="width:190px;">
						<span class="fl" style="margin-bottom: 7px;">
							성별 : 
							<select name="BlliBabyVO[0].babySex" class="genderSelector">
								<option value="${requestScope.blliMemberVO.blliBabyVOList.get(0).babySex}" selected="selected">
									${requestScope.blliMemberVO.blliBabyVOList.get(0).babySex}</option>
								<option value="남자">남자</option>
								<option value="여자">여자</option>
								<option value="쌍둥이">쌍둥이</option>
								<option value="모름">모름</option>
							</select>					
						</span>
					<label><input type="text" placeholder="아이이름" name="BlliBabyVO[0].babyName" class="babyName"
					value="${requestScope.blliMemberVO.blliBabyVOList.get(0).babyName}"></label>
					<label><input type="text" id="datepicker" value="${requestScope.blliMemberVO.blliBabyVOList.get(0).babyBirthday}" 
					name="BlliBabyVO[0].babyBirthday" placeholder="아이생일" readonly="readonly" style="margin-top:5px;" ></label>
					<input type="button" class="fr" onclick="addInfoBg2()" style="margin-top: 10px ;height: 20px;" value="아이 추가 하기">
				</div>
			</div>
			</div>
				<div class="info_bg2" style="display: block;">
					<div>
						<div class="fl">
							<label>
								<div class="baby_foto" >
									<input type="file" class="babyPhoto" name="BlliBabyVO[1].babyPhoto" style="display: none;" 
									value="${requestScope.blliMemberVO.blliBabyVOList.get(1).babyPhoto}"
									accept="image/*" onchange="setUpdateBabyPhotoNum(1)">
			 						<c:if test="${requestScope.blliMemberVO.blliBabyVOList.get(1).babyPhoto!='default'}">
				 						<img src="${initParam.root}babyphoto/${requestScope.blliMemberVO.blliBabyVOList.get(1).babyPhoto}" 
				 						alt="사진추가하기" class="foto_plus" id="babyPhoto1" style="height: 116px;width: 116px;">
				 					</c:if>
			 						<c:if test="${requestScope.blliMemberVO.blliBabyVOList.get(1).babyPhoto=='default'}">
			 							<img src="./img/foto_plus.png" alt="사진추가하기" class="foto_plus" id="babyPhoto1">
			 						</c:if>
								</div>
							</label>
						</div>
						<div class="fr" style="width:190px;">
							<span class="fl" style="margin-bottom: 7px; margin-top:11px;">
									성별 : 
									<select name="BlliBabyVO[1].babySex" class="genderSelector">
										<option value="${requestScope.blliMemberVO.blliBabyVOList.get(1).babySex}" selected="selected">
										${requestScope.blliMemberVO.blliBabyVOList.get(1).babySex}</option>
										<option value="남자">남자</option>
										<option value="여자">여자</option>
										<option value="쌍둥이">쌍둥이</option>
										<option value="모름">모름</option>
									</select>					
							</span>
							<span class="fr" style="margin-top:-15px; margin-left:20px;"><a href="#" onclick="cancelInfoBg2()"><img src="./img/cancle.png" alt="삭제"></a></span>
							<label><input type="text" placeholder="아이이름" name="BlliBabyVO[1].babyName" class="babyName" value="${requestScope.blliMemberVO.blliBabyVOList.get(1).babyName}"></label>
							<label><input type="text" id="datepicker2" placeholder="아이생일" value="${requestScope.blliMemberVO.blliBabyVOList.get(1).babyBirthday}"
							name="BlliBabyVO[1].babyBirthday" readonly="readonly" style="margin-top:5px;"></label>
							<input type="button" class="fr" onclick="addInfoBg3()" style="margin-top: 10px; height: 20px;" value="아이 추가 하기">
						</div>
					</div>
				</div>
				<div class="info_bg3" style="display: block;">
					<div>
						<div class="fl">
							<label>
								<div class="baby_foto" >
									<input type="file" class="babyPhoto" name="BlliBabyVO[2].babyPhoto" style="display: none;" 
									value="${requestScope.blliMemberVO.blliBabyVOList.get(2).babyPhoto}"
									accept="image/*" onchange="setUpdateBabyPhotoNum(2)">
			 						<c:if test="${requestScope.blliMemberVO.blliBabyVOList.get(2).babyPhoto!='default'}">
				 						<img src="${initParam.root}babyphoto/${requestScope.blliMemberVO.blliBabyVOList.get(2).babyPhoto}" 
				 						alt="사진추가하기" class="foto_plus" id="babyPhoto2" style="height: 116px;width: 116px;">
				 					</c:if>
			 						<c:if test="${requestScope.blliMemberVO.blliBabyVOList.get(2).babyPhoto=='default'}">
			 							<img src="./img/foto_plus.png" alt="사진추가하기" class="foto_plus" id="babyPhoto2">
			 						</c:if>
								</div>
							</label>
						</div>
						<div class="fr" style="width:190px;">
							<span class="fl" style="margin-bottom: 7px; margin-top:11px;">
									성별 : 
									<select name="BlliBabyVO[2].babySex" class="genderSelector">
										<option value="${requestScope.blliMemberVO.blliBabyVOList.get(2).babySex}" selected="selected">
										${requestScope.blliMemberVO.blliBabyVOList.get(2).babySex}</option>
										<option value="남자">남자</option>
										<option value="여자">여자</option>
										<option value="쌍둥이">쌍둥이</option>
										<option value="모름">모름</option>
									</select>					
							</span>
							<span class="fr" style="margin-top:-15px; margin-left:20px;"><a href="#" onclick="cancelInfoBg3()"><img src="./img/cancle.png" alt="삭제"></a></span>
							<label><input type="text" placeholder="아이이름" name="BlliBabyVO[1].babyName" class="babyName" value="${requestScope.blliMemberVO.blliBabyVOList.get(2).babyName}"></label>
							<label><input type="text" id="datepicker3" placeholder="아이생일" value="${requestScope.blliMemberVO.blliBabyVOList.get(2).babyBirthday}"
							name="BlliBabyVO[2].babyBirthday" readonly="readonly" style="margin-top:5px;"></label>
						</div>
					</div>
				</div>
		</c:if>
		
		<div class="info_bt" style="top: 55%;">
			<div style="width:317px; margin:auto; ">
			<input type="button" class="loginButton" value="아이정보수정완료" style="margin-top:20px;" onclick="insertBabyInfo()"> 
			<input type="button" class="loginButton" value="아이정보수정취소" style="margin-top:20px;" onclick="backButton()"> 
			</div>
		</div>
	</div>
	</form>
	</div>
	<c:if test="${requestScope.blliMemberVO.blliBabyVOList.size()==3 }">
		<script>
			$(document).ready(function(){
				$('.info_bg').css('top','125px');
				$('.info_bg2').css('top','375px');
				$('.info_bt').css('top','780px');
				$('.info_bg3').css('display','block');
				$('.info_bg2').css('display','block');
			});
		</script>
	</c:if>
	<c:if test="${requestScope.blliMemberVO.blliBabyVOList.size()==2 }">
		<script>
			$(document).ready(function(){
				$('.email_bg').css('top','20px');
				$('.info_bg').css('top','125px');
				$('.info_bt').css('top','610px');
				$('.info_bg2').css('display','block');
			});
		</script>
	</c:if>
	