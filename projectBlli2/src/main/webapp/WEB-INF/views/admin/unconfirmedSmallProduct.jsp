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
    .h-div {width:70%; margin-left: auto; margin-right: auto; position: relative; height: 100%;}
</style>
<script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
<script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$("#confirmBtn").click(function(){
			if(confirm("확실해?")){
				for(var i=0;i<"${fn:length(requestScope.resultList.smallProductList)}";i++){
					if($("input:checkbox[name=delete]")[i].value != "삭제"){
						if($("input:text[name=min]")[i].value > 36 || $("input:text[name=min]")[i].value < 0){
							alert("최소 연령은 0개월부터 36개월까지 입력해");
							$("input:text[name=min]")[i].focus();
							return false;
						}
						if($("input:text[name=max]")[i].value != "" && $("input:text[name=max]")[i].value < 1){
							alert("최대 연령은 1개월 이상 입력해");
							$("input:text[name=max]")[i].focus();
							return false;
						}
						if($("input:text[name=min]")[i].value > $("input:text[name=max]")[i].value){
							alert("최소 연령이 최대 연령을 초과했다");
							$("input:text[name=min]")[i].focus();
							return false;
						}
					}
				}
				var array = [];
				for(var i=0;i<"${fn:length(requestScope.resultList.smallProductList)}";i++){
					if($("input:text[name=smallProduct]")[i].value == "" && $("input:text[name=min]")[i].value == "" && $("input:text[name=max]")[i].value == "" && $("input:checkbox[name=delete]")[i].checked == false){
						
					}else if($("input:checkbox[name=delete]")[i].checked == true){
					    array.push({"smallProductId": $("input:checkbox[name=delete]")[i].value, "smallProduct": $("input:text[name=smallProduct]")[i].value,
					    	"smallProductWhenToUseMin": $("input:text[name=min]")[i].value, "smallProductWhenToUseMax": $("input:text[name=max]")[i].value, 
					    	"delete": "삭제"});
					}else if($("input:checkbox[name=delete]")[i].checked == false){
						array.push({"smallProductId": $("input:checkbox[name=delete]")[i].value, "smallProduct": $("input:text[name=smallProduct]")[i].value,
					    	"smallProductWhenToUseMin": $("input:text[name=min]")[i].value, "smallProductWhenToUseMax": $("input:text[name=max]")[i].value, 
					    	"delete": ""});
					}
				}
				if(array.length == 0){
					return false;
				}
				var json_data=JSON.stringify(array);
				$.ajax({
					url:"registerSmallProduct.do",
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

<div class="h-div">
	<div>
		<c:choose>
		<c:when test="${requestScope.resultList.pagingBean.nowPage < requestScope.resultList.pagingBean.totalPage }">
		<strong>소제품</strong> ${requestScope.resultList.pagingBean.nowPage*10-9} - ${requestScope.resultList.pagingBean.nowPage*10} / ${requestScope.resultList.pagingBean.totalPosting}건
		</c:when>
		<c:otherwise>
		<strong>소제품</strong> ${requestScope.resultList.pagingBean.nowPage*10-9} - ${requestScope.resultList.pagingBean.totalPosting} / ${requestScope.resultList.pagingBean.totalPosting}건
		</c:otherwise>
		</c:choose>
	</div>
	<br>
<c:forEach items="${requestScope.resultList.smallProductList}" var="smallProductList" varStatus="count">
	<c:choose>
		<c:when test="${count.index%2 == 0}">
			<c:choose>
			<c:when test="${count.index%4 == 0}">
			<div style="width:47%; float: left; background-color: #f7f7f7; padding: 10px;">
			</c:when>
			<c:otherwise>
			<div style="width:47%; float: left; padding: 10px;">
			</c:otherwise>
			</c:choose>
				<div style="float: left;">${smallProductList.midCategory} > ${smallProductList.smallProduct}</div>
				<div style="float: right;"><input type="checkbox" name="delete" value="${smallProductList.smallProductId}"><strong>삭제</strong></div><br>
				<div><p align="center"><img src="${smallProductList.smallProductMainPhotoLink}" width="300px" height="400px"><br><br>
				<input type="text" name="smallProduct" placeholder="${smallProductList.smallProduct}" size="${fn:length(smallProductList.smallProduct)+15}" style="padding: 5px"><br>
				최소 연령 : <input type="text" name="min" size="1px"> 개월<br>
				최대 연령 : <input type="text" name="max" size="1px"> 개월</p></div>
			</div>
		</c:when>
		<c:otherwise>
			<c:choose>
			<c:when test="${count.index%4 == 3}">
			<div style="width:47%; float: right; background-color: #f7f7f7; padding: 10px;">
			</c:when>
			<c:otherwise>
			<div style="width:47%; float: right; padding: 10px;">
			</c:otherwise>
			</c:choose>
				<div style="float: left;">${smallProductList.midCategory} > ${smallProductList.smallProduct}</div>
				<div style="float: right;"><input type="checkbox" name="delete" value="${smallProductList.smallProductId}"><strong>삭제</strong></div><br>
				<div><p align="center"><img src="${smallProductList.smallProductMainPhotoLink}" width="300px" height="400px"><br><br>
				<input type="text" name="smallProduct" placeholder="${smallProductList.smallProduct}" size="${fn:length(smallProductList.smallProduct)+15}" style="padding: 5px"><br>
				최소 연령 : <input type="text" name="min" size="1px"> 개월<br>
				최대 연령 : <input type="text" name="max" size="1px"> 개월</p></div>
			</div>
		</c:otherwise>
	</c:choose>
</c:forEach>
</div>

<table width="70%" align="center">
	<tr>
		<td>
		<div align="center">
		<c:set var="pb" value="${requestScope.resultList.pagingBean}"></c:set>
		<c:if test="${pb.previousPageGroup}">
			<a href="${initParam.root}unconfirmedSmallProduct.do?pageNo=${pb.startPageOfPageGroup-1}">Prev</a>
		</c:if>
		<c:forEach var="i" begin="${pb.startPageOfPageGroup}" end="${pb.endPageOfPageGroup}">
			<c:choose>
				<c:when test="${pb.nowPage!=i}">
					<a href="${initParam.root}unconfirmedSmallProduct.do?pageNo=${i}">${i}</a>
				</c:when>
				<c:otherwise>
					${i}
				</c:otherwise>
			</c:choose>
		</c:forEach>
		<c:if test="${pb.nextPageGroup}">
			<a href="${initParam.root}unconfirmedSmallProduct.do?pageNo=${pb.endPageOfPageGroup+1}">Next</a>
		</c:if>
		</div>
		<div style="float: right;"><input type="button" id="confirmBtn" value="소제품 등록" style="font-size:15px;width: 100px;height: 45px;">
		<input type="button" id="cancel" value="취소" style="font-size:15px;width: 100px;height: 45px;"></div>
		</td>
	</tr>
</table>
</body>
</html>