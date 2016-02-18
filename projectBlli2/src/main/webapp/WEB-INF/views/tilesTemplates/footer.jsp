<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script>
$(document).ready(function(){
	$.ajax({
		type:"get",
		url:"footerStatics.do",
		success:function(data){
			$('.footerProductStatics').text(data.productStatics);
			$('.footerPostingStatics').text(data.postingStatics);
		}
	});
});
</script>
<footer style="width: 100% ; float: left; text-align: center; background-color: ghostwhite; z-index:1;">
	<div class="fl login_bottom_ft">
			</div>
	
	<p style="margin-top: 20px; margin-bottom: 10px">
			<font color="black" size="2px">	블리는 <span class="footerProductStatics"></span>개의 상품을 소개하고, 관련된 <span class="footerPostingStatics"></span>개의 블로그를 분석하고 소개합니다.</font>
	</p>
	<p style="margin-bottom: 20px"> &copy;Blli Company 2016· 블로그리스트 소개 · 이용약관 · 개인정보취급방침 · 문의</p>
	
			<%-- <div class="fr">
				<a href="${initParam.root}adminIndex.do"><img src="./img/bottom_app1.png" alt="안드로이드 다운로드받기"></a>
				<a href="#"><img src="./img/bottom_app2.png" alt="애플 다운로드받기"></a>
			</div> --%>
</footer>

<script>
(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
})(window,document,'script','//www.google-analytics.com/analytics.js','ga');

ga('create', 'UA-72734813-1', {'cookieDomain': 'none'});
ga('send', 'pageview');

</script>
