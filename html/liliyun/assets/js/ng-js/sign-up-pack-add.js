/*angular for 提现*/
var app=angular.module("app",["ngFilter"]);

/*main 控制器*/
app.controller("signUpPackAdd",["$scope","$filter",function($s,$filter){
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.pageSize=10;    //每页显示-显示条数
	$s.name="";    //高级查询
	$s.qiniuToken;	//七牛密钥

	$s.getDataList=function(){
		var json=getJson($s.defaultPage);
		$.AJAX({
			type:"get",
			url:json.url,
			data:json.data,
			success:function(data){
				var DATA=getListData(data);
				/*数据渲染页面*/
				$s.datas=DATA.dataList;
				//console.log($s.datas);
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
			url:config.basePath+"ept/batch",
			data: {
				"pageNo": nowPage,
			    "pageSize": parseInt($s.pageSize),
			    "cityId":$s.cityId,
			    "name": $s.name
			}
		};
		return json;
	};

	/*获取七牛密钥*/
	//$.get(config.basePath+"resource/pic-token", function(data){$s.qiniuToken = data.result.pageData;});

	/*以下是将大块合并的数据细分并格式化后再显示在页面*/
	// $s.priceDetailFormat="";
	// $s.testConditionFormat = "";
	// $s.examProfileFormat = "";
	// $s.describtionLocal = "";
	// $s.describtionStrange = "";
	// $s.describtionHK = "";
	// $s.describtionArmy = "";
	// $s.tmpcoursepriceFormat = {c1:{base:"",drive:""},c2:{base:"",drive:""}};
	// $s.tmpcourseFormat = {course2:"",course3:"",}
	// $s.tmpstandardFormat = "";
	// $s.tmpserviceFormat = "";
	/*信息项自增*/
	function packFormItemAdd () {
		// body...
	}

	/*参数配置函数*/
	function addPackJson(url){
		var json={
				name:$s.editData.name,
				cityId:parseInt($s.editData.cityId),
				phoneNum:$s.editData.phoneNum

			};

		return {
			url:config.basePath+url,
			data:json
		}
	}

	/*修改 | 新增 训练场信息*/
	$s.submitEditMsg=function($event){
		var json=addPackJson("ept/add");

		if(!$s.editData.name || !regCombination('*').test($s.editData.name)){
			Layer.alert({width:300,height:150,type:"msg",title:"请填写驾校名称"});
			return false;
		}	

		$.AJAX({
			url:json.url,
			data:json.data,
			type:'get',
			success:function(data){
				clearTimeout(secLocation);
				Layer.alert({width:300,height:150,type:"msg",title:"添加报名包成功！2秒后跳回报名包列表页<br>如果没有自动跳转，请点击如下链接<br><a href='"+config.basePath+"sign-up-pack-list.html'>"+config.basePath+"sign-up-pack-list.html</a>"});
				/*更新列表*/
				var secLocation = setTimeout(function(){window.location.href=config.basePath+"sign-up-pack-list.html"},2000)
			},
			error:function(XMLHttpRequest){
				console.log(XMLHttpRequest);
			}
		});/*AJAX end*/		
	}


/*表单块收缩*/
	$s.packItemToggle = function($event){
		var icoType = $($event.target).html();
		if(icoType == "-"){$($event.target).html("+").parent("h2.z-toggle-h2").next("div").slideUp();}
		else if(icoType == "+"){$($event.target).html("-").parent("h2.z-toggle-h2").next("div").slideDown();}
		else{alert("请注意ICO的+号和-号");}
	}



	



}]);