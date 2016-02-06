<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>블리 - 충동구매보다 빠른 합리적 쇼핑!</title>
<link href="${initParam.root}img/favicon/favicon.ico" rel="shortcut icon" type="image/x-icon" />
<link href="${initParam.root}css/font-awesome.min.css" rel="stylesheet" />
 <!-- 아이콘 만들기 api font-awesome -->
</head>
<script type="text/javascript">  
var midPageTimer = setTimeout("exitMidPage()", 1500);

function exitMidPage() {
	setTimeout(function(){
		var targetUrl = '${requestScope.targetURL}'
		location.href = targetUrl;
		var aLinkBtn = document.createElement("a"); 
		aLinkBtn.href = targetUrl; 
		document.body.appendChild(aLinkBtn); 
		aLinkBtn.click(); 

	}, 500);
}

function layOverMidPage() {
	clearTimeout(midPageTimer);
}
</script>

<body style="width: 100%; margin:0px; padding:0px;'">
<div style="height: 30%; ">
</div>
	<div style="height: 40%; background-color: #ffd347; text-align: center;">
		<img alt="" src="${initParam.root}img/midPage.png" align="middle" style="margin-top: 10px;">
		<div style="margin-top: 10px; font-size:medium; color: brown; font-weight: bold;">
			 네이버 지식쇼핑을 경유하여,<br>
			판매사이트인 ${reqeustScope.blliBuyLinkClickVO.seller } (으)로 이동 중입니다.<br>
		<i class="fa fa-play" style="color: #655666 ; margin-top: 30px; margin-right: 5px;" onclick="exitMidPage()"></i>
		<i class="fa fa-pause" style="color: #655666;margin-left: 5px; margin-top: 30px" onclick="layOverMidPage()"></i>
		</div>
		<img alt="" src="${initParam.root}img/midPage2.png" align="middle" style="margin-top: 40px;">
	</div>
<div style="height: 30% background-color: gray; text-align: center; padding-top: 30px;">
<font color="gray" size="2px">Copyright&copy;<strong>Blli Company</strong> 2016, All Right Reserved.</font>
<script>
(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
})(window,document,'script','//www.google-analytics.com/analytics.js','ga');

ga('create', 'UA-72734813-1', {'cookieDomain': 'none'});
ga('send', 'pageview');

</script>
</div>
</body>
</html>