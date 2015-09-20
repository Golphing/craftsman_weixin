$(document).ready(function() {
	initUserInfo();
	bindEditUserBtnAction();
	bindAddWorkBtnAction();
	bindAddWorkFormAction();
	bindEditWorkBtnAction();
	bindEditWorkFormAction();
/*	
	$('.form_datetime').datetimepicker({
    	format: 'yyyy-mm-dd',
    	pickerPosition: "bottom-left",
        todayBtn:  true,
		autoclose: true,
		todayHighlight: true,
		startView: 4,
		minView: 2,
		endDate: new Date()
	});*/
	
	function initUserInfo() {
		var data = {
			userId: ADMIN.URL.getHashParm('userId'),
		};
		$.get('../user/search/resume.do', data, function(result) {
			if(result.status) {
				pageData.resume = result.data;
				updateUserInfo(result.data);
				updateWorkInfo(result.data.works);
			} else {
				alert(result.msg);
			}
		}, 'json');
	}
	function updateUserInfo(data) {
		$('#userInfo_name').text(data.name);
		$('#userInfoForm_name').val(data.name);
		$('#userInfo_gender').text(data.gender);
		$('#userInfoForm [name="gender"][value="'+ data.gender +'"]')[0].checked = true;
		$('#userInfo_birthday').text(data.birthday);
		$('#userInfoForm_birthday').val(data.birthday);
		$('#userInfo_telephone').text(data.telephone);
		$('#userInfoForm_telephone').val(data.telephone);
		$('#userInfo_email').text(data.email);
		$('#userInfoForm_email').val(data.email);		
		$('#userInfo_home').text(data.home);
		$('#userInfoForm_home').val(data.home);				
	}
	function updateWorkInfo(works) {
		var html = '';
		for(var i=0; i<works.length; i++) {
			html += '' +
				'<div class="form-horizontal list-group-item">' +
					'<a href="javascript:void(0)" class="edit-work-btn" row="'+ i +'"><span class="glyphicon glyphicon-edit pull-right work-edit-icon"></span></a>' +
					'<div class="form-group">' + 
						'<label class="col-md-3 control-label">时间:</label>' +
						'<div class="col-md-4">' +
							'<label id="userInfo_name" class="control-label">'+ works[i].beginTime + '--' + works[i].endTime +'</label>' +
						'</div>' +
					'</div>' +
					'<div class="form-group">' + 
						'<label class="col-md-3 control-label">行业:</label>' +
						'<div class="col-md-4">' +
							'<label id="userInfo_name" class="control-label">'+ works[i].profession +'</label>' +
						'</div>' +
					'</div>' +					
					'<div class="form-group">' + 
						'<label class="col-md-3 control-label">公司:</label>' +
						'<div class="col-md-4">' +
							'<label id="userInfo_name" class="control-label">'+ works[i].company +'</label>' +
						'</div>' +
					'</div>' +
					'<div class="form-group">' + 
						'<label class="col-md-3 control-label">部门:</label>' +
						'<div class="col-md-4">' +
							'<label id="userInfo_name" class="control-label">'+ works[i].department +'</label>' +
						'</div>' +
					'</div>' +
					'<div class="form-group">' + 
						'<label class="col-md-3 control-label">职位:</label>' +
						'<div class="col-md-4">' +
							'<label id="userInfo_name" class="control-label">'+ works[i].position +'</label>' +
						'</div>' +
					'</div>' +
					'<div class="form-group">' + 
						'<label class="col-md-3 control-label">描述:</label>' +
						'<div class="col-md-4">' +
							'<label id="userInfo_name" class="control-label">'+ works[i].description +'</label>' +
						'</div>' +
					'</div>' +					
				'</div>';
		}
		$('#workList').html(html);
	}
	function bindEditUserBtnAction() {
		$('#userInfoEditBtn').click(function() {
			$(this).toggleClass('editing');
			if($(this).hasClass('editing')) {
				$(this).text('保存');
				$('#userInfo').hide();
				$('#userInfoForm').show();
			} else {
				var data = {
					userId: ADMIN.URL.getHashParm('userId'),
					resumeId: pageData.resume.id,
					name: $('#userInfoForm_name').val(),
					gender: $('#userInfoForm [name="gender"]:checked').val(),
					birthday: $('#userInfoForm_birthday').val(),
					telephone: $('#userInfoForm_telephone').val(),
					email: $('#userInfoForm_email').val(),
					home: $('#userInfoForm_home').val(),
				};
				$.post('../user/resume/modify.do', data, function(result) {
					if(result.status) {
						initUserInfo();
						$(this).text('修改');
					} else {
						alert(result.msg);
					}
				}, 'json');
			}
		});
	}
	function bindAddWorkBtnAction() {
		$('#addWorkBtn').click(function() {
			$('#addWorkDialog').modal('show');
		});
	}
	function bindAddWorkFormAction() {
		$('#addWorkForm').submit(function() {
			if(!ADMIN.formValidate('#addWorkForm', {
				beginTime: 'nonempty',
				endTime: 'nonempty',
				profession: 'nonempty',
				company: ['nonempty'],
				department: 'nonempty',
				position: 'nonempty',
				description: 'nonempty',
			})) {
				return false;
			}
			var data = {
				workId: $('#editWorkDialog').data().id,
				beginTime: $('#addWorkForm [name="beginTime"]').val(),
				endTime: $('#addWorkForm [name="endTime"]').val(),				
				profession: $('#addWorkForm [name="profession"]').val(),
				company: $('#addWorkForm [name="company"]').val(),
				department: $('#addWorkForm [name="department"]').val(),
				position: $('#addWorkForm [name="position"]').val(),
				description: $('#addWorkForm [name="description"]').val(),
			};
			$.post('../user/work/create.do', data, function(result) {
				if(result.status) {
					initUserInfo();
					$('#addWorkDialog').modal('hide');
				} else {
					alert(result.msg);
				}
			}, 'json');
			return false;
		});
	}
	function bindEditWorkBtnAction() {
		$('#workList').on('click', '.edit-work-btn', function() {
			var row = $(this).attr('row');
			var work = pageData.resume.works[row];
			$('#editWorkDialog').data().row = row;
			$('#editWorkDialog').data().id = work.id;
			$('#editWorkDialog [name="beginTime"]').val(work.beginTime);
			$('#editWorkDialog [name="endTime"]').val(work.endTime);			
			$('#editWorkDialog [name="profession"]').val(work.profession);
			$('#editWorkDialog [name="company"]').val(work.company);
			$('#editWorkDialog [name="department"]').val(work.department);
			$('#editWorkDialog [name="position"]').val(work.position);
			$('#editWorkDialog [name="description"]').val(work.description);
			
			$('#editWorkDialog').modal('show');
		});
	}
	function bindEditWorkFormAction() {
		$('#editWorkForm').submit(function() {
			if(!ADMIN.formValidate('#editWorkForm', {
				beginTime: 'nonempty',
				endTime: 'nonempty',
				profession: 'nonempty',
				company: ['nonempty'],
				department: 'nonempty',
				position: 'nonempty',
				description: 'nonempty',
			})) {
				return false;
			}
			var data = {
				workId: $('#editWorkDialog').data().id,
				beginTime: $('#editWorkDialog [name="beginTime"]').val(),
				endTime: $('#editWorkDialog [name="endTime"]').val(),				
				profession: $('#editWorkDialog [name="profession"]').val(),
				company: $('#editWorkDialog [name="company"]').val(),
				department: $('#editWorkDialog [name="department"]').val(),
				position: $('#editWorkDialog [name="position"]').val(),
				description: $('#editWorkDialog [name="description"]').val(),
			};
			$.post('../user/work/modify.do', data, function(result) {
				if(result.status) {
					initUserInfo();
					$('#editWorkDialog').modal('hide');
				} else {
					alert(result.msg);
				}
			}, 'json');
			return false;
		});
	} 
	
});