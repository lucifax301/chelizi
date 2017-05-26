/*angular for 提现*/
var app=angular.module("app",["ngFilter"]);

/*main 控制器*/
app.controller("SchoolApply",["$scope","$rootScope","$filter",function($s,$rootScope,$filter){

	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.pageSize=10;    //每页显示-显示条数
	$s.startTime="";   //数据查询-开始时间
	$s.endTime="";     //数据查询-结束时间
	$s.sign ="";    //申请类型 1-开通钱包，2-变更资料
	$s.checkStatus="-1";   //审核状态  1未处理，2-审核通过，3-审核不通过，4-同意变更，5-拒绝变更


    /*模拟数据*/
    $s.data={
        pages:10,
        total:100,
        pageSize:10,
        pageNo:1,
        dataList:[
            {applyTime:12556211212,shortName:"新新驾校",schoolName:"东莞新新驾校",cityId:100103,city:"东莞市",address:"东莞市淡水一街2号",applyer:"新新",phoneNum:18900000002,schoolAccount:"xinxin",state:0,note:""},
            {applyTime:12556261212,shortName:"深南驾校",schoolName:"深圳市深南驾校",cityId:100101,city:"深圳市",address:"深圳市深南大道特1号",applyer:"小申申",phoneNum:18900000001,schoolAccount:"shennan",state:0,note:""}


        ]
    };

	/*-----------------------------------------查询数据列表----------------------------------------------------*/
	/*获取数据列表并展示*/
	$s.getDataList=function(){
		var json=getJson($s.defaultPage);
		$.AJAX({
			type:"post",
			url:json.url,
			data:json.data,
			success:function(data){

				var DATA=getListData(data);
				/*数据渲染页面*/
				$s.datas=DATA.dataList;
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
	};	
	$s.getDataList();
	/*参数配置函数*/
	function getJson(nowPage){
		var json={
			url:config.basePath+"school/applyQuery",
			data: {
				"pageNo": nowPage,
			    "pageSize": parseInt($s.pageSize),
				"startTime":$s.startTime,
				"endTime":$s.endTime,
				"status":$s.checkStatus,
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

	/*按时间筛选列表数据*/
	$s.getDataForTime=function($event,type){
		$($event.target).addClass('active').siblings().removeClass("active");
		switch(type){
			case 'all':
				$s.startTime=$s.endTime="";
				break;
			case '0':
				$s.startTime = $filter('date')((new Date().getTime()-604800000), 'yyyy-MM-dd');
				$s.endTime= $filter('date')(new Date().getTime(), 'yyyy-MM-dd');
				break;
			case '1':
				$s.startTime = $filter('date')((new Date().getTime()-1296000000), 'yyyy-MM-dd');
				$s.endTime= $filter('date')(new Date().getTime(), 'yyyy-MM-dd');
				break;
		};
		$s.defaultPage=1; //默认第一页
		win.showLoading();
		$s.getDataList();
	}

	/*按输入时间筛选数据列表*/
	$('#reservation').daterangepicker({format: 'YYYY/MM/DD'},
		function(start, end, label) {
			$s.startTime=$s.endTime="";
			$s.startTime = $filter('date')(start.toISOString(), 'yyyy-MM-dd');
			$s.endTime = $filter('date')(end.toISOString(), 'yyyy-MM-dd');
			$s.defaultPage=1; //默认第一页
			win.showLoading();
			$s.getDataList();
			$s.$apply();
		});

	/*按审核记录筛选数据*/
	$s.checkStatusType=function($event,type){
		$($event.target).addClass('active').siblings().removeClass("active");
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.checkStatus=type;
		$s.getDataList();
	}

	/*按分页条数筛选列表数据*/
	$s.getDataForPage=function(){
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.getDataList();
	}


/*--------------------------选择城市和驾校--------------------------------------*/
    /*加载城市列表*/
    queryCity({
        callback:function(data){
            $s.citys=data; $s.$apply();
        }
    });

    /*加载驾校列表*/
    $s.getSchools=function(cityId){
        $s.cityId=cityId;
        if($s.cityId){
            $s.checkHaveCity(); //判断是否选择城市
            $('#select-school li').last().addClass('active').siblings().removeClass("active");
            /*加载驾校列表*/
            querySchool({
                cityId:$s.cityId,
                callback:function(data){
                    $s.schools=data;
                    $s.$apply();
                }
            });
        }else{
            $('#select-school li').first().addClass('active').siblings().removeClass("active");
            $s.schoolNo=""; //情况驾校number
            $s.schools=""; //清空数据列表
        }
    }
    /*判断是否选择城市*/
    $s.checkHaveCity=function(){
        $s.cityError=$s.cityId?false:true;
    }
    /*获得schoolId*/
    $s.getSchoolId=function(schoolNo){
        $s.editData.schoolId=schoolNo;

    }

//******审核通过****************************/
    $s.editData={};
    $s.passSchoolApply = function(){
        /*点击按钮关闭弹出层*/
        $(".closeAlert").click(function(){
            $(this).parents("div.edit-alert").fadeOut("fast");
        });
    }

    /*做为新驾校添加*/
    $s.setNewSchool = function($event){
        $(".existSchoolCon").css("display","none");
        $s.isSchoolNew=true;
    }
    /*做为老驾校添加*/
    $s.setNewSchool = function($event){
        $(".existSchoolCon").css("display","block");
        $s.isSchoolNew=false;
    }

    $s.checkPass=function(item){
        $s.editData.id = item.id;
        //弹出编辑层
        $(".school-apply-pass").fadeIn("fast");
        $("#edit-content").css("marginTop",parseInt(($(win).height()- $("#edit-content").height()-100)/2)+"px");
        $s.submitPassMsg = function(){
            $s.submitData = {};
            /*当选了已存在驾校时,较验并提交schoolId*/
            if($("#existSchool").prop("checked")==true){
                if(!$s.editData.schoolId){
                    Layer.alert({width:300,height:170,type:"msg",title:"请选择驾校",header:"审核通过"});
                    return false;
                }
                $s.submitData = {id:item.id,schoolId:$s.editData.schoolId}
            }
            /*当选了新驾校时,直接提交ID*/
            else{
                $s.submitData = {id:item.id,schoolId:0}
            }
            $.AJAX({
                type:"post",
                url:config.basePath+"school/applyAudit",
                data:$s.submitData,
                success:function(data){
                    $("div.edit-alert").fadeOut("fast");
                    Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                    $s.getDataList();
                }
            })
        }
    }


//******审核不通过****************************/
    $s.checkReject=function(id){
        /*确认拒绝*/
        Layer.confirmNotByTextAlert({
            header:"审核不过",
            width:400,
            height:260,
            botByText:'请填写拒绝理由',
            title:"您已选择拒绝通过",
            errmsg:'请填写拒绝的理由！',
        },function(notByText){
            $.AJAX({
                url:config.basePath+"school/applyUnAudit",
                data:{
                    "id":id,
                    "remark":notByText,
                },
                success:function(data){
                    /*提示成功信息*/
                    Layer.miss({width:250,height:90,title:"拒绝成功！",closeMask:true});
                    /*更新列表*/
                    $s.getDataList();
                }
            });/*AJAX end*/
        });
    }



}]);