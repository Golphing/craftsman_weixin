$(document).ready(function() {
	initJqGrid();
	bindSearchUserFormAction();
	bindJqGridDataAction();
	bindEditCompanyFormAction();
	
	function initJqGrid() {
		$("#jqGrid").jqGrid({
			url: '../user/search.do',
			colModel: [
				{label: '微信号', name: 'name', width: '20%'},
				{label: '昵称', name: 'weight', width: '20%'},
				{label: '姓名', name: 'weight', width: '20%'},
				{label: '性别', name: 'weight', width: '20%'},
				{label: '出生日期', name: 'weight', width: '20%'},
				{label: '电话', name: 'weight', width: '20%'},
				{label: 'email', name: 'weight', width: '20%'},
				{label: '籍贯', name: 'weight', width: '20%'},
				{label: '操作', name: '', width: '20%', formatter: function(cellValue, options, rowObject) {
					return '<button type="button" data-action="edit" class="btn btn-warning">修改</button><button type="button" data-action="positionMgmt" class="btn btn-primary">简历管理</button>'
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
			if(action == 'edit') {
				$('#editCompanyDialog').data().id = user.id;
				$('#editCompanyDialog [name="name"]').text(company.name);
				$('#editCompanyDialog [name="weight"]').val(company.weight);
				$('#editCompanyDialog [name="url"]').val(company.url);
				$('#editCompanyDialog [name="expire"][value="'+ company.isExpired +'"]')[0].checked = true;
				$('#editCompanyDialog .has-error').removeClass('has-error');
				$('#editCompanyDialog .input-tips').hide();
				$('#editCompanyDialog').modal('show');
			} else if(action == 'positionMgmt') {
				window.open('#/company/position?companyId=' + company.id + '&name=' + encodeURIComponent(company.name));
			}
		});
	}
	function bindEditCompanyFormAction() {
		$('#editCompanyForm').submit(function() {
			if(!ADMIN.formValidate('#editCompanyForm', {
				weight: ['nonempty', 'number'],
				url: 'http',
			})) {
				return false;
			}
			var data = {
				id: $('#editCompanyDialog').data().id,
				weight: $('#editCompanyDialog [name="weight"]').val(),
				url: $('#editCompanyDialog [name="url"]').val(),
				isExpired: $('#editCompanyDialog [name="expire"]:checked').val(),
			};
			$.post('../company/modify.do', data, function(result) {
				if(result.status) {
					alert('成功');
					$('#editCompanyDialog').modal('hide');
					$('#jqGrid').trigger("reloadGrid");
				} else {
					alert(result.msg);
				}
			}, 'json');
			return false;
		});
	}

});
