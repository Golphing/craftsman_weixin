$(document).ready(function() {
	initUserInfo();
	bindEditUserBtnAction();
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
					'<a href="javascript:void(0)"><span class="glyphicon glyphicon-edit pull-right work-edit-icon"></span></a>' +
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
				};
				$.post('../user/resume/modify.do', data, function(result) {
					if(result.status) {
						updateUserInfo(data);
						$(this).text('修改');
						$('#userInfoForm').hide();
						$('#userInfo').show();
					} else {
						alert(result.msg);
					}
				}, 'json');
			}
		});
	}
	
});