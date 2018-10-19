
$(function(){
	var cardNo=$('#cardNo').val();
	$.ajax({
		url:_getUserCar,
		data:{cardNo:cardNo},
		type:'POST',
		dataType:'json',
		success:function(data){
			if(data.code=="000000"){
				//成功。
				$('#chepNo').text(data.data[0].carNo);
				$('#userPhone').text(data.data[0].userPhone);
			}
		},error:function(data){
			console.log(JSON.stringify(data));
		}
	})
})
function toModBind(){
	var _openid=$('#openid').val();
	window.location.href='modbind?openid='+_openid;
}
function toMod(){
	var _openid=$('#openid').val();
	window.location.href='mod?openid='+_openid;
}