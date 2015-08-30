$(document).ready(function() {
	initJqGrid();
	bindSearchCompanyFormAction();
	bindJqGridDataAction();
	bindEditCompanyFormAction();
	
	function initJqGrid() {
		$("#jqGrid").jqGrid({
			url: '../js/company/search_position.json',
			datatype: "json",
			colModel: [
				{label: '公司', name: 'name', width: '20%'},
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
				var name = $('#searchPositionForm_name').val();
				var title = $('#searchPositionForm_title').val();
				var expire = $('#searchPositionForm_expire').val();
				var city = $('#searchPositionForm_city').val();
				name && (postData.name = name);
				title && (postData.title = title);
				expire && (postData.expire = expire);
				city && (postData.city = city);
				return postData;
			},
			loadComplete:function(xhr) {
				pageData.positionList = xhr.data;
			},
			viewrecords: true, // show the current page, data rang and total records on the toolbar
			autowidth: true,
			height: 'auto',
			jsonReader: {root: 'data', page: 'currpage', total: 'totalpages', records: 'totalrecords'},
			rowNum: 10,
			rowList:[10,20,30],
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
			var row = parseInt($(this).closest('tr')[0].id) - 1;
			var position = pageData.positionList[row];
			if(action == 'edit') {
				$('#editCompanyDialog').data().id = position.id;
				$('#editCompanyDialog [name="name"]').text(company.name);
				$('#editCompanyDialog [name="weight"]').val(company.weight);
				$('#editCompanyDialog [name="url"]').val(company.url);
				$('#editCompanyDialog [name="expire"][value="'+ company.is_expire +'"]').attr('checked', 'checked');
				$('#editCompanyDialog').modal('show');
			} else if(action == 'addPosition') {
				
			}
		});
	}
	function bindEditCompanyFormAction() {
		$('#editCompanyForm').submit(function() {
			var data = {
				id: $('#editCompanyDialog').data().id,
				weight: $('#editCompanyDialog [name="weight"]').val(),
				url: $('#editCompanyDialog [name="url"]').val(),
				is_expire: $('#editCompanyDialog [name="expire"]:checked').val(),
			};
			$.post('', data, function(result) {
				
			});
			return false;
		});
	}

});
