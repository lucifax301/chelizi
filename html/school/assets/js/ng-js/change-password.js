/*angular for 提现*/
var app=angular.module("app",[]);

/*main 控制器*/
app.controller("ChangePassword",["$scope",function($s){
	$s.oldPassword="";  //原始密码
	$s.isoldPassword=""; 
	$s.newPassword=""; //新密码
	$s.isnewPassword="";
	$s.confirmPassword=""; //确认密码
	$s.isconfirmPassword="";
	$s.username=localStorage.getItem("lili-username");

	/*检测原始密码是否正确*/
	$s.checkOldPassWord=function(){
		$.AJAX({
			url:config.basePath+"user/verify-password",
			data:{
				password:$s.oldPassword,
			},
			code:true,
			success:function(data){
                console.log(data)
                if(data.msg=="wrong_oldpwd"){$s.isoldPassword='输入密码有误';}
                else if(data.msg=="请求正确"){$s.isoldPassword=0;}
                $s.$apply();
			},
			error:function(data){
                console.log("error"+data)
				$s.isoldPassword='输入密码有误'; $s.$apply();
			}
		});
	};

	/*检测新密码是否是正确的类型*/
	$s.checkNewPassWord=function(){
		if(!regCombination('special',[6,16]).test($s.newPassword)){
			$s.isnewPassword='请输入6-16位数的密码';
		}else{
			$s.isnewPassword=0;
		};
	};

	/*检测两次输入密码是否一致*/
	$s.checkShrePassWord=function(){
		if(!regCombination('special',[6,16]).test($s.confirmPassword)){
			$s.isconfirmPassword='请输入6-16位数的密码';
			return false;
		};
		if($s.newPassword!==$s.confirmPassword||$s.confirmPassword==''){
			$s.isconfirmPassword='两次密码输入不一致';
		}else{
			$s.isconfirmPassword=0;
		}
	}

	/*修改密码*/
	$s.submitChangePassword=function(){
        if(!regCombination('*').test($s.oldPassword)){
            $s.isoldPassword='请填写原始密码';
            return false;
        };
        if($s.isoldPassword!=0){
            $s.isoldPassword='原始密码不正确';
            return false;
        };
		if(!regCombination('*').test($s.newPassword)){
			$s.isnewPassword='请输入6-请填写新密码';
			return false;
		};
		if(!regCombination('*').test($s.confirmPassword)){
			$s.isconfirmPassword='请填写确认密码';
			return false;
		};
		if($s.confirmPassword!=$s.newPassword){
			$s.isconfirmPassword='两次密码输入不一致';
			return false;
		};
		/*修改密码的AJAX*/
		$.AJAX({
			url:config.basePath+"user/update-password",
			data:{
				password:$s.newPassword,
			},
			success:function(data){
				Layer.miss({width:250,height:90,title:"修改密码成功",closeMask:true});
				loginOut(); //退出登录
			}
		});
	};



}]);