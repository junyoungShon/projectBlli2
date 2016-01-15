<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<c:if test="${sessionScope.blliMemberVO.memberId }"></c:if>
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
</body>
</html>