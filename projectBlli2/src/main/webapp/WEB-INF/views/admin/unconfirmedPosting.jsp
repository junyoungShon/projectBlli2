<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
	.product_text{
		width:800px;
		height:150px;
		background:#f7f7f7;
		padding:10px;
		font-family:'Nanum Barun Gothic';
		margin-left:10px;
		line-height:20px;
		overflow-y:scroll;
	}
</style>
<script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
<script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		
		$("#confirmBtn").click(function(){
			if($("input:radio:checked").length == 0){
				alert("체크 안했다");
				return false;
			}
			if(confirm("확실해?")){
				var array = [];
				for(var i=0;i<"${fn:length(requestScope.resultList.postingList)}";i++){
					// 선택한 소제품과 postingUrl을 배열에 저장
				    array.push({"smallProduct": $("input:radio[name='"+i+"']:checked").next().text(), 
				    	"postingUrl": $("input:radio[name='"+i+"']:checked").val()});
				}
				var json_data=JSON.stringify(array);
				$.ajax({
					url:"registerPosting.do",
					type:"POST",
					dataType:"json",
					data:json_data,
					contentType:"application/json; charset=UTF-8",
					success:function(){
						alert("완료!");
						location.reload(true);
					},
					error:function(jqXHR, textStatus, errorThrown){
			            alert("에러 발생~~ \n" + textStatus + " : " + errorThrown);
			        }
				});
			}
		});
		
		$("#cancel").click(function(){
			if(confirm("취소할거니?")){
				location.href="${initParam.root}index.do";
			}
		});
		
	});
</script>
</head>
<body>

<table width="70%" align="center" cellpadding="10">
<tr>
	<td colspan="2">
		<c:choose>
		<c:when test="${requestScope.resultList.pagingBean.nowPage < requestScope.resultList.pagingBean.totalPage }">
		<strong>포스팅</strong> 
		${requestScope.resultList.pagingBean.nowPage*5-4} - ${requestScope.resultList.pagingBean.nowPage*5} / 
		${requestScope.resultList.pagingBean.totalPosting}건
		</c:when>
		<c:otherwise>
		<strong>포스팅</strong> 
		${requestScope.resultList.pagingBean.nowPage*5-4} - ${requestScope.resultList.pagingBean.totalPosting} / 
		${requestScope.resultList.pagingBean.totalPosting}건
		</c:otherwise>
		</c:choose>
	</td>
</tr>
<c:forEach items="${requestScope.resultList.postingList}" var="postingList" varStatus="count">
	<tr>
		<td style="border-bottom: dotted; border-bottom-color: silver;"><h3><strong>
		<a href="${postingList.postingUrl}" style="text-decoration:none; color: black;">${postingList.postingTitle}</a>
		</strong></h3></td>
		<td><p align="right"><input type="radio" name="${count.index}" value="${postingList.postingUrl}">
		<span><strong>삭제</strong></span></p></td>
	</tr>
	<tr>
		<td>
			<c:forEach items="${postingList.imageList}" var="imgList">
			<img src="http://t1.daumcdn.net/thumb/R1024x0/?fname=${imgList}" width="190px" height="150px">
			</c:forEach>
			<div class="product_text">${postingList.postingContent}</div>
		</td>
		<td style="background-color: #f7f7f7;">
			<input type="radio" name="${count.index}" value="${postingList.postingUrl}"><span>${postingList.smallProduct}</span>
			<br><br>
			<c:forEach var="map" items="${postingList.smallProductImage}">
				<!-- 해당 소제품의 대표 이미지 찾아서 보여줌 -->
				<c:if test="${map.key == postingList.smallProduct}">
					<img src="${map.value}"><br><br>
				</c:if>
			</c:forEach>
		</td>
	</tr>
</c:forEach>
</table>

<p align="center">
	<c:set var="pb" value="${requestScope.resultList.pagingBean}"></c:set>
	<c:if test="${pb.previousPageGroup}">
		<a href="${initParam.root}unconfirmedPosting.do?pageNo=${pb.startPageOfPageGroup-1}">Prev</a>
	</c:if>
	<c:forEach var="i" begin="${pb.startPageOfPageGroup}" end="${pb.endPageOfPageGroup}">
		<c:choose>
			<c:when test="${pb.nowPage!=i}">
				<a href="${initParam.root}unconfirmedPosting.do?pageNo=${i}">${i}</a>
			</c:when>
			<c:otherwise>
				${i}
			</c:otherwise>
		</c:choose>
	</c:forEach>
	<c:if test="${pb.nextPageGroup}">
		<a href="${initParam.root}unconfirmedPosting.do?pageNo=${pb.endPageOfPageGroup+1}">Next</a>
	</c:if>
</p>

<p align="right"><input type="button" id="confirmBtn" value="포스팅 등록" style="font-size:15px;width: 100px;height: 45px;">
<input type="button" id="cancel" value="취소" style="font-size:15px;width: 100px;height: 45px;">
</p>

</body>
</html>