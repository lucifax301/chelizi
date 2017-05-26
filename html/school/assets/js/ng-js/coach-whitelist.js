/*angular for 提现*/
var app=angular.module("app",["ngFilter"]);

/*main 控制器*/
app.controller("CoachWhitelist",["$scope","$compile","$filter",function($s,$compile,$filter){
	$s.defaultPage=location.hash.substring(2) || 1;
	$s.pageSize=10;    //每页显示-显示条数
    $s.timeArea="";     //筛选时间段
	$s.search="";            //高级查询
	$s.searchType="name";    //默认搜索字段
    win.hideLoading();

	///*模拟数据*/
	// $s.data={
	// 	pages:10,
	// 	total:100,
	// 	pageSize:10,
	// 	pageNo:1,
	// 	dataList:[
	// 		{coachId:101,name:"张教练",phoneNum:"13476002586"},
     //       {coachId:101,name:"李教练",phoneNum:"13476002586,18988795525"}
	// 	]
	// };
	// //或得的数据列表
	// $s.datas=$s.data.dataList;

	/*获取数据列表并展示*/
	$s.getDataList=function(){
		var json=getJson($s.defaultPage);
		$.AJAX({
			type:"post",
			url:json.url,
			data:json.data,
			success:function(data){
				var DATA=getListData(data);
				$s.total=DATA.total;
				/*数据渲染页面*/
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
			url:config.basePath+"examPlace/whitelist",
			data: {
				"pageNo": nowPage,
			    "pageSize": parseInt($s.pageSize),
			    "ctime":$s.timeArea,
                "mobile":$s.mobile
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

	/*按分页条数筛选列表数据*/
	$s.getDataForPage=function(){
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.getDataList();
	}

    /*按输入时间筛选数据列表*/
    $('#reservation').daterangepicker({format: 'YYYY/MM/DD'},
        function(start, end, label) {
            $s.startTime=$s.endTime="";
            $s.startTime = $filter('date')(start.toISOString(), 'yyyy-MM-dd');
            $s.endTime = $filter('date')(end.toISOString(), 'yyyy-MM-dd');
            $s.timeArea = $s.startTime + "," + $s.endTime
            win.showLoading();
            $s.defaultPage=1; //默认第一页
            $s.getDataList();
            $s.$apply();
        });

	/*高级查询*/
	$s.getDataForSearch=function(){
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.getDataList();
	}


/*-------------------------------教练 新增|修改 弹出层操作---------------------------------------*/
	/*include 加载完成后执行*/
	$s.coachWhiteEditLoad=function(){
		/*点击按钮关闭弹出层*/
		$(".closeAlert").click(function(){
			$(this).parents("div.edit-coach").fadeOut("fast");
            $("#coachPhones").empty();
		})
	}



	/*点击 编辑|新增 教练信息*/
	$s.editData={};
	$s.editType="add";
	$s.coachWhiteEdit=function(type,data){
        $(".edit-coach").fadeIn("fast");
		/*弹出层垂直居中*/
		$("#editCoachWhitelist").css("marginTop",parseInt(($(win).height()- $("#editCoachWhitelist").height()-100)/2)+"px");
        //输错重输入时，消除红框警告
        $("#editCoachWhitelist").on("focus","input",function(){$(this).removeClass("border-error")})

        if(type=="edit"){
		/*编辑白名单时，把数据拉出来，多个电话分割后动态插入表单*/
			$s.editData = data;
			$s.editType="edit";
            $s.phonesArr = $s.editData.mobile.split(",");//获取到已存的电话集合
            var dthtml="";
            for( var i=0;i<$s.phonesArr.length;i++){
                //遍历第一项按正常显示，其后的要加删除小图标
                if(i==0){dthtml = '<dt>手机号码</dt>'}
                else{dthtml = '<dt class="item-auto-del"><div ng-click="zItemDel($event)" class="z-ico-del">X</div></dt>'}

                $("#coachPhones").append($compile('\
                <dl>\
                    '+dthtml+'\
                    <dd><input type="text" value='+$s.phonesArr[i]+' maxlength="11" class="form-control phoneitem" placeholder="请填写教练手机号码"></dd>\
                </dl>')($s))
            }
            btnAddShow()
		}
        else{
		/*新增白名单时，直接生成一段输入项插入表单*/
			$s.editData={};
			$s.editType="add";
            $("#coachPhones").append('\
                <dl>\
                    <dt>手机号码</dt>\
                    <dd><input type="text" maxlength="11" class="form-control phoneitem" placeholder="请填写教练手机号码"></dd>\
                </dl>')
		}
	}

    //删除一个电话输入框
    $s.zItemDel = function($event){
        console.log($event.target)
        $($event.target).parent("dt").parent("dl").remove();
        $("#editCoachWhitelist").css("marginTop",parseInt(($(win).height()- $("#editCoachWhitelist").height()-100)/2)+"px");
        btnAddShow()
    }

    //增加一个电话输入框
    $s.zItemAdd = function($event){
        //动态获取规则下拉的模板
        $s.autoFormItemTPL = '\
            <dl>\
                <dt class="item-auto-del"><div ng-click="zItemDel($event)" class="z-ico-del">X</div></dt>\
                <dd><input type="text" maxlength="11" class="form-control phoneitem" placeholder="请填写教练手机号码"></dd>\
            </dl>';
        //点增加按钮时动态插入的HTML内容
        $("#coachPhones").append($compile($s.autoFormItemTPL)($s));
        $("#editCoachWhitelist").css("marginTop",parseInt(($(win).height()- $("#editCoachWhitelist").height()-100)/2)+"px");
        btnAddShow();
    }

    function btnAddShow(){
        $(".wrap-btn-add").css("display",$("#coachPhones dl").length>1?"none":"block")
    }



	/*参数配置函数*/
	function editCoachJson(url){
        $s.canSubmit = true;
        if(!$s.editData.name){
            Layer.alert({type:"msg",height:150,title:"请填写教练姓名"});
            $(".coach-name").addClass("border-error");
            $s.canSubmit = false;
            return false;
        }
        var phoneArr = [];
        $(".phoneitem").each(function(){
            if(!$(this).val()){
                Layer.alert({type:"msg",height:150,title:"请检查手机号不能为空！"});
                $(this).addClass("border-error");
                $s.canSubmit = false;
                return false;
            }
            if($(this).val().length<11){
                Layer.alert({type:"msg",height:150,title:"请检查手机号格式不正确！"});
                $(this).addClass("border-error");
                $s.canSubmit = false;
                return false;
            }
            phoneArr.push($(this).val());
        })

		var json={
            name:$s.editData.name,
            mobile:phoneArr.join(),
			};
		if($s.editType=="edit"){
			angular.extend(json,{id:$s.editData.id});
		}
		return {
			url:config.basePath+url,
			data:json
		}
	}

	/*修改 | 新增 教练白名单*/
	$s.submitEditMsg=function($event){
		var json=$s.editType=="add"?editCoachJson("examPlace/whitelist/add"):editCoachJson("examPlace/whitelist/modify");
        console.log(json)
        if($s.canSubmit){
            $.AJAX({
                url:json.url,
                data:json.data,
                success:function(data){
                    /*关闭弹出层*/
                    $($event.target).parents("div.edit-coach").fadeOut("fast");
                    Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                    $("#coachPhones").empty();
                    /*更新列表*/
                    $s.getDataList();
                }
            });
        }

	}

    /*删除教练白名单*/
    $s.whiteCoachDel = function(coachId){
        Layer.confirm({width:350,height:160,title:"确定把该教练从白名单中剔除吗？",header:"删除"},function(){
            $.AJAX({
                url:config.basePath+"examPlace/whitelist/del",
                data:{"id":coachId},
                success:function(data){
                    Layer.miss({width:250,height:90,title:"删除成功",closeMask:true});
                    $s.getDataList();
                }
            })
        })
    }

}]);