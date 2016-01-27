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
<a href="iframeTest.do">아이프레임테스트</a>
 	<sec:authorize access="hasAnyRole('ROLE_USER')">
	<script type="text/javascript">
		location.href='${initParam.root}member_proceedingToMain.do'
	</script>
</sec:authorize>

<!-- 아이정보를 입력하지 않은 자동로그인 사용자 && 이미 로그인한 유저의 경우 authorityCheck.jsp로 가서 아이정보 입력 -->
<sec:authorize access="hasAnyRole('ROLE_RESTRICTED')">
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
		<li><a href="${initParam.root}member_calender.do">calender.do</a>
		<li><a href="${initParam.root}insert_big_category.do">대분류 리스트 긁어모아</a>
		<li><a href="${initParam.root}insert_mid_category.do">중분류 리스트 긁어모아</a>
		<li><a href="${initParam.root}insert_small_product.do">소분류 리스트 긁어모아</a>
		<li><a href="${initParam.root}schedule_jsoupTest.do">포스팅 리스트 긁어모아</a>
		<li><input type = "text" name="searchWord"><input type="submit" value="검색">
		<li><a href="${initParam.root}postingListWithSmallProducts.do">소제품 하나로 추려줘</a></li>
		<li><a href="${initParam.root}unconfirmedPosting.do">포스팅 등록해줘</a></li>
	<sec:authorize access="isAuthenticated()">
		<li><a href="${initParam.root}j_spring_security_logout">로그아웃</a>
	</sec:authorize>
	
		<li><a href="${initParam.root}goJoinMemberPage.do">회원가입</a><br>
		 <a id="kakao-login-btn"></a>
		<div id="naver_id_login"></div>
		<!-- 네이버 로그인 버튼 -->
		<script type="text/javascript">
		
		//sns 공통 로그인 function
		function snsLogin(memberId,memberName,whichChannel){
			$.ajax({
            	type:"get",
            	url:"findMemberBySNSId.do?memberId="+whichChannel+"_"+memberId,
            	success:function(result){
            		if(result==true){
            			location.href="${initParam.root}loginBySNSId.do?memberId="+whichChannel+"_"+memberId
            		}else{
            			location.href=
            				"${initParam.root}joinMemberBySNS.do?memberId="+whichChannel+"_"+memberId+"&memberName="+memberName
            		}
            	}
            });
		}
		
		//네이버 아이디로 로그인 시작
			var naver_id_login = new naver_id_login("vy4NpIx3E_02LT8vXvkh", "http://bllidev.dev/projectBlli2/");
			naver_id_login.setButton("green", 3,80);
			naver_id_login.setState("abcdefghijkmnopqrst");
			naver_id_login.init_naver_id_login();
			//get_naver_userprofile 동작후 callback 될 function
			function naverUserInfoCallBack(){
				var naverMail = naver_id_login.getProfileData('email')
				var naverName = naver_id_login.getProfileData('name');
				snsLogin(naverMail,naverName,'naver');
			}
			naver_id_login.get_naver_userprofile("naverUserInfoCallBack()");
			//네이버 아이디로 로그인 끝
      //카카오 아이디로 로그인 시작
     	Kakao.init('7e613c0241d9f07553638f04b7df66ef');
      function kakaoLogin(){
        // 로그인 성공시, API를 호출합니다.
        Kakao.Auth.login({
	        success: function(authObj) {
	        	Kakao.API.request({
	                url: '/v1/user/me',
	                success: function(authObj) {
	                	 var kakaoId = authObj.id;
	                 	var kakaoNickName = authObj.properties.nickname;
	                 	snsLogin(kakaoId, kakaoNickName, 'kakao')
	                },
	                fail: function(error) {
                  alert(JSON.stringify(error))
               }
             });
        },
        fail: function(error) {
        	alert(JSON.stringify(error))
        }
        });       
      }
      //카카오 아이디로 로그인 끝
    
	//페이스북 로그인 시작    
  
		  function checkLoginState() {
			  FB.login(function(response) {
				    if (response.authResponse) {
				     FB.api('/me', function(response) {
				    	 snsLogin(response.id, response.name, 'fb');
				     });
				    } else {
				     alert('페이스북으로 로그인하기를 취소하셨습니다!');
				    }
				}, {scope: 'email,user_likes'});
		  }
			  window.fbAsyncInit = function() {
			  FB.init({
			    appId      : '{476938162497817}',
			    cookie     : true,  // 쿠키가 세션을 참조할 수 있도록 허용
			    xfbml      : true,  // 소셜 플러그인이 있으면 처리
			    version    : 'v2.5' // 버전 2.1 사용
			  });
			  
			    FB.getLoginStatus(function(response) {
			      statusChangeCallback(response);
			    });
			    
				};
		  // SDK를 비동기적으로 호출
		(function(d, s, id) {
		var js, fjs = d.getElementsByTagName(s)[0];
		    if (d.getElementById(id)) return;
		    js = d.createElement(s); js.id = id;
		    js.src = "//connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.5&appId=476938162497817";
		    fjs.parentNode.insertBefore(js, fjs);
		}(document, 'script', 'facebook-jssdk'));
	
    </script>
	</ul>
	
</form>
<!--
  아래는 소셜 플러그인으로 로그인 버튼을 넣는다.
  이 버튼은 자바스크립트 SDK에 그래픽 기반의 로그인 버튼을 넣어서 클릭시 FB.login() 함수를 실행하게 된다.
-->


<a href="#" onclick="kakaoLogin()">
<img src="${initParam.root}/image/login_with_kakao.png" style="width: 300px;height: 100px"></a>
<div class="fb-login-button" onclick="checkLoginState()">
<img src="${initParam.root}/image/login_with_fb.png"></div>
</body>
<script>
(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
})(window,document,'script','//www.google-analytics.com/analytics.js','ga');

ga('create', 'UA-72734813-1', {'cookieDomain': 'none'});
ga('send', 'pageview');

</script>
</html>
