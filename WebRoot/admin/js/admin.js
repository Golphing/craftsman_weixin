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
    ADMIN.initSidebar(hash);
    ADMIN.loadHtml(hash);
    window.onhashchange = function() {
    	var hash = window.location.hash;
    	ADMIN.loadHtml(hash);
    };
 
});
var pageData = {};
var ADMIN = {
	pageHashMap: {
		'#resume': 				'resume/search',
		
		'#company/add': 		'company/add',
		'#company/search':		'company/search',
		'#company/position/search':'company/search_position',
	},
	initSidebar: function(hash) {
		hash == '' && (hash = '#resume');
	    var li = $('#sidebar a[href="'+ hash +'"]').closest('li');
	    li.addClass('active');
	    if(li.parents('ul').length > 1) {
	    	var navDiv = li.closest('div');
			$('li[data-target=#'+ navDiv[0].id +']').addClass('active').removeClass('collapsed');
			navDiv.collapse('show');
	    }
	},
	loadHtml: function(hash) {
		hash == '' && (hash = '#resume');
		if(!hash in ADMIN.pageHashMap) return;
		var url = ADMIN.pageHashMap[hash] + '.html';
		$.get(url, function(result) {
			$('.main').html(result);
		}, 'html');
	},
	formNonemptyValidate: function(selector, fieldNames) {
		var flag = true;
		
		var hintFunc = function(jqDom) {
			if(jqDom.val() === '') {
				flag = false;
				jqDom.parents('.form-group').addClass('has-error');
			} else {
				jqDom.parents('.form-group').removeClass('has-error');
			}
		}
		
		if(fieldNames === null || fieldNames === undefined) {
			inputs = $(selector + ' input');
			selects = $(selector + ' select');
			textarea = $(selector + ' textarea');
			inputs.each(function() {
				hintFunc($(this));
			});
			selects.each(function() {
				hintFunc($(this));
			});
			textarea.each(function() {
				hintFunc($(this));
			});
		} else {
			for(var i=0; i<fieldNames.length; i++) {
				field = $(selector + ' [name="'+ fieldNames[i] +'"]');
				hintFunc(field);
			}
		}
		
		return flag;
	},
};

