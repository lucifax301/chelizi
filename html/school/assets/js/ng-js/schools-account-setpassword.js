/*angular for 提现*/
var app=angular.module("app",["ngSelects"]);

/*main 控制器*/
app.controller("SetPassword",["$scope","getMsgTime",function($s,getMsgTime){
	getMsgTime.getNowTime('getpassword');            //页面加载后短信时间没有跑完时继续执行
	$s.schoolId=getQueryString('schoolId')   //驾校id
	$s.mobile=getQueryString('mobile')   //手机号
	/*------初始化-------*/
	//找回密码
	$s.captcha=""; //验证码
	$s.isCaptcha="";
	$s.password="";  //新密码
	$s.ispassword="";
	$s.confirmPassword=""; //确认密码
	$s.isconfirmPassword="";
	$s.obtainCode="";  //倒计时
	$s.isobtainCode="";  //倒计时
	//修改密码
	$s.oldpassword=""     //原密码
	$s.isoldpassword=""
	$s.newpassword=""     //新密码
	$s.isnewPassword=""
	$s.newconfirmPassword=""  //确认密码
	$s.isnewconfirmPassword=""
	//初始化命名
	$s.passwordName="";  //title信息
	$s.accountText="";   //执行成功后的提示信息

	//获取url参数
	if(getQueryString('type')=='update'){
		$s.updatePassword=true; //显示修改支付密码
		$s.passwordName="修改支付密码";
	}else {
		$s.findPassword=true;  //显示找回支付密码
		$s.passwordName="找回支付密码";
	}


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


	/*------找回支付密码-------*/
	$s.SetPassword=function(){
		//验证码
		if(!regCombination('number',[5,6]).test($s.captcha)){
			$s.iscaptcha='请输入5-6位数字验证码';
			return false;
		}else {
			$s.iscaptcha=0;
		};
		//新密码
		if(!regCombination('*',[8,16]).test($s.password)){
			$s.ispassword='请输入8-16位密码';
			return false;
		}else {
			$s.ispassword=0;
		};
		//确认密码
		if($s.confirmPassword!=$s.password){
			$s.isconfirmPassword='两次密码输入不一致';
			return false;
		}else {
			$s.isconfirmPassword=0;
		};
		/*找回支付密码的AJAX*/
		$.AJAX({
			url:config.basePath+"school/queryPasswd",
			data:{
				mobile:$s.mobile,
				codeInput: $s.captcha,
				passwd:$s.password,
			},
			success:function(data){
				$s.findPassword=false; //隐藏找回密码表单
				$s.accountText=true;   //显示提示
				$s.accountText="新密码设置成功，请牢记您的新密码"
				$s.$apply();
			},
			error:function(data){
				$s.iscaptcha=data.msg;
				$s.$apply();
			}

		});
	};
	//获取验证码
	$s.ObtainCode = function () {       //点击发送短信
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
				getMsgTime.shotMessage('getpassword',function(){$s.isobtainCode=false;});   //点击后执行时间定时器
			}
		});

	};


	/*------修改支付密码-------*/
	$s.UpdatePassword=function(){
		//原密码
		if(!regCombination('*',[8,16]).test($s.oldpassword)){
			$s.isoldpassword='请输入8-16位密码';
			return false;
		}else {
			$s.isoldpassword=0;
		};
		//新密码
		if(!regCombination('*',[8,16]).test($s.newpassword)){
			$s.isnewpassword='请输入8-16位密码';
			return false;
		}else {
			$s.isnewpassword=0;
		};
		//确认密码
		if($s.newconfirmPassword!=$s.newpassword){
			$s.isnewconfirmPassword='两次密码输入不一致';
			return false;
		}else {
			$s.isnewconfirmPassword=0;
		};
		/*修改支付密码的AJAX*/
		$.AJAX({
			url:config.basePath+"school/alertPasswd",
			data:{
				passwdOld:$s.oldpassword,
				passwd:$s.newpassword,
			},
			success:function(data){
				$s.updatePassword=false; //隐藏修改密码表单
				$s.accountText=true;   //显示提示
				$s.accountText="支付密码修改成功，请保管好您的新密码"
				$s.$apply();
			},
			error:function(data){
				$s.isoldpassword=data.msg;
				$s.$apply();
			}
		});
	}
}]);