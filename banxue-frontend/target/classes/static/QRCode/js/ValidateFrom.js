var entity={
			getCode:true,
			codeTime:5,
			codeStart:function(){
				$('#_getCode').css('background-color','#cccccc');
				$('#_getCode').text('('+(entity.codeTime--)+')S后再次获取');
			},
			codeEnd:function(){
				$('#_getCode').css('background-color','#4cd463');
					$('#_getCode').text('获取验证码');
					entity.getCode=true;
					entity.codeTime=60;
			},
			codePass:function(dom){
				dom.parent('div').css('border','1px solid #4cd463');
			},
			codeNPass:function(dom){
				entity.errTips(dom.attr('tips'));
				dom.parent('div').css('border','1px solid red');
				dom.focus();
			},err_tips_state:true,
			errTips:function(msg){
				var istips=$('#_errTips').text();
				if(istips){
					$('#_errTips').text(msg);
					
				}else{var tipsHtml='<div id="_errTips" style="border-radius:5px;top:20px;position:fixed;width:80%;left:10%;height:40px;line-height:40px;text-align:center;background-color:red;">'+msg+'</div>';
					$('body').append(tipsHtml);
				}
				if(entity.err_tips_state){
					$('#_errTips').show();
					entity.err_tips_state=false;
					setTimeout(function(){
						$('#_errTips').hide();	
						entity.err_tips_state=true;
					},2000);
				}
				
			},
			startloading:function(){
				var isload=$('#_loading').text();
				if(isload){
					$('#_loading').show();
				}else{
					var loadHtml='<div id="_loading" onclick="entity.endloading()" style="border-radius:5px;top:20px;position:fixed;width:100%;height:100%;text-align:center;background-color:black;opacity:0.5;"><div style="margin-top:10%;color:white;">加载中..</div></div>';
					$('body').append(loadHtml);
				}
			},endloading:function(){
				$('#_loading').hide();
			}
		}
var _validateFrom=function(){
		var inps=$('#subForm :input');
		var params={};
		var res=true;
		var thDom;
		for(var i=0;i<inps.size();i++){
			var thDom=$(inps[i]);
			var vthen=thDom.attr('valid');
			var thenVal=thDom.val();
			if(vthen=='required'){
				if(thenVal==''){
					res=false;
					break;
				}
				entity.codePass(thDom);
			}
			if(vthen=='phone'){
				var phoneReg = /(^1[3|4|5|7|8]\d{9}$)|(^09\d{8}$)/;
				if (!phoneReg.test(thenVal)) {
					res=false;
					break;
				}
				entity.codePass(thDom);
			}
			if(vthen=='ID'){
				var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
				if(reg.test(thenVal) === false){
					res=false;
					break;
				}
				entity.codePass(thDom);
			}
			if(vthen=='not'){
				if(thenVal==thDom.attr('v-value')){
					res=false;
					break;
				}
				entity.codePass(thDom);
			}
			if(thDom.attr('not')!='not'){
				var thId=thDom.attr('id');
				params[thId]=thenVal;
			}
		}
		if(!res){
			entity.codeNPass(thDom);
		}
		params['res']=res;
		return params;
	}