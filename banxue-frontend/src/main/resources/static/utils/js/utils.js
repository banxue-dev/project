var entity = {
	isGet : true,// 是否可以获取code了，如果还没有走出超时时间，就不允许获取
	codeInit:function(timeout,codeid){
		entity.codeTime=timeout;
		entity.codeDom=codeid;
	}
	codeDom:'test',
	codeTime : 60,
	codeStart : function() {
		$('#'+codeDom).css('background-color', '#cccccc');
		$('#'+codeDom).text('(' + (entity.codeTime--) + ')S后再次获取');
	},
	codeCenter:function(){
		$('#'+codeDom).text('(' + (--entity.codeTime) + ')S后再次获取');
	}
	codeEnd : function() {
		$('#'+codeDom).css('background-color', '#4cd463');
		$('#'+codeDom).text('获取验证码');
		entity.isGet = true;
		entity.codeTime = 60;
	},
	getCode:function(url,data,callback){
		if(isGet){
			isGet=false;
			$.ajax({
				url:url,
				data:data,
				success:function(res){
					if(callback){
						callback(res);
					}else{
						if(data.code=="000000"){
							//成功
							entity.codeStart();
							var codeInv = setInterval(function() {
								if (entity.codeTime <= 1) {
									entity.codeEnd();
									window.clearInterval(codeInv);
								} else {
									entity.codeCenter();
								}
							}, 1000);
						}else{
							//失败
							entity.errTips(res.msg);
						}
					}
				}
			})
		}
	}
	/**
	 * 单元行验证通过
	 */
	codePass : function(dom) {
		dom.parent('div').css('border', '1px solid #4cd463');
	},
	/**
	 * 单元行验证不通过
	 */
	codeNPass : function(dom) {
		entity.errTips(dom.attr('tips'));
		dom.parent('div').css('border', '1px solid red');
		dom.focus();
	},
	err_tips_state : true,
	/**
	 * 错误提示
	 * @param msg
	 * @returns
	 */
	errTips : function(msg) {
		var istips = $('#_errTips').text();
		if (istips) {
			$('#_errTips').text(msg);

		} else {
			var tipsHtml = '<div id="_errTips" style="border-radius:5px;top:20px;position:fixed;width:80%;left:10%;height:40px;line-height:40px;text-align:center;background-color:#ec5224;">'
					+ msg + '</div>';
			$('body').append(tipsHtml);
		}
		if (entity.err_tips_state) {
			$('#_errTips').show();
			entity.err_tips_state = false;
			setTimeout(function() {
				$('#_errTips').hide();
				entity.err_tips_state = true;
			}, 2000);
		}

	},
	startloading : function() {
		var isload = $('#_loading').text();
		if (isload) {
			$('#_loading').show();
		} else {
			var loadHtml = '<div id="_loading" style="border-radius:5px;top:20px;position:fixed;width:100%;height:100%;text-align:center;background-color:black;opacity:0.5;"><div style="margin-top:10%;color:white;">加载中..</div></div>';
			$('body').append(loadHtml);
		}
	},
	endloading : function() {
		$('#_loading').hide();
	}
}
/**
 * 验证表单
 */
var validateFrom = function(FormId) {
	var inps = $('#'+FormId+' :input');
	var params = {};
	var res = true;
	var thDom;
	for (var i = 0; i < inps.size(); i++) {
		var thDom = $(inps[i]);
		var vthen = thDom.attr('valid');
		var thenVal = thDom.val();
		if(!vthen){
			//没有valid属性，直接跳过。
			continue;
		}
		if (vthen == 'required') {
			if (thenVal == '') {
				res = false;
				break;
			}
			entity.codePass(thDom);
		}
		if (vthen == 'phone') {
			var phoneReg = /(^1[3|4|5|7|8]\d{9}$)|(^09\d{8}$)/;
			if (!phoneReg.test(thenVal)) {
				res = false;
				break;
			}
			entity.codePass(thDom);
		}
		if (vthen == 'ID') {
			var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
			if (reg.test(thenVal) === false) {
				res = false;
				break;
			}
			entity.codePass(thDom);
		}
		//如果valid=att，则表示要与他的某个属性值做比较,这里使用v-value
		if (vthen == 'att') {
			if (thenVal == thDom.attr('v-value')) {
				res = false;
				break;
			}
			entity.codePass(thDom);
		}
		//如果有not这个属性，表示此值不参与提交
		if (thDom.attr('not') != 'not') {
			var thId = thDom.attr('id');
			params[thId] = thenVal;
		}
	}
	if (!res) {
		entity.codeNPass(thDom);
	}
	params['res'] = res;
	return params;
}
$(function() {
	/**
	 * 增加当ajax提交时的加载框
	 */
	$(document).ajaxStart(function () {
        //正在加载
        entity.startloading();
    });
    $(document).ajaxStop(function () {
       //删除加载
       entity.endloading();
    });
})