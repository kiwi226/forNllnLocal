<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/common/sessionChk.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>재고 수정</title>
<link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&family=Noto+Sans+KR:wght@300&display=swap" rel="stylesheet">
<link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&family=Noto+Sans+KR:wght@300&display=swap" rel="stylesheet">
<link href="/project/css/inventory/modifyStockForm.css?7" rel="stylesheet" type="text/css">
</head>
<body>
	<div class="titleBox">
		<div class="title">재고 수정</div>
	</div>
	<form action="/project/inventory/modifyStock.do" method="post" name="frm">
		<input type="hidden" name="p" value="${p}"> 
		<input type="hidden" name="modified_stock" value="${product.stock}"> 
		<input type="hidden" name="emp_no" value="${Hr.emp_no}"> 
		<table>
			<tr>
				<th>상품코드</th>
				<td>
					${product.product_no}
					<input type="hidden" name="product_no" value="${product.product_no}">
				</td>
			</tr>
			<tr>
				<th>상품명</th>
				<td>${product.product_name}</td>
			</tr>
			<tr>
				<th>입고단가</th>
				<td>${product.cost}</td>
			</tr>
			<tr>
				<th>출고단가</th>
				<td>${product.price}</td>
			</tr>
			<tr>
				<th>수량</th>
				<td><input type="number" name="stock" value="${product.stock}" required="required" style="width:120px;"></td>
			</tr>
			<tr>
				<th>담당자</th>
				<td>${Hr.emp_no} / ${Hr.emp_name}</td>
			</tr>
			<tr class="last">
				<th>사유</th>
				<td><textarea rows="3" cols="20" name="modified_memo" required="required"></textarea></td>
			</tr>
			<tr>
				<td><input type="button" name="button" value="수정"></td>
			</tr>
		</table>
	</form>
	<script type="text/javascript">
		window.history.forward();
		frm.button.onclick = function() {
			if( confirm('수정하시겠습니까?') ) {
				opener.name = 'parent';
				frm.target = 'parent';
				frm.submit();
				window.close();
			}
		} 
	</script>
</body>
</html>