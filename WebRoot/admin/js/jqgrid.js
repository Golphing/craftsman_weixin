$(document).ready(function () {
	$.jgrid.defaults.width = 780;
	$.jgrid.defaults.styleUI = 'Bootstrap';

	$("#jqGrid").jqGrid({
		url: '/admin/js/data.json',
		datatype: "json",
		colModel: [
			{ label: 'Category Name', name: 'CategoryName', width: '10%' },
			{ label: 'Product Name', name: 'ProductName', width: '20%' },
			{ label: 'Country', name: 'Country', width: '20%' },
			{ label: 'Price', name: 'Price', width: '20%', sorttype: 'integer' },
			// sorttype is used only if the data is loaded locally or loadonce is set to true
			{ label: 'Quantity', name: 'Quantity', width: '20%', sorttype: 'number' }                   
		],
		viewrecords: true, // show the current page, data rang and total records on the toolbar
		autowidth: true,
		height: 'auto',
		rowNum: 10,
		rowList:[10,20,30],
		pager: "#jqGridPager"
	});
});