<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 초기화</title>
<link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&family=Noto+Sans+KR:wght@300&display=swap" rel="stylesheet">
<link href="/project/css/common/initForm.css?18" rel="stylesheet">
<script type="text/javascript">
	window.history.forward();
	
	window.onload = function() {
		document.getElementsByClassName('boxTitle')[0].onclick = function() {
			location.href = '/project/loginForm.do';
		}
		
		frm.emp_no.onchange = function() {
			var xhr = new XMLHttpRequest();
			var msg = document.getElementsByClassName('msg');
			var hiddenInput = document.getElementsByClassName('hiddenInput');
			xhr.open('POST', '/project/initCheck.do');
			xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
			xhr.send('emp_no=' + frm.emp_no.value); 
		
			xhr.onreadystatechange = function() {
				if (xhr.readyState === XMLHttpRequest.DONE && xhr.status == 200) {
					var result = xhr.responseText;
					if (result == 0) {
						msg[0].style.display = 'flex';
						msg[0].innerHTML = '존재하지 않는 사번입니다.';
						hiddenInput[0].style.display = 'none';				
						hiddenInput[1].style.display = 'none';
						frm.email.value = '';
						msg[1].innerHTML = '';
					} else {
						msg[0].style.display = 'none';
						hiddenInput[0].style.display = 'flex';				
						hiddenInput[1].style.display = 'flex';				
					}
				}
			};		
		};
	};
	
	function submitChk() {
		var xhr = new XMLHttpRequest();
		var msg = document.getElementsByClassName('msg');
		
		xhr.open('POST', '/project/initCheck.do');
		xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
		xhr.send('emp_no=' + frm.emp_no.value + '&email=' + frm.email.value); 
	
		xhr.onreadystatechange = function() {
			if (xhr.readyState === XMLHttpRequest.DONE && xhr.status == 200) {
				var result = xhr.responseText;
				if (result == 2) {
					msg[1].innerHTML = '등록된 이메일과 일치하지 않습니다.';
				} else if (result == 3) {
					location.href='/project/hr/init.do?emp_no='+frm.emp_no.value;
				}
			}
		};
		
		return false;
	}
	
</script>
</head>
<body>
	<div class="initBox"> 
		<div class="boxTitle"></div>
		<form action="" name="frm" method="post">
			<div class="input">
				<div>
					<div class="descript">비밀번호를 초기화하려는 사번을 입력하세요.</div>
					<input type="text" name="emp_no" required="required" autofocus="autofocus" placeholder="사번">
					<div class="msg"></div>
				</div>
				<div class="hiddenInput">
					<div class="descript">사번에 등록된 이메일을 입력해주세요.</div>
					<input type="email" name="email" required="required" placeholder="이메일">
				</div>
				<div class="hiddenInput">
					<div class="msg"></div>
					<input type="submit" value="초기화" onclick="return submitChk()">
				</div>
			</div>
		</form>
	</div>
	<div class="footer">
		ⓒ 만만 by project team no.3
	</div>
</body>
</html>