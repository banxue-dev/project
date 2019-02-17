
(function($) {
	$.fn.clients = function(options) {
		var opts = $.extend({},$.fn.mChange.defaults,options);
		var listcopy =opts.arr;
		cl=$(this);
		
		
		function delay(e,i){
			var s=GetRandomNum(3,6);
			choose(e);
			clearInterval(i);
			i=setInterval(function(){delay(e,i)},s*1000);
		}
		
		//派发随机动作
		if(cl.find(".scroll").size()==5){
			var s1,s2,s3,s4,s5;
			s1=setInterval(function(){delay(".s1",s1)},GetRandomNum(2,8)*1000);
			s2=setInterval(function(){delay(".s2",s2)},GetRandomNum(2,8)*1000);
			s3=setInterval(function(){delay(".s3",s3)},GetRandomNum(2,8)*1000);
			s4=setInterval(function(){delay(".s4",s4)},GetRandomNum(2,8)*1000);
			s5=setInterval(function(){delay(".s5",s5)},GetRandomNum(2,8)*1000);
			
		}
		function choose(e){
			//if(!cl.find(e).attr("class").match("hover")){
				//循环判断是否重复
				while(1>0){
					var x=0;
					var ac = GetRandomNum(0,listcopy.length-1);
					if(cl.find(".child[aid="+$(listcopy[ac]).attr("aid")+"]").size()==0){
						cl.find(e).append(listcopy[ac]);
						cl.find(e).animate({scrollTop:80},"slow",function(){cl.find(e).find(".child:first").remove();});
						break;
					}
				}	
			//}
		}
	
		//显示摘要
		/*$("#client .scroll").hover(
			function(){
				$(this).addClass("hover");
				$(this).find("h2").stop().animate({marginTop:-50},300);
			},
			function(){
				$(this).removeClass("hover");
				$(this).find("h2").stop().animate({marginTop:15},300);
			}
		);*/
	}

	$.fn.clients.defaults = {
			 arr : ""
	}
})(jQuery);
