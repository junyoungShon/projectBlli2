<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- 카카오로그인 스크립트 -->
<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
<script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
<script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
</head>
<body>
<form action="${initParam.root}search_jsoupTest.do">
<h1>이곳은 인덱스입니다 아무나 들어올 수 있죠.</h1>
	<sec:authorize access="isAuthenticated()">
		<sec:authentication property="name"/>
		${memberId} 님 환영해요
		${sessionScope.SPRING_SECURITY_CONTEXT}
	</sec:authorize>
	<ul>
		<li><a href="${initParam.root}member_goMain.do">goMain.do</a>
		<li><a href="${initParam.root}admin_goAdminPage.do">goAdminPage.do</a>
		<li><a href="${initParam.root}schedule_jsoupTest.do">포스팅 리스트 긁어모아</a>
		<li><input type = "text" name="searchWord"><input type="submit" value="검색">
	<sec:authorize access="isAuthenticated()">
		<li><a href="${initParam.root}j_spring_security_logout">로그아웃</a>
	</sec:authorize>
		<li><a href="${initParam.root}goJoinMemberPage.do">회원가입</a>
		 <a id="kakao-login-btn"></a>

    <script>
      // 사용할 앱의 JavaScript 키를 설정해 주세요.
      Kakao.init('7e613c0241d9f07553638f04b7df66ef');

      // 카카오 로그인 버튼을 생성합니다.
      Kakao.Auth.createLoginButton({
        container: '#kakao-login-btn',
        success: function(authObj) {
        	// 로그인 성공시, API를 호출합니다.
            Kakao.API.request({
              url: '/v1/user/me',
              success: function(res) {
                var kakaoId = res.id;
                var kakaoNickName = res.properties.nickname;
                $.ajax({
                	type:"get",
                	url:"findMemberByKakaoId.do?memberId="+kakaoId,
                	success:function(result){
                		if(result==true){
                			//로그인
                			alert(result);
                		}else{
                			//post로 이동하기 위한 form생성 및 전송
                			var form = document.createElement('form');
                			var inputMemberId;
							var inputMemberName;
							inputMemberId = document.createElement('input');
							inputMemberId.setAttribute('type', 'hidden');
							inputMemberId.setAttribute('name', 'memberId');
							inputMemberId.setAttribute('value', kakaoId);
							inputMemberName = document.createElement('input');
							inputMemberName.setAttribute('type', 'hidden');
							inputMemberName.setAttribute('name', 'memberName');
							inputMemberName.setAttribute('value', kakaoNickName);
                			form.appendChild(inputMemberId);
                			form.appendChild(inputMemberName);
                			form.setAttribute('method', 'post');
                			form.setAttribute('action', "${initParam.root}goJoinMemberPageByKakaoId.do");
                			document.body.appendChild(form);
                			form.submit();
                			alert(result);
                		}
                	}
                });
              },
              fail: function(error) {
                alert(JSON.stringify(error))
              }
            });       
        },
        fail: function(err) {
          alert(JSON.stringify(err))
        }
      });
    </script>
	</ul>
</form>
</body>
</html>
