// JavaScript Document

<!--上导航js-->
jQuery(".menu").slide({ type: "menu", titCell: ".nli", targetCell: ".sub", effect: "slideDown", delayTime:300, triggerTime: 0,returnDefault: true });
if ($(window).width() >= 1200) {
$('.menuph').click(function(){
	$('.menu').toggleClass('on');
	$(this).find(".point").toggleClass("active");
})
}
<!--上导航jsend-->
<!--手机导航js-->
	iHei=$(window).height();
if ($(window).width() < 1200) {
	$("#menuph").click(function(){
		"use strict";/*进入严格模式标语*/
		$(this).find(".point").toggleClass("active");
		$(".pf_menu").fadeToggle();
		$(".pf_menu_bg").fadeToggle();
		$('.topnav').toggleClass('on');
	});
	
	$('#navSlide .nli p').on('click',function(){
		if($(this).parent().hasClass('on')){
			$(this).parent().removeClass('on');
			$(this).next().stop().slideUp();
		}else{
			$(this).parent().addClass('on');
			$(this).next().stop().slideDown();
			$(this).parent().siblings().removeClass('on');
			$(this).parent().siblings().find('.l2_ul').stop().slideUp()
		}
	})
	$('#navSlide .nli .l2').on('click',function(){
	if($(this).hasClass('on')){
		$(this).removeClass('on');
		$(this).find('.l3_ul').stop().slideUp();
		}
	else{
		$(this).addClass('on');
		$(this).find('.l3_ul').stop().slideDown();
		$(this).siblings().removeClass('on');
		$(this).siblings().find('.l3_ul').stop().slideUp()
		}
	})
}
<!--手机导航jsend-->
<!--底部js-->
if ($(window).width() < 768) {
	$('.down h5').click(function(){
		$(this).next().slideToggle();
		$(this).toggleClass('on')
	});
	$('.footer ul li dl dt').click(function(){
		$(this).next().slideToggle();
		$(this).toggleClass('on')
	})
}
<!--底部js-end-->
<!--右侧漂浮js-->
$('.pf_right li .a1').mousemove(function(){
	$('.ltc_box').hide()
	$(this).next().show()
})
$('.pf_right li').mouseleave(function(){
	$(this).find('.ltc_box').hide()
})
$('.pf_right .btn').click(function(){
	$('.pf_right').toggleClass('on')
	$(this).toggleClass('on')
})
<!--右侧漂浮jsend-->
<!--内页边导航2-->
$('.pz_SideLayer_2 .ul1 .l1').hover(function(){
$(this).find('.ul2').stop().slideToggle()
})
if ($(window).width() < 992) {
$('.pz_SideLayer_2 .btn').click(function(){
	$(this).next().slideToggle()
	$(this).toggleClass('on')
})
}
<!--内页边导航2end-->






