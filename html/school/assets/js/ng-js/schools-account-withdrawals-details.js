/*angular for 提现*/
var app=angular.module("app",["ngFilter"]);

/*main 控制器*/
app.controller("MyApp",["$scope",function($s){

	//0-审核中，1-提现成功，2-提现失败，3-财务确认, 4银行处理中，5银行处理失败
	$s.type=getQueryString("type")||1; 
	$s.createTime=getQueryString("createTime");
	$s.waterId=getQueryString("waterId")||""; //流水号
	$s.queryDeposit={};

/*------------------------------------------查询提现申请详情------------------------------------------------*/
	if(!$s.createTime){
		$.AJAX({
			type:"get",
			url:config.basePath+"school/queryDeposit",
			data:{
				waterId:$s.waterId,
			},
			success:function(data){
				$s.queryDeposit=getListData(data);
				$s.type=$s.queryDeposit.checkStatus;
				console.log($s.queryDeposit)
				$s.$apply();
			}
		});
	}else{
		//查询账户详情
		schoolsAccountDetails({
			callback:function(data){
				$s.type=1;
				$s.queryDeposit.applyTime=$s.createTime;
				$s.queryDeposit.cardName=data.subBankName;
				$s.queryDeposit.bankName=data.merName;
				$s.queryDeposit.checkStatus=0;
				$s.queryDeposit.bankCard=data.publicAccount;
				$s.queryDeposit.money=getQueryString("money")*100;

				console.log(data)
				$s.type=0;
				$s.$apply();
			}
		});
	}
	











}]);



