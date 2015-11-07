$(document).ready(function () {
	$('#companyName').text(decodeURIComponent(ADMIN.URL.getHashParm('name')));
    $('#editRequireForm .summernote').summernote({
        height: 200,
        tabsize: 2,
        lang: 'zh-CN'
    });
	initJqGrid();
	bindJqGridDataAction();
	bindEditPositionFormAction();
	bindAddPositionAction();
	bindAddPositionFormAction();
	
	function initJqGrid() {
		$("#jqGrid").jqGrid({
			mtype: 'post',
			url: '../company/position/search.do',
			datatype: "json",
			colModel: [
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
				postData.companyId = ADMIN.URL.getHashParm('companyId');
				postData.companyTypeCode = 'cooperate';
				return postData;
			},
			loadComplete:function(xhr) {
				pageData.positionList = xhr.data;
			},
			pager: "#jqGridPager"
		});
	}

	function bindJqGridDataAction() {
		$('#jqGrid').on('click', 'tr button', function() {
			var action = $(this).attr('data-action');
			var rowId = parseInt($(this).closest('tr')[0].id);
			var position = ADMIN.getItemFromByAttr(pageData.positionList, 'id', rowId);
			if(action == 'edit') {
				$('#editPositionDialog').data().id = position.id;
				$('#editPositionForm [name="name"]').text(position.company.name);
				$('#editPositionForm [name="title"]').val(position.title);
				$('#editPositionForm [name="wage"]').val(position.wage);
				$('#editPositionForm [name="requirement"]').val(position.requirement);
				$('#editPositionForm [name="city"]').val(position.city);
				$('#editPositionForm [name="weight"]').val(position.weight);
				$('#editPositionForm [name="expire"][value="'+ position.isExpired +'"]')[0].checked = true;;
				
				$('#editPositionDialog .has-error').removeClass('has-error');
				$('#editPositionDialog .input-tips').hide();
				$('#editPositionDialog').modal('show');
			} else if(action == '') {
				
			}
		});
	}
	function bindEditPositionFormAction() {
		$('#editPositionDialog [name="requirement"]').click(function() {
			$('#editRequireDialog').modal('show');
			$('#editRequireDialog').data().type = "edit";
			$('#editRequireForm .summernote').code($('#editPositionForm [name="requirement"]').val());
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
	function bindAddPositionAction() {
		$('.page-path .add-btn').click(function() {
			$('#addPositionDialog .has-error').removeClass('has-error');
			$('#addPositionDialog .input-tips').hide();
			$('#addPositionForm')[0].reset();
			$('#addPositionDialog').modal('show');
		});
	}
	function bindAddPositionFormAction() {
		$('#addPositionForm [name="requirement"]').click(function() {
			$('#editRequireForm .summernote').code($('#addPositionForm [name="requirement"]').val());
			$('#editRequireDialog').modal('show');
			$('#editRequireDialog').data().type = "add";
		});
		$('#editRequireForm').submit(function() {
			var requirement = $('#editRequireForm .summernote').code();
			$('#editRequireDialog').modal('hide');
			if($('#editRequireDialog').data().type == "add") {
				$('#addPositionForm [name="requirement"]').val(requirement);
			} else {
				$('#editPositionForm [name="requirement"]').val(requirement);
			}
			return false;
		});
		$('#addPositionForm').submit(function() {
			if(!ADMIN.formValidate('#addPositionForm', {
				title: 'nonempty',
				wage: 'nonempty',
				requirement: 'nonempty',
				city: 'nonempty',
				weight: ['nonempty', 'number'],
			})) {
				return false;
			}
			var data = {
				companyId: ADMIN.URL.getHashParm('companyId'),
				title: $('#addPositionForm [name="title"]').val(),
				wage: $('#addPositionForm [name="wage"]').val(),
				requirement: $('#addPositionForm [name="requirement"]').val(),
				city: $('#addPositionForm [name="city"]').val(),
				weight: $('#addPositionForm [name="weight"]').val(),
			};
			$.post('../company/position/create.do', data, function(result) {
				if(result.status) {
					alert('成功');
					$('#addPositionDialog').modal('hide');
					$('#jqGrid').trigger("reloadGrid");
				} else {
					alert(result.msg);
				}
			}, 'json');
			return false;
		});
	}
});