var toolbar = document.getElementsByClassName('toolbar');
var tool = document.getElementsByClassName('tool');
tool[0].onclick = function() {
	location.href = '/project/seller/list.do?p=1'
};
tool[1].onclick = function() {
	location.href = '/project/customer/list.do?p=1'
};
tool[2].onclick = function() {
	location.href = '/project/inventory/list.do?p=1'
};
tool[3].onclick = function() {
	location.href = '/project/accounting/purchaseList.do?p=1'
};
tool[4].onclick = function() {
	location.href = '/project/hr/list.do?p=1'
};

var pathName = window.location.pathname

if (pathName.includes('project/seller') || pathName.includes('project/product') || pathName.includes('project/purchase')) {
	tool[0].setAttribute('style', 'background: #f8f7f2; color: #000; box-shadow: 0 -0.15rem 0.15rem #808080; z-index: 1;');

} else if (pathName.includes('project/customer') || pathName.includes('project/sales')) {
	tool[1].setAttribute('style', 'background: #f8f7f2; color: #000; box-shadow: 0 -0.15rem 0.15rem #808080; z-index: 1;');

} else if (pathName.includes('project/inventory')) {
	tool[2].setAttribute('style', 'background: #f8f7f2; color: #000; box-shadow: 0 -0.15rem 0.15rem #808080; z-index: 1;');

} else if (pathName.includes('project/accounting')) {
	tool[3].setAttribute('style', 'background: #f8f7f2; color: #000; box-shadow: 0 -0.15rem 0.15rem #808080; z-index: 1;');

} else if (pathName.includes('project/hr')) {
	tool[4].setAttribute('style', 'background: #f8f7f2; color: #000; box-shadow: 0 -0.15rem 0.15rem #808080; z-index: 1;');
}




