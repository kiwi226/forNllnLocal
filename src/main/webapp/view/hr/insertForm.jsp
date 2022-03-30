<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/common/sessionChk.jsp" %>
<%@ include file="/view/hr/hrChk.jsp" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원 등록</title>

<link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&family=Noto+Sans+KR:wght@300&display=swap" rel="stylesheet">
<link href="/project/css/common/outline.css" rel="stylesheet">
<link href="/project/css/hr/hrInsert.css?2" rel="stylesheet">

<script type="text/javascript">
	window.history.forward();
	
	window.onload = function() {
		var label = document.getElementsByClassName('label');
		label[2].setAttribute('style', 'background: #186343');
	}

	function lastChk() {
		if (frm['dept_no'].value == 0) {
			alert('부서코드를 설정하세요.');
			frm['dept_no'].focus();
			return false;
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
					<div class="label_name">사원 등록</div>
				</div>
				<div class="content_body">
					<form action="/project/hr/insert.do" name="frm" method="post">
					<table>
						<tr>
							<th>사번</th>
							<td>
								${emp_no}
								<input type="hidden" name="emp_no" value="${emp_no}" required="required" />
							</td>
						</tr>
						<tr>
							<th>이름</th>
							<td><input type="text" name="emp_name" required="required" maxlength="5" placeholder="이름" /></td>
						</tr>
						<tr>
							<th>부서</th>
							<td>
								<select name="dept_no">
									<option value="0">부서명(부서코드)</option>
									<c:forEach var="dept" items="${deptList}">
										<option value="${dept.dept_no}">${dept.dept_name}팀(${dept.dept_no})</option>
									</c:forEach>
								</select> 
							</td>
						</tr>
						<tr class="addrRow">
							<th>주소</th>
							<td>
								<div>
									<input type="text" name="emp_addr_no" id="postcode" required="required" placeholder="우편번호" />
									<input type="button" value="우편번호 입력" onclick="DaumPostcode()">
								</div>
								<div><input type="text" name="emp_addr" id="address" required="required" placeholder="도로명주소"/></div>
								<div><input type="text" name="emp_addr_detail" id="detailAddress" required="required" placeholder="상세주소"/></div>
							</td>
						</tr>
						<tr>
							<th>전화번호</th>
							<td><input type="tel" name="emp_tel" required="required" placeholder="전화번호" /></td>
						</tr>
						<tr>
							<th>이메일</th>
							<td><input type="text" name="emp_email" required="required" placeholder="이메일" /></td>
						</tr>
						<tr>
							<th>입사일</th>
							<td><input type="date" name="hiredate" required="required" /></td>
						</tr>
						<tr>
							<td>
								<input type="submit" value="사원 등록" onclick="return lastChk()">
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