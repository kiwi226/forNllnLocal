<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<script type="text/javascript">
		var result = '${result}'
		if (result == 0) {
			alert('판매처 수정 실패');
			location.href = '/project/customer/list.do?p=1';
		} else if (result > 0) {
			alert('판매처를 수정했습니다.');
			location.href = '/project/customer/list.do?p=1';
		} else  if (result < 0) {
			alert('잘못된 접근입니다.');
			location.href= '/project/loginForm.do';
		}
	</script>
</body>
</html>