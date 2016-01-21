<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
<script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		
		//중분류 추천 제거 클릭 시 추천 대상에서 제외
		$('.recommendMidDelete').click(function(){
			if(confirm('정말 삭제하시겠어요??')){
				//alert($(this).parent().siblings('.midCategory').text());
				//alert($(this).siblings('.categoryId').val());
				$.ajax({
					type:"get",
					url:"deleteRecommendMidCategory.do?midCategory="+$(this).parent().siblings('.midCategory').text()
							+"&memberId=${sessionScope.blliMemberVO.memberId}&categoryId="+$(this).siblings('.categoryId').val(),
					success:function(){
						alert("삭제완료!");
					}
				});
			}
		});
		//추천 받을 아이 바꾸기 메서드
		$('.babyChanger').change(function(){
			//alert($(this).val());
			$.ajax({
				type:"get",
				url:"changeRecommendingBaby.do?babyName="+$(this).val()
						+"&memberId=${sessionScope.blliMemberVO.memberId}",
				success:function(){
					alert("변경완료!");
					location.href='${initParam.root}member_goMain.do '
				}
			});
		});
		
	});
</script>
</head>
<body>
여기는 메인페이쟈
${sessionScope.blliMemberVO.memberName}님 환영합니다.<br>
<hr>

	<c:forEach items="${requestScope.blliMemberVO.blliBabyVOList}" var="babyList">
		<c:choose>
			<c:when test="${babyList.recommending==1}">
			현재 추천 아이는 ${babyList.babyName }입니다.<br>
			${babyList.babyMonthAge}달째 입니다.<br>
			</c:when>
		</c:choose>
	</c:forEach>
	추천 아이 바꾸기
<select class="babyChanger">
	<c:forEach items="${requestScope.blliMemberVO.blliBabyVOList}" var="babyList">
		<c:choose>
			<c:when test="${babyList.recommending==1}">
				<option value="${babyList.babyName}" selected="selected">${babyList.babyName}</option>
			</c:when>
			<c:otherwise>
				<option value="${babyList.babyName}" >${babyList.babyName}</option>
			</c:otherwise>		
		</c:choose>
	</c:forEach>
</select><br>
<hr>
<h1>추천 중분류 상품 입니다.</h1>
<table border="1" >
	<tr>
		<td>제품명</td><td>설명</td><td>이미지</td><td>누구꺼</td><td>사용시기</td><td>대분류</td><td>추천제외</td>
	</tr>
	<c:forEach items="${requestScope.blliMidCategoryVOList}" var="recommProductList">
		<tr>
			<td class="midCategory">${recommProductList.midCategory }</td><td>${recommProductList.midCategoryInfo}</td><td><img src='${recommProductList.midCategoryMainPhotoLink}'></td>
			<td>
				<c:forEach items="${requestScope.blliMemberVO.blliBabyVOList}" var="babyList">
					<c:if test="${babyList.babyMonthAge>=recommProductList.whenToUseMin && babyList.babyMonthAge<=recommProductList.whenToUseMax}">
						${babyList.babyName}
					</c:if>
				</c:forEach>
			</td>
			<td>${recommProductList.whenToUseMin}개월~${recommProductList.whenToUseMax}</td>
			<td>${recommProductList.bigCategory}</td>
			<td><input type="button" class="recommendMidDelete" value="삭제"><input type="hidden" value="${recommProductList.categoryId}" class="categoryId"></td>
		</tr>
	</c:forEach>
	
</table>
<hr>
<h1>또래 엄마들이 많이 찜한 상품들입니다.</h1>
<table border="1" >
	<tr>
		<td>제품명</td><td>설명</td><td>이미지</td><td>누구꺼</td><td>사용시기</td><td>대분류</td><td>추천제외</td>
	</tr>
		<tr>
			
		</tr>
	
</table>


</body>
</html>