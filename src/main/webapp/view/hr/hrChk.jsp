<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- 현재 유저가 인사팀이 아니면 이전 화면으로 되돌려보내기 -->
<c:if test="${sessionScope.Hr.dept_name != '인사'}">
	<script type="text/javascript">
		alert('사원 등록 권한이 없습니다.\r\n인사팀에 문의하세요');
		history.go(-1);
	</script>
</c:if>
