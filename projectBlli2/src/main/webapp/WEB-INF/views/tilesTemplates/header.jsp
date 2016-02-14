<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:if test="${requestScope['javax.servlet.forward.request_uri']!='/projectBlli2/member_goMain.do'}">
<form action="${initParam.root}searchSmallProduct.do" method="get">
<div class="jbMenu">
      <div class="in_fr">
		<a href="${initParam.root}member_goMain.do"><img src="${initParam.root}img/top_logo.png" alt="탑로고" class="logo" style="margin-top:-6px"></a>
		<div class="top_search">
			<input type="text" class="search_text" placeholder="검색어를 입력하세요" name="searchWord">
			<a href="#" onclick="$(this).closest('form').submit()"><img src="${initParam.root}img/search.png" alt="검색"></a>
		</div>
		<div class="top_nav">
			<a href="${initParam.root}goModifyMemberInfoPage.do">회원정보수정</a>   ㅣ   
				<a href="${initParam.root}goModifyBabyInfoPage.do">아이정보수정</a>   ㅣ  
			 <a href="#">스크랩</a>
			ㅣ <a href="#">알림</a> ㅣ <a href="#">아이일정</a> ㅣ <a href="${initParam.root}logout.do">로그아웃</a>
		</div>
	</div>
	</div>
</form>
</c:if>
<c:if test="${requestScope['javax.servlet.forward.request_uri']=='/projectBlli2/member_goMain.do'}">
<form action="${initParam.root}searchSmallProduct.do" method="get">
<div class="main_top">
		<div class="in_fr">
			<div class="top_nav">
				<a href="${initParam.root}goModifyMemberInfoPage.do">회원정보수정</a>   ㅣ   
				<a href="${initParam.root}goModifyBabyInfoPage.do">아이정보수정</a>   ㅣ  
				<a href="#">스크랩</a>   ㅣ   <a href="#">알림</a>   ㅣ   
				<a href="${initParam.root}goCalenderPage.do">아이일정</a> ㅣ
				<a href="${initParam.root}logout.do">로그아웃</a>
			</div>
			<div class="main_logo">
				<a href="#"><img src="${initParam.root}img/main_logo.png" alt="로고" style="margin-top:-6px"></a>
				<input type="text" class="search_text2" placeholder="검색어를 입력하세요" name="searchWord">
			<a href="#" onclick="$(this).closest('form').submit()"><img src="${initParam.root}img/search.png" alt="검색"></a>
			</div>
			
		</div>
</div>
</form>
<form action="${initParam.root}searchSmallProduct.do" method="get">
   <div class="jbMenu">
	<div class="in_fr">
		<a href="${initParam.root}member_goMain.do"><img src="${initParam.root}img/top_logo.png" style="margin-top:-6px" alt="탑로고"
			class="logo"></a>
		<div class="top_search">
			<input type="text" class="search_text" placeholder="검색어를 입력하세요" name="searchWord">
			<a href="${initParam.root}searchSmallProduct.do" onclick="$(this).closest('form').submit()"><img src="${intiParam.root}img/search.png" alt="검색"></a>
		</div>
		<div class="top_nav">
			<a href="#">회원정보수정</a> ㅣ <a href="#">아이정보수정</a> ㅣ <a href="#">스크랩</a>
			ㅣ <a href="#">알림</a> ㅣ <a href="#">아이일정</a>ㅣ <a href="${initParam.root}logout.do">로그아웃</a>
		</div>
	</div>
</div>
</form>
</c:if>
