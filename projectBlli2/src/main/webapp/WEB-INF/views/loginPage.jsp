<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>블리</title>
<meta name="Keywords" content="" />
<meta name="Description" content="" />
<link rel="stylesheet" type="text/css" href="./css/reset.css" />
<link rel="stylesheet" type="text/css" href="./css/css.css" />
<link href='
https://cdn.rawgit.com/openhiun/hangul/14c0f6faa2941116bb53001d6a7dcd5e82300c3f/nanumbarungothic.css' 
rel='stylesheet' type='text/css'>
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$('.loginButton').click(function(){
			if($('#memberId').val()==""){
				alert('id를 입력해주세요');
				$('#memberId').focus(); 
				return false;
			}
			if($('#memberPassword').val()==""){
				alert('비밀번호를 입력해주세요');
				$('#memberPassword').focus(); 
				return false;
			}
			$('#loginfrm').submit();
		});
		$('input[name="remember-me"]').change(function(){
			if($(this).is(':checked')==true){
				if(!confirm('자동로그인 기능은 보안을 위해 개인컴퓨터에서만 실행시켜주세요')){
					$(this).attr("checked",false);
				}
			}
		});
		
		if("${requestScope.memberEmail}"!="") {
			alert("회원님의 이메일로 임시 비밀번호가 발송되었습니다.");
		}
	})
</script>
</head>
<body class="loginPage_bg">
${requestScope.loginFail}
<c:if test="${requestScope.loginFail=='true'}">
	<script type="text/javascript">
		alert('로그인에 실패했습니다. 비밀번호를 확인해주세요!');
	</script>
</c:if>
		<div class="loginComp_bg" style="height: 350px; margin-top: 5%">
			<div class="title" style="margin-top: 40px;">
				Email Login
			</div>
			<form id="loginfrm" name="loginfrm" method="POST" action="${initParam.root}j_spring_security_check">
				<label><input type="text" name="memberId" placeholder="Email Id" style="width: 
				250px; margin-top:20px;" id="memberId"></label><br>
				<label><input type="password" placeholder="Password" name="memberPassword"
				style="margin-top:20px; width: 250px" id="memberPassword"></label><br>
				<label>
					<input type="checkbox" name="remember-me" style="width: 15px; height: 15px; margin-top:20px;"/> 자동로그인 체크
				</label>
				<br>
				<input type="button" class="loginButton" value="로그인" style="margin-top:20px;" > 
			</form>
			<br>
			<a href="${initParam.root}goFindPasswordPage.do"><font color="white"><b>비밀번호가 뭐였지..?</b></font></a>
		</div>
	<div class="login_bottom">
		<div class="fl login_bottom_ft">
			블리 3231개의 상품과, 3216400개의 블로그가 있습니다.
		</div>
		<div class="fr">
			<a href="#"><img src="./img/bottom_app1.png" alt="안드로이드 다운로드받기"></a>
			<a href="#"><img src="./img/bottom_app2.png" alt="애플 다운로드받기"></a>
		</div>
	</div>
</body>
</html>      

