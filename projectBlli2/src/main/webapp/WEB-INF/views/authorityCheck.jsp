<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />

<title>블리</title>
<meta name="Keywords" content="" />
<meta name="Description" content="" />
<link rel="stylesheet" type="text/css" href="${initParam.root}css/reset.css" />
<link rel="stylesheet" type="text/css" href="${initParam.root}css/css.css" />
<link href='https://cdn.rawgit.com/openhiun/hangul/14c0f6faa2941116bb53001d6a7dcd5e82300c3f/nanumbarungothic.css' rel='stylesheet' type='text/css'>
<!-- jquery -->
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<!-- modal script -->
<script type="text/javascript">


	//이메일 유효성 변수
	var emailValidity = false;
	//쌍둥이 선택 시 몇번째 칸 아이인지 저장하는 변수
	var selectBabyNum ;
	
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
		
</script>

<body class="login_bg">

	<!-- 본페이지는 로그인 페이지로서 Restricted멤버의 경우 아이 추가 페이지를 출력하며 비인증 회원의 경우 로그인 폼을 출력해줍니다. -->
	
	<!-- 회원가입페이지에 ROLE_USER접근 시 alert과 메인페이지 이동 -->
	<sec:authorize access="hasRole('ROLE_USER')">
			alert('이미 로그인은 완료하셨습니다^^ 메인으로 이동할게요~');
			location.replace('${initParam.root}member_proceedingToMain.do')	;
	</sec:authorize>
	
	<!-- 미인증 유저에게는 로그인 폼이 제공된다. -->
	<sec:authorize access="isAnonymous()">
		<form id="loginfrm" name="loginfrm" method="POST" action="${initParam.root}j_spring_security_check">
			<table>
			    <tr>
			        <td style="width:50px;">id</td>
			        <td style="width:150px;">
			            <input style="width:145px;" type="text" id="loginid" name="memberId" value="" />
			        </td>
			    </tr>
			    <tr>
			        <td>pwd</td>
			        <td>
			            <input style="width:145px;" type="text" id="loginpwd" name="memberPassword" value="" />
			        </td>
			    </tr>
			    <tr>
			        <td>
			            <input type="submit" id="loginbtn" value="로그인" />
			        </td>
			    <td>Remember Me: <input type="checkbox" name="remember-me" /></td>
			    </tr>
			</table> 
		</form>
	</sec:authorize>

	<!-- 회원가입하였으나 아이정보를 추가하지 않은 경우 경우 아이정보 추가 폼이 출력된다. -->
	<sec:authorize access="hasAnyRole('ROLE_RESTRICTED')"><form action="insertBabyInfo.do" id="babyInfoForm" method="post"><div class="info_fr">
			<input type="hidden" name="memberId" value="${sessionScope.blliMemberVO.memberId}">
			
			<c:if test="${sessionScope.blliMemberVO.memberEmail=='needsYourEmail'}">
				<div class="email_bg" style="top: 15%;">
					이메일 주소 <input type="text" name="memberId" placeholder="Email 주소">
				</div>
			</c:if>
			
			<c:if test="${sessionScope.blliMemberVO.memberEmail!='needsYourEmail'}">
				<script type="text/javascript">
				emailValidity = true;
			</script>
			</c:if>
			<!-- 몇명의 아이를 입력했는지 정보를 넘겨주는 히든 폼 -->
			<input type="hidden" value="1" name="targetAmount"> 
		<div class="info_bg" style="top: 28%;">
		
			<div class="title">
				아이 정보 입력
			</div>
			<div>
				<div class="fl">
					<div class="baby_foto">
						<a href="#"><img src="./img/foto_plus.png" alt="사진추가하기" class="foto_plus"></a>
					</div>
				</div>
				<div class="fr" style="width:190px;">
						<span class="fl" style="margin-bottom: 7px;">
							성별 : 
							<select name="BlliBabyVO[0].babySex" class="genderSelector">
								<option value="남자">남자</option>
								<option value="여자">여자</option>
								<option value="쌍둥이">쌍둥이</option>
								<option value="모름">모름</option>
							</select>					
						</span>
					<label><input type="text" placeholder="아이이름" name="BlliBabyVO[0].babyName" class="babyName"></label>
					<label><input type="text" id="datepicker" name="BlliBabyVO[0].babyBirthday" placeholder="아이생일" readonly="readonly" style="margin-top:5px;" ></label>
					<input type="button" class="fr" onclick="addInfoBg2()" style="margin-top: 10px ;height: 20px;" value="아이 추가 하기">
				</div>
			</div>
		</div>
		<div class="info_bg2" style="display: none;">
			<div>
				<div class="fl">
					<div class="baby_foto" style="margin-top:10px;">
						<a href="#"><img src="./img/foto_plus.png" alt="사진추가하기" class="foto_plus"></a>
					</div>
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
					<div class="baby_foto" style="margin-top:10px;">
						<a href="#"><img src="./img/foto_plus.png" alt="사진추가하기" class="foto_plus"></a>
					</div>
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
		<div class="info_bt" style="top: 55%;">
			<div style="width:317px; margin:auto; ">
				<a href="#" onclick="insertBabyInfo()"><img src="./img/info_bt1.png" alt="아이입력완료"></a>
			</div>
			<p>
				가입과 동시에 쿠키 사용 및 약관에 동의하는 것으로 합니다.<br/>
				<a href="#"><span class="yellow">약관 보기</span></a>
			</p>
		</div>
	</div>
	</form>
	</sec:authorize>
	
</body>
</html>