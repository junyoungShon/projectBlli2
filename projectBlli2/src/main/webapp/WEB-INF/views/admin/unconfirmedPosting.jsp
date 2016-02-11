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
		var urlAndImage = [];
		var flag = true;
		
		$(".deleteBtn").click(function(){
			$(this).parent().prev().children("img").css("border", "0px");
			if(urlAndImage.length == 0){
				urlAndImage.push({"postingUrl": $(this).children().eq(0).val(), "postingPhotoLink": "", "smallProductId": $(this).children().eq(0).attr("class"), "del": "YES"});
			}else{
				for(var i=0;i<urlAndImage.length;i++){
					if(urlAndImage[i].postingUrl == $(this).children().eq(0).val()){
						urlAndImage[i].del = "YES";
						flag = false;
						break;
					}
				}
				if(flag){
					urlAndImage.push({"postingUrl": $(this).children().eq(0).val(), "postingPhotoLink": "", "smallProductId": $(this).children().eq(0).attr("class"), "del": "YES"});
				}
				flag = true;
			}
		});
		
		$(".mainImage").click(function(){
			$(this).parent().children("img").css("border", "0px");
			$(this).css("border", "5px solid red");
			$(this).parent().next().children("label").children().first().prop("checked", false);
			if(urlAndImage.length == 0){
				urlAndImage.push({"postingUrl": $(this).attr("name"), "postingPhotoLink": $(this).attr("id"), "smallProductId": $(this).next().text(), "del": "NO"});
			}else{
				for(var i=0;i<urlAndImage.length;i++){
					if(urlAndImage[i].postingUrl == $(this).attr("name")){
						urlAndImage[i].postingPhotoLink = $(this).attr("id");
						urlAndImage[i].del = "NO";
						flag = false;
						break;
					}
				}
				if(flag){
					urlAndImage.push({"postingUrl": $(this).attr("name"), "postingPhotoLink": $(this).attr("id"), "smallProductId": $(this).next().text(), "del": "NO"});
				}
				flag = true;
			}
		});
		
		$("#confirmBtn").click(function(){
			if(urlAndImage.length.length == 0){
				alert("대표 이미지 사진을 선택하세요!");
				return false;
			}
			if(confirm("확실합니까?")){
				var json_data=JSON.stringify(urlAndImage);
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
			if(confirm("취소하시겠습니까?")){
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
<c:forEach items="${requestScope.resultList.list}" var="postingList" varStatus="count">
	<tr>
		<td colspan="2" style="border-bottom: dotted; border-bottom-color: silver;"><h3><strong>
		<a href="${postingList.postingUrl}" style="text-decoration:none; color: black;">${postingList.postingTitle}</a>
		</strong></h3></td>
	</tr>
	<tr>
		<td>
			<c:forEach items="${postingList.imageList}" var="imgList">
			<img src="http://t1.daumcdn.net/thumb/R1024x0/?fname=${imgList}" width="190px" height="150px" class="mainImage" id="${imgList}" name="${postingList.postingUrl}">
			<span style="display: none;">${postingList.smallProductId}</span>
			</c:forEach>
			<div class="product_text">${postingList.postingContent}</div>
		</td>
		<td style="background-color: #f7f7f7;">
			${postingList.smallProduct}
			<br><br>
			<c:forEach var="map" items="${postingList.smallProductImage}">
				<!-- 해당 소제품의 대표 이미지 찾아서 보여줌 -->
				<c:if test="${map.key == postingList.smallProduct}">
					<img src="${map.value}"><br><br>
				</c:if>
			</c:forEach>
			<hr>
			<label class="deleteBtn">
			<input type="radio" name="${count.index}" value="${postingList.postingUrl}" class="${postingList.smallProductId}">
			<span><strong>삭제</strong></span>
			</label>
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