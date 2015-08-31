$(document).ready(function() {
	$('#addCompanyForm').submit(function() {
		if(!ADMIN.formNonemptyValidate('#addCompanyForm', ['name', 'weight'])) {
			return false;
		}
		var data = {
			name: $('[name=name]', this).val(),
			weight: $('[name=weight]', this).val(),
			url: $('[name=url]', this).val(),
		};
		$.post('', data, function(result) {
			
		}, 'json');
		return false;
	});
});