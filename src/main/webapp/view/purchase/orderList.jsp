<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/common/sessionChk.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>구매 내역</title>

<link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&family=Noto+Sans+KR:wght@300&display=swap" rel="stylesheet">
<link href="/project/css/common/outline.css" rel="stylesheet" type="text/css">
<link href="/project/css/purchase/list.css?3" rel="stylesheet" type="text/css">

<script type="text/javascript">
	window.onload = function() {
		var label = document.getElementsByClassName('label');
		label[4].setAttribute('style', 'background: #186343');
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
					<div class="label_name">구매 내역</div>
				</div>
				<div class="content_body">
					<form method="post" name="search" action="/project/purchase/orderSearchList.do">
						<div class="searchBox">
							<input type="date" name="from">&nbsp;부터
							<input type="date" name="to">&nbsp;까지 
							<select name="searchField">
								<option value="0">선택</option>
								<option value="seller_no">업체코드</option>
								<option value="seller_name">업체명</option>
								<option value="product_no">상품코드</option>
								<option value="product_name">상품명</option>
								<option value="emp_no">담당자(사번)</option>
								<option value="emp_name">담당자(이름)</option>
							</select> 
							<div class="inputBox">
								<input type="text" name="keyword" placeholder="검색어를 입력하세요.">
								<button type="submit"></button>							
							</div>
						</div>
					</form>
					<table>
						<tr>
							<th>구매일</th>
							<th>업체코드</th>
							<th>업체명</th>
							<th>상품코드</th>
							<th>상품명</th>
							<th>매입단가</th>
							<th>수량</th>
							<th>담당자</th>
						</tr>
						<c:if test="${empty purchaseList}">
							<tr>
								<th>등록된 구매 내역이 없습니다</th>
							</tr>
						</c:if>
						<c:if test="${not empty purchaseList }">
							<c:forEach var="purchase" items="${purchaseList }">
								<tr>
									<td>${purchase.purchase_order_date}</td>
									<td>${purchase.seller_no}</td>
									<td>${purchase.seller_name}</td>
									<td>${purchase.product_no}</td>
									<td>${purchase.product_name}</td>
									<td>${purchase.cost}</td>
									<td>${purchase.purchase_detail_pcount}</td>
									<td>${purchase.emp_no}</td>
								</tr>
							</c:forEach>
						</c:if>
					</table>
					<div class="page">
						<a href="/project/purchase/orderList.do?p=${p-5}">&lt;</a>
						<c:forEach begin="${firstPage}" end="${lastPage}" varStatus="vs">
							<c:if test="${p == vs.index}">
								<b><a href="/project/purchase/orderList.do?p=${vs.index}">${vs.index}</a></b>
							</c:if>
							<c:if test="${p != vs.index}">
								<a href="/project/purchase/orderList.do?p=${vs.index}">${vs.index}</a>
							</c:if>
						</c:forEach>
						<a href="/project/purchase/orderList.do?p=${p+5}">&gt;</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="/project/script/header.js"></script>
	<script type="text/javascript" src="/project/script/label.js"></script>
	<script type="text/javascript" src="/project/script/toolbar.js"></script>
</body>
</html>