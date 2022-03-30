<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&family=Noto+Sans+KR:wght@300&display=swap" rel="stylesheet">
<link href="/project/css/common/loginForm.css?2" rel="stylesheet">

</head>
<body>
	<div class="loginBox"> 
		<div class="boxTitle"></div>
			<form action="/project/login.do" method="post">
				<div class="input">
					<div>
						<input type="text" name="emp_no" required="required" autofocus="autofocus" placeholder="사번">
					</div>
					<div>
						<input type="password" name="password" required="required" placeholder="비밀번호">
					</div>
					<div>
						<input type="submit" value="로그인">
					</div>
				</div>
			</form>
		<div class="option">
			<a href="/project/initForm.do">비밀번호를 잊으셨나요?</a>
		</div>
	</div>
	<div class="footer">
		ⓒ 만만 by project team no.3
	</div>
</body>
</html>