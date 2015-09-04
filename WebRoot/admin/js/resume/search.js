$(document).ready(function () {
	$("#jqGrid").jqGrid({
		url: '../js/resume/search.json',
		datatype: "json",
		colModel: [
			{ label: '姓名', name: 'name', width: '10%'},
			{ label: '性别', name: 'gender', width: '10%'},
			{ label: '出生日期', name: 'birthday', width: '10%'},
			{ label: '住址', name: 'home', width: '20%' },
			{ label: '联系电话', name: 'telephone', width: '20%'},
			{ label: 'email', name: 'email', width: '20%',},
			// sorttype is used only if the data is loaded locally or loadonce is set to true
			{ label: '操作', name: '', width: '20%', formatter: function(cellValue, options, rowObject) {
				return '<button type="button" data-action="edit" class="btn btn-warning">修改</button><button type="button" data-action="workinfo" class="btn btn-primary">工作经历</button>'
			}}                   
		],
		loadComplete:function(data) {
			console.log(data);
		},
		viewrecords: true, // show the current page, data rang and total records on the toolbar
		autowidth: true,
		height: 'auto',
		rowNum: 10,
		rowList:[10,20,30],
		pager: "#jqGridPager"
	});
});