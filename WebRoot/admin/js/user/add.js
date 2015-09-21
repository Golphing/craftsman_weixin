$(document).ready(function() {
	ADMIN.initDateTimePicker('.form_datetime');
	$('#addUserForm').submit(function() {
		if(!ADMIN.formValidate('#addUserForm', {
			wechatAccount: 'nonempty',
			nickname: 'nonempty',
			password: ['nonempty'],
			repassword: 'nonempty',
			name: 'nonempty',
			birthday: 'nonempty',
			telephone: 'nonempty',
			email: ['nonempty', 'email'],
			home: 'nonempty',
		})) {
			return false;
		}
		var psw = $('[name=password]', this).val();
		var repsw = $('[name=repassword]', this).val();
		if(psw != repsw) {
			$('[name=repassword]', this).parents('.form-group').addClass('has-error').find('.input-tips').text('两次密码输入不同').show();
			return false;
		} else {
			$('[name=repassword]', this).parents('.form-group').removeClass('has-error').find('.input-tips').hide();
		}
		var data = {
			wechatAccount: $('[name=wechatAccount]', this).val(),
			nickname: $('[name=nickname]', this).val(),
			password: $('[name=password]', this).val(),
			name: $('[name=name]', this).val(),
			gender: $('[name="gender"]:checked', this).val(),
			birthday: $('[name=birthday]', this).val(),
			telephone: $('[name=telephone]', this).val(),
			email: $('[name=email]', this).val(),
			home: $('[name=home]', this).val(),
		};
		$.post('../user/create.do', data, function(result) {
			if(result.status) {
				alert('成功');
				$('#addUserForm')[0].reset();
			} else {
				alert(result.msg);
			}
		}, 'json');
		return false;
	});
});