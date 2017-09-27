/*angular for 提现*/
var app=angular.module("app",["ngFilter"]);

/*main 控制器*/
app.controller("Student",["$scope","$filter",function($s,$filter){
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.pageSize=10;    //每页显示-显示条数
	
	$s.name="";              //高级查询

	
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
			url:config.basePath+"vip/batch",
			data: {
				"pageNo": nowPage,
			    "pageSize": parseInt($s.pageSize),
			    "name": $s.name
			    
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

	/*高级查询*/
	$s.getDataForSearch=function(){
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.getDataList();
	}

	/*按驾校筛选列表数据*/
	$s.getDataForSchool=function(){
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.schoolName=$s.schoolNameSelect;
		$s.getDataList();
	}

	/*数据导出*/
	$s.vipDataExport=function(){
		//调起数据导出
		dataExportForIframe({
			getSearchs:getJson($s.defaultPage).data,
			total:$s.total,
			url:'vip/export-excel',
		});
	}
	
/*------------------------------------------编辑 | 新增 教练车---------------------------------------------*/

	/*include 加载完成后执行*/
	$s.vipEditLoad=function(){
		/*点击按钮关闭弹出层*/
		$(".closeAlert").click(function(){
			$(this).parents("div.edit-alert").fadeOut("fast");
		})
	}
	
	/*点击 编辑|新增 教练信息*/
	$s.editData={};
	$s.editType="add";
	$s.vipEdit=function(type,data){
		$(".edit-alert").fadeIn("fast");
		/*弹出层垂直居中*/
		$("#edit-content").css("marginTop",parseInt(($(win).height()- $("#edit-content").height()-100)/2)+"px");
		if(type=="edit"){
		/*修改*/
			$s.editData = data;
			$s.editType="edit";
		}else{
		/*新增*/
			$s.editData={};
			$s.editType="add";
		}
	}
	
	$s.vipDelete=function(data){
		Layer.confirm({width:350,height:160,title:"确认删除大客户"+data.name+"？",header:"确认"},function(){
			$.AJAX({
				url: config.basePath+"vip/delete",
				type : "POST",
				data: {
					id: data.id
				},
				success : function(data){
					Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
					$s.getDataList();
				}
			});
		});
	}

	/*参数配置函数*/
	function editVipJson(url){
		var json={
				name:$s.editData.name,
				mobile:$s.editData.mobile,
				c1count:0,
				c2count:0
			};
		if($s.editType=="edit"){
			angular.extend(json,{id:$s.editData.id});
		}	
		return {
			url:config.basePath+url,
			data:json
		}
	}

	/*修改 | 新增 教练车信息*/
	$s.submitEditMsg=function($event){
		var json=$s.editType=="add"?editVipJson("vip/add"):editVipJson("vip/update");

		if(!$s.editData.name || !regCombination('*').test($s.editData.name)){
			Layer.alert({type:"msg",title:"请填写大客户名称"});
			return false;	
		}
		if(!$s.editData.mobile || !regCombination('*').test($s.editData.mobile)){
                        Layer.alert({type:"msg",title:"请填写大客户手机号"});
                        return false;
                }
		//if(!$s.editData.c1count || !regCombination('*').test($s.editData.c1count)|| !regCombination('number').test($s.editData.c1count)){
		//	Layer.alert({type:"msg",title:"请填写c1数量"});
		//	return false;
		//}
		//if(!$s.editData.c2count || !regCombination('*').test($s.editData.c2count)|| !regCombination('number').test($s.editData.c2count)){
		//	Layer.alert({type:"msg",title:"请填写c2数量"});
		//	return false;
		//}
		
		
		$.AJAX({
			url:json.url,
			data:json.data,
			success:function(data){
				/*关闭弹出层*/
				$($event.target).parents("div.edit-alert").fadeOut("fast");
				Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
				/*更新列表*/
				$s.getDataList();
			}
		});/*AJAX end*/
	}

	/*取消时执行*/
	$s.closeAlert=function(){
		$s.getDataList(); //取消时还原页面数据
	}

}]);
