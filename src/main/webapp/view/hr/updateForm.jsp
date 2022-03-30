<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/common/sessionChk(onlyHr).jsp" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>정보 수정</title>

<link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&family=Noto+Sans+KR:wght@300&display=swap" rel="stylesheet">
<link href="/project/css/common/outline.css" rel="stylesheet">
<link href="/project/css/hr/hrUpdate.css?2" rel="stylesheet">

<script type="text/javascript">
	window.history.forward();

	window.onload = function() {
		var label = document.getElementsByClassName('label');
		label[1].setAttribute('style', 'background: #186343');
		
		var msg = document.getElementsByClassName('msg');
		
		frm.passwordChk.onchange = function() {
			if (frm.password.value != frm.passwordChk.value) {
				msg[1].style.color = 'red';
				msg[1].innerHTML = '비밀번호가 일치하지 않습니다.';
			} else if (frm.password.value == frm.passwordChk.value) {
				msg[1].style.color = 'blue';
				msg[1].innerHTML = '비밀번호가 일치합니다.';
			}
		}	
		
		frm.password.onchange = function() {
			if(!/^[a-zA-Z0-9]{8,15}$/.test(frm.password.value)){
				msg[0].style.color = 'red';
				msg[0].innerHTML = '비밀번호는 숫자와 영문자의 조합<br> 8~15자로 만들어주세요.';
		    } else {
		    	msg[0].style.color = 'blue';
				msg[0].innerHTML = '유효한 비밀번호 입니다. ';
		    }
			
			if (frm.passwordChk.value != null && frm.passwordChk.value !== '') {
				if (frm.password.value != frm.passwordChk.value) {
					msg[1].style.color = 'red';
					msg[1].innerHTML = '비밀번호가 일치하지 않습니다.';
				} else if (frm.password.value == frm.passwordChk.value) {
					msg[1].style.color = 'blue';
					msg[1].innerHTML = '비밀번호가 일치합니다.';
				}
			}
		}
	}
	
	function lastChk() {
		//비밀번호를 입력하지 않으면 비밀번호를 수정하지 않을 것인지 확인
		var newbee = '${sessionScope.newbee}'
		
		if (frm.password.value == null || frm.password.value === '') {
			if(newbee != null && newbee != '') {
				var msg = document.getElementsByClassName('msg');
				msg[0].style.color = 'red';
				msg[0].innerHTML = '초기 비밀번호는 반드시 수정되어야합니다.';
				frm.password.focus();
				return false;
			}
			
			if(confirm('비밀번호를 수정하지 않으시겠습니까?')) {
				frm.submit();
			} else {
				frm.password.focus();
				return false;
			}
		} 
		
		//비밀번호가 유효하지 않을 때
		else if (!/^[a-zA-Z0-9]{8,15}$/.test(frm.password.value)){
			alert('비밀번호를 확인해주세요.');
			frm.password.focus();
			return false;
	    }
		
		//비밀번호와 비밀번호 확인이 일치하지 않을 때
		else if (frm.password.value != frm.passwordChk.value) {
			alert('비밀번호를 확인해주세요.');
			frm.password.focus();
			return false;
		} else {
			if(confirm('개인정보를 수정하시겠습니까?')) {
				frm.submit();
			} else {
				return false;
			}	
		}
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
					<form action="/project/hr/update.do" name="frm" method="post">
					<table>
						<tr>
							<th>사번</th>
							<td>
								${emp.emp_no}
								<input type="hidden" name="emp_no" required="required" value="${emp.emp_no}" />
							</td>
						</tr>
						<tr>
							<th>이름</th>
							<td><input type="text" name="emp_name" required="required" value="${emp.emp_name}" maxlength="5" /></td>
						</tr>
						<tr>
							<th>비밀번호</th>
							<td>
								<input type="password" name="password" />
								<div class="msg"></div>
							</td>
						</tr>
						<tr>
							<th>비밀번호 확인</th>
							<td>
								<input type="password" name="passwordChk" />
								<div class="msg"></div>
							</td>
						</tr>
						<tr>
							<th>부서</th>
							<td>${sessionScope.Hr.dept_name}팀</td>
						</tr>
						<tr class="addrRow">
							<th>주소</th>
							<td>
								<div>
									<input type="text" name="emp_addr_no" id="postcode" required="required" value="${emp.emp_addr_no}" placeholder="우편번호" />
									<input type="button" value="우편번호 입력" onclick="DaumPostcode()">
								</div>
								<div><input type="text" name="emp_addr" id="address" required="required" value="${emp.emp_addr}" placeholder="주소"/></div>
								<div><input type="text" name="emp_addr_detail" id="detailAddress" required="required" value="${emp.emp_addr_detail}" placeholder="상세주소"/></div>
							</td>
						</tr>
						<tr>
							<th>전화번호</th>
							<td><input type="tel" name="emp_tel" required="required" value="${emp.emp_tel}" /></td>
						</tr>
						<tr>
							<th>이메일</th>
							<td><input type="text" name="emp_email" required="required" value="${emp.emp_email}" /></td>
						</tr>
						<tr>
							<th>입사일</th>
							<td>${emp.hiredate}</td>
						</tr>
						<tr>
							<td>
								<input type="submit" value="정보 수정" onclick="return lastChk()">
							</td>
						</tr>
					</table>
					</form>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="/project/script/header.js"></script>
	<script type="text/javascript" src="/project/script/label.js"></script>
	<script type="text/javascript" src="/project/script/toolbar.js"></script>
	<script type="text/javascript" src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script type="text/javascript" src="/project/script/postAddress.js"></script>
</body>
</html>