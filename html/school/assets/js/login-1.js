//关闭加载层
win.hideLoading();
/***************************************************用户登陆*********************************************************************/
/*展示用户名*/
$('#username').val(localStorage.getItem("school-username"));

/*登录*/
$('#admin-submit').click(function(){ login(); });
$(document).keydown(function (event) { if(event.keyCode==13){login();} });

/*登录函数*/
function  login(){
	var username = $('#username').val().trim();
	var password = $('#password').val().trim();

	console.log(CryptoJS.SHA1(password).toString().toLowerCase())

	if(!regCombination('*',[2,20]).test(username)){
		Layer.alert({width:300,height:150,type:"error",title:"请填写用户名！"});
		return false;
	}
	if(!regCombination('*',[2,20]).test(password)){
		Layer.alert({width:300,height:150,type:"error",title:"请填密码！"});
		return false;
	}
	win.showLoading();
	$.AJAX({
		type:"post",
		url:config.basePath+"login/check",
		data:{
			account:username,
			password:CryptoJS.SHA1(password).toString().toLowerCase(),
		},
		success:function(data){
			//设置浏览器cookie
			localStorage.setItem("school-username",username);
			//设置登录后的跳转首页 iframeSrc
			window.location.href=sessionStorage.getItem("school-url") || config.homeUrl;
		}
	});
}




