$(document).ready(function() {
	initJqGrid();
	bindSearchUserFormAction();
	bindJqGridDataAction();
	
	function initJqGrid() {
		$("#jqGrid").jqGrid({
			mtype: 'post',
			url: '../user/search.do',
			colModel: [
				{label: '微信号', name: 'wechatAccount', width: '20%'},
				{label: '昵称', name: 'nickName', width: '20%'},
				{label: '姓名', name: 'name', width: '20%'},
				{label: '性别', name: 'gender', width: '20%'},
				{label: '电话', name: 'telephone', width: '20%'},
				{label: '操作', name: '', width: '20%', formatter: function(cellValue, options, rowObject) {
					var html = '<button type="button" data-action="resumeMgmt" class="btn btn-primary">简历管理</button>';
					if(rowObject.resumeId !== 0) {
						html += '<button type="button" data-action="resumeDeliver" class="btn btn-info">投递</button>';
					}
					return html;
				}}
			],
			serializeGridData: function(postData) {
				postData.name = $('#searchUserForm_name').val();
				postData.telephone = $('#searchUserForm_telephone').val();
				postData.wechatAccount = $('#searchUserForm_wechatAccount').val();
				return postData;
			},
			loadComplete:function(xhr) {
				pageData.userList = xhr.data;
			},
			pager: "#jqGridPager"
		});
	}
	function bindSearchUserFormAction() {
		$('#searchUserForm').submit(function() {
			$('#jqGrid').trigger("reloadGrid");
			return false;
		});
	}
	function bindJqGridDataAction() {
		$('#jqGrid').on('click', 'tr button', function() {
			var action = $(this).attr('data-action');
			var rowId = parseInt($(this).closest('tr')[0].id);
			var user = ADMIN.getItemFromByAttr(pageData.userList, 'id', rowId);
			if(action == 'resumeMgmt') {
				window.open('#/user/resume?userId=' + user.id);
			} else if(action == 'resumeDeliver') {
				window.open('#/user/resume/deliver?userId=' + user.id);
			}
		});
	}

});
