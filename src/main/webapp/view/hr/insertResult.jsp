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
			alert('사원 등록 실패');
			location.href = '/project/hr/list.do?p=1';
		} else {
			alert('신규 사원을 등록했습니다.');
			location.href = '/project/hr/list.do?p=1';
		}
	</script>
</body>
</html>