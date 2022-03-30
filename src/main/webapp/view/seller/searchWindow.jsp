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
<link href="/project/css/seller/searchWindow.css?2" rel="stylesheet" type="text/css">
</head>
<body>
	<form action="/project/seller/searchWindow.do" method="get" name="frm">
		<select name="searchField">
			<option value="seller_no">업체코드</option>
			<option value="seller_name" selected="selected">업체명</option>
			<option value="seller_memo">참고사항</option>
		</select>
		<input type="text" name="keyword" required="required" placeholder="검색어를 입력하세요."> 
		<input type="submit" value="검색">
	</form>
	<table>
		<tr>
			<th>업체코드</th>
			<th>업체명</th>
			<th>참고사항</th>
		</tr>
		<c:if test="${empty searchList}">
		<tr>
			<td>검색 조건과 일치하는 구매처가 존재하지 않습니다.</td>
		</tr>
		</c:if>
		<c:if test="${not empty searchList}">
		<c:forEach var="search" items="${searchList}">
		<tr>
			<td>${search.seller_no}</td>	
			<td>${search.seller_name}</td>	
			<td>${search.seller_memo}</td>	
		</tr>		
		</c:forEach>
		</c:if>
	</table>
	<script type="text/javascript">
		document.querySelectorAll('tr:not(:first-of-type)').forEach(function(element, index) {
			if (element.getElementsByTagName('td').length == 1) {
				element.getElementsByTagName('td')[0].style.cursor = 'auto';
			} else {
				element.addEventListener('click', function(event) {
					opener.document.querySelector('input[name=seller_no]').value = this.getElementsByTagName('td')[0].innerHTML;
					opener.document.querySelector('input[name=seller_name]').value = this.getElementsByTagName('td')[1].innerHTML;
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
