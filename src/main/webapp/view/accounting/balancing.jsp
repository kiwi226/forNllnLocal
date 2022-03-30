<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/common/sessionChk.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>매출 내역</title>

<link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&family=Noto+Sans+KR:wght@300&display=swap" rel="stylesheet">
<link href="/project/css/common/outline.css" rel="stylesheet" type="text/css">
<link href="/project/css/accounting/balancing.css?11" rel="stylesheet" type="text/css">

<script type="text/javascript">
	window.onload = function() {
		var label = document.getElementsByClassName('label');
		label[2].setAttribute('style', 'background: #186343');
		
		var month = '${month}';
		search.month.value = month;
	}
</script>
</head>
<body>
	<div id="header">
		<jsp:include page="/common/header.jsp" />
	</div>
	<div id="body_container">
		<div class="side_bar">
			<div>회 계</div>
			<div class="label">매입 내역</div>
			<div class="label">매출 내역</div>
			<div class="label">월별 결산</div>
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
					<div class="label_name">월별 결산</div>
				</div>
				<div class="content_body">
					<form method="post" name="search" action="/project/accounting/balanceSearchList.do">
						<div class="searchBox">
							<input type="month" name="month">
							<div class="inputBox">
								<button type="submit"></button>							
							</div>
						</div>
					</form>
					<table class="total">
						<tr>
							<th>총 매입액</th>
							<td>${totalPurchase}</td>
						</tr>
						<tr>
							<th>총 매출액</th>
							<td>${totalSales}</td>
						</tr>
						<tr>
							<th>총 손익</th>
							<td>${totalSales - totalPurchase}</td>
						</tr>
					</table>
					<div class="table-container">
						<table class="balancing">
							<tr>
								<th>상품명</th>
								<th>매입단가</th>
								<th>판매단가</th>
								<th>매입수량</th>
								<th>판매수량</th>
								<th>매입총액</th>
								<th>매출총액</th>
								<th>손익</th>
							</tr>
							<c:if test="${empty balanceList}">
								<tr class="onerow">
									<th>이번 달 매입/매출 내역이 존재하지 않습니다.</th>
								</tr>
							</c:if>
							<c:if test="${not empty balanceList}">
								<c:forEach var="balance" items="${balanceList}">
									<tr>
										<td>${balance.purchase.product_name}</td>
										<td>${balance.purchase.cost}</td>
										<td>${balance.sales.price}</td>
										<td>${balance.purchase.purchase_detail_pcount}</td>
										<td>${balance.sales.sales_detail_pcount}</td>
										<td>${balance.purchase.cost * balance.purchase.purchase_detail_pcount}</td>
										<td>${balance.sales.price * balance.sales.sales_detail_pcount}</td>
										<td>${balance.sales.price * balance.sales.sales_detail_pcount - balance.purchase.cost * balance.purchase.purchase_detail_pcount}</td>
									</tr>
								</c:forEach>
							</c:if>
						</table>
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