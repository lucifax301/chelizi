app.controller("CoachIndex",["$scope","$filter", "$http", function($scope,$filter,$http) {
	//编辑教练
   $scope.toEditHref = function(id){
	   window.location.href = $scope.NURL+'/coach/updateCoach?coachid='+id;
   }
   //查看学员
   $scope.toStudentOpentHref = function(id){
	   window.location.href = $scope.NURL+'/coach/showStuOfCoach?coachid='+id;
   }
   //分配学员
   $scope.toDistributionStuHref = function(id){
	   window.location.href = $scope.NURL+'/coach/distributionStu?coachid='+id;
   }
   
   $scope.toAddHref = function(id){
	   window.location.href = $scope.NURL+'/coach/addCoach';
   }
    $scope.sexDatas = [{id: 1,value: '男'},{id: 2,value: '女'}];
    $scope.statusDatas = [{id: 0,value: '在职'},{id: 1,value: '离职'}];
    $scope.carTypeDatas = perdritype;
    $scope.baseImgurl = config.imagePath;
    $scope.uploadbtn = true;
    $scope.pageSize = 10; 
    $scope.defaultPage = getUrl('page') || 1; 

    if(getUrl('search')) {
        $scope.condition = getUrl('search');
    }

    window.onhashchange = function() {
        $scope.defaultPage= getUrl('page') || 1; 
    }

    $scope.getkey = function(e){
        var keycode = window.event?e.keyCode:e.which;
        if(keycode==13){
            $scope.searchList();
        }
    }

    $scope.getDataList = function() {
        $.AJAX({
			type : "POST",
            data : {
                coachId : $scope.coachId,
                condition : $scope.condition,
                pageNo : $scope.defaultPage,
                pageSize : $scope.pageSize
            },
            url:config.basePath + "coach/list",
            success:function(data){
                $scope.datas = data.result.list
                $scope.total = data.result.total;
                $scope.$apply();
                new Page({
                    parent:$("#copot-page"),
                    nowPage:$scope.defaultPage,
                    pageSize:$scope.pageSize,
                    totalCount:$scope.total,
                    search:$scope.coachId
                });
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



    $scope.studentOpent=function(coachId){
        $("#studentList").modal("toggle");
        $scope.getList = function(page){
            if(page) {
                var nowpage = page;
            }else{
                var nowpage = 1;
            }
            var json = {coachId:coachId,pageNo :nowpage,pageSize : 10};
            $.AJAX({
                method: "GET",
                url:config.basePath+"student/stuListofCoach",
                data:json,
                success:function(data){
                    $scope.studatas = data.result.list;

                    $scope.stutotal = data.result.total;
                    $scope.$apply();
                    new Page({
                        type:2,
                        parent:$("#copot-page2"),
                        nowPage:nowpage,
                        pageSize:10,
                        totalCount:$scope.stutotal,
                        callback:$scope.getList
                    }); 

                }
            });
        }
        $scope.getList();
    }

    $scope.trainAreaOpen=function(data){
        $("#trainAreaList").modal("toggle");
        $scope.coachIdHidden = data.coachId;
        $scope.getAreaList = function(page){
            if(page) {
                var nowpage = page;
            }else{
                var nowpage = 1;
            }
            var json = {pageNo :nowpage,pageSize : 10};
            $.AJAX({
                method: "GET",
                url:config.basePath+"/trainarea/list",
                data:json,
                success:function(data){
                    $scope.areadatas = data.result.list;

                    $scope.areatotal = data.result.total;
                    $scope.$apply();
                    new Page({
                        type:2,
                        parent:$("#copot-page3"),
                        nowPage:nowpage,
                        pageSize:10,
                        totalCount:$scope.areatotal,
                        callback:$scope.getAreaList
                    }); //分页请求完毕

                }
            });
        }
        $scope.getAreaList();
    }


    //分配教练
    $scope.allocationArea = function(instid){
        var JSON = {
            coachId:$scope.coachIdHidden,
            trainareaid:instid
        };
        Layer.confirm({width:300,height:160,title:"确认分配此教学区域信息？",header:"分配教学区域"},function(){
            $.AJAX({
                url:config.basePath+"coachCar/updateCoachCarById",
                data:JSON,
                success:function(data){
                    $("#trainAreaList").modal('hide');
                    Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                    $scope.editData = {};
                    $scope.getDataList();
                }
            });
        });
    }


    $scope.coachDelete = function(coachid){
        Layer.confirm({width:300,height:160,title:"确认注销此教练信息？",header:"注销教练"},function(){
            $.AJAX({
                url:config.basePath + "coach/updateCoachById?coachid=" + coachid,
                data: {
                    employstatus : 1
                },
                success:function(data){
                    Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                    $scope.getDataList();
                }
            });
        });
    }

    $scope.upload = function() {
        if ($("#uploadfile").val() == "") {
            Layer.miss({width:250,height:90,title:"请选择上传文件!",closeMask:true});
            return;
        }
        var form = new FormData(document.getElementById("f1"));
        $.ajax({
            url:config.basePath + "coach/uploadExcel",
            type:"post",
            data:form,
			xhrFields: {
                withCredentials: true
            },
			crossDomain: true,
            processData:false,
            xhrFields:{withCredentials: true},
            crossDomain: true,
            contentType:false,
            success:function(data){
                $scope.datas = data.result.data;
                $scope.filename = data.result.filename;
                $scope.$apply();
                new Page({
                    parent:$("#copot-page"),
                    nowPage:$scope.defaultPage,
                    pageSize:data.result.data.length,
                    totalCount:data.result.data.length,
                });

            }
        });
    }

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

    $scope.uploadfile = function() {
        var html='<div id="createFileHtml" class="hidden"><form method="post" enctype="multipart/form-data" name="form1" id="f1"><input type="file" id="apkFile" name="file"/></form></div>';
        if(!$('#createFileHtml').length){
            $('body').append(html);
        };
        $('#apkFile').click();
        $('#apkFile').change(function(){
        Layer.confirm({width:300,height:160,title:"确认上传吗？",header:"删除"},function(){
            var form = new FormData(document.getElementById("f1"));
            $.ajax({
                url:config.basePath+"coach/uploadExcel",
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
                    $scope.uploadbtn = false;
                    new Page({
                        parent:$("#copot-page"),
                        nowPage:$scope.defaultPage,
                        pageSize:data.result.data.length,
                        totalCount:data.result.data.length
                    }); 
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
            url:config.basePath+"coach/importExcel",
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
        var url = config.basePath+"coach/download";
        window.open(url);
    }

}])






