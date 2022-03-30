<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<div class="logo"></div>
<div class="user_info">${sessionScope.Hr.dept_name}팀 ${sessionScope.Hr.emp_name}님</div>
<div class="logout_container">
	<button>로그아웃</button>
</div>