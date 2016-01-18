<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	이곳은 권한없음(403) 오류가 나면 넘어오는 페이지 입니다.
	<script type="text/javascript">
		location.href='authorityCheck.do'
	</script>
</body>
</html>