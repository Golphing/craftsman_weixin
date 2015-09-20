$("document").ready(function () {
	$.extend(true, $.jgrid.defaults, {
		width: 780,
		styleUI: 'Bootstrap',
		datatype: "json",
		viewrecords: true, // show the current page, data rang and total records on the toolbar
		autowidth: true,
		height: 'auto',
		rowNum: 10,
		rowList:[10,20,30],
		prmNames: {
			page: 'pageNumber',
			rows: 'pageSize',
			sort: 'orderBy',
			order: 'order',
		},
		jsonReader: {
			root: 'data',
			page: 'pagingResult.pageNumber',
			total: 'pagingResult.totalPage',
			records: 'pagingResult.recordNumber'
		},

	});

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
    
    var hash = ADMIN.URL.getHashPath();
    ADMIN.initSidebar(hash);
    ADMIN.loadHtml(hash);
    window.onhashchange = function() {
    	var hash = ADMIN.URL.getHashPath();
    	ADMIN.loadHtml(hash);
    };
 
});
var pageData = {};
var ADMIN = {
	pageHashMap: {
		'#/resume': 			'resume/search',
		'#/position':			'position/search',
		
		'#/company/add': 		'company/add',
		'#/company/search':		'company/search',
		'#/company/position':	'company/position',
		'#/company/position/search':'company/search_position',
		
		'#/user/add':			'user/add',
		'#/user/search':		'user/search',
		'#/user/resume':		'user/resume',
		'#/user/resume/deliver':'user/resumeDeliver',
	},
	initSidebar: function(hash) {
		hash == '' && (hash = '#/resume');
	    var li = $('#sidebar a[href="'+ hash +'"]').closest('li');
	    li.addClass('active');
	    if(li.parents('ul').length > 1) {
	    	var navDiv = li.closest('div');
			$('li[data-target=#'+ navDiv[0].id +']').addClass('active').removeClass('collapsed');
			navDiv.collapse('show');
	    }
	},
	loadHtml: function(hash) {
		hash == '' && (hash = '#/resume');
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
		};
		
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
	getItemFromByAttr: function(array, attr, value) {
		for(var i in array) {
			if(array[i][attr] == value) {
				return array[i];
			}
		}
		return false;
	},
	formValidate: function(selector, options) {
		var flag = true;
		for(var i in options) {
			var field = $(selector + ' [name="'+ i +'"]');
			if(!(options[i] instanceof Array)) {
				options[i] = [options[i]];
			}
			for(var j=0; j<options[i].length; j++) {
				var funcName = options[i][j];
				if(ADMIN.Validator[funcName].call(this, field.val())) {
					field.parents('.form-group').removeClass('has-error').find('.input-tips').hide();
				} else {
					field.parents('.form-group').addClass('has-error').find('.input-tips').text(ADMIN.Validator.message[funcName]).show();
					flag = false;
					break;
				}
			}
		}
		return flag;
	},
	Validator: {
		message: {
			nonempty: '这是必填项',
			number: '请输入数字',
			http: '格式（http://*** 或 https://***）',
			email: '格式(***@***)',
		},
		nonempty: function(value) {
			if(value == '') {
				return false;
			}
			return true;
		},
		number: function(value) {
			if(value && isNaN(Number(value))) {
				return false;
			}
			return true;
		},
		http: function(value) {
			var reg = /(http|https):\/\/.*/;
			if(value && !reg.test(value)) {
				return false;
			}
			return true;
		},
		email: function(value) {
			var reg = /^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/;
			if(value && !reg.test(value)) {
				return false;
			}
			return true;
		},
	},
	initDateTimePicker: function(selector, config) {
		$(selector).datetimepicker($.extend({
	    	format: 'mm/dd/yyyy',
	    	pickerPosition: "bottom-left",
	        todayBtn:  true,
			autoclose: true,
			todayHighlight: true,
			startView: 2,
			minView: 2,
			endDate: new Date()
		}, config));
	},
	URL: {
		getParam: function(param) {
			var search = window.location.search;
			var reg = new RegExp("(\\?|&)"+ param +"=([^&]*)(&|$)");
			var m = hash.match(reg);
			if(m) {
				return m[2];
			}
			return null;
		},
		getHash: function() {
			return window.location.hash;
		},
		getHashPath: function() {
			var hash = ADMIN.URL.getHash(), index;
			if((index = hash.indexOf('?')) == '-1') {
				return hash;
			} else {
				return hash.substring(0, index);
			}
		},
		getHashParm: function(param) {
			var hash = ADMIN.URL.getHash();
			var reg = new RegExp("(\\?|&)"+ param +"=([^&]*)(&|$)");// /(\?|&)id=([^&]*)(&|$)/
			var m = hash.match(reg);
			if(m) {
				return m[2];
			}
			return null;
		},
	}
};

