<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/common/sessionChk.jsp" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>정보 수정</title>

<link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&family=Noto+Sans+KR:wght@300&display=swap" rel="stylesheet">
<link href="/project/css/common/outline.css" rel="stylesheet">
<link href="/project/css/hr/hrCheck.css?1" rel="stylesheet">

<script type="text/javascript">
	window.history.forward();

	window.onload = function() {
		var label = document.getElementsByClassName('label');
		label[1].setAttribute('style', 'background: #186343');
	}
	
	function submitChk() {
		var msg = document.getElementsByClassName('msg')[0];
		if (frm.password.value == null || frm.password.value === '') {
			msg.style.color = '#000';
			msg.innerHTML = '비밀번호를 입력하세요.';
			frm.password.focus();
			return false;
		}
		
		chk();	
		return false;
	}
	
	function chk() {
		var xhr = new XMLHttpRequest();
		var msg = document.getElementsByClassName('msg')[0];
		xhr.open('POST', '/project/hr/check.do');
		xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
		xhr.send('password=' + frm.password.value); 
	
		xhr.onreadystatechange = function() {
			if (xhr.readyState === XMLHttpRequest.DONE && xhr.status == 200) {
				var result = xhr.responseText;
				if (result > 0) {
					if (result >= 6) {
						location.replace('/project/hr/init.do?emp_no=${sessionScope.Hr.emp_no}');
					} else {
						msg.style.color = 'red';
						msg.innerHTML = '비밀번호가 일치하지 않습니다.('+(result-1)+'회 틀림)';
					}
				} else if (result == 0) {
					location.href = '/project/hr/updateForm.do';
				}
			}
		};		
	}
</script>
</head>
<body>
	<div id="header"> 
		<jsp:include page="/common/header.jsp" />
	</div>
	<div id="body_container">
		<div class="side_bar">
			<div>인 사</div>
			<div class="label">사원 목록</div>
			<div class="label">정보 수정</div>
			<div class="label">사원 등록</div>
		</div>
		<div class="body">
			<div class="toolbar">
				<div class="tool">구매</div>
				<div class="tool">판매</div>
				<div class="tool">재고</div>
				<div class="tool">회계</div>
				<div class="tool">인사</div>
				<div></div>
			</div>
			<div class="content">
				<div class="content_head">
					<div class="label_name">정보 수정</div>
				</div>
				<div class="content_body">
					<div class="message">
						본인 확인을 위해 한 번 더 비밀번호를 입력해주세요. <br>
						5회 이상 비밀번호를 틀릴 시 계정의 비밀번호가 초기화 되고 <br>
						초기화된 비밀번호가 계정에 입력된 이메일로 발송됩니다. <br>
					</div>
					<form action="/project/hr/updateForm.do" name="frm" method="post">
					<input type="hidden" name="cnt" value="0">
						<table>
							<tr>
								<th>비밀번호</th>
								<td><input type="password" name="password" required="required" /></td>
							</tr>
							<tr>
								<td>
									<div class="msg"></div>
									<input type="submit" value="확인" onclick="return submitChk()">
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>
	</div>
	<!-- 헤더 동작 -->
	<script type="text/javascript" src="/project/script/header.js"></script>
	<script type="text/javascript" src="/project/script/label.js"></script>
	<script type="text/javascript" src="/project/script/toolbar.js"></script>
</body>
</html>