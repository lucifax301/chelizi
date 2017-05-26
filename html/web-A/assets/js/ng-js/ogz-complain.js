app.controller("ComplainList",["$scope","$filter", "$http", function($scope,$filter,$http) {

	//var load = new Loading();
    //load.init();
	//load.start(); //开始加载
	// load.stop(); 关闭
  
  //全选、取消全选的事件  
	$scope.selectAll = function(){  
		if ($("#SelectAll").prop("checked")) {  
			$(".subcheck").prop("checked", true);  
		} else {  
			$(".subcheck").prop("checked", false);  
		}  
	}  
	//子复选框的事件  
	$scope.setSelectAll = function(id){  
		//当没有选中某个子复选框时，SelectAll取消选中  
		if (!$(".subcheck").checked) {  
			$("#SelectAll").prop("checked", false);  
		}  
		var chsub = $(".subcheck").length; //获取subcheck的个数  
		var checkedsub = $("input[class='subcheck']:checked").length; //获取选中的subcheck的个数  
		if (checkedsub == chsub) {  
			$("#SelectAll").prop("checked", true);  
		}  
	}
  
	//搜索人员列表
	var vm = $scope.vm = {};	
	 vm.statusOpt = {  
		allowClear : true  
	} 
	$.AJAX({
		url:config.basePath+"user/list",
		async:false,
		success:function(data){
			$scope.gzUsers = data.result.list;
		}
	});
    $scope.statsuDatas = [{id:0,value:'未处理'},{id:1,value:'处理中'},{id:2,value:'已处理'},{id:3,value:'拒绝'}];
    $scope.uploadbtn = true;//上传按钮
    $scope.isCoachValid = 0;
    $scope.isCoachValid2 = 0;

    $scope.defaultPage= getUrl('page') || 1;  //默认请求页
    if(getUrl('search')){
        $scope.condition = getUrl('search');//查询当前url是否存在参数search，如有赋值搜索框
    }
    $scope.pageSize=10;    //每页显示-显示条数

    window.onhashchange=function(){
        $scope.defaultPage= getUrl('page') || 1;  //默认请求页
    }

	//查询回车事件
	$scope.getkey = function(e){
		var keycode = window.event?e.keyCode:e.which;
		if(keycode==13){
			$scope.searchList();
		}
	}
	
    $scope.getDataList=function(){
		 var json = {
		complainid:$scope.complainid,
		condition:$scope.condition,
		pageNo : $scope.defaultPage,
		pageSize : $scope.pageSize
		};
		$.AJAX({
			type:"POST",
			data:json,
			url:config.basePath+"complain/list",
			success:function(data){
				//load.stop();
				$scope.datas = data.result.list
				$scope.total = data.result.total;
				$scope.$apply();
				new Page({
					parent:$("#copot-page"),
					nowPage:$scope.defaultPage,
					pageSize:$scope.pageSize,
					totalCount:$scope.total,
					search:$scope.coachId
				}); //分页请求完毕
			}
		});
    };
    $scope.getDataList();
	
    $scope.searchList = function(){
        if($scope.condition){
            var nowURL = location.href.split('#');
            var newurl = nowURL[1] + "#&&search=" + $scope.condition;
            location.hash= newurl;
        }else{
            var nowURL = location.href.split('#');
            var newurl = nowURL[1];
            location.hash= newurl;
    }
    }

    //清理新增痕迹
	$scope.clearMsg = function(){
		$scope.saveData={};
		$scope.saveData.complainmobile = '';
		$scope.myForm.$setPristine();
		
	}

    $scope.submitAddMsg = function() {
        $.AJAX({
            url : config.basePath + "complain/addComplain",
            type : "POST",
            data : {
                complainuser : $scope.saveData.complainuser,
                complainmobile : $scope.saveData.complainmobile,
                summary : $scope.saveData.summary,
                content :  $scope.saveData.content,
                remark : $scope.saveData.remark
            },
            success:function(data){
				//load.stop();
                Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                $('#addComplain').modal('hide');
                $scope.saveData = {};
                $scope.getDataList();
            }
        });
    }
	
	 //保存
    $scope.submitAddForm = function(isValid) {
         $scope.submitAddMsg(); 
    };

	//投诉编辑弹框
	$scope.complainEdit=function(type,data){
		$("#editComplain").modal("toggle");
		$scope.editData = data;
	}
    $scope.submitEditMsg = function() {
        $.AJAX({
            url : config.basePath + "complain/updateComplain",
            type : "POST",
            data : {
                complainid : $scope.editData.complainid,
                complainuser : $scope.editData.complainuser,
                complainmobile : $scope.editData.complainmobile,
                summary : $scope.editData.summary,
                content :  $scope.editData.content,
                remark : $scope.editData.remark
            },
            success : function(data){
                Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                $('#editComplain').modal('hide');
                $scope.editData = {};
                $scope.getDataList();
            }
        });
    }
	
	 //投诉编辑提交
    $scope.submitEditForm = function(isValid) {
        $scope.submitEditMsg();
    };
	
	// ‘处理’弹框
	$scope.manageUser=function(data){
		$("#manageUser").modal("toggle");
		$scope.complainidHiddden = data.complainid;
		$scope.namaData = data;
		$scope.manaform.$setPristine();
	}
	
	//处理投诉
    $scope.submitManaMsg = function() {
        $.AJAX({
            url : config.basePath + "complain/updateComplain",
            type : "POST",
            data : {
                complainid : $scope.complainidHiddden,
                manageuser : $scope.namaData.manageuser,
                managetype : $scope.namaData.managetype,
                status : $scope.namaData.status,
                manageremark : $scope.namaData.manageremark
            },
            success : function(data){
                Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                $('#manageUser').modal('hide');
                $scope.namaData = {};
                $scope.getDataList();
            }
        });
    }
	
	//处理投诉提交
    $scope.submitManaForm = function(isValid) {
        $scope.submitManaMsg();
    };

	//新增回访弹框
	$scope.returnAdd=function(data){
		$("#addReturn").modal("toggle");
		$scope.returnData = {};
		$scope.returnform.$setPristine();
		$scope.complainidHiddden = data.complainid;
	}
	
    $scope.submitReturnMsg = function() {
        $.AJAX({
            url : config.basePath + "complain/addComplainReturn",
            type : "POST",
            data : {
                complainid : $scope.complainidHiddden,
                returnuser :   $scope.returnData.manageuser,
                returncontent : $scope.returnData.returncontent
            },
            success : function(data){
                Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                $('#addReturn').modal('hide');
                $scope.returnData = {};
                $scope.getDataList();
            }
        });
    }
	
	//新增回访提交
    $scope.submitReturnForm = function(isValid) {
        $scope.submitReturnMsg();
    };

    $scope.importData = function() {
        if ($("#uploadfile").val() == "" | $scope.filename == null) {
            Layer.miss({width:250,height:90,title:"请选择上传文件!",closeMask:true});
            return;
        }
        if ($scope.filename == null) {
            Layer.miss({width:250,height:90,title:"请先上传文件!",closeMask:true});
            return;
        }
        Layer.confirm({width:300,height:160,title:"确认导入列表中的数据？",header:"导入确认"},function(){
            $.AJAX({
                url:config.basePath+"coach/importExcel",
                data:{filename:$scope.filename},
                success:function(data){
                    Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                    $scope.getList();
                }
            });
        });
    }



    //excel导入
    $scope.uploadfile = function(){
        var html='<div id="createFileHtml" class="hidden"><form method="post" enctype="multipart/form-data" name="form1" id="f1"><input type="file" id="apkFile" name="file"/></form></div>';
        if(!$('#createFileHtml').length){
            $('body').append(html);
        };
        $('#apkFile').click();
        $('#apkFile').change(function(){
            Layer.confirm({width:300,height:160,title:"确认上传吗？",header:"删除"},function(){
                var form = new FormData(document.getElementById("f1"));
                $.ajax({
                    url:config.basePath+"complain/uploadExcel",
                    type:"post",
                    data:form,
                    xhrFields:{withCredentials: true},
                    crossDomain: true,
                    processData:false,
                    contentType:false,
                    async: false,
                    success:function(data){
                        if(data.code==200){
                            Layer.alert({width:300,height:150,type:"error",title:data.msg});
                            return;
                        }
                        if(data.code==201){
                            Layer.alert({width:300,height:150,type:"error",title:data.msg});
                            return;
                        }

                        $scope.filename = data.result.filename;
                        $scope.datas = data.result.data;
                        console.log(data);
                        $scope.uploadbtn = false;
                        new Page({
                            parent:$("#copot-page"),
                            nowPage:$scope.defaultPage,
                            pageSize:data.result.data.length,
                            totalCount:data.result.data.length
                        }); //分页请求完毕
                        $scope.$apply();
                    }
                })
                $('#createFileHtml').remove();
                $scope.$apply();

            })
            $('.confirmRight').click(function(){
                $('#createFileHtml').remove();
            })
        })

    }

    //导入db
    $scope.importFile = function(type){
        $.AJAX({
            url:config.basePath+"complain/importExcel",
            data:{filename:$scope.filename,type:type},
            success:function(data){
                if(data.code==0){
                    Layer.miss({width:250,height:90,title:data.msg,closeMask:true});
                }
                if(data.code==2){
                    Layer.miss({width:250,height:90,title:data.msg,closeMask:true});
                }
                $scope.uploadbtn = true;
                $scope.getDataList();
                $scope.pagediv = true;
                $scope.$apply();

            }
        });
    }


    //下载
    $scope.download = function(){
        var url = config.basePath+"complain/download";
        window.open(url);
    }


}])






