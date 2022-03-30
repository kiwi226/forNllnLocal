<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/common/sessionChk.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>업체 검색</title>
<link href="/project/css/product/searchWindow.css?2" rel="stylesheet" type="text/css">
</head>
<body>
	<form action="/project/product/searchWindow.do" method="get" name="frm">
		<select name="searchField">
			<option value="product_no">상품코드</option>
			<option value="product_name" selected="selected">상품명</option>
			<option value="product_memo">참고사항</option>
		</select>
		<input type="text" name="keyword" required="required" placeholder="검색어를 입력하세요."> 
		<input type="submit" value="검색">
	</form>
	<div>
		<table>
			<tr>
				<th>상품코드</th>
				<th>상품명</th>
				<th>참고사항</th>
			</tr>
			<c:if test="${empty searchList}">
				<tr>
					<th>검색 조건과 일치하는 상품이 존재하지 않습니다.</th>
				</tr>
			</c:if>
			<c:if test="${not empty searchList}">
			<c:forEach var="search" items="${searchList}">
				<tr>
					<td>${search.product_no}</td>	
					<td>${search.product_name}</td>	
					<td class="no">${search.product_memo}</td>
					<td style="display: none;">${search.cost}</td>	
					<td style="display: none;">${search.price}</td>	
					<td style="display: none;">${search.stock}</td>	
				</tr>		
			</c:forEach>
			</c:if>
		</table>
	</div>
	<script type="text/javascript">
		document.querySelectorAll('tr:not(:first-of-type)').forEach(function(element) {
			if (element.getElementsByTagName('td').length == 1) {
				element.getElementsByTagName('td')[0].style.cursor = 'auto';
			} else {
				element.addEventListener('click', function(event) {
					opener.document.querySelector('input[name=product_no]').value = this.getElementsByTagName('td')[0].innerHTML;
					opener.document.querySelector('input[name=product_name]').value = this.getElementsByTagName('td')[1].innerHTML;
					if(opener.document.querySelector('input[name=cost]') != null)
						opener.document.querySelector('input[name=cost]').value = this.getElementsByTagName('td')[3].innerHTML;
					if(opener.document.querySelector('input[name=price]') != null)
						opener.document.querySelector('input[name=price]').value = this.getElementsByTagName('td')[4].innerHTML;
					if(opener.document.querySelector('input[name=stock]') != null)
						opener.document.querySelector('input[name=stock]').value = this.getElementsByTagName('td')[5].innerHTML;
					if(opener.document.querySelector('input[name=possible_pcount]') != null)
						opener.document.querySelector('input[name=possible_pcount]').value = Math.floor('${cash}'/this.getElementsByTagName('td')[3].innerHTML);
					window.close();
				});
			}
		}); 
		
		frm.onsubmit = function() {
			if (frm.keyword.value == null || frm.keyword.value.trim() == ''){
				alert('검색어를 입력하세요.');
				frm.keyword.focus();
				return false;
			}
		};
	</script>
</body>
</html>
