

app.controller("ClassManage",["$scope","$filter", "$http", function($scope,$filter,$http) {

        $scope.typeDatas = [{id: "1",value: '服务全包'},{id: "2",value: '先学后付'},{id: "3",value: '先付后学'}];

        $scope.uploadbtn = true;//上传按钮

        $scope.defaultPage= getUrl('page') || 1;  //默认请求页
        if(getUrl('search')){
            $scope.condition = getUrl('search');//查询当前url是否存在参数search，如有赋值搜索框
        }
        $scope.pageSize=10;    //每页显示-显示条数


        /*hash值改变的时候加载数据列表*/
        window.onhashchange=function(){
            $scope.defaultPage= getUrl('page') || 1;  //默认请求页   
        }

        $scope.getDataList=function(){

            var JSON = {
                classid:$scope.classid,
                condition:$scope.condition,
                pageNo : $scope.defaultPage,
                pageSize : $scope.pageSize
            };

            $.AJAX({
                url:config.basePath+"class/list",
                data:JSON,
                success:function(data){
                    var datas = eval(data);

                    $scope.datas = datas.result.list
                    $scope.total = datas.result.total;
                    $scope.$apply();
                    new Page({
                        parent:$("#copot-page"),
                        nowPage:$scope.defaultPage,
                        pageSize:$scope.pageSize,
                        totalCount:$scope.total,
                    }); //分页请求完毕
					
					initValidate();
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

		//重置表单
		$scope.resetForm = function() {
			$scope.saveData = {};
			resetForm("addForm"); 
		}
		
        /*新增 教练信息*/
        $scope.submitAddMsg=function($event){
			$scope.checkClass();
			
            //表单检测
            var opentime = $("#q_saveDate").val();
            var JSON = {
                classname:$scope.saveData.classname,
                opentime:opentime,
                type:$scope.saveData.type,
                amount:parseInt($scope.saveData.amount),
                singleamount:$scope.saveData.singleamount,
                classdesc:$scope.saveData.classdesc
            };

            $.AJAX({
                url:config.basePath+"class/addClass",
                data:JSON,
                success:function(data){
                    Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                    $('#addCoach').modal('hide');//隐藏控件
                    $scope.saveData={};//清空默认表单
                    $scope.saveData.opentime = "";
                    $scope.saveData.type='1';
                    $scope.getDataList();
                }
            });

        }

        /*编辑 教练信息*/
        $scope.submitEditMsg=function($event){

            var opentime = $("#q_editDate").val();

            var JSON = {
                classid:$scope.editData.classid,
                classname:$scope.editData.classname,
                opentime:opentime,
                type:$scope.editData.type,
                amount:parseInt($scope.editData.amount),
                singleamount:$scope.editData.singleamount,
                classdesc:$scope.editData.classdesc
            };

            $.AJAX({
                url:config.basePath+"class/updateClass",
                data:JSON,
                success:function(data){
                    Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                    $('#editCoach').modal('hide');
                    $scope.getDataList();
                }
            });
        }

        /*include 加载完成后执行*/
        $scope.coachEdit=function(type,data){
            $("#editCoach").modal("toggle");
            $scope.editData = data;
        }

        /*include 加载完成后执行*/
        $scope.coachDelete=function(classid){
            var JSON = {
                classid:classid
            };
            Layer.confirm({width:300,height:160,title:"确认删除此班级信息？",header:"删除班级"},function(){
                $.AJAX({
                    url:config.basePath+"class/deleteClass",
                    data:JSON,
                    success:function(data){
                        Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                        $scope.getDataList();
                    }
                });
            });
        }

        /*取消时执行*/
        $scope.closeAddAlert=function(){
            $('#addCoach').modal('hide');
            $scope.saveData={};//清空默认表单
            $scope.saveData.opentime = "";
            $scope.saveData.type='1';

        }

        /*取消时执行*/
        $scope.closeEditAlert=function(){
            $('#editCoach').modal('hide');
            $scope.editData={};//清空默认表单
        }


    //保存
	 $scope.nameAddMsg = false;
    $scope.checkClass = function() {
        var json = {
            classname:$scope.saveData.classname,
            pageNo : $scope.defaultPage,
            pageSize : $scope.pageSize
        };

        $.ajax({
            url:config.basePath+"class/list",
            data:json,
            xhrFields:{withCredentials: true},
            crossDomain: true,
            contentType:false,
            async: false,
            success:function(data){
                if(data.result.total > 0) {
                    $scope.nameAddMsg = true;
                }

            }
        })

        if($scope.nameAddMsg == true) {
            return;
        }
        $scope.submitAddMsg();
        $scope.timeAddMsg = false;
    };

    //编辑
    $scope.editForm = function() {

        var reg=/\d{4}-\d{2}-\d{2}/;
        if(!reg.test($("#q_editDate").val())){
            //$("#divTime").append('<span style="color:red">日期格式不正确。</span>');
            $scope.timeEditMsg = true;
            return;
        }

        $scope.submitEditMsg();
        $scope.timeEditMsg = false;
    };
	
	var fields = {
		classname:{
					validators: {
						notEmpty: {
							message: '班别名称不能为空'
						}
						
					}
				},
				opentime:{
					validators: {
						notEmpty : {  
							message : '驾驶证初领日期不能为空'
						},
						date: {
							format: 'YYYY-MM-DD',
							message: 'The birthday is not valid'
						}
					}
				},
				amount:{
					validators: {
						notEmpty: {
							message: '报名金额不能为空'
						},
						regexp: {
							regexp: /^[1-9].*$/,
							message: '报名金额必须是正整数'
						}
						
					}
				},
				singleamount:{
					validators: {
						notEmpty: {
							message: '服务金额不能为空'
						},
						regexp: {
							regexp: /^[1-9].*$/,
							message: '单次服务金额必须是正整数'
						}
						
					}
				},
				classdesc:{
					validators: {
						notEmpty: {
							message: '班别描述不能为空'
						}
					}
				}
		
	}
	
	  var initValidate = function() {
    	$('#addForm').bootstrapValidator({
	        fields: fields ,
	        submitHandler: function() {
	      		$scope.addClass();
	    	}
		});
		$('#editForm').bootstrapValidator({
	        fields: fields,
	        submitHandler: function() {
	      		$scope.editClass();
	    	},
		});
    }
	
	
	
}])






