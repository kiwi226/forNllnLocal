<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/common/sessionChk.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 등록</title>

<link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&family=Noto+Sans+KR:wght@300&display=swap" rel="stylesheet">
<link href="/project/css/common/outline.css" rel="stylesheet" type="text/css">
<link href="/project/css/product/insertForm.css?1" rel="stylesheet" type="text/css">

<script type="text/javascript">
	window.history.forward();
	
	window.onload = function() {
		var label = document.getElementsByClassName('label');
		label[3].setAttribute('style', 'background: #186343');
		
		frm.onsubmit = function() {
			if (frm.cost.value > frm.price.value) {
				alert('적절하지 못한 구매가/판매가 입니다.');
				frm.price.focus();
				return false;
			} else {
				return true;
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
			<div>구 매</div>
			<div class="label">구매처 목록</div>
			<div class="label">구매처 등록</div>
			<div class="label">상품 목록</div>
			<div class="label">상품 등록</div>			
			<div class="label">구매 내역</div>
			<div class="label">구매 등록</div>
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
					<div class="label_name">상품 등록</div>
				</div>
				<div class="content_body">
					<form action="/project/product/insert.do" method="post" name="frm">
					<table>
						<tr>
							<th>상품코드</th>
							<td>${product_no}<input type="hidden" name="product_no" required="required" value="${product_no}" /></td>
						</tr>
						<tr>
							<th>상품명</th>
							<td><input type="text" name="product_name" required="required" maxlength="10" placeholder="상품명" /></td>
						</tr>
						<tr>
							<th>판매단가</th>
							<td><input type="number" name="price" required="required" placeholder="판매단가" /></td>
						</tr>
						<tr>
							<th>매입단가</th>
							<td><input type="number" name="cost" required="required" placeholder="매입단가" /></td>
						</tr>
						<tr class="last">
							<th>참고사항</th>
							<td><textarea name="product_memo" rows="2" cols="40"></textarea></td>
						</tr>
						<tr>
							<td>
								<input type="submit" value="등록" >
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
</body>
</html>