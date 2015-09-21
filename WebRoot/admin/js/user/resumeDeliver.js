$(document).ready(function() {
	initJqGrid();
	bindJqGridDataAction();
	bindAddBtnAction();
	bindAddDeliverDialogAction();
	bindSearchCompanyDialogAction();
	bindSearchPositionDialogAction();
	function initJqGrid() {
		$("#jqGrid").jqGrid({
			url: '../../wechat/position/search/subscribed/info.do',
			colModel: [
				{label: '公司', name: 'company.name', width: '20%'},
				{label: '职位', name: 'title', width: '20%'},
				{label: '薪酬', name: 'wage', width: '20%'},
				{label: '城市', name: 'city', width: '20%'},
				{label: '操作', name: '', width: '20%', formatter: function(cellValue, options, rowObject) {
					return '' + 
						'<button type="button" data-action="access" class="btn btn-success">通过</button>'+
						'<button type="button" data-action="deny" class="btn btn-danger">驳回</button>';
				}}
			],
			serializeGridData: function(postData) {
				postData.userId = ADMIN.URL.getHashParm('userId');
				return postData;
			},
			loadComplete:function(xhr) {
				pageData.userList = xhr.data;
			},
			pager: "#jqGridPager"
		});
		$("#companyGrid").jqGrid({
			colModel: [
				{label: '名称', name: 'name', width: 200},
				{label: '权重', name: 'weight', width: 200},
				{label: '是否过期', name: 'isExpired', width: 200, formatter: function(cellValue, options, rowObject) {
					if(cellValue == '1') {
						return '是';
					}
					return '否';
				}},
				{label: '操作', name: '', width: 100, formatter: function(cellValue, options, rowObject) {
				//	return '<span class="glyphicon glyphicon-pencil btn"></span><span class="glyphicon glyphicon-remove btn"></span></a>';
					return '<button type="button" data-action="ok" class="btn btn-warning btn-xs">确定</button>'
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
			pager: "#companyPager"
		});
		$("#positionGrid").jqGrid({
			datatype: "json",
			colModel: [
				{label: '职位', name: 'title', width: 200},
				{label: '权重', name: 'weight', width: 200},
				{ label: '是否过期', name: 'isExpired', width: 200, formatter: function(cellValue, options, rowObject) {
					if(cellValue == '1') {
						return '是';
					}
					return '否';
				}},
				{ label: '操作', name: '', width: 100, formatter: function(cellValue, options, rowObject) {
					return '<button type="button" data-action="ok" class="btn btn-warning">确定</button>';
				}}
			],
			serializeGridData: function(postData) {
				postData.companyId = $('#addDeliverDialog').data().companyId;
				return postData;
			},
			loadComplete:function(xhr) {
				pageData.positionList = xhr.data;
			},
			pager: "#positionPager"
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
	function bindAddBtnAction() {
		$('.add-btn').click(function() {
			$('#addDeliverDialog').data().companyId = null;
			$('#addDeliverDialog').data().positionId = null;
			$('#addDeliverDialog').modal('show');
		});
	}
	
	function bindAddDeliverDialogAction() {
		$('#addDeliverForm [name=company]').click(function() {
			$('#searchCompanyDialog').modal('show');
			$("#companyGrid").setGridParam({url: '../company/search.do'}).trigger('reloadGrid');
		});
		$('#addDeliverForm [name=position]').click(function() {
			var companyId = $('#addDeliverDialog').data().companyId;
			if(!companyId) {
				alert('请先选择公司');
				return;
			}
			$('#searchPositionDialog').modal('show');
			$("#positionGrid").setGridParam({url: '../company/position/search.do'}).trigger('reloadGrid');
		});
		$('#addDeliverForm').submit(function() {
			if(!ADMIN.formValidate('#addDeliverForm', {
				company: 'nonempty',
				position: 'nonempty',
			})) {
				return false;
			}
			var data = {
				userId: ADMIN.URL.getHashParm('userId'),
				companyId: $('#addDeliverDialog').data().companyId,
				positionId: $('#addDeliverDialog').data().positionId,
			};
			
			$.post('../../wechat/position/subscribe.do', data, function(result) {
				if(result.status) {
					$('#addDeliverDialog').modal('hide');
					$("#jqGrid").trigger('reloadGrid');
				} else {
					alert(result.msg);
				}
			}, 'json');
			return false;
		});
	}
	
	function bindSearchCompanyDialogAction() {
		$('#searchCompanyForm').submit(function() {
			$('#companyGrid').trigger("reloadGrid");
			return false;
		});
		
		$('#companyGrid').on('click', 'tr button', function() {
			var action = $(this).attr('data-action');
			var rowId = parseInt($(this).closest('tr')[0].id);
			var company = ADMIN.getItemFromByAttr(pageData.companyList, 'id', rowId);
			if(action == 'ok') {
				$('#addDeliverDialog').data().companyId = company.id;
				$('#addDeliverForm [name=company]').val(company.name);
				$('#searchCompanyDialog').modal('hide');
			}
		});
	}
	
	function bindSearchPositionDialogAction() {
		$('#positionGrid').on('click', 'tr button', function() {
			var action = $(this).attr('data-action');
			var rowId = parseInt($(this).closest('tr')[0].id);
			var position = ADMIN.getItemFromByAttr(pageData.positionList, 'id', rowId);
			if(action == 'ok') {
				$('#addDeliverDialog').data().positionId = position.id;
				$('#addDeliverForm [name=position]').val(position.title);
				$('#searchPositionDialog').modal('hide');
			}
		});
	}

});