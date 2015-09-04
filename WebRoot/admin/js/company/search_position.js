$(document).ready(function() {
	initJqGrid();
	bindSearchPositionListFormAction();
	bindJqGridDataAction();
	bindEditPositionFormAction();
	
	function initJqGrid() {
		$("#jqGrid").jqGrid({
			url: '../company/position/search.do',
			datatype: "json",
			colModel: [
				{label: '公司', name: 'company.name', width: '20%'},
				{label: '职位', name: 'title', width: '20%'},
				{label: '薪酬', name: 'wage', width: '20%'},
				{label: '要求', name: 'requirement', width: '20%'},
				{label: '城市', name: 'city', width: '20%'},
				{label: '权重', name: 'weight', width: '20%'},
				{ label: '是否过期', name: 'is_expire', width: '20%', formatter: function(cellValue, options, rowObject) {
					if(cellValue == '1') {
						return '是';
					}
					return '否';
				}},
				// sorttype is used only if the data is loaded locally or loadonce is set to true
				{ label: '操作', name: '', width: '20%', formatter: function(cellValue, options, rowObject) {
				//	return '<span class="glyphicon glyphicon-pencil btn"></span><span class="glyphicon glyphicon-remove btn"></span></a>';
					return '<button type="button" data-action="edit" class="btn btn-warning">修改</button>';
				}}
			],
			serializeGridData: function(postData) {
				postData.name = $('#searchPositionForm_name').val();
				postData.title = $('#searchPositionForm_title').val();
				postData.isExpired = $('#searchPositionForm_expire').val();
				postData.city = $('#searchPositionForm_city').val();
				return postData;
			},
			loadComplete:function(xhr) {
				pageData.positionList = xhr.data;
			},
			pager: "#jqGridPager"
		});
	}
	function bindSearchPositionListFormAction() {
		$('#searchPositionForm').submit(function() {
			$('#jqGrid').trigger("reloadGrid");
			return false;
		});
	}
	function bindJqGridDataAction() {
		$('#jqGrid').on('click', 'tr button', function() {
			var action = $(this).attr('data-action');
			var rowId = parseInt($(this).closest('tr')[0].id);
			var position = ADMIN.getItemFromByAttr(pageData.positionList, 'id', rowId);
			if(action == 'edit') {
				$('#editPositionDialog').data().id = position.id;
				$('#editPositionDialog [name="name"]').text(position.name);
				$('#editPositionDialog [name="title"]').text(position.title);
				$('#editPositionDialog [name="wage"]').val(position.wage);
				$('#editPositionDialog [name="requirement"]').val(position.requirement);
				$('#editPositionDialog [name="city"]').val(position.city);
				$('#editPositionDialog [name="weight"]').val(position.weight);
				
				$('#editPositionDialog [name="expire"][value="'+ position.is_expire +'"]').attr('checked', 'checked');
				$('#editPositionDialog').modal('show');
			} else if(action == '') {
				
			}
		});
	}
	function bindEditPositionFormAction() {
		$('#editPositionForm').submit(function() {
			var data = {
				id: $('#editCompanyDialog').data().id,
				wage: $('#editCompanyDialog [name="wage"]').val(),
				requirement: $('#editCompanyDialog [name="requirement"]').val(),
				city: $('#editCompanyDialog [name="city"]').val(),
				weight: $('#editCompanyDialog [name="weight"]').val(),
				is_expire: $('#editCompanyDialog [name="expire"]:checked').val(),
			};
			$.post('', data, function(result) {
				
			});
			return false;
		});
	}

});
