<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&family=Noto+Sans+KR:wght@300&display=swap" rel="stylesheet">
<style type="text/css">
	@font-face {
		font-family: 'paybooc-Medium';
    	src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-07@1.0/paybooc-Medium.woff') format('woff');
    	font-weight: normal;
    	font-style: normal;
	}
	@font-face {
    	font-family: 'paybooc-Bold';
    	src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-07@1.0/paybooc-Bold.woff') format('woff');
    	font-weight: normal;
    	font-style: normal;
	}
	@font-face {
    font-family: 'GoyangDeogyang';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_one@1.0/GoyangDeogyang.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}
	@font-face {
    font-family: 'SDSamliphopangche_Outline';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts-20-12@1.0/SDSamliphopangche_Outline.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}
</style>
<style type="text/css">
	@import url('/project/css/outline.css?24')
</style>
<script type="text/javascript">
	window.onload = function() {
		document.getElementById('currLabel').setAttribute('style', 'background: #186343');
	};
</script>
</head>
<body>
	<div id="header"> 
		<!--========================= 추후 수정(여기부터) =========================-->
		<div class="logo"></div>
		<div class="user_info">인사팀 이종민님</div>
		<div class="logout_container">
			<button>로그아웃</button>
		</div>
		<!--========================= 추후 수정(여기까지) =========================-->
	</div>
	<div id="body_container">
		<div class="side_bar">
			<!--========================= 이 부분이 달라짐(여기부터) =========================-->
			<div>탭 제목</div>
			<div id="currLabel" class="label">현재 레이블</div>
			<div class="label">레이블 2</div>
			<div class="label">레이블 3</div>
			<div class="label">레이블 4</div>
			<div class="label">레이블 5</div>
			<div class="label">레이블 6</div>
			<!--========================= 이 부분이 달라짐(여기까지) =========================-->
		</div>
		<div class="body">
			<div class="toolbar">
				<div class="tool">구매</div>
				<div class="tool">판매</div>
				<div class="tool">재고</div>
				<div class="tool">회계</div>
				<div class="tool">인사</div>
				<div class="tool"></div>
			</div>
			<div class="content">
				<div class="content_head">
					<div class="label_name">레이블 제목</div>
				</div>
				<div class="content_body">
					<!--========================= 이 부분이 달라짐(여기부터) =========================-->
					내용
					<!--========================= 이 부분이 달라짐(여기까지) =========================-->
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="/project/script/label.js"></script>
	<script type="text/javascript" src="/project/script/toolbar.js"></script>
</body>
</html>