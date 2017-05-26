/*angular for 提现*/
var app=angular.module("app",['ngFilter',"ngSelects"]);

/*main 控制器*/
app.controller("bankCard",["$scope","$rootScope","$filter","Selects",function($s,$rootScope,$filter,Selects){
	
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.pageSize=10;    //每页显示-显示条数
	$s.startTime="";   //数据查询-开始时间
	$s.endTime="";     //数据查询-结束时间
	$s.status="";	   //数据查询-状态
	$s.name="";    //数据查询-姓名	

	/*模拟数据*/
	// $s.data={
	// 	pages:10,
	// 	total:100,
	// 	pageSize:10,
	// 	pageNo:1,
	// 	dataList:[
	// 		{id:0,createTime:"2015-12-12",cardName:"wangwei",bankName:"银联",bankNo:"432544856644515",status:0},
	// 		{id:1,createTime:"2015-12-12",cardName:"wangwei",bankName:"银联",bankNo:"432544856644515",status:1},
	// 		{id:2,createTime:"2015-12-12",cardName:"wangwei",bankName:"银联",bankNo:"432544856644515",status:2},
	// 		{id:3,createTime:"2015-12-12",cardName:"wangwei",bankName:"银联",bankNo:"432544856644515",status:0},
	// 		{id:4,createTime:"2015-12-12",cardName:"wangwei",bankName:"银联",bankNo:"432544856644515",status:1},
	// 	]
	// }; 
	// //或得的数据列表
	// $s.datas=$s.data.dataList;

	/*获取数据列表并展示*/
	$s.getWithdrawCashData=function(){
		var json=getJson($s.defaultPage);
		$.AJAX({
			type:"get",
			url:json.url,
			data:json.data,
			success:function(data){
				var DATA=getListData(data);
				$s.datas=DATA.dataList;
				/*全选与取消全选*/
				Selects.selects({datas:$s.datas,whichId:'id',num:''});
				$s.$apply();
				//分页请求
				new Page({
					parent:$("#copot-page"),
					nowPage:$s.defaultPage,
					pageSize:$s.pageSize,
					totalCount:DATA.total, 
				}); //分页请求完毕
			}
		});
	};	
	$s.getWithdrawCashData();
	/*参数配置函数*/
	function getJson(nowPage){
		var json={
			url:config.basePath+"boundBankCard/query",
			data: {
				"pageNo": nowPage,
			    "pageSize": parseInt($s.pageSize),
			    "startTime": $s.startTime,
			    "endTime": $s.endTime,
			    "status": $s.status,
			    "name": $s.name
			}
		};
		return json;
	};

	/*hash值改变的时候加载数据列表*/
	window.onhashchange=function(){
		win.showLoading();
		$s.defaultPage=location.hash.substring(2) || 1;
		$s.getWithdrawCashData();
	}
	
	/*按时间筛选列表数据*/
	$s.getDataForTime=function($event,type){
		$($event.target).addClass('active').siblings().removeClass("active");
		switch(type){
			case 'all':
				$s.startTime=$s.endTime="";
				break;
			case '0':
				$s.startTime = $filter('date')((new Date().getTime()-604800000), 'yyyy-MM-dd');;
				$s.endTime= $filter('date')(new Date().getTime(), 'yyyy-MM-dd');
				break;
			case '1':
				$s.startTime = $filter('date')((new Date().getTime()-1296000000), 'yyyy-MM-dd');;
				$s.endTime= $filter('date')(new Date().getTime(), 'yyyy-MM-dd');
				break;		
		};
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.getWithdrawCashData();
	}

	/*按输入时间查询列表*/
	$('#reservation').daterangepicker({format: 'YYYY/MM/DD'},
	function(start, end, label) {
		$s.startTime=$s.endTime="";
		$s.startTime = $filter('date')(start.toISOString(), 'yyyy-MM-dd');
		$s.endTime = $filter('date')(end.toISOString(), 'yyyy-MM-dd');
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.getWithdrawCashData();
		$s.$apply();
	});

	/*按状态筛选列表数据*/
	$s.getDataForState=function($event,type){
		$($event.target).addClass('active').siblings().removeClass("active");
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.status=type;
		$s.getWithdrawCashData();
	}

	/*按分页条数筛选列表数据*/
	$s.getDataForPage=function(){
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.getWithdrawCashData();
	}

	/*高级查询*/
	$s.getDataForSearch=function(){
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.getWithdrawCashData();
	}

	/*银行卡有效操作*/
	$s.success=function(){
		if(!$rootScope.idList.length){
			Layer.alert({width:300,height:150,type:"msg",title:"请选择需要标示的数据！",header:"银行卡有效"});
			return false;
		};
		/*获得选择的data数据*/
    	var datas=getDataForKey({datas:$s.datas,id:'id',idList:$rootScope.idList});
    	/*操作*/
		Layer.confirm({width:300,height:160,title:"确认银行卡有效吗？",header:"银行卡有效"},function(){
			$.AJAX({
				url:config.basePath+"boundBankCard/pass",
				data:{
					idList:$rootScope.idList.join(),
					statusList:getKeyArrFromData(datas,'status').toString(),
				},
				success:function(data){
					$rootScope.idList=[];
					Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
					$s.getWithdrawCashData();
				}
			});
		});
	}

	/*银行卡无效操作*/
	$s.error=function(){
		if(!$rootScope.idList.length){
			Layer.alert({width:300,height:150,type:"msg",title:"请选择需要标示的数据！",header:"银行卡无效"});
			return false;
		};
	    Layer.confirmNotByTextAlert({
	        header:"银行卡无效",
	        width:380,
	        height:250,
	        title:"您已选择<b class='error'>"+$rootScope.idList.length+"</b>张银行卡，是否全部确认无效?",
	    },function(notByText){
	    	/*获得选择的data数据*/
	    	var datas=getDataForKey({datas:$s.datas,id:'id',idList:$rootScope.idList});
	    	/*标记无效*/
	        $.AJAX({
				url:config.basePath+"boundBankCard/reject",
				data:{
					idList:$rootScope.idList.join(),
					statusList:getKeyArrFromData(datas,'status').toString(),
					remark:notByText
				},
				success:function(data){
					$rootScope.idList=[];
					Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
					$s.getWithdrawCashData();
				}
			});
	    });
	};
	// /*银行卡无效操作*/
	// $s.error=function(){
	// 	if(!$rootScope.idList.length){
	// 		Layer.alert({type:"msg",title:"请选择需要标示的数据！",header:"银行卡无效"});
	// 		return false;
	// 	};
	// 	var html='<div class="custom-msg-but">\
	// 				<textarea class="form-control" id="bank-remark" rows="3" placeholder="请填写备注(可为空)"></textarea>\
	// 				<div><button type="button" onclick=Layer.closeNowLayer("customHtmlMask"); class="btn btn-sm btn-default">取消</button>\
	// 				<button type="button" onclick="submitBeiZmsg()" class="btn btn-sm btn-primary">确定</button></div>\
	// 			</div>';
	// 	Layer.customHtml({header:"银行卡无效!",width:350,height:200,html:html});
	// }

	// /*确认备注信息*/
	// window.submitBeiZmsg=function(){
	// 	var remark=$('#bank-remark').val();
	// 	Layer.closeNowLayer("customHtmlMask");
	// 	$.AJAX({
	// 		url:config.basePath+"boundBankCard/reject",
	// 		data:{
	// 			idList:$rootScope.idList.join(),
	// 			statusList:$rootScope.idList1.join(),
	// 			remark:remark
	// 		},
	// 		success:function(data){
	// 			$rootScope.idList=[];
	// 			Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
	// 			$s.getWithdrawCashData();
	// 		}
	// 	});
	// }

	/*下载txt文档*/
	$s.downloadType=null;
	$s.downLoadTxt=function(){
		window.location.href=config.basePath+"boundBankCard/downLoad?startTime="+$s.startTime+"&endTime="+$s.endTime+"&status="+$s.status+"&name="+$s.name;
	}

	/*下载excel文档*/
	$s.downloadType=null;
	$s.downLoadExcel=function(){
		window.location.href=config.basePath+"boundBankCard/downLoadExcel?startTime="+$s.startTime+"&endTime="+$s.endTime+"&status="+$s.status+"&name="+$s.name;
	}
	
}]);