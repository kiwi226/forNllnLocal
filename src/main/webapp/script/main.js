	document.getElementsByClassName('icon')
	var icon = document.getElementsByClassName('icon');
	icon[0].onclick = function() {
		location.href = '/project/seller/list.do?p=1';
	};
	icon[1].onclick = function() {
		location.href = '/project/customer/list.do?p=1';
	};
	icon[2].onclick = function() {
		location.href = '/project/inventory/list.do?p=1';
	};
	icon[3].onclick = function() {
		location.href = '/project/accounting/purchaseList.do?p=1';
	};
	icon[4].onclick = function() {
		location.href = '/project/hr/list.do?p=1';
	};
	
	var button = document.getElementsByTagName('button');
	button[0].onclick = function() {
		location.href = '/project/seller/list.do?p=1';
	};
	button[1].onclick = function() {
		location.href = '/project/customer/list.do?p=1';
	};
	button[2].onclick = function() {
		location.href = '/project/inventory/list.do?p=1';
	};
	button[3].onclick = function() {
		location.href = '/project/accounting/purchaseList.do?p=1';
	};
	button[4].onclick = function() {
		location.href = '/project/hr/list.do?p=1';
	};