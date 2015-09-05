$(document).ready(function () {
	initJqGrid();
	bindSearchResumeFormAction();
	bindJqGridDataAction();
	
	function initJqGrid() {
		$("#jqGrid").jqGrid({
			url: '../resume/search.do',
			datatype: "json",
			colModel: [
				{label: '姓名', name: 'name', width: '10%'},
				{label: '性别', name: 'gender', width: '10%'},
				{label: '公司', name: 'companyName', width: '10%'},
				{label: '状态', name: 'status', width: '10%'},
				{label: '联系电话', name: 'telephone', width: '20%'},
				{label: '微信号', name: 'wechatAccount', width: '20%',},
				{label: '操作', name: '', width: '20%', formatter: function(cellValue, options, rowObject) {
					return '' + 
						'<button type="button" data-action="access" class="btn btn-success">通过</button>'+
						'<button type="button" data-action="deny" class="btn btn-danger">驳回</button>';
				}}
			],
			serializeGridData: function(postData) {
				postData.name = $('#searchResumeForm_name').val();
				postData.companyName = $('#searchResumeForm_companyName').val();
				postData.status = $('#searchResumeForm_status').val();
				postData.wechatAccount = $('#searchResumeForm_wechatAccount').val();
				return postData;
			},
			loadComplete:function(xhr) {
				pageData.resumeList = xhr.data;
			},
			pager: "#jqGridPager"
		});
	}
	function bindSearchResumeFormAction() {
		$('#searchResumeForm').submit(function() {
			$('#jqGrid').trigger("reloadGrid");
			return false;
		});
	}
	function bindJqGridDataAction() {
		$('#jqGrid').on('click', 'tr button', function() {
			var action = $(this).attr('data-action');
			var rowId = parseInt($(this).closest('tr')[0].id);
			var data = {
				id: rowId,
			};
			if(action == 'access') {
				data.pass = true;
			} else {
				data.pass = false;
			}
			$.post('../resume/pass.do', data, function(result) {
				if(result.status) {
					alert('成功');
					$('#jqGrid').trigger("reloadGrid");
				} else {
					alert(result.msg);
				}
			}, 'json');
		});
	}
});