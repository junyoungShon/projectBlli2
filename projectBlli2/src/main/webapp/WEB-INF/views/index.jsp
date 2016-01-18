<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
<script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<!-- 카카오로그인 스크립트 -->
<script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
<!-- 네이버 로그인 용 스크립트 -->
<script type="text/javascript" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.2.js"></script>
</head>
<body>

 	<sec:authorize access="hasAnyRole('ROLE_USER_KAKAO','ROLE_USER_EMAIL')">
	<script type="text/javascript">
		location.href='${initParam.root}member_proceedingToMain.do'
	</script>
</sec:authorize>

<!-- 아이정보를 입력하지 않은 자동로그인 사용자 && 이미 로그인한 유저의 경우 authorityCheck.jsp로 가서 아이정보 입력 -->
<sec:authorize access="hasAnyRole('ROLE_RESTRICTED_KAKAO','ROLE_RESTRICTED_EMAIL','ROLE_RESTRICTED_NAVER')">
	<script type="text/javascript">
		location.href='${initParam.root}authorityCheck.do'
	</script>
</sec:authorize>

<!-- 자동로그인 된 관리자의 경우 관리자 페이지로 이동합니다. -->
<sec:authorize access="hasRole('ROLE_ADMIN')">
	<script type="text/javascript">
		location.href='${initParam.root}authorityCheck.do'
	</script>
</sec:authorize>

<form action="${initParam.root}search_jsoupTest.do">
<h1>이곳은 인덱스입니다 비회원 및 비인증회원만 접근할 수 있어요.</h1>
	<sec:authorize access="isAnonymous()">
		저희 블리는 회원 가입 및 아이정보를 입력해야만 이용 하 실 수 있습니다. 소셜 계정 및 이메일 등을 통해서 가입해주세요
	</sec:authorize>
	<ul>
		<li><a href="${initParam.root}member_goMain.do">goMain.do</a>
		<li><a href="${initParam.root}admin_goAdminPage.do">goAdminPage.do</a>
		<li><a href="${initParam.root}schedule_jsoupTest.do">포스팅 리스트 긁어모아</a>
		<li><input type = "text" name="searchWord"><input type="submit" value="검색">
	<sec:authorize access="isAuthenticated()">
		<li><a href="${initParam.root}j_spring_security_logout">로그아웃</a>
	</sec:authorize>
	
		<li><a href="${initParam.root}goJoinMemberPage.do">회원가입</a><br>
		 <a id="kakao-login-btn"></a>
		<div id="naver_id_login"></div>
		<!-- 네이버 로그인 버튼 -->
		<script type="text/javascript">
			var naver_id_login = new naver_id_login("vy4NpIx3E_02LT8vXvkh", "http://bllidev.dev/projectBlli2/");
			naver_id_login.setButton("white", 3,50);
			naver_id_login.setState("abcdefghijkmnopqrst");
			naver_id_login.init_naver_id_login();
			
			
			//get_naver_userprofile 동작후 callback 될 function
			function naverUserInfoCallBack(){
			    var naverMail = naver_id_login.getProfileData('email')
			    var naverAge = naver_id_login.getProfileData('age')
			    var naverName = naver_id_login.getProfileData('name');
			    $.ajax({
                	type:"get",
                	url:"findMemberByKakaoId.do?memberId=naver_"+naverMail,
                	success:function(result){
                		if(result==true){
                			location.href="${initParam.root}loginByKakaoId.do?memberId=naver_"+naverMail
                		}else{
                			//post로 이동하기 위한 form생성 및 전송
                			var form = document.createElement('form');
                			var inputMemberId;
							var inputMemberName;
							inputMemberId = document.createElement('input');
							inputMemberId.setAttribute('type', 'hidden');
							inputMemberId.setAttribute('name', 'memberId');
							inputMemberId.setAttribute('value', naverMail);
							inputMemberName = document.createElement('input');
							inputMemberName.setAttribute('type', 'hidden');
							inputMemberName.setAttribute('name', 'memberName');
							inputMemberName.setAttribute('value', naverName);
                			form.appendChild(inputMemberId);
                			form.appendChild(inputMemberName);
                			form.setAttribute('method', 'post');
                			form.setAttribute('action', "${initParam.root}joinMemberByNaverMail.do");
                			document.body.appendChild(form);
                			form.submit();
                			alert(result);
                		}
                	}
                });
			}
			naver_id_login.get_naver_userprofile("naverUserInfoCallBack()");
			
		</script>
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
                	url:"findMemberByKakaoId.do?memberId=kakao_"+kakaoId,
                	success:function(result){
                		if(result==true){
                			location.href="${initParam.root}loginByKakaoId.do?memberId=kakao_"+kakaoId
                		}else{
                			//post로 이동하기 위한 form생성 및 전송
                			var form = document.createElement('form');
                			var inputMemberId;
							var inputMemberName;
							inputMemberId = document.createElement('input');
							inputMemberId.setAttribute('type', 'hidden');
							inputMemberId.setAttribute('name', 'memberId');
							inputMemberId.setAttribute('value', "kakao_"+kakaoId);
							inputMemberName = document.createElement('input');
							inputMemberName.setAttribute('type', 'hidden');
							inputMemberName.setAttribute('name', 'memberName');
							inputMemberName.setAttribute('value', kakaoNickName);
                			form.appendChild(inputMemberId);
                			form.appendChild(inputMemberName);
                			form.setAttribute('method', 'post');
                			form.setAttribute('action', "${initParam.root}joinMemberByKakaoId.do");
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
	<script>
  // This is called with the results from from FB.getLoginStatus().
  function statusChangeCallback(response) {
    console.log('statusChangeCallback');
    console.log(response);
    // response 객체는 현재 로그인 상태를 나타내는 정보를 보여준다. 
    // 앱에서 현재의 로그인 상태에 따라 동작하면 된다.
    // FB.getLoginStatus().의 레퍼런스에서 더 자세한 내용이 참조 가능하다.
    if (response.status === 'connected') {
      // 페이스북을 통해서 로그인이 되어있다.
      testAPI();
    } else if (response.status === 'not_authorized') {
      // 페이스북에는 로그인 했으나, 앱에는 로그인이 되어있지 않다.
      document.getElementById('status').innerHTML = 'Please log ' +
        'into this app.';
    } else {
      // 페이스북에 로그인이 되어있지 않다. 따라서, 앱에 로그인이 되어있는지 여부가 불확실하다.
      document.getElementById('status').innerHTML = 'Please log ' +
        'into Facebook.';
    }
  }
 
  // 이 함수는 누군가가 로그인 버튼에 대한 처리가 끝났을 때 호출된다.
  // onlogin 핸들러를 아래와 같이 첨부하면 된다.
  function checkLoginState() {
    FB.getLoginStatus(function(response) {
      statusChangeCallback(response);
    });
  }
 
  window.fbAsyncInit = function() {
  FB.init({
    appId      : '{476938162497817}',
    cookie     : true,  // 쿠키가 세션을 참조할 수 있도록 허용
    xfbml      : true,  // 소셜 플러그인이 있으면 처리
    version    : 'v2.1' // 버전 2.1 사용
  });
 
  // 자바스크립트 SDK를 초기화 했으니, FB.getLoginStatus()를 호출한다.
  //.이 함수는 이 페이지의 사용자가 현재 로그인 되어있는 상태 3가지 중 하나를 콜백에 리턴한다.
  // 그 3가지 상태는 아래와 같다.
  // 1. 앱과 페이스북에 로그인 되어있다. ('connected')
  // 2. 페이스북에 로그인되어있으나, 앱에는 로그인이 되어있지 않다. ('not_authorized')
  // 3. 페이스북에 로그인이 되어있지 않아서 앱에 로그인이 되었는지 불확실하다.
  //
  // 위에서 구현한 콜백 함수는 이 3가지를 다루도록 되어있다.
 
    FB.getLoginStatus(function(response) {
      statusChangeCallback(response);
    });
 
  };
 
  // SDK를 비동기적으로 호출
  (function(d, s, id) {
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) return;
    js = d.createElement(s); js.id = id;
    js.src = "//connect.facebook.net/en_US/sdk.js";
    fjs.parentNode.insertBefore(js, fjs);
  }(document, 'script', 'facebook-jssdk'));
 
  // 로그인이 성공한 다음에는 간단한 그래프API를 호출한다.
  // 이 호출은 statusChangeCallback()에서 이루어진다.
  function testAPI() {
    console.log('Welcome!  Fetching your information.... ');
    FB.api('/me', function(response) {
      console.log('Successful login for: ' + response.name);
      document.getElementById('status').innerHTML =
        'Thanks for logging in, ' + response.name + '!';
    });
  }
</script>
 
<!--
  아래는 소셜 플러그인으로 로그인 버튼을 넣는다.
  이 버튼은 자바스크립트 SDK에 그래픽 기반의 로그인 버튼을 넣어서 클릭시 FB.login() 함수를 실행하게 된다.
-->
 
<fb:login-button scope="public_profile,email" onlogin="checkLoginState();">
</fb:login-button>
 
<div id="status">
</div>
</body>
</html>
