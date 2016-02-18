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
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
</head>
<script type="text/javascript">
	
	function weSendMail() {
		alert("회원님의 메일로 링크가 발송되었습니다!");
	}
	
</script>
<body class="loginPage_bg">
	<div class="loginComp_bg" style="height: 250px; margin-top: 10%">
		<div class="title" style="margin-top: 40px;">
			Email로 비밀번호 찾기			
		</div>
		<form id="loginfrm" method="POST" action="${initParam.root}sendLinkToGetTemporaryPassword.do" onsubmit="weSendMail()">
			<input type="text" name="memberEmail" placeholder="가입하신 이메일 주소를 적어주세요" style="width: 250px; margin-top:20px;" id="memberEmail"><br>
			<input type="submit" class="loginButton" value="링크 보내기" style="margin-top:20px;" > 
		</form>
		<br>
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