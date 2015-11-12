$(document).ready(function () {
	initJqGrid();
	bindSearchResumeFormAction();
	bindJqGridDataAction();
	bindReplyFormAction();
	
	function initJqGrid() {
		$("#jqGrid").jqGrid({
			mtype: 'post',
			url: '../resume/search.do',
			datatype: "json",
			colModel: [
				{label: '姓名', name: 'name', width: '10%'},
				{label: '性别', name: 'gender', width: '10%'},
				{label: '公司', name: 'companyName', width: '10%'},
				{label: '职位名称', name: 'positionName', width: '10%'},
				{label: '状态', name: 'allStatus', width: '20%', formatter:function(cellValue, options, rowObject) {
					var length = cellValue.length;
					if(length) {
						return cellValue[length-1];
					} else {
						return '未初筛';
					}
				}},
				{label: '联系电话', name: 'telephone', width: '20%'},
				{label: '微信号', name: 'wechatAccount', width: '20%',},
				{label: '操作', name: '', width: '20%', formatter: function(cellValue, options, rowObject) {
					if(rowObject.statusId == 8) {
						return '';
					}
					return '' + 
						'<button type="button" data-action="reply" class="btn btn-primary">答复</button>'
				}}
			],
			serializeGridData: function(postData) {
				postData.name = $('#searchResumeForm_name').val();
				postData.companyName = $('#searchResumeForm_companyName').val();
				postData.title = $('#searchResumeForm_positionName').val();
				postData.telephone = $('#searchResumeForm_tel').val();
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
			var id = parseInt($(this).closest('tr')[0].id);

			if(action == 'reply') {
				$('#replyDialog').data().id = id;
				var resume = ADMIN.getItemFromByAttr(pageData.resumeList, 'id', id);
				$('#replyDialog .dialogTitle').text(resume.name + ' / ' + resume.companyName + ' / ' + resume.positionName);
				var preStatus = '未初筛';
				resume.allStatus && resume.allStatus.length && (preStatus = resume.allStatus[resume.allStatus.length - 1]);
				$('#replyDialog [name=preStatus]').text(preStatus);
				$('#replyForm')[0].reset();
				$('#replyDialog').modal('show');
			}
		});
	}
	function bindReplyFormAction() {
		$('#replyForm').submit(function() {
			if(!ADMIN.formValidate('#replyForm', {
				status: 'nonempty',
				reply: 'nonempty',
			})) {
				return false;
			}
			var data = {
				id: $('#replyDialog').data().id,
				status: $('#replyDialog [name=status]').val(),
				reply: $('#replyDialog [name=reply]').val(),
			};
			$.post('../resume/modify.do', data, function(result) {
				if(result.status) {
					alert('成功');
					$('#jqGrid').trigger("reloadGrid");
				} else {
					alert(result.msg);
				}
			}, 'json');
			
			return false;
		});
	}
});