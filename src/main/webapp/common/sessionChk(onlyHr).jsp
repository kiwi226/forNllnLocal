<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- 현재 세션에 로그인이 된 유저(Hr)가 존재하지 않으면 로그인화면으로 되돌려보내기 -->
<c:if test="${empty sessionScope.Hr}">
	<script type="text/javascript">
		alert('로그인이 필요한 서비스입니다.');
		location.replace('/project/loginForm.do');
	</script>
</c:if>
