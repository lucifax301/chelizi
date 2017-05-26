/*angular for 提现*/
var app=angular.module("app",["ngSelects"]);

/*main 控制器*/
app.controller("ApplicationAccount",["$scope","getMsgTime",function($s,getMsgTime){
	getMsgTime.getNowTime();        //页面加载后短信时间没有跑完时继续执行
	$s.schoolId=getQueryString('schoolId')
	/*------初始化-------*/
	$s.applicationName="申请驾校账户"
	//填写企业基本信息
	$s.posName="";  //商户名称
	$s.isposName="";
	$s.RegistrationNumber=""; //工商注册号
	$s.isRegistrationNumber="";
	$s.Organizations=""; //阻止机构代码
	$s.isOrganizations="";
	$s.TaxRegistration=""; //税务登记账户
	$s.isTaxRegistration="";
	$s.PublicAccount=""; //对公账户
	$s.isPublicAccount="";
	$s.BankDeposit=""; //开户银行
	$s.isBankDeposit="";
	$s.Branches=""; //开户支行
	$s.isBranches="";
	//填写喱喱企业账户
	$s.password="";  //支付密码
	$s.ispassword="";
	$s.confirmPassword=""; //确认密码
	$s.isconfirmPassword="";
	$s.telphone=""; //手机号
	$s.istelphone="";
	$s.obtainCode="";  //倒计时
	$s.isobtainCode="";  //倒计时
	$s.captcha=""; //验证码
	$s.isCaptcha="";



	//获取url参数
	if(getQueryString('type')=='change'){
		$s.accountText=false;   //显示变更表单
		$s.applicationName="申请变更商户信息"  //修改标题信息
		$s.title=true;  //显示标题
		$s.basicInformation=true;  //显示'申请变更商户信息'
		$s.dLoad=false;  //隐藏'企业基本信息表单'进度
		$s.ChangeBtn=true;  //显示确认提交按钮
		$s.textHide=true;   //显示'申请变更商户信息'提示
        $.AJAX({
            type:"get",
            url:config.basePath+"school/queryAccount",
            data:{
                schoolId:$s.schoolId,
            },
            success:function(data){
                $s.schoolDetails=getListData(data);
                $s.posName=$s.schoolDetails.merName;
                $s.RegistrationNumber=$s.schoolDetails.registNo;
                $s.Organizations=$s.schoolDetails.orgCode;
                $s.TaxRegistration=$s.schoolDetails.taxId;
                $s.PublicAccount=$s.schoolDetails.publicAccount;
                $s.BankDeposit=$s.schoolDetails.bankName;
                $s.Branches=$s.schoolDetails.subBankName;
                $s.$apply();
            }
        })
	}else if(getQueryString('type')=='audit'){
		$s.liliAudit=true;
	}else if(getQueryString('type')=='noPass'){
		$s.auditNotpass=true;
	}else{
		$s.accountText=true;
	}

	/*------申请开通-------*/
	//$s.accountText=true;           //默认显示
	$s.opened=function(){
		$s.title=true;             //显示标题
		$s.accountText=false;      //隐藏'申请开通信息'
		$s.basicInformation=true;  //显示'填写企业基本信息'
		$s.dLoad=true;  //显示'企业基本信息表单'进度
		$s.BasicBtn=true;  //显示下一步按钮
	}

	/*------填写企业基本信息------*/
	$s.BasicInformation=function(){
		//商户名称
		if(regCombination('chinese',[0,1]).test($s.posName)){
			$s.isposName='输入字数有误';
			return false;
		}else if(!regCombination('chinese',[2,30]).test($s.posName)){
			$s.isposName='字符格式不对';
			return false;
		}else {
			$s.isposName=0;
		};
		//工商注册号
		if(regCombination('number',[0,14]).test($s.RegistrationNumber)){
			$s.isRegistrationNumber='输入字数有误';
			return false;
		}else if(!regCombination('number',[15,15]).test($s.RegistrationNumber)){
			$s.isRegistrationNumber='字符格式不对';
			return false;
		}else {
			$s.isRegistrationNumber=0;
		};
		//组织机构代码
		if(regCombination('chinese',[6,20]).test($s.Organizations) || !regCombination('special',[6,20]).test($s.Organizations)){
			$s.isOrganizations='输入的长度或类型有误';
			return false;
		}else {
			$s.isOrganizations=0;
		};
		//税务登记账号
		if(regCombination('chinese',[6,20]).test($s.TaxRegistration) || !regCombination('special',[6,20]).test($s.TaxRegistration)){
			$s.isTaxRegistration='输入的长度或类型有误';
			return false;
		}else {
			$s.isTaxRegistration=0;
		};
		//对公账户
		if(regCombination('number',[0,5]).test($s.PublicAccount)){
			$s.isPublicAccount='输入字数有误';
			return false;
		}else if(!regCombination('number',[6,20]).test($s.PublicAccount)){
			$s.isPublicAccount='字符格式不对';
			return false;
		}else {
			$s.isPublicAccount=0;
		};
		//开户银行
		if(!regCombination('*').test($s.BankDeposit)){
			$s.isBankDeposit='请选择开户银行';
			return false;
		}else {
			$s.isBankDeposit=0;
		};
		//开户支行
		if(!regCombination('*').test($s.Branches)){
			$s.isBranches='请选择开户支行';
			return false;
		}else if(!regCombination('chinese',[1,30]).test($s.Branches)){
			$s.isBranches='字符格式不对';
			return false;
		}else{
			$s.isBranches=0;
		};
		$s.basicInformation=false;  //隐藏'企业基本信息'
		$s.businessAccount=true;    //显示'填写喱喱企业账户'
	};

	//通过"对公账号"获取"开户银行"
	$s.getDataBankDeposit=function(){
		$.AJAX({
			url:config.basePath+"authcodes/bankName",
			type:"get",
			data:{
				publicAccount:$s.PublicAccount,
			},
			success:function(data){
				$s.BankDeposit=data.result.pageData;
				$s.$apply();
			},
			error:function(data){
				$s.isPublicAccount=data.msg
				$s.$apply();
			}
		})
	}


	/*------填写喱喱企业账户------*/
	$s.BusinessAccount=function(){
		//支付密码
		if(regCombination('*',[0,7]).test($s.password)){
			$s.ispassword='输入字数有误';
			return false;
		}else if(!regCombination('*',[8,16]).test($s.password)){
			$s.ispassword='字符格式不对';
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
		//手机号
		if(!regCombination('*').test($s.telphone)){
			$s.istelphone='请填写手机号';
			return false;
		}else if(!regCombination('phone').test($s.telphone)){
			$s.istelphone='手机号格式错误';
			return false;
		}else {
			$s.istelphone=0;
		};
		//验证码
		if(!regCombination('number',[5,6]).test($s.captcha)){
			$s.iscaptcha='请输入5-6位数字验证码';
			return false;
		}else {
			$s.iscaptcha=0;
		};
		/*申请开通驾校账户*/
		$.AJAX({
			url:config.basePath+"school/applyAccount",
			data:{
				merName:$s.posName,  //商户名称
				type:1,  // 1: 申请   2:变更
				registNo:$s.RegistrationNumber,  //工商注册号
				orgCode:$s.Organizations,   //组织机构代码
				taxId:$s.TaxRegistration,   //税务登记号
				publicAccount:$s.PublicAccount,   //对公账号
				bankName:$s.BankDeposit,   //开户银行
				subBankName:$s.Branches,   //开户支行
				mobile:$s.telphone,  //手机号
				passwd:$s.password,  //密码
				codeInput:$s.captcha,  //验证码
			},
			success:function(data){
				$s.businessAccount=false;    //显示'填写喱喱企业账户'
				$s.liliAudit=true;   //显示等待喱喱审核
				window.location.href="schools-account.html";
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
		if(!regCombination('*').test($s.telphone)){
			$s.istelphone='请填写手机号';
			return false;
		}else if(!regCombination('phone').test($s.telphone)){
			$s.istelphone='手机号格式错误';
			return false;
		}else {
			$s.istelphone=0;
		};
		$.AJAX({
			type:"get",
			url:config.basePath+"authcodes/one",
			data:{
				mobile:$s.telphone,
				userType:"5",
				reqType:"1",
				timestamp:time(),
			},
			success:function(data){
				$s.isobtainCode='验证码已经发送到绑定手机号，30分钟内有效，请注意查收！';
				getMsgTime.shotMessage(function(){$s.isobtainCode=false;});   //点击后执行时间定时器
			}
		});
	};




	/*------申请变更商户信息------*/
	$s.ChangeInformation=function(){
        //若填错重填，则隐掉错误符，现重新提交，需再次显示较验信息
        $(".d-change-password .ng-valid").siblings("span").show();
		//商户名称
		if(regCombination('chinese',[0,1]).test($s.posName)){
			$s.isposName='输入字数有误';
			return false;
		}else if(!regCombination('chinese',[2,30]).test($s.posName)){
			$s.isposName='字符格式不对';
			return false;
		}else {
			$s.isposName=0;
		};
		//工商注册号
		if(regCombination('number',[0,14]).test($s.RegistrationNumber)){
			$s.isRegistrationNumber='输入字数有误';
			return false;
		}else if(!regCombination('number',[15,15]).test($s.RegistrationNumber)){
			$s.isRegistrationNumber='字符格式不对';
			return false;
		}else {
			$s.isRegistrationNumber=0;
		};
		//组织机构代码
		if(regCombination('chinese',[6,20]).test($s.Organizations) || !regCombination('special',[6,20]).test($s.Organizations)){
			$s.isOrganizations='输入的长度或类型有误';
			return false;
		}else {
			$s.isOrganizations=0;
		};
		//税务登记账号
		if(regCombination('chinese',[6,20]).test($s.TaxRegistration) || !regCombination('special',[6,20]).test($s.TaxRegistration)){
			$s.isTaxRegistration='输入的长度或类型有误';
			return false;
		}else {
			$s.isTaxRegistration=0;
		};
		//对公账户
		if(regCombination('number',[0,5]).test($s.PublicAccount)){
			$s.isPublicAccount='输入字数有误';
			return false;
		}else if(!regCombination('number',[6,20]).test($s.PublicAccount)){
			$s.isPublicAccount='字符格式不对';
			return false;
		}else {
			$s.isPublicAccount=0;
		};
		//开户银行
		if(!regCombination('*').test($s.BankDeposit)){
			$s.isBankDeposit='请选择开户银行';
			return false;
		}else {
			$s.isBankDeposit=0;
		};
		//开户支行
		if(!regCombination('*').test($s.Branches)){
			$s.isBranches='请选择开户支行';
			return false;
		}else if(!regCombination('chinese',[1,30]).test($s.Branches)){
			$s.isBranches='字符格式不对';
			return false;
		}else {
			$s.isBranches=0;
		};

		/*申请变更商户信息*/
		$.AJAX({
			url:config.basePath+"school/applyAccount",
			data:{
				merName:$s.posName,  //商户名称
				type:2,  // 1: 申请   2:变更
				registNo:$s.RegistrationNumber,  //工商注册号
				orgCode:$s.Organizations,   //组织机构代码
				taxId:$s.TaxRegistration,   //税务登记号
				publicAccount:$s.PublicAccount,   //对公账号
				bankName:$s.BankDeposit,   //开户银行
				subBankName:$s.Branches,   //开户支行
			},
			success:function(data){
				$s.businessAccount=false;    //显示'填写喱喱企业账户'
				$s.liliAudit=true;   //显示等待喱喱审核
				window.location='schools-account.html';
				$s.$apply();
			}
		});
	}

    /*较验数据不合格时，重填应该去除不合格ICO*/
    $(".d-change-password").on("focus",".ng-valid",function(){$(this).siblings("span").hide();})
}]);