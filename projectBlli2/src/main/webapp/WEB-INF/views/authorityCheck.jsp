<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- 본페이지는 로그인 페이지로서 Restricted멤버의 경우 아이 추가 페이지를 출력하며 비인증 회원의 경우 로그인 폼을 출력해줍니다. -->
	
	
	<!-- 회원가입페이지에 ROLE_USER접근 시 alert과 메인페이지 이동 -->
	<sec:authorize access="hasAnyRole('ROLE_USER_KAKAO','ROLE_USER_EMAIL','ROLE_RESTRICTED_NAVER')">
			alert('이미 로그인은 완료하셨습니다^^ 메인으로 이동할게요~');
			location.href=${initParam.root}+'member_proceedingToMain.do';
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
	
	<!-- 회원가입하였으나 인증하지 않은 사용자의 경우 아이정보 추가 폼이 출력된다.(카카오 아이디의 경우 이메일란 추가) -->
	<sec:authorize access="hasRole('ROLE_RESTRICTED_KAKAO')">

		<form id="insertBabyInfoForKakaoUser" name="insertBabyInfoForKakaoUser" method="post" 
			action="insertBabyInfoForKakaoUser.do">
			<input type="hidden" name="memberId" value="${sessionScope.blliMemberVO.memberId}">
			이메일 주소 <input type="email" name="memberEmail"> <br>
			
			아이이름 <input type="text" name="firstBabyName"><br>
			아이 성별 남자 <input type="radio" name="firstBabySex" value="남자"> 여자 <input type="radio" name="firstBabySex" value="여자"><br>
			아이 생일<input type="date" name="firstBabyBirthday"><br>
			
			아이이름 <input type="text" name="secondBabyName"><br>
			아이 성별 남자 <input type="radio" name="secondBabySex" value="남자"> 여자 <input type="radio" name="secondBabySex" value="여자"><br>
			아이 생일<input type="date" name="secondBabyBirthday"><br>
			
			아이이름 <input type="text" name="thirdBabyName"><br>
			아이 성별 남자 <input type="radio" name="thirdBabySex" value="남자"> 여자 <input type="radio" name="thirdBabySex" value="여자"><br>
			아이 생일<input type="date" name="thirdBabyBirthday"><br>
			
			<input type="submit" value="아이정보입력">
		</form>
	</sec:authorize>
	
	<!-- 회원가입하였으나 인증하지 않은 사용자의 경우 아이정보 추가 폼이 출력된다.(카카오 아이디의 경우 이메일란 추가) -->
		<sec:authorize access="hasAnyRole('ROLE_RESTRICTED_EMAIL','ROLE_RESTRICTED_NAVER')">
		<form id="insertBabyInfoForEmailUser" name="insertBabyInfoForEmailUser" method="post" 
			action="insertBabyInfoForEmailUser.do">
			<input type="hidden" name="memberId" value="${sessionScope.blliMemberVO.memberId}">
			아이이름 <input type="text" name="firstBabyName"><br>
			아이 성별 남자 <input type="radio" name="firstBabySex" value="남자"> 여자 <input type="radio" name="firstBabySex" value="여자"><br>
			아이 생일<input type="date" name="firstBabyBirthday"><br>
			
			아이이름 <input type="text" name="secondBabyName"><br>
			아이 성별 남자 <input type="radio" name="secondBabySex" value="남자"> 여자 <input type="radio" name="secondBabySex" value="여자"><br>
			아이 생일<input type="date" name="secondBabyBirthday"><br>
			
			아이이름 <input type="text" name="thirdBabyName"><br>
			아이 성별 남자 <input type="radio" name="thirdBabySex" value="남자"> 여자 <input type="radio" name="thirdBabySex" value="여자"><br>
			아이 생일<input type="date" name="thirdBabyBirthday"><br>
			
			<input type="submit" value="아이정보입력">
		</form>
	</sec:authorize>
</body>
</html>