$(document).ready(function() {
    $('#editRequireForm .summernote').summernote({
        height: 200,
        tabsize: 2,
        lang: 'zh-CN'
    });
	initJqGrid();
	bindSearchPositionListFormAction();
	bindJqGridDataAction();
	bindEditPositionFormAction();
	
	function initJqGrid() {
		$("#jqGrid").jqGrid({
			mtype: 'post',
			url: '../company/position/search.do',
			datatype: "json",
			colModel: [
				{label: '公司', name: 'company.name', width: '20%'},
				{label: '职位', name: 'title', width: '20%'},
				{label: '薪酬', name: 'wage', width: '20%'},
				{label: '城市', name: 'city', width: '20%'},
				{label: '权重', name: 'weight', width: '20%'},
				{ label: '是否过期', name: 'isExpired', width: '20%', formatter: function(cellValue, options, rowObject) {
					if(cellValue == '1') {
						return '是';
					}
					return '否';
				}},
				{ label: '操作', name: '', width: '20%', formatter: function(cellValue, options, rowObject) {
					return '<button type="button" data-action="edit" class="btn btn-warning">修改</button>';
				}}
			],
			serializeGridData: function(postData) {
				postData.companyName = $('#searchPositionForm_name').val();
				postData.title = $('#searchPositionForm_title').val();
				postData.isExpired = $('#searchPositionForm_expire').val();
				postData.city = $('#searchPositionForm_city').val();
				postData.companyTypeCode = 'cooperate';
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
				$('#editPositionDialog [name="name"]').text(position.company.name);
				$('#editPositionDialog [name="title"]').val(position.title);
				$('#editPositionDialog [name="wage"]').val(position.wage);
				$('#editPositionDialog [name="requirement"]').val(position.requirement);
				$('#editPositionDialog [name="city"]').val(position.city);
				$('#editPositionDialog [name="weight"]').val(position.weight);
			//	debugger
				$('#editPositionDialog [name="expire"][value="'+ position.isExpired +'"]')[0].checked = true;
				$('#editPositionDialog .has-error').removeClass('has-error');
				$('#editPositionDialog .input-tips').hide();
				$('#editPositionDialog').modal('show');
			} else if(action == '') {
				
			}
		});
	}
	function bindEditPositionFormAction() {
		$('#editPositionForm [name="requirement"]').click(function() {
			$('#editRequireDialog').modal('show');
			$('#editRequireDialog').data().type = "edit";
			$('#editRequireForm .summernote').code($('#editPositionForm [name="requirement"]').val());
		});
		$('#editRequireForm').submit(function() {
			var requirement = $('#editRequireForm .summernote').code();
			$('#editRequireDialog').modal('hide');
			$('#editPositionForm [name="requirement"]').val(requirement);

			return false;
		});
		$('#editPositionForm').submit(function() {
			if(!ADMIN.formValidate('#editPositionForm', {
				title: 'nonempty',
				wage: 'nonempty',
				requirement: 'nonempty',
				city: 'nonempty',
				weight: ['nonempty', 'number'],
			})) {
				return false;
			}
			var data = {
				positionId: $('#editPositionDialog').data().id,
				title: $('#editPositionForm [name="title"]').val(),
				wage: $('#editPositionForm [name="wage"]').val(),
				requirement: $('#editPositionForm [name="requirement"]').val(),
				city: $('#editPositionForm [name="city"]').val(),
				weight: $('#editPositionForm [name="weight"]').val(),
				isExpired: $('#editPositionForm [name="expire"]:checked').val(),
			};
			$.post('../company/position/modify.do', data, function(result) {
				if(result.status) {
					alert('成功');
					$('#editPositionDialog').modal('hide');
					$('#jqGrid').trigger("reloadGrid");
				} else {
					alert(result.msg);
				}
			}, 'json');
			return false;
		});
	}

});
