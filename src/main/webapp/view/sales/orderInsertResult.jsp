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
		if (result >= 4) {
			alert('판매 주문을 등록했습니다.');
			location.href = '/project/sales/orderList.do?p=1';
		} else  if (result < 0) {
			alert('잘못된 접근입니다.');
			location.href= '/project/loginForm.do';
		} else {
			alert('판매 주문 등록을 실패했습니다.');
			location.href = '/project/sales/orderList.do?p=1';
		}
	</script>
</body>
</html>