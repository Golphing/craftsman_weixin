$(document).ready(function() {
	$('#addCompanyForm').submit(function() {
		if(!ADMIN.formValidate('#addCompanyForm', {
			name: 'nonempty',
			weight: ['nonempty', 'number'],
			url: 'http',
		})) {
			return false;
		}

		var data = {
			name: $('[name=name]', this).val(),
			weight: $('[name=weight]', this).val(),
			url: $('[name=url]', this).val(),
		};
		$.post('../company/create.do', data, function(result) {
			if(result.status) {
				alert('成功');
				$('#addCompanyForm')[0].reset();
			} else {
				alert(result.msg);
			}
		}, 'json');
		return false;
	});
});