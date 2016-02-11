<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>블리 - 충동구매보다 빠른 합리적 쇼핑!</title>
<link href="${initParam.root}img/favicon/favicon.ico" rel="shortcut icon" type="image/x-icon" />
</head>
<META http-equiv="Expires" content="-1"> 
<META http-equiv="Pragma" content="no-cache"> 
<META http-equiv="Cache-Control" content="No-Cache"> 
<link rel="stylesheet" type="text/css" href="${initParam.root}css/reset.css" />
<link rel="stylesheet" type="text/css" href="${initParam.root}css/css.css" />
<link href='https://cdn.rawgit.com/openhiun/hangul/14c0f6faa2941116bb53001d6a7dcd5e82300c3f/nanumbarungothic.css' rel='stylesheet' type='text/css'>
<!-- jquery -->
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<script type="text/javascript">


	$(document).ready(function(){
		$('.babyName').keyup(function(){
			var userWritingBabyName = $(this).val();
			$(this).val(userWritingBabyName.substring(0,5));
			if($(this).length>5){
				alert('아이이름은 5글자 이하로 작성해주세요 ^^');
				$(this).val(userWritingBabyName.substring(0,5));
			}
		});
		
	});
	var twinsName;
	function sendChildValue(){
		for(var i=0;i<$('.babyName').size();i++){
			if(i==0){
				if($($('.babyName').get(1)).val()!=""){
					twinsName = $($('.babyName').get(0)).val();
				}
			}else{
				if($($('.babyName').get(i)).val()!=""){
					twinsName += "&"+ $($('.babyName').get(i)).val();
				}
			}
		}
		opener.setChildValue(twinsName);
		window.close();
	}
	 function closeWindow() {
		 if(twinsName==""){
	        opener.setChildValue("exit");
		 }
	 }
</script>
<body onbeforeunload="closeWindow()">
	<div class="title" style="color: black ; padding-top:40px;">쌍둥이 이름 입력</div>
	<div style="width : 360px;">
	<label><input type="text" class="babyName" placeholder="첫째 이름" style="margin-left: 100px;margin-top: 20px;"></label><br>
	<label><input type="text" class="babyName" placeholder="둘째 이름" style="margin-left: 100px;margin-top: 20px;"></label><br>
	<label><input type="text" class="babyName" placeholder="셋째 이름" style="margin-left: 100px;margin-top: 20px;"></label><br>
	<a href="#" onclick="sendChildValue()"><img src="${initParam.root}img/info_bt1.png" style="padding-top: 20px; width: 200px; height:40px; margin-left: 85px;"></a>
	</div>
</body>
</html>