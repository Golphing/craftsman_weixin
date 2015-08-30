$("document").ready(function () {
	$.jgrid.defaults.width = 780;
	$.jgrid.defaults.styleUI = 'Bootstrap';
	
    $('#sidebar li').click(function() {    	
    	$('#sidebar li.active').removeClass('active');
    	$(this).addClass('active');
    	if($(this).parents('ul').length > 1) {
    		var pid = $(this).closest('div')[0].id;
    		$('li[data-target=#'+ pid +']').addClass('active');
    	}
    	if($(this).attr('data-target')) {
    		$('.glyphicon', this).toggleClass('glyphicon-menu-up').toggleClass('glyphicon-menu-down');
    	}
    	if($(this).hasClass('collapsed')) {
    		var cli = $('#sidebar .nav-li').not('.collapsed');
    		if(cli.length) {
    			var navId = cli.attr('data-target');
    			cli.addClass('collapsed');
    			$(navId).collapse('hide');
    		}
    	}
    });
    
    var hash = window.location.hash;
    loadHtml(hash);
    window.onhashchange = function() {
    	var hash = window.location.hash;
    	loadHtml(hash);
    }
 
});
function loadHtml(hash) {
	var hashMap = {
		'#resume': 				'resume/search',
		
		'#company/add': 		'company/add',
		'#company/search':		'company/search',
		'#company/position/search':'company/search_position'
	};
	hash == '' && (hash = '#resume');
	if(!hash in hashMap) return;
	var url = hashMap[hash] + '.html';
	$.get(url, function(result) {
		$('.main').html(result);
	}, 'html');
}
