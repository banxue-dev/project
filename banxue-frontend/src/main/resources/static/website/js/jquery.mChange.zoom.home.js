// JavaScript Document
/*
$("").mChange({
	auto  : 1,       //是否自动播放,1为自动,0为手动
	arrow : 1,       //是否反转箭头,1为点击左侧向右滚动,0则相反
	speed : 500,     //效果速度,大于0.3
	time  : 5000,    //周期时间，大于1
	nav   : null,    //导航
	nave  : "click", //导航事件
	navs  : 0,  //导航滚动
	prev  : null,    //上一个
	next  : null,    //下一个
	cycle : 1,       //是否循环
	mousestop : 0,   //是否鼠标划入停止
	mousewheel : 0,  //是否开启滚轮事件
	animates : function(idx){} //自定义函数
});
*/
(function($) {
	$.fn.mChange = function(options) {
		var w1,np,ns,v;
		var opts = $.extend({},$.fn.mChange.defaults,options); 
		//初始化
		var c=$(this);
		var f=c.parent();
		var n=$(opts.nav);
		var i=0;
		var sw=1;
		var cnow;
		c.css({scale:0.75,opacity:0});
		
		if(opts.navs){
			w1 = n.outerWidth()+(parseInt(n.css("margin-left"))?parseInt(n.css("margin-left")):0)+(parseInt(n.css("margin-right"))?parseInt(n.css("margin-right")):0);
			np= $(opts.nav).parent();
			n.wrapAll("<div class='scroll' style='height:100%;'></div>");
			ns=np.find(".scroll");
			ns.width(n.size()*w1);
			np.stop().animate({scrollLeft:0},100);
		}
			
		function run(){
			if(sw){
				i++;
				i=change(i);
			}
		}
		
		function change(idx){
			if(c.size()>1 || idx=="begin"){
				sw=0;
				if(idx=="begin"){
					idx=0;
					speed=opts.speed;
					cnow=c.eq(0);
					cnow.addClass("dly");
				}
				else{
					speed=opts.speed;
					cnow.stop().transition({opacity:0,scale:1.5},speed,'cubic-bezier(0.5,0,0.2,1)',function(){
						$(this).css({scale:0.7,zIndex:-1});
					});
					c.removeClass("dly");
				}
				
				n.removeClass("now");
				if(idx==c.size()){idx=0;}
				if(idx<0){idx=c.size()-1;}
				
				cnow=c.eq(idx);
				cnow.stop().transition({opacity:1,scale:1,zIndex:0},speed,'cubic-bezier(0,1,1,1)',function(){
					if(typeof(opts.animates)=="function"){
						//opts.animates(idx);//执行自定义动画
					}
					$(this).transition({scale:1.1},7000);
					sw=1;
				});
				opts.animates(idx);//执行自定义动画
				
				c.removeClass('s');
				cnow.addClass('s');
				
				n.eq(idx).addClass("now");			
				if(opts.navs){autonav(idx);}
				
				return idx;
			}
		}
		change("begin");
		
		if(opts.auto==1){
			setTimeout(function(){
				v=setInterval(run,opts.time);
			},1500);
			
		}
		
		if(opts.mousestop==1){
			c.hover(
				function(){sw=0;},
				function(){sw=1;}
			);
		}
		
		//导航溢出部分滑动显示
		function autonav(idx){
			np.animate({scrollLeft:idx*w1});
		}
			
		n.each(function(index, element) {
            $(this).bind(opts.nave,function(){
				if(sw==1 && c.eq(index).size()>0){
					change(index);
					i=index;
					if(opts.auto){
						clearInterval(v);
						v=setInterval(run,opts.time);
					}
				}
			});
        });
		
		$(opts.prev).click(function(){
			if(sw==1){
				if(opts.cycle==1 || (opts.cycle==0 && i>0)){
					i--;
					i=change(i);
					if(opts.auto==1){
						clearInterval(v);
						v=setInterval(run,opts.time);
					}
				}
			}
		});
		$(opts.next).click(function(){
			if(sw==1){
				if(opts.cycle==1 || (opts.cycle==0 && i<c.size()-1)){
					i++;
					i=change(i);
					if(opts.auto){
						clearInterval(v);
						v=setInterval(run,opts.time);
					}
				}
			}
		});
		
		if(opts.mousewheel){
			f.mousewheel(function(event,delta,deltaX,deltaY){
				if(delta<0){
					if(sw==1){
						if(opts.cycle==1 || (opts.cycle==0 && i<c.size()-1)){
							i++;
							i=change(i);
							if(opts.auto){
								clearInterval(v);
								v=setInterval(run,opts.time);
							}
						}
					}
				}
				else{
					if(sw==1){
						if(opts.cycle==1 || (opts.cycle==0 && i>0)){
							i--;
							i=change(i);
							if(opts.auto){
								clearInterval(v);
								v=setInterval(run,opts.time);
							}
						}
					}
				}
				event.stopPropagation();
				event.preventDefault();
			});
		}
			
	};
	$.fn.mChange.defaults = {
		auto  : 1,
		arrow : 1,
		speed : 800,
		time  : 5000,
		nav   : null,
		nave  : "click",
        navs  : 0,
		prev  : null,
		next  : null,
		cycle : 1,
		mousestop : 0,
	    mousewheel : 0,
		animates  : null
	}
})(jQuery);