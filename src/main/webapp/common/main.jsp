<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/sessionChk.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>main</title>

<link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&family=Noto+Sans+KR:wght@300&display=swap" rel="stylesheet">
<link href="/project/css/common/main.css" rel="stylesheet"> 

</head>
<body>
	<div class="container">
		<div class="logo">
			<img alt="" src="images/logo4.jpg" width="500">
		</div>
		<div class="dept_icon_container">
			<div class="icon_box">
				<div class="icon" title="buying/list.do">
					<img alt="" src="images/buy.jpg"> 
				</div>
				<div class="dept_name">
					<button>구 매</button>
				</div>
			</div>
			<div class="icon_box">
				<div class="icon" title="selling/list.do"> 
					<img alt="" src="images/sell.jpg"> 
				</div>
				<div class="dept_name">
					<button>판 매</button>
				</div>
			</div>
			<div class="icon_box">
				<div class="icon" title="product/list.do">
					<img alt="" src="images/box.jpg"> 
				</div>
				<div class="dept_name">
					<button>재 고</button>
				</div>
			</div>
			<div class="icon_box">
				<div class="icon" title="accounting/list.do">
					<img alt="" src="images/cal.jpg">	
				</div>
				<div class="dept_name">
					<button>회 계</button>
				</div>
			</div>
			<div class="icon_box">
				<div class="icon" title="hr/list.do">
					<img alt="" src="images/hr.jpg">
				</div>
				<div class="dept_name">
					<button>인 사</button>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="script/main.js"></script>
</body>
</html>