/*angular for 教练*/
var app=angular.module("app",["ngFilter","ngSelects"]);

/*main 控制器*/
app.controller("Users",["$scope","$rootScope","$filter","Selects",function($s,$rootScope,$filter,Selects){
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.pageSize=10;    //每页显示-显示条数
	$s.search="";            //高级查询
	$s.searchType="account";    //默认搜索字段
    $s.account = "";
    $s.userName = "";
    $s.phoneNum = "";
    $s.email = "";
    $s.accountStatus = -1;


	/*获取数据列表并展示*/
	$s.getDataList=function(){
		var json=getJson($s.defaultPage);
		$.AJAX({
			url:json.url,
			data:json.data,
			success:function(data){
				var DATA=getListData(data);
				$s.total=DATA.total;
				/*数据渲染页面*/
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
	$s.getDataList();
	/*参数配置函数*/
	function getJson(nowPage){
		var json={
			url:config.basePath+"user/findUser",
			data: {
				"pageNo": nowPage,
			    "pageSize": parseInt($s.pageSize),
                "account":$s.account,
                "userName":$s.userName,
                "phoneNum":$s.phoneNum,
                "email":$s.email,
                "enabled":$s.accountStatus
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

	/*按账号状态筛选列表数据*/
	$s.getDataForAccountStatus=function($event,type){
		$($event.target).addClass('active').siblings().removeClass("active");
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.accountStatus=type;
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


/*----------------用户 新增|修改 弹出层操作----------------------*/
	/*include 加载完成后执行*/
	$s.usersEditLoad=function(){
		/*点击按钮关闭弹出层*/
		$(".closeAlert").click(function(){
			$(this).parents("div.edit-users").fadeOut("fast");
		})

	}
	
	/*点击 编辑|新增 用户信息*/
	$s.editData={};
	$s.editType="add";
	$s.userEdit=function(type,data){
		$(".edit-users").fadeIn("fast");
		/*弹出层垂直居中*/
		$("#edit-users").css("marginTop",parseInt(($(win).height()- $("#edit-users").height()-100)/2)+"px");
		if(type=="edit"){
		/*修改,禁用用户名编辑*/
			$s.editData = data;
			$s.editType="edit";
            $s.userDisable = true;
		}else{
		/*新增*/
			$s.editData={};
			$s.editType="add";
            $s.userDisable = false;
		}
	}

    /*参数配置函数*/
    function editUserJson(url){
        var tmpJson = {
            //url:config.basePath+url,
            url:url,
            data:{
                account:$s.editData.account,
                userName:$s.editData.userName,
                phoneNum:$s.editData.phoneNum,
                email:$s.editData.email
            }
        }
        if($s.editType=="edit"){
            tmpJson.data.id = $s.editData.id
        }
        if($s.editType=="add"){
            tmpJson.data.password = $s.editData.phoneNum
        }
        return tmpJson;
    }

	/*修改 | 新增 用户*/
	$s.submitEditMsg=function($event){
		var json=$s.editType=="add"?editUserJson(config.basePath+"user/add"):editUserJson(config.basePath+"user/update");

        if(!$s.editData.account || !$s.editData.account.match(/^[a-z0-9A-Z]+$/i) || $s.editData.account.length<2){
            Layer.alert({width:450,height:150,type:"msg",title:"用户名必须为2-20位字母或数字)"});
            return false;
        }
        if(!$s.editData.userName || !regCombination('chinese',[2,20]).test($s.editData.userName)){
            Layer.alert({width:450,height:150,type:"msg",title:"姓名必须为2-20位中文字符)"});
            return false;
        }
        if(!$s.editData.email || !regCombination('email').test($s.editData.email)){
            Layer.alert({width:300,height:150,type:"msg",title:"请检查用户的邮箱"});
            return false;
        }
        if(!$s.editData.phoneNum || !regCombination('*').test($s.editData.phoneNum)){
            Layer.alert({width:300,height:150,type:"msg",title:"请检查手机号码"});
            return false;
        }

		$.AJAX({
			url:json.url,
			data:json.data,
			success:function(data){
				/*关闭弹出层*/
				$($event.target).parents("div.edit-users").fadeOut("fast");
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


    /*-----帐户恢复 & 停用---------------------------*/
    $s.activeAction = function(cAction){
        var actionInfo = {msgWords:"",msgUrl:"",msgCode:""};
        switch (cAction) {
            case "cActive":
                actionInfo.msgWords = "恢复";
                actionInfo.msgUrl = config.basePath+"user/active";
                actionInfo.msgCode = 1;//点激活时要求的状态值（为停用状态值）
                actionInfo.msgWords2 = "停用";
                break;
            case "cCancle":
                actionInfo.msgWords = "停用";
                actionInfo.msgUrl = config.basePath+"user/cancle";
                actionInfo.msgCode = 0;//点停用时要求的状态值（为激活状态值）
                actionInfo.msgWords2 = "恢复";
                break;
            default: break;
        }

        /*检测是否选择*/
        if(!$rootScope.idList.length){
            Layer.alert({width:300,height:150,type:"msg",title:"请选择需要"+actionInfo.msgWords+"的帐户！"});
            return false;
        };

        /*获得选择的data数据*/
        var datas=getDataForKey({datas:$s.datas,id:'id',idList:$rootScope.idList});
        /*只能选择同一组别的方案（停用或启用）*/
        var dataOK = true;//选择的数据，默认为合格
        var errorWorlds = "";//提示语句
        /*判断所选数据是否合法*/
        for (var i=0;i<datas.length;i++){
            if(datas[i].enabled!=actionInfo.msgCode ){
                errorWorlds = "您只能选择"+actionInfo.msgWords2+"的帐户，<br>请重新选择！";
                dataOK = false; break; //数据不合格，跳出
            }
        }
        if(!dataOK){
            Layer.alert({width:330,height:175,type:"msg",title:errorWorlds});
            return false;
        }
        $s.activeJson = {}
        /*当勾选数多项时，取集合传ids,  当勾选项为一项时，取单个id*/
        if(datas.length==1){$s.activeJson.userId=$rootScope.idList.toString()}
        if(datas.length>1){$s.activeJson.ids=$rootScope.idList.toString()}
        /*弹出确认框*/
        Layer.confirm({width:300,height:160,title:"确认"+actionInfo.msgWords+"选中帐户吗？",header:actionInfo.msgWords+"帐户"},function(){
            $.AJAX({
                url:actionInfo.msgUrl,
                data:$s.activeJson,
                success:function(data){
                    $rootScope.idList=[];
                    Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                    $s.getDataList();
                }
            });
        });/*layer end*/
    }



}]);