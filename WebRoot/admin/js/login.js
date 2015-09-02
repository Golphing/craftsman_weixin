$(document).ready(function() {
	$('#loginForm').submit(function() {
		var flag = true;
		var $username = $('#loginForm [name="username"]');
		var $psw = $('#loginForm [name="password"]');
		if($username.val() == '') {
			flag = false;
			$username.parents('.form-group').addClass('has-error');
		} else {
			$username.parents('.form-group').removeClass('has-error');
		}
		if($psw.val() == '') {
			flag = false;
			$psw.parents('.form-group').addClass('has-error');
		} else {
			$psw.parents('.form-group').removeClass('has-error');
		}	
		if(flag) {
			window.location.href = 'index.html';
		}
		return false;
	});
	
});