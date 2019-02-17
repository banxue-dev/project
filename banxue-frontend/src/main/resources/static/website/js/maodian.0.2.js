function maodian(hd, bd, speed) {
    $(function () {
        $(hd).on('click', function () {
            var o = $(this).index();
            var s_top = $(bd).eq(o).offset().top;
            $('html,body').animate({ scrollTop: s_top }, speed);
        });

        var Sp = 0, Bdp = 0, io = 0;
        $(window).scroll(function () {
            io = 0;
            Sp = $(window).scrollTop();
            $(bd).each(function () {
                Bdp = $(this).offset().top;
                if (Sp >= Bdp) {
                    $(hd).removeClass('on').eq(io).addClass('on');
                }
                io++;
            });
        });
    });
}

//自由定义滚动
//Dom:要滚动到的位置（支持[#,.]）
//Oint:位置变化值，正负。
function scrollTo(Dom, Oint, speed) {
    var s_top = $(Dom).offset().top + Oint;
    $('html,body').animate({ scrollTop: s_top }, speed);
}