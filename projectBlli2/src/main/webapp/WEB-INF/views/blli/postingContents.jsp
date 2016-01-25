<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
<script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		//도착시간 체크를 위해 
		var connectDate = new Date();
		var connectTime = connectDate.getTime();
		//뒤로 가기 버튼 클릭 시 뒤로 간다.
		$('.backBtn').click(function(){
			history.back();
		});
		
		//사용자가 페이지를 벗어나면 체류시간을 기록한다.
		 $(window).on("beforeunload", function(){
		        //체류시간 체크를 위해 새로운 시간 객체 생성
				var exitDate = new Date();
				var exitTime = exitDate.getTime();
				var residenceTime = Math.round((exitTime - connectTime)/1000);
				$.ajax({
					type:"get",
					url:"recordResidenceTime.do?postingUrl=${requestScope.blliPostingVO.postingUrl}&smallProductId=${requestScope.blliPostingVO.smallProductId}&postingTotalResidenceTime="+residenceTime,
					success:function(){
						history.back();
					}
				});
		 });
		
		     
		  
	});
</script>
<body style="height: 900px;">
<div class="topMenu" style="width: 100%;height: 10%">
	여기가 상단바 
	<input type="button" value="이전페이지가기" class="backBtn"><input type="hidden" >
</div>
<iframe id="socialbar-frame" name="socialbar-frame" height="90%" width="100%" src="${requestScope.blliPostingVO.postingUrl}" frameborder="0"></iframe>
</body>
</html>