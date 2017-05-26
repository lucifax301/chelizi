/*angular for 提现*/
var app=angular.module("app",["ngFilter"]);

/*main 控制器*/
app.controller("MyApp",["$scope",function($s){

/*--------------------------------------------提现----------------------------------------------------------*/
	//当前显示状态  0 表示可以进行提现操作  1 表示 账户密码输入错误5次  2：提现超额提示，每周只能提现一次 
	$s.type=getQueryString("type")||0; 
	$s.withdraMoney="";//提现金额
	$s.password=""; //支付密码
	$s.isThanTotal=""; //输入的余额是否大于总余额
	$s.isTransPasswordError=""; //交易密码是否错误

	//获取手机号
	$.AJAX({
		type:"get",
		url:config.basePath+"school/queryAccount",
		data:{
			schoolId:$s.schoolId,
		},
		success:function(data){
			$s.schoolDetails=getListData(data);   //获取返回数据
			$s.$apply();
		}
	});

	//检测用户是否可以提现  0 表示可以进行提现操作 1 表示 账户密码输入错误5次 2：提现超额提示，每周只能提一次
	$.AJAX({
		url:config.basePath+"school/depositVal",
		data:{},
		success:function(data){
			$s.type=getListData(data);
			console.log($s.type)
			$s.$apply();
		}	
	});

	//模拟数据
	$s.accountData={money:10000,bankName:'中国农业银行（深圳支行）',merName:'深圳市深港驾培集团',publicAccount:'516516515165165165165'}
	//查询账户详情
	schoolsAccountDetails({
		callback:function(data){
			$s.accountData=data;
			$s.$apply()
		}
	});

	//检测提现金额是否大于总金额 或者小于500
	$s.isThanTotalMoney=function(){
		if($s.withdraMoney<500){
			$s.isThanTotal='最低提现500元';
		}else if($s.withdraMoney>$s.accountData.money/100){
			$s.isThanTotal='您的余额只有'+$s.accountData.money/100+'元';
		}else{
			$s.isThanTotal="";
		}
	}
		
	//确认提现
	$s.submitAccount=function(){
		if(!regCombination('number').test($s.withdraMoney)){
			$s.isThanTotal='请输入需要提现的金额'; return false;
		}
		if($s.withdraMoney<500){ //支付密码报错
			$s.isThanTotal='最低提现500元';
		}
		if($s.withdraMoney>$s.accountData.money/100){ //支付密码报错
			$s.isThanTotal='您的余额只有'+$s.accountData.money/100+'元'; return false;
		}
		if(!regCombination('*').test($s.password)){
			$s.isTransPasswordError="请输入支付密码"; return false;	
		}
		//申请提现
		$.AJAX({
			url:config.basePath+"school/deposit",
			data:{
				money:$s.withdraMoney,
				bankCard:$s.accountData.bankNo,
				passwd:$s.password,
				},
			success:function(data){
				$s.datas=getListData(data);
				window.location.href="schools-account-withdrawals-details.html?createTime="+$s.datas.applyTime+'&money='+$s.withdraMoney
			},
			error:function(data){
				$s.isTransPasswordError=data.msg;
				$s.$apply();
			}	
		});
	}











}]);



