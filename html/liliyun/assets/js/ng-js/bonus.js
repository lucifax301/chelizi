/*angular for 提现*/
var app=angular.module("app",["ngFilter"]);

/*main 控制器*/
app.controller("Bonus",["$scope","$filter",function($s,$filter){
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.pageSize=10;    //每页显示-显示条数

	// /*模拟数据*/
	$s.data={
		pages:10,
		total:100,
		pageSize:10,
		pageNo:1,
		dataList:[
			{id:201,createTime:'2015-12-12 12:13',bonusName:"案例",bonusNum:5,bonusMoney:2120,status:1,creator:"admin",award:"李四",awardTime:'2015-12-12 12:13'},
			{id:201,createTime:'2015-12-12 12:13',bonusName:"案例",bonusNum:5,bonusMoney:2120,status:2,creator:"admin",award:"李四",awardTime:'2015-12-12 12:13'},
			{id:201,createTime:'2015-12-12 12:13',bonusName:"案例",bonusNum:5,bonusMoney:2120,status:3,creator:"admin",award:"李四",awardTime:'2015-12-12 12:13'},
		]
	}; 
	//或得的数据列表
	$s.datas=$s.data.dataList;

	/*获取数据列表并展示*/
	$s.getDataList=function(){
		var json=getJson($s.defaultPage);
		$.AJAX({
			type:"get",
			url:json.url,
			data:json.data,
			success:function(data){
				var DATA=getListData(data);
				$s.total=DATA.total;
				$s.datas=DATA.dataList;
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
	$s.getDataList();
	/*参数配置函数*/
	function getJson(nowPage){
		var json={
			url:config.basePath+"bonus/query",
			data: {
				"pageNo": nowPage,
			    "pageSize": parseInt($s.pageSize),
			}
		};
		return json;
	};

	/*hash值改变的时候加载数据列表*/
	window.onhashchange=function(){
		win.showLoading();
		$s.defaultPage=location.hash.substring(2) || 1;
		$s.getDataList();
	}

	/*按分页条数筛选列表数据*/
	$s.getDataForPage=function(){
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.getDataList();
	}

	/*确认提交*/
	$s.confirmSubmit=function($event,id){

		Layer.confirm({width:300,height:170,title:"提交后不能再更改表里的<br>内容,是否确认提交？",header:"提交"},function(){
            //按钮加锁
            $($event.target).addClass("z-locked");
			$.AJAX({
				url:config.basePath+"bonus/submit",
				data:{"id":id},
				success:function(data){
					/*提示成功信息*/
					Layer.miss({width:250,height:90,title:"提交成功",closeMask:true});
					/*更新列表*/
					$s.getDataList();
				}
			});/*AJAX end*/
		});
	};

	/*删除*/
	$s.deteleSubmit=function(id){
		Layer.confirm({width:300,height:160,title:"确认删除？",header:"删除"},function(){
			$.AJAX({
				url:config.basePath+"bonus/delete",
				data:{"id":id},
				success:function(data){
					/*更新列表*/
					Layer.miss({width:250,height:90,title:"删除成功",closeMask:true});
					$s.getDataList();
				}
			});/*AJAX end*/
		});
	};

	/*发放奖金*/
	$s.successSubmit=function($event,id){
		Layer.confirm({width:300,height:170,title:"确认发放奖金到个人的<br>账户余额？",header:"发放奖金"},function(){
            //按钮加锁
            $($event.target).addClass("z-locked");
			$.AJAX({
				url:config.basePath+"bonus/grant",
				data:{"id":id},
				success:function(data){
					/*提示成功信息*/
					Layer.miss({width:250,height:90,title:"发放奖金成功！",closeMask:true});
					/*更新列表*/
					$s.getDataList();
				}
			})

		});
	};

	/*奖金详情*/
	$s.alertBonusDetails=function(id){
		if($(window).width()<=768){
			var width=$(window).width()*0.9;
		}else{
			var width=$(window).width()*0.6;
		}
		/*奖金详情弹出层*/
		Layer.iframe({
			header:"奖金详情",
			width:width,
			height:400,
			href:"bonus-details.html?id="+id
		});
	}

	/*导入数据*/
	$s.createFileForm=function(){
		/*FormData 上传*/
		cerateFileFormData({
			url:config.basePath+"bonus/upload",
			callback:function(data){
				/*提示成功信息*/
				Layer.miss({width:250,height:90,title:"导入成功",closeMask:true});
				/*更新列表*/
				$s.getDataList();
			}
		});
	};

	/*下载导入模板*/
	$s.downLoadTemp=function(){
		window.location.href=config.basePath+"bonus/downLoadTemplate";
	}

	/*------------------------财务拒绝加载层--------------------------------*/
	$s.financialRejectionSubmit=function(id){
        /*确认拒绝*/
        Layer.confirmNotByTextAlert({
            header:"财务拒绝",
            width:400,
            height:260,
            botByText:'请填写拒绝理由',
            title:"您已选择财务拒绝",
            errmsg:'请填写拒绝的理由！',
        },function(notByText){
            $.AJAX({
				url:config.basePath+"bonus/finReject",
				data:{
					"id":id,
					"remark":notByText,
				},
				success:function(data){
					/*提示成功信息*/
					Layer.miss({width:250,height:90,title:"财务拒绝成功！",closeMask:true});
					/*更新列表*/
					$s.getDataList();
				}
			});/*AJAX end*/
        });
    }

}]);