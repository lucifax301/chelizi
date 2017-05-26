/*angular for 提现*/
var app=angular.module("app",["ngFilter","ngSelects"]);

/*main 控制器*/
app.controller("RechargePrivi",["$scope","$rootScope","$filter","Selects",function($s,$rootScope,$filter,Selects){

/*------------------------------------------充值送方案分页数据及其查询-----------------------------------------------------*/
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.pageSize=10;    //每页显示-显示条数
	$s.searchType="rcname";    //默认搜索字段
	$s.search="";     //搜索内容

	/*获取数据列表并展示*/
	$s.getDataList=function(){
		var json=getJson($s.defaultPage);
		$.AJAX({
			type:"get",
			url:json.url,
			data:json.data,
			success:function(data){
				//console.log(data)
				var DATA=getListData(data);
				$s.total=DATA.total;
				/*数据渲染页面*/
				$s.datas=DATA.dataList;
				/*全选与取消全选*/
				Selects.selects({datas:$s.datas,whichId:'rcid'});
				$s.$apply();
				/*冒泡弹出太长的文字*/
				topLongText();
				//分页请求
				new Page({
					parent:$("#copot-page"),
					nowPage:$s.defaultPage,
					pageSize:$s.pageSize,
					totalCount:DATA.total,
				}); //分页请求完毕
			}
		});
		/*增加搜索条件*/
		json.data[$s.searchType]=$s.search;
		return json;
	};
	$s.getDataList();

	/*全选与取消全选*/
	Selects.selects({datas:$s.datas,whichId:'rcid'});

	/*参数配置函数*/
	function getJson(nowPage){
		var json={
			url:config.basePath+"vip/plan-batch",
			data: {
				"pageNo": nowPage,
			    "pageSize": parseInt($s.pageSize),
			    "name": $s.name
			}
		};
		/*增加搜索条件*/
		json.data[$s.searchType]=$s.search;
		return json;
	};

	/*hash值改变的时候加载数据列表*/
	window.onhashchange=function(){
		win.showLoading();
		$s.defaultPage=location.hash.substring(2) || 1;
		$s.getDataList();
	}

	/*高级查询*/
	$s.getDataForSearch=function(){
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.getDataList();
	}

	/*按分页条数筛选列表数据*/
	$s.getDataForPage=function(){
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.getDataList();
	}

	/*更新搜索框提示文字*/
	$("#search_type").change(function(){
		$("#search_placeholder").attr("placeholder","输入"+$(this).children("option:selected").text()+"查询");
        if($("#search_type").val()=="rcid"){
            $("#search_placeholder").attr("type","number")
        }else{
            $("#search_placeholder").attr("type","text")
        }
	})


/*-----启用停用---------------------------*/
	$s.activeAction = function(cAction){
		var actionInfo = {msgWords:"",msgUrl:"",msgCode:""};
		switch (cAction) {
			case "cActive":
				actionInfo.msgWords = "激活";
				actionInfo.msgUrl = config.basePath + "vip/active-plan";
				actionInfo.msgCode = 0;//点激活时要求的状态值（为停用状态值）
				actionInfo.msgWords2 = "停用";
				break;
			case "cCancle":
				actionInfo.msgWords = "停发";
				actionInfo.msgUrl = config.basePath + "vip/inactive-plan";
				actionInfo.msgCode = 1;//点停用时要求的状态值（为激活状态值）
				actionInfo.msgWords2 = "激活";
				break;
			default: break;
		}

		/*检测是否选择*/
		if(!$rootScope.idList.length){
			Layer.alert({width:300,height:150,type:"msg",title:"请选择需要"+actionInfo.msgWords+"的方案！"});
			return false;
		};

		/*获得选择的data数据*/
		var datas=getDataForKey({datas:$s.datas,id:'rcid',idList:$rootScope.idList});
		/*只能选择同一组别的方案（停用或启用）*/
		var dataOK = true;//选择的数据，默认为合格
		var errorWorlds = "";//提示语句
		for (var i=0;i<datas.length;i++){
			if(datas[i].active!=actionInfo.msgCode ){
				errorWorlds = "您只能选择"+actionInfo.msgWords2+"的方案，<br>请重新选择！";
				dataOK = false; break; //数据不合格，跳出
			}
		}
		/*判断数据是否合格*/
		if(!dataOK){
			Layer.alert({width:330,height:175,type:"msg",title:errorWorlds});
			return false;
		}

		Layer.confirm({width:300,height:160,title:"确认"+actionInfo.msgWords+"选中方案吗？",header:actionInfo.msgWords+"方案"},function(){
			$.AJAX({
				url:actionInfo.msgUrl,
				data:{
					ids:$rootScope.idList.toString()
				},
				success:function(data){
					$rootScope.idList=[];
					Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
					$s.getDataList();
				}
			});
		});/*layer end*/
	}

	




}]);