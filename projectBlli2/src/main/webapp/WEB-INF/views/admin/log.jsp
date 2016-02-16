<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
html, body{
	height: 100%;
}
table {
	width: 70%;
	line-height: 21px;
	border-top: 1px solid #cccccc;
	border-left: 1px solid #cccccc;
	border-collapse: collapse;
	margin: auto;
	table-layout: fixed;
}
#mainTable {
	margin-top: 100px;
}
#subTable {
	margin-top: 20px;
}
#subTable2 {
	margin-bottom: 20px;
}
table th, table td {
	color: #678197;
	text-align: center;
	border-right: 1px solid #cccccc;
	border-bottom: 1px solid #cccccc;
	padding: 3px 0;
	text-align: center;
}
table th {
	background-color: #eeeeee;
}
</style>
<script src="//code.jquery.com/jquery-1.12.0.min.js"></script>
<script src="//code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bPopup/0.11.0/jquery.bpopup.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$(".detail").click(function(){
		var table = "<table id='subTable'>";
		var methodName = $(this).attr("name");
		var highRankCategoryCount = $(this).parent().children("input[name=highRankCategoryCount]").val();
		var categoryCount = $(this).parent().children("input[name=categoryCount]").val();
		var insertCategoryCount = $(this).parent().children("input[name=insertCategoryCount]").val();
		var denySmallProductCount = $(this).parent().children("input[name=denySmallProductCount]").val();
		var updateCategoryCount = $(this).parent().children("input[name=updateCategoryCount]").val();
		var notUpdateProductCount = $(this).parent().children("input[name=notUpdateProductCount]").val();
		var denyPostingCount = $(this).parent().children("input[name=denyPostingCount]").val();
		var notUpdatePostingCount = $(this).parent().children("input[name=notUpdatePostingCount]").val();
		var delayConnectionCount = $(this).parent().children("input[name=delayConnectionCount]").val();
		var exceptionCount = $(this).parent().children("input[name=exceptionCount]").val();
		var detailException = $(this).parent().children("input[name=detailException]").val();
		if(methodName == "insertBigCategory"){
			table += "<tr>";
			table += "<th>총 대분류 개수</th>";
			table += "<th>insert한 대분류 개수</th>";
			table += "<th>update한 대분류 개수</th>";
			table += "<th>Exception 발생 횟수</th>";
			table += "</tr>";
			table += "<tr>";
			table += "<td>"+categoryCount+"</td>";
			table += "<td>"+insertCategoryCount+"</td>";
			table += "<td>"+updateCategoryCount+"</td>";
			table += "<td>"+exceptionCount+"</td>";
			table += "</tr>";
		}else if(methodName == "insertMidCategory"){
			table += "<tr>";
			table += "<th>총 대분류 개수</th>";
			table += "<th>총 중분류 개수</th>";
			table += "<th>insert한 중분류 개수</th>";
			table += "<th>update한 중분류 개수</th>";
			table += "<th>Exception 발생 횟수</th>";
			table += "</tr>";
			table += "<tr>";
			table += "<td>"+highRankCategoryCount+"</td>";
			table += "<td>"+categoryCount+"</td>";
			table += "<td>"+insertCategoryCount+"</td>";
			table += "<td>"+updateCategoryCount+"</td>";
			table += "<td>"+exceptionCount+"</td>";
			table += "</tr>";
		}else if(methodName == "insertSmallProduct"){
			table += "<tr>";
			table += "<th>총 중분류 개수</th>";
			table += "<th>총 소제품 개수</th>";
			table += "<th>insert한 소제품 개수</th>";
			table += "<th>update한 소제품 개수</th>";
			table += "<th>insert한 조건에 맞지 않는 소제품 개수</th>";
			table += "<th>update하지 않은 소제품 개수</th>";
			table += "<th>Exception 발생 횟수</th>";
			table += "</tr>";
			table += "<tr>";
			table += "<td>"+highRankCategoryCount+"</td>";
			table += "<td>"+categoryCount+"</td>";
			table += "<td>"+insertCategoryCount+"</td>";
			table += "<td>"+updateCategoryCount+"</td>";
			table += "<td>"+denySmallProductCount+"</td>";
			table += "<td>"+notUpdateProductCount+"</td>";
			table += "<td>"+exceptionCount+"</td>";
			table += "</tr>";
		}else if(methodName == "insertPosting"){
			table += "<tr>";
			table += "<th>총 소제품 개수</th>";
			table += "<th>총 포스팅 개수</th>";
			table += "<th>insert한 포스팅 개수</th>";
			table += "<th>update한 포스팅 개수</th>";
			table += "<th>insert하지 않은 조건에 맞지 않는 포스팅 개수</th>";
			table += "<th>update하지 않은 포스팅 개수</th>";
			table += "<th>시간지연되어 insert하지 않은 포스팅 개수</th>";
			table += "<th>Exception 발생 횟수</th>";
			table += "</tr>";
			table += "<tr>";
			table += "<td>"+highRankCategoryCount+"</td>";
			table += "<td>"+categoryCount+"</td>";
			table += "<td>"+insertCategoryCount+"</td>";
			table += "<td>"+updateCategoryCount+"</td>";
			table += "<td>"+denyPostingCount+"</td>";
			table += "<td>"+notUpdatePostingCount+"</td>";
			table += "<td>"+delayConnectionCount+"</td>";
			table += "<td>"+exceptionCount+"</td>";
			table += "</tr>";
		}
		table += "</table><table id='subTable2'>";
		for(var i=0;i<detailException;i++){
			if(i%2 == 0){
				table += "<tr>";
				table += "<th>Exception이 발생한 bigCategoryId</th>";
				table += "<th>Exception 내용</th>";
				table += "<th>Exception이 발생한 bigCategoryId</th>";
				table += "<th>Exception 내용</th>";
				table += "</tr>";
				table += "<tr>";
				table += "<td>"+$(this).parent().children("input[name=categoryId]").eq(i).val()+"</td>";
				table += "<td><div style='display:none; width:50%; height:auto; background-color: white; padding: 20px; margin: auto;'>";
				table += "<table>";
				table += "<tr>";
				table += "<th>Exception 내용</th>";
				table += "</tr>";
				table += "<tr>";
				table += "<td>"+$(this).parent().children("input[name=exceptionContent]").eq(i).val()+"</td>";
				table += "</tr>";
				table += "</table>";
				table += "</div>";
				table += "<img src='${initParam.root}img/상세보기.PNG' alt='상세보기' width='20px' style='cursor: pointer;' class='exceptionPopUp'></td>";
				if(i == (detailException-1)){
					table += "<td></td><td></td></tr>";
				}
			}else{
				table += "<td>"+$(this).parent().children("input[name=categoryId]").eq(i).val()+"</td>";
				table += "<td><div style='display:none; width:50%; height:auto; background-color: white; padding: 20px; margin: auto;'>";
				table += "<table>";
				table += "<tr>";
				table += "<th>Exception 내용</th>";
				table += "</tr>";
				table += "<tr>";
				table += "<td>"+$(this).parent().children("input[name=exceptionContent]").eq(i).val()+"</td>";
				table += "</tr>";
				table += "</table>";
				table += "</div>";
				table += "<img src='${initParam.root}img/상세보기.PNG' alt='상세보기' width='20px' style='cursor: pointer;' class='exceptionPopUp'></td>";
				table += "</tr>";
			}
		}
		table += "</table>";
		$("#detailPopUp").html(table);
		$("#detailPopUp").bPopup();
	});
});
$(document).on("click", ".exceptionPopUp", function(){
	$(this).prev().bPopup();
});
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>로그 조회</title>
</head>
<body>
<div style='margin: auto; padding: 10px;'></div>
<table id="mainTable">
	<tr>
		<th>번호</th>
		<th>method</th>
		<th>요청자</th>
		<th>처리 시간</th>
		<th>발생 일자</th>
		<th>상세 보기</th>
	</tr>
	<c:forEach items="${requestScope.logList}" var="log" varStatus="index">
	<tr>
		<td>${log.number}</td>
		<td>${log.methodName}</td>
		<td>${log.executor}</td>
		<td>${log.runTime}</td>
		<td>${log.startTime}</td>
		<td>
			<input type="hidden" value="${log.highRankCategoryCount}" name="highRankCategoryCount">
			<input type="hidden" value="${log.denySmallProductCount}" name="denySmallProductCount">
			<input type="hidden" value="${log.notUpdateProductCount}" name="notUpdateProductCount">
			<input type="hidden" value="${log.categoryCount}" name="categoryCount">
			<input type="hidden" value="${log.insertCategoryCount}" name="insertCategoryCount">
			<input type="hidden" value="${log.updateCategoryCount}" name="updateCategoryCount">
			<input type="hidden" value="${log.denyPostingCount}" name="denyPostingCount">
			<input type="hidden" value="${log.notUpdatePostingCount}" name="notUpdatePostingCount">
			<input type="hidden" value="${log.delayConnectionCount}" name="delayConnectionCount">
			<input type="hidden" value="${log.exceptionCount}" name="exceptionCount">
			<input type="hidden" value="${fn:length(log.detailException)}" name="detailException">
			<c:forEach items="${log.detailException}" var="exceptionInfo">
				<input type="hidden" value="${exceptionInfo.categoryId}" name="categoryId">
				<input type="hidden" value="${exceptionInfo.exceptionContent}" name="exceptionContent">
			</c:forEach>
			<c:if test="${log.methodName != 'sendRecommendingMail'}">
			<img src="${initParam.root}img/상세보기.PNG" alt="상세보기" width="20px" class="detail" style="cursor: pointer;" name="${log.methodName}" id="${index.index}">
			</c:if>
		</td>
	</tr>
	</c:forEach>
</table>
<div id="detailPopUp" style="display:none; width:70%; height:auto; background-color: white; overflow-y: auto;">
</div>
</body>
</html>
