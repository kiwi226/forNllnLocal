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
<link href="/project/css/customer/searchWindow.css?1" rel="stylesheet" type="text/css">
</head>
<body>
	<form action="/project/customer/searchWindow.do" method="get" name="frm">
		<select name="searchField">
			<option value="customer_no">업체코드</option>
			<option value="customer_name" selected="selected">업체명</option>
			<option value="customer_memo">참고사항</option>
		</select>
		<input type="text" name="keyword" required="required" placeholder="검색어를 입력하세요."> 
		<input type="submit" value="검색">
	</form>
	<div>
		<table>
			<tr>
				<th>업체코드</th>
				<th>업체명</th>
				<th>참고사항</th>
			</tr>
			<c:if test="${empty searchList}">
				<tr>
					<th>검색 조건과 일치하는 구매처가 존재하지 않습니다.</th>
				</tr>
			</c:if>
			<c:if test="${not empty searchList}">
			<c:forEach var="search" items="${searchList}">
				<tr>
					<td>${search.customer_no}</td>	
					<td>${search.customer_name}</td>	
					<td>${search.customer_memo}</td>	
				</tr>		
			</c:forEach>
			</c:if>
		</table>
	</div>
	<script type="text/javascript">
		document.querySelectorAll('tr:not(:first-of-type)').forEach(function(element, index) {
			if (element.getElementsByTagName('td').length == 1) {
				element.getElementsByTagName('td')[0].style.cursor = 'auto';
			} else {
				element.addEventListener('click', function(event) {
					opener.document.querySelector('input[name=customer_no]').value = this.getElementsByTagName('td')[0].innerHTML;
					opener.document.querySelector('input[name=customer_name]').value = this.getElementsByTagName('td')[1].innerHTML;
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
