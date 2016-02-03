<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript">
	//이메일 유효성 변수
	var emailValidity = false;
	//회원 이름 유효성 변수
	var memberNameValidity = false;
	//회원 비밀번호 유효성 변수
	var passwordValidity = false;
	//회원 비밀번호 확인 유효성 변수
	var repasswordValidity = false;
	//이메일 정규식
	var regExp = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i; 
	$(document).ready(function(){
		if($(':input[name="memberId"]').val()!=null){
			memberNameValidity = true;
		}
		if(!regExp.test($(':input[name="memberEmail"]').val())){
			emailValidity = false;
			alert('asdf');
			$(':input[name="memberEmail"]').attr("type","text");
		}else{
			emailValidity = true;
		}
		//입력 값 초기화;
		$(':input[name="memberPassword"]').val('');
		$(':input[name="memberRePassword"]').val('');
		
		$(':input[name="memberEmail"]').keyup(function(){
			//유저의 입력값
			var userMail = $(this).val();
			
			//이메일 정규식
			
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
	            			if(userMail==$(':input[name="memberId"]').val()){
	            				emailValidity = true;
	            			}else{
		            			$('#memberIdInsertMSG').text('이미 등록한 이메일 주소 입니다.');
	            			}
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
		$(':input[name="memberName"]').keyup(function(){
			//유저의 입력값
			var userName = $(this).val();
			if(userName==""){
				$('#memberNameInsertMSG').text('성함 혹은 별칭을 입력해주세요');
			}
			if(userName.length>=10){
				$('#memberNameInsertMSG').text('성함 혹은 별칭은 9글자 이하로 입력해주세요');
				$(this).val(userName.substring(0,9));
			}else if(userName.length>0){
				$('#memberNameInsertMSG').text('유효한 이름입니다!');
				memberNameValidity = true;
			}
			
		});
		$(':input[name="memberPassword"]').keyup(function(){
			//유저의 입력값
			var userPassword = $(this).val();
			if(userPassword==""){
				$('#memberPasswordInsertMSG').text('비밀번호를 입력 해주세요');
			}
			if(userPassword.length>=12){
				$('#memberPasswordInsertMSG').text('비밀번호는 6글자 이상, 12글자 이하로 입력해주세요');
				$(this).val(userPassword.substring(0,12));
			}else if(userPassword.length>6){
				$('#memberPasswordInsertMSG').text('유효한 비밀번호입니다!');
				passwordValidity = true;
			}
		});
		$(':input[name="memberRePassword"]').keyup(function(){
			var userRePassword = $(this).val();
			if(userRePassword==""){
				$('#memberRePasswordInsertMSG').text('비밀번호를 확인 해주세요');
			}
			if(userRePassword.length>=12){
				$('#memberRePasswordInsertMSG').text('비밀번호는 6글자 이상, 12글자 이하로 입력해주세요');
				$(this).val(userRePassword.substring(0,12));
			}else if(userRePassword.length>6){
				if($(':input[name="memberPassword"]').val()==userRePassword){
					$('#memberRePasswordInsertMSG').text('비밀번호가 확인되었습니다.');
					repasswordValidity = true;
				}else{
					$('#memberRePasswordInsertMSG').text('비밀번호가 서로 일치하지 않습니다.');
				}
			}
		});
		
	});
		function submitMemberInfo(){
			if(!emailValidity){
				alert('이메일을 확인해주세요!');
				$(':input[name="memberId"]').focus();
				return false;	
			}
			if(!memberNameValidity){
				alert('입력하신 이름을 확인해주세요');
				$(':input[name="memberName"]').focus();
				return false;	
			}
			if(!passwordValidity){
				alert('입력하신 비밀번호를 확인해주세요');
				$(':input[name="memberPassword"]').focus();
				return false;	
			}
			if(!repasswordValidity){
				alert('확인하신 비밀번호가 올바르지않습니다.');
				$(':input[name="memberRePassword"]').focus();
				return false;	
			}
			document.getElementById("memberInfoForm").submit();
		}
		
		function pageBack() {
			history.back();
		}
</script>

</head>
	<!-- 회원가입페이지에 비회원 접근 시 회원가입 폼 출력 -->
	<div class="loginPage_bg" style="height: 1000px;">
		<div class="join_bg" style="margin-top: 50px;">
			<div class="title">
				회원 정보 수정
			</div>
			<form action="updateMemberInfoByEmail.do" method="post" name="memberInfo" id="memberInfoForm">
				<input type="hidden" name="memberId" value="${requestScope.blliMemberVO.memberId}"  readonly="readonly">
				<div class="h30"><h2 id="memberIdInsertMSG" style="color: white; padding-top: 10px; padding-left:11px; font-weight: bold; text-align: left;" ></h2>
				</div>
				<input type="hidden" name="memberEmail" placeholder="이메일 주소를 입력해주세요">
				<div class="h30"><h2 id="memberEmailInsertMSG" style="color: white; padding-top: 10px; padding-left:11px; font-weight: bold; text-align: left;" ></h2>
				</div>
				<input type="text" name="memberName" value="${requestScope.blliMemberVO.memberName}">
				<div class="h30"><h2 id="memberNameInsertMSG" style="color: white; padding-top: 10px; padding-left:11px; font-weight: bold; text-align: left;" ></h2>
				</div>
				<input type="password" name="memberPassword" placeholder="비밀번호(6자 이상)">
				<div class="h30"><h2 id="memberPasswordInsertMSG" style="color: white; padding-top: 10px; padding-left:11px; font-weight: bold; text-align: left;" ></h2>
				</div>
				<input type="password" name="memberRePassword" placeholder="비밀번호 확인">
				<div class="h30"><h2 id="memberRePasswordInsertMSG" style="color: white; padding-top: 10px; padding-left:11px; font-weight: bold; text-align: left;" ></h2>
				</div>
				<input type="button" class="loginButton" value="정보 수정" style="margin-top:20px;" onclick="submitMemberInfo()" > 
				<input type="button" class="loginButton" value="정보수정 취소" style="margin-top:20px;" onclick="pageBack()" > 
			</form>
			
		<!-- 	<div class="login_bt">
				<p>
					<a href="#"><img src="./img/login_bt1.png" alt="페이스북으로 가입하기"></a>
				</p>
				<p>
					<a href="#"><img src="./img/login_bt2.png" alt="카카오톡으로 가입하기"></a>
				</p>
			</div> -->
		</div>
	
	</div>
	