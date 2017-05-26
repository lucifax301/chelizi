/*angular for 提现*/
var app=angular.module("app",["ngSelects"]);

/*main 控制器*/
app.controller("UptateTel",["$scope","getMsgTime",function($s,getMsgTime){
	getMsgTime.getNowTime('setpassword');            //页面加载后短信时间没有跑完时继续执行
	$s.schoolId=getQueryString('schoolId')   //驾校id
	$s.mobile=getQueryString('mobile')   //手机号

	/*------初始化-------*/
	//修改密码
	$s.captcha=""     //验证码1
	$s.iscaptcha=""
	$s.isobtainCode=""   //验证码提示信息
	$s.newtel=""     //新手机号
	$s.isnewtel=""
	$s.newcaptcha=""     //验证码2
	$s.isnewcaptcha=""

	$s.findPassword=true;

	/*------获取手机号------*/
	$.AJAX({
		type:"get",
		url:config.basePath+"school/queryAccount",
		data:{
			schoolId:$s.schoolId,
		},
		success:function(data){
			$s.schoolDetails=JSON.parse(data.result.pageData);
			$s.$apply();
		}
	});

	/*------修改手机号,输入验证码并执行下一步-------*/
	$s.SetPassword=function(){
		//验证码1
		if(!regCombination('number',[5,6]).test($s.captcha)){
			$s.iscaptcha='请输入5-6位数字验证码';
			return false;
		}else {
			$s.iscaptcha=0;
		};
		/*校验验证码是否正确，正确执行下一步	async:false,*/
			$.AJAX({
			type:"get",
			url:config.basePath+"authcodes/verify",
			data:{
				mobile:$s.mobile,
				codeInput:$s.captcha,
			},
			success:function(data){
				//清除短信定时器
				localStorage.setItem('nowMsgTimesetpassword',"");
				localStorage.setItem('nowTimesetpassword',"");
				
				//隐藏显示相关
				$s.findPassword=false; //隐藏找回密码表单
				$s.newTel=true;   //显示提示
				$s.$apply();
			},
			error:function(data){
				$s.iscaptcha=data.msg;
				$s.$apply();
			}
		});
	};
	//获取验证码1
	$s.ObtainCode1 = function () {       //点击发送短信
		$.AJAX({
			type:"get",
			url: config.basePath + "authcodes/one",
			data: {
				mobile:$s.mobile,
				userType:"5",
				reqType:"2",
				timestamp:time(),
			},
			success: function (data) {
				$s.isobtainCode='验证码已经发送到绑定手机号，30分钟内有效，请注意查收！';
				getMsgTime.shotMessage('setpassword',function(){$s.isobtainCode=false;});   //点击后执行时间定时器
			}
		});
	};


	/*------修改手机号------*/
	$s.uptateTel=function(){
		//新手机号
		if(!regCombination('*').test($s.newtel)){
			$s.isnewtel='请输入绑定的手机号';
			return false;
		}else if(!regCombination('phone').test($s.newtel)){
			$s.isnewtel='手机号格式错误';
			return false;
		}else {
			$s.isnewtel=0;
		};
		//验证码2
		if(!regCombination('number',[5,6]).test($s.newcaptcha)){
			$s.isnewcaptcha='请输入5-6位数字验证码';
			return false;
		}else {
			$s.isnewcaptcha=0;
		};
		//修改手机号
		$.AJAX({
			url:config.basePath+"school/alertMobile",
			data:{
				codeInput:$s.newcaptcha,
				mobile:$s.newtel,
			},
			success:function(data){
				//清除短信定时器
				localStorage.setItem('nowMsgTimesetpassword',"");
				localStorage.setItem('nowTimesetpassword',"");


				$s.newTel=false; //隐藏找回密码表单
				$s.accountText=true;   //显示提示
				$s.$apply();
			},
			error:function(data){
				$s.isnewcaptcha=data.msg;
				$s.$apply();
			}
		});
	}
	//获取验证码2
	$s.ObtainCode2 = function () {       //点击发送短信
		if(!regCombination('*').test($s.newtel)){
			$s.isnewtel='请输入绑定的手机号';
			return false;
		}else if(!regCombination('phone').test($s.newtel)){
			$s.isnewtel='手机号格式错误';
			return false;
		}else {
			$s.isnewtel='';
		};
		$.AJAX({
			type:"get",
			url:config.basePath+"authcodes/one",
			data:{
				mobile:$s.newtel,
				userType:"5",
				reqType:"2",
				timestamp:time(),
			},
			success: function (data) {
				$s.isobtainCodeTo='验证码已经发送到绑定手机号，30分钟内有效，请注意查收！';
				getMsgTime.shotMessage('setpassword',function(){$s.isobtainCodeTo=false;});   //点击后执行时间定时器
			}
		});
	};

}]);