$(document).ready(function() {
	initJqGrid();
	bindSearchCompanyFormAction();
	bindJqGridDataAction();
	bindEditCompanyFormAction();
	
	function initJqGrid() {
		$("#jqGrid").jqGrid({
			url: '../company/search.do',
			colModel: [
				{label: '名称', name: 'name', width: '20%'},
				{label: '权重', name: 'weight', width: '20%'},
				{label: 'url', name: 'url', width: '20%', formatter: function(cellValue, options, rowObject) {
					return '<a href="'+ cellValue +'" target="_blank">'+ cellValue +'</a>';
				}},
				{label: '是否过期', name: 'is_expire', width: '20%', formatter: function(cellValue, options, rowObject) {
					if(cellValue == '1') {
						return '是';
					}
					return '否';
				}},
				{label: '操作', name: '', width: '20%', formatter: function(cellValue, options, rowObject) {
				//	return '<span class="glyphicon glyphicon-pencil btn"></span><span class="glyphicon glyphicon-remove btn"></span></a>';
					return '<button type="button" data-action="edit" class="btn btn-warning">修改</button><button type="button" data-action="positionMgmt" class="btn btn-primary">职位管理</button>'
				}}
			],
			serializeGridData: function(postData) {
				postData.name = $('#searchCompanyForm_name').val();
				postData.isExpired = $('#searchCompanyForm_expire').val();
				return postData;
			},
			loadComplete:function(xhr) {
				pageData.companyList = xhr.data;
			},
			pager: "#jqGridPager"
		});
	}
	function bindSearchCompanyFormAction() {
		$('#searchCompanyForm').submit(function() {
			$('#jqGrid').trigger("reloadGrid");
			return false;
		});
	}
	function bindJqGridDataAction() {
		$('#jqGrid').on('click', 'tr button', function() {
			var action = $(this).attr('data-action');
			var rowId = parseInt($(this).closest('tr')[0].id);
			var company = ADMIN.getItemFromByAttr(pageData.companyList, 'id', rowId);
			if(action == 'edit') {
				$('#editCompanyDialog').data().id = company.id;
				$('#editCompanyDialog [name="name"]').text(company.name);
				$('#editCompanyDialog [name="weight"]').val(company.weight);
				$('#editCompanyDialog [name="url"]').val(company.url);
				$('#editCompanyDialog [name="expire"][value="'+ company.isExpired +'"]').attr('checked', 'checked');
				$('#editCompanyDialog').modal('show');
			} else if(action == 'positionMgmt') {
				window.open('?companyId='+ company.id+ '#company/position');
			}
		});
	}
	function bindEditCompanyFormAction() {
		$('#editCompanyForm').submit(function() {
			var data = {
				id: $('#editCompanyDialog').data().id,
				weight: $('#editCompanyDialog [name="weight"]').val(),
				url: $('#editCompanyDialog [name="url"]').val(),
		//		isExpire: $('#editCompanyDialog [name="expire"]:checked').val(),
			};
			$.post('../company/modify', data, function(result) {
				
			});
			return false;
		});
	}

});
