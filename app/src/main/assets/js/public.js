var phonereg=/^1[3|4|5|7|8][0-9]{9}$/;
var uid=localStorage.getItem('uid');
var uto=localStorage.getItem('uto');
var machineid=localStorage.getItem('machineid');;


function request(){
	return 'https://app.zshshz.com/';
}


function GetQueryString(name){
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}

Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
var skey='AH20181818123999system.com.out.gc.pc.do_'+new Date().Format("yyyy-MM-dd")+uid;


function nav(){
	$('#box').off().on('click',function(){
		$('.nav').hide();
	})
	$('.mores').off().on('click',function(){
		if($('.nav').css('display')=='none'){
			setTimeout(function(){
				$('.nav').show();
			},0)
		}else{
			$('.nav').hide();
		}
		
	})
}
nav();

function state() {
	$('#box').append('<div class="state"><span></span></div>');
}
state();

function seti(a) {
	$(".state").fadeIn(300);
	$(".state span").html(a);
	var ti = setTimeout(function() {
		$(".state").hide();
		clearTimeout(ti);
	}, 1000)
}

function deleteItem(){
    localStorage.removeItem('oidcart');
    localStorage.removeItem('oidbal');
    localStorage.removeItem('oidnum');
    localStorage.removeItem('oidinv');
}


var getUserInfo = new Promise(function(resolve, reject) {
	var time=Number(new Date());
	$.ajax({
		type: "post",
		url:request()+"GetUserInfo",
		data:{userid:uid,token:uto,secret_key:hex_md5(skey+time),time:time},
		success: function(data) {
			var msg = JSON.parse(data);
			if(msg.code == 0) {
				resolve(msg);
			}else if(msg.code==10000 || msg.code==8888){
				localStorage.removeItem('uid');
				localStorage.removeItem('uto');
				location.href='login.html';
			} else {
				return false;
				reject(msg.code);
			}
		}
	});
})
