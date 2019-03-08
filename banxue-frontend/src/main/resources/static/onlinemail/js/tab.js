
!function(window) {
    "use strict";

    var doc = window.document
      , ydui = {};

    $(window).on('load', function() {});

    var util = ydui.util = {

        parseOptions: function(string) {},

        pageScroll: function() {}(),

        localStorage: function() {}(),

        sessionStorage: function() {}(),

        serialize: function(value) {},

        deserialize: function(value) {}
    };

    function storage(ls) {}

    $.fn.emulateTransitionEnd = function(duration) {}
    ;

    if (typeof define === 'function') {
        define(ydui);
    } else {
        window.YDUI = ydui;
    }

}(window);
var refTAB;
!function(window) {
    "use strict";

    function Tab(element, options) {
        this.$element = $(element);
        this.options = $.extend({}, Tab.DEFAULTS, options || {});
        this.init();
        this.bindEvent();
        this.transitioning = false;
    }

    Tab.TRANSITION_DURATION = 150;

    Tab.DEFAULTS = {
        nav: '.tab-nav-item',
        panel: '.tab-panel-item',
        activeClass: 'tab-active'
    };

    Tab.prototype.init = function() {
        var _this = this
          , $element = _this.$element;

        _this.$nav = $element.find(_this.options.nav);
        _this.$panel = $element.find(_this.options.panel);
    }
    ;

    Tab.prototype.bindEvent = function() {
        var _this = this;
        _this.open(0);
        _this.$nav.each(function(e) {
            $(this).on('click.ydui.tab', function() {
                _this.open(e);
                refTAB=_this;
            });
        });
    }
    ;

    Tab.prototype.open = function(index) {
        var _this = this;

        index = typeof index == 'number' ? index : _this.$nav.filter(index).index();

        var $curNav = _this.$nav.eq(index);
        /*
         * 切换tab
         */
        _this.active($curNav, _this.$nav);
        _this.active(_this.$panel.eq(index), _this.$panel, function() {
            $curNav.trigger({
                type: 'opened.ydui.tab',
                index: index
            });
            _this.transitioning = false;
        });

		var html='';
        /*
         * 使用加载框
         */
		html+='<div style="width:100%;height:100%;text-align:center;top:20px;">';
		html+='<div class="loader"><div class="loader-inner line-scale-pulse-out-rapid"><div></div><div></div><div></div><div></div><div></div></div></div></div>';
		$('.tab-panel-item').eq(index).html(html);
		
		/*
         * 获取数据
         */
		$.ajax({
			url:'/bxNuoOrder/getOrderList',
			data:{state:index,openid:$('#openid').val()},
			type:'POST',
			async:false,
			dataType:'json',
			success:function(data){
				if(data.code=='000000'){
					html='';
					var orders=data.data;
					if(orders==null || orders.length<1){
						html='<div style="text-align:center;">暂时没有订单哦。</div>';
					}
					for(var i=0;i<orders.length;i++){
						var order=orders[i];
						var goods=order.goods;
						html+='<div class="tab-item">';
							html+=' <a href="javascript:void(0);" class="aui-well-item aui-well-item-clear">';
								html+='  <div class="aui-well-item-hd">'
									html+='  <img src="/QRCode/img/user-logo.png" alt="">';
								html+='</div>';
								html+='<div class="aui-well-item-bd">';
									html+=' <h3>半雪购物商城</h3>';
								html+=' </div>';
									html+=' <span class="aui-well-wait">'+order.orderCode+'</span>';
									//html+=' <span class="aui-well-item-fr"></span>';
							html+=' </a>';
							for(var j=0;j<goods.length;j++){
								var good=goods[j];
								html+='<div class="aui-mail-product">';
									html+='<a href="javascript:;" class="aui-mail-product-item">';
										html+='<div class="aui-mail-product-item-hd">';
											html+='<img src="'+good.goodsTitleImg+'" alt="">';
	                                	html+='</div>';
	                            		html+='<div class="aui-mail-product-item-bd">';
	                            			html+='<p>'+good.goodsName+'</p>';
	                            		html+='</div>';
	                               html+='</a>';
	                            html+='</div>';
								if(goods.length>1 && (j+1)<goods.length){
		                        	 html+='<div class="divHeight"></div>';
		                        }
							}
                        html+='<a href="javascript:;" class="aui-mail-payment">';
	                        html+='<p>'+order.createTime;
	                            html+='共<em>'+goods.length+'</em>';
	                                html+='件商品 实付款: ￥<i>'+order.totailPrice+'</i>';
	                        html+='</p>';
                        html+='</a>';
                        html+='<div class="aui-mail-button">';
                        	if(order.orderStatus==3){
                        		//完成
                        		html+='<a href="http://www.banxue.fun/wx/sendWxSignJumpage?jumpAddr=onliemail%2Findex">再次购买</a>';
                        	}
	                        if(order.orderStatus==1){
	                        	//待支付
	                        	html+=' <a href="javascript:;"  orderCode='+order.orderCode+' onclick="toPay(this)">去支付</a>';
	                        }
	                        if(order.orderStatus==2){
	                        	//已支付
	                        	html+='<a href="http://www.banxue.fun/wx/sendWxSignJumpage?jumpAddr=onliemail%2Findex">再次购买</a>';
	                        	if(order.wuliuId==null){
	                        		html+='<a href="javascript:;"  class="aui-df-color">待发货</a>';
	                        	}else{
	                        		html+='<a href="javascript:;" onclick="lookWuliu('+order.wuliuId+')" class="aui-df-color">查看物流</a>';
	                        	}
	                        }
	                        if(order.orderStatus==4){
	                        	//失效
	                        	html+='<a href="http://www.banxue.fun/wx/sendWxSignJumpage?jumpAddr=onliemail%2Findex">再次购买</a>';
	                        }
                            html+='</div>';
                        html+='</div>';
                        if(orders.length>1 && (i+1)<orders.length){
                        	 html+='<div class="divHeight"></div>';
                        }
					}
					$('.tab-panel-item').eq(index).html(html);
				}
			},
			error:function(res){
				alert('稍后再试');
			}
		})

    }
    ;

    Tab.prototype.active = function($element, $container, callback) {
        var _this = this
          , activeClass = _this.options.activeClass;

        var $avtive = $container.filter('.' + activeClass);

        function next() {
            typeof callback == 'function' && callback();
        }

        $element.one('webkitTransitionEnd', next).emulateTransitionEnd(Tab.TRANSITION_DURATION);

        $avtive.removeClass(activeClass);
        $element.addClass(activeClass);
    }
    ;

    function Plugin(option) {
        var args = Array.prototype.slice.call(arguments, 1);

        return this.each(function() {
            var target = this
              , $this = $(target)
              , tab = $this.data('ydui.tab');

            if (!tab) {
                $this.data('ydui.tab', (tab = new Tab(target,option)));
            }

            if (typeof option == 'string') {
                tab[option] && tab[option].apply(tab, args);
            }
        });
    }

    $(window).on('load.ydui.tab', function() {
        $('[data-ydui-tab]').each(function() {
            var $this = $(this);
            $this.tab(window.YDUI.util.parseOptions($this.data('ydui-tab')));
        });
    });

    $.fn.tab = Plugin;

}(window);
