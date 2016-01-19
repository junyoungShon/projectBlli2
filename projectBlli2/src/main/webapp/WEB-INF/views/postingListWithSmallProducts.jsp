<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
<script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#confirmBtn").click(function(){
		var array = [];
		for(var i=0;i<"${fn:length(requestScope.resultList)}";i++){
			// 선택한 소제품과 postingUrl을 배열에 저장
			array.push({"smallProduct": $("input:radio[name='"+i+"']:checked").next().text(), "postingUrl": $("input:radio[name='"+i+"']:checked").val()});
		}
		var json_data=JSON.stringify(array);
		$.ajax({
			url:"selectProduct.do",
			type:"POST",
			dataType:"json",
			data:json_data,
			contentType:"application/json; charset=UTF-8",
			success:function(){
				alert("완료!");
				location.href="${initParam.root}index.do";
			},
			error:function(jqXHR, textStatus, errorThrown){
	            alert("에러 발생~~ \n" + textStatus + " : " + errorThrown);
	        }
		});
	});
	$("#cancel").click(function(){
		location.href="${initParam.root}index.do";
	});
});
</script>
</head>
<body>
<table border="1" width="70%" align="center" cellpadding="10">
<c:forEach items="${requestScope.resultList}" var="postingList" varStatus="count">
	<tr>
		<td colspan="2"><h3><strong><a href="${postingList.postingUrl}" style="text-decoration:none; color: black;">${postingList.postingTitle}</a></strong></h3></td>
	</tr>
	<tr>
		<td>
		<c:forEach items="${postingList.imageList}" var="imgList">
		<img src="http://t1.daumcdn.net/thumb/R1024x0/?fname=${imgList}" width="200px" height="150px">
		</c:forEach>
		</td>
		<td>
		<c:forEach items="${postingList.smallProductList}" var="productList">
			<input type="radio" name="${count.index}" value="${postingList.postingUrl}"><span>${productList}</span><br><br>
			<c:forEach var="map" items="${postingList.smallProductImage}">
				<!-- 해당 소제품의 대표 이미지 찾아서 보여줌 -->
				<c:if test="${map.key == productList}">
					<img src="${map.value}"><br><br>
				</c:if>
			</c:forEach>
		</c:forEach>
		</td>
	</tr>
</c:forEach>
</table>
<p align="center"><input type="button" id="confirmBtn" value="소제품 확정" style="font-size:15px;width: 100px;height: 45px;">
<input type="button" id="cancel" value="취소" style="font-size:15px;width: 100px;height: 45px;">
</p>
</body>
</html>