
var label = document.getElementsByClassName('label');
var pathName = window.location.pathname

if(pathName.includes('project/hr')) {
	label[0].onclick = function() {
		location.href = '/project/hr/list.do?p=1';
	};
	label[1].onclick = function() {
		location.href = '/project/hr/checkForm.do';
	};
	label[2].onclick = function() {
		location.href = '/project/hr/insertForm.do';
	};	
} else if (pathName.includes('project/customer') || pathName.includes('project/sales')) {
	label[0].onclick = function() {
		location.href = '/project/customer/list.do?p=1';
	};
	label[1].onclick = function() {
		location.href = '/project/customer/insertForm.do';
	};
	label[2].onclick = function() {
		location.href = '/project/sales/orderList.do?p=1';
	};
	label[3].onclick = function() {
		location.href = '/project/sales/orderInsertForm.do';
	};
} else if (pathName.includes('project/seller') || pathName.includes('project/product') || pathName.includes('project/purchase') ) {
	label[0].onclick = function() {
		location.href = '/project/seller/list.do?p=1';
	};
	label[1].onclick = function() {
		location.href = '/project/seller/insertForm.do';
	};
	label[2].onclick = function() {
		location.href = '/project/product/list.do?p=1';
	};
	label[3].onclick = function() {
		location.href = '/project/product/insertForm.do';
	};
	label[4].onclick = function() {
		location.href = '/project/purchase/orderList.do?p=1';
	};
	label[5].onclick = function() {
		location.href = '/project/purchase/orderInsertForm.do';
	};
} else if (pathName.includes('project/inventory')) {
	label[0].onclick = function() {
		location.href = '/project/inventory/list.do?p=1';
	};
	label[1].onclick = function() {
		location.href = '/project/inventory/modifiedList.do?p=1';
	};
	label[2].onclick = function() {
		location.href = '/project/inventory/enterList.do?p=1';
	};
	label[3].onclick = function() {
		location.href = '/project/inventory/releaseList.do?p=1';
	};	
} else if (pathName.includes('project/accounting')) {
	label[0].onclick = function() {
		location.href = '/project/accounting/purchaseList.do?p=1';
	};
	label[1].onclick = function() {
		location.href = '/project/accounting/salesList.do?p=1';
	};
	label[2].onclick = function() {
		location.href = '/project/accounting/balancing.do?p=1';
	};	
}
