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
		if (result >= 2) {
			alert('재고량을 수정했습니다.');
			location.href = '/project/inventory/list.do?p=${p}';
		} else  if (result < 0) {
			alert('잘못된 접근입니다.');
			location.href= '/project/loginForm.do';
		} else {
			alert('재고 수정에 실패했습니다.');
			location.href = '/project/inventory/list.do?p=${p}';
		}
	</script>
</body>
</html>