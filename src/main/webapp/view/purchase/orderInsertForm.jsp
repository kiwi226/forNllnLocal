<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/common/sessionChk.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>구매 등록</title>

<link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&family=Noto+Sans+KR:wght@300&display=swap" rel="stylesheet">
<link href="/project/css/common/outline.css" rel="stylesheet" type="text/css">
<link href="/project/css/purchase/orderInsertForm.css?6" rel="stylesheet" type="text/css">

<script type="text/javascript">
	window.history.forward();
	
	window.onload = function() {
		var label = document.getElementsByClassName('label');
		label[5].setAttribute('style', 'background: #186343');

		document.getElementById('sellerSearch').onclick = function() {
			window.open('/project/seller/searchWindow.do', '업체검색', 'width=500, height=600, scrollbars=no, resizable=no, left=900, top=150');
		}
		document.getElementById('productSearch').onclick = function() {
			window.open('/project/product/searchWindow.do', '상품검색', 'width=500, height=600, scrollbars=no, resizable=no, left=900, top=150');
		}
		
		frm.onsubmit = function() {
			if (frm.seller_no.value == null || frm.seller_no.value == '') {
				alert('업체를 입력하세요');
				document.getElementById('sellerSearch').click();
				return false;
			}
			if (frm.product_no.value == null || frm.product_no.value == '') {
				alert('상품을 입력하세요');
				document.getElementById('productSearch').click();
				return false;
			}
			if (parseInt(frm.purchase_detail_pcount.value) >= parseInt(frm.possible_pcount.value)) {
				alert('주문수량은 구매 가능 수량보다 많을 수 없습니다.');
				frm.purchase_detail_pcount.value = '';
				frm.purchase_detail_pcount.focus();
				return false;
			}
			if (parseInt(frm.purchase_detail_pcount.value) <= 0) {
				alert('적절한 주문수량을 입력해주세요.');
				frm.purchase_detail_pcount.value = '';
				frm.purchase_detail_pcount.focus();
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
					<div class="label_name">구매 등록</div>
				</div>
				<div class="content_body">
					<form action="/project/purchase/orderInsert.do" method="post" name="frm">
					<table>
		<!-- //판매주문일, 주문번호, 판매처코드, 판매처명, 상품코드, 상품명, 판매가, 매입가, 현재 재고량, 주문수량, 주문 등록 사원번호 -->			
						<tr>
							<th>주문번호</th>
							<td>${purchase_order_no}<input type="hidden" name="purchase_order_no" required="required" value="${purchase_order_no}" /></td>
						</tr>
						<tr>
							<th>구매업체코드</th>
							<td>
								<input type="text" name="seller_no" required="required" readonly="readonly" />
								<input id="sellerSearch" type="button" value="업체검색">
							</td>
							
						</tr>
						<tr>
							<th>구매업체명</th>
							<td><input type="text" name="seller_name" required="required" readonly="readonly" /></td>
						</tr>
						<tr>
							<th>상품코드</th>
							<td>
								<input type="text" name="product_no" required="required" readonly="readonly" />
								<input id="productSearch" type="button" value="상품검색">
							</td>
						</tr>
						<tr>
							<th>상품명</th>
							<td><input type="text" name="product_name" required="required" readonly="readonly" /></td>
						</tr>
						<tr>
							<th>매입단가</th>
							<td><input type="number" name="cost" required="required" readonly="readonly" /></td>
						</tr>
						<tr>
							<th>주문수량</th>
							<td><input type="number" name="purchase_detail_pcount" required="required" placeholder="주문수량" /></td>
						</tr>
						<tr>
							<th>재고량</th>
							<td><input type="number" name="stock" required="required" readonly="readonly" /></td>
						</tr>
						<tr>
							<th>구매 가능 수량</th>
							<td><input type="number" name="possible_pcount" required="required" readonly="readonly" /></td>
						</tr>
						<tr>
							<th>담당자</th>
							<td>
								${Hr.emp_no} / ${Hr.dept_name}팀 ${Hr.emp_name}
								<input type="hidden" name="emp_no" required="required" value="${Hr.emp_no}" /> 
							</td>
						</tr>
						<tr>
							<td><input type="submit" value="등록"></td>
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