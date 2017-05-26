/*angular for 提现*/
var app=angular.module("app",["ngFilter"]);

/*main 控制器*/
app.controller("ExcelBigClient",["$scope","$filter",function($s,$filter){
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.pageSize=10;//每页显示-显示条数
    /*获取EXCEL文件记录信息*/
	$s.billNo = getQueryString("billNo");
    $s.cityId = getQueryString("cityId");
    $s.coid = getQueryString("coid");
    $s.erportId=getQueryString("id"); //获取充值送ID值
    $s.sum=getQueryString("sum"); //获取充值送ID值
    $s.validSum=getQueryString("validSum"); //获取充值送ID值
    //$.AJAX({
    //    type:"get",
    //    data:{erportId:$s.erportId},
    //    url:config.basePath+"vip/custom-export",
    //    success:function(data){
    //        //console.log(data.result.pageData);
    //        $s.excelInfo=JSON.parse(data.result.pageData).dataList[0];
    //        $s.$apply();
    //    }
    //})

	/*获取数据列表并展示*/
	$s.getDataList=function(){
		var json=getJson($s.defaultPage);
		$.AJAX({
			type:"get",
			url:json.url,
			data:json.data,
			success:function(data){
                console.log(data)
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

    /*确认导入*/
    $s.imConfirm = function(){
        Layer.confirm({width:300,height:160,title:"确认导入吗？",header:"导入大客户学员"},function(){
            $.AJAX({
                url:config.basePath+"vip/submit",
                data:{
                    id:$s.erportId
                },
                success:function(data){
                    Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                    setTimeout(function(){window.location.href="big-client-details.html?coid="+$s.coid+"&cityId="+$s.cityId},1500)
                }
            })
        });
    }

    /*取消导入*/
    $s.imCancel = function(){
        Layer.confirm({width:300,height:160,title:"确认取消吗？",header:"取消导入大客户学员"},function(){
            $.AJAX({
                url:config.basePath+"vip/giveUp",
                data:{
                    id:$s.erportId
                },
                success:function(data){
                    Layer.miss({width:250,height:90,title:"已取消导入",closeMask:true});
                    setTimeout(function(){window.location.href="big-client-details.html?coid="+$s.coid+"&cityId="+$s.cityId},1500)
                }
            })
        });


    }


	/*参数配置函数*/
	function getJson(nowPage){
		var json={
			url:config.basePath+"vip/custom-exportDetail",
			data: {
                exportId:$s.erportId
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


	
}]);