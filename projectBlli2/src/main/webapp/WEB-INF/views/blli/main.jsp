<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
여기는 메인페이쟈
${sessionScope.blliMemberVO.memberName}님 환영합니다.<br>
당신의 아이 정보입니다.
${sessionScope.blliMemberVO.babyList}
</body>
</html>