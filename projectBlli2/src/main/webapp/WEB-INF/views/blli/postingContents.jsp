<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html style="height: 100%;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>Insert title here</title>
</head>
<link rel="stylesheet" type="text/css" href="./css/reset.css" />
<link rel="stylesheet" type="text/css" href="./css/css.css" />
<link href='https://cdn.rawgit.com/openhiun/hangul/14c0f6faa2941116bb53001d6a7dcd5e82300c3f/nanumbarungothic.css' rel='stylesheet' type='text/css'>
<script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
<script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		
		var jbOffset = $( '.jbMenu' ).offset();
		$( window ).scroll( function() {
		  if ( $( document ).scrollTop() > jbOffset.top ) {
			$( '.jbMenu' ).addClass( 'jbFixed' );
		  }
		  else {
			$( '.jbMenu' ).removeClass( 'jbFixed' );
		  }
		});
		
		
		//뒤로 가기 버튼 클릭 시 뒤로 간다.
		$('.backBtn').click(function(){
			history.back();
		});
		
		//사용자가 페이지를 벗어나면 체류시간을 기록한다.
		 $(window).on("beforeunload", function(){
		        //체류시간 체크를 위해 새로운 시간 객체 생성
				
		 });
	});
	
	
</script>


		

<body style="height: 100%; overflow-y: hidden">
	    <div class="jbMenu">
      <div class="in_fr">
		<a href="/main.html"><img src="./img/top_logo.png" alt="탑로고" class="logo"></a>
		<div class="top_blog">
			<div style='width:450px; overflow:hidden;white-space:nowrap; text-overflow:ellipsis;'>
				${requestScope.blliPostingVO.postingTitle}
			</div>
			
		</div>
		<div class="top_blog2">
			<a href="#"><img src="./img/bt_blogbuy.jpg" alt="구매하러가기" style="margin-right:30px;"></a>
			<img src="./img/icon_share2.jpg" alt="공유하기">
			<span>6</span>
			<img src="./img/icon_like2.jpg" alt="좋아요">
			<span>6</span>
			<img src="./img/icon_hate2.jpg" alt="싫어요">
			<span>6</span>
			<a href="#"><img src="./img/icon_back.jpg" alt="뒤로가기"></a>
			<a href="#"><img src="./img/icon_x.jpg" alt="취소하기"></a>
		</div>
	  </div>
</div>
<iframe id="socialbar-frame" name="socialbar-frame" height="94%" width="100%" src="${requestScope.blliPostingVO.postingUrl}" frameborder="0"></iframe>
</body>
</html>