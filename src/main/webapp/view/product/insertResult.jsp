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
			alert('상품 등록 실패');
			location.href = '/project/product/list.do?p=1';
		} else if (result > 0) {
			alert('신규 상품을 등록했습니다.');
			location.href = '/project/product/list.do?p=1';
		} else  if (result < 0) {
			alert('잘못된 접근입니다.');
			location.href= '/project/loginForm.do';
		}
	</script>
</body>
</html>