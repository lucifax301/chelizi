/**
 * Created by Administrator on 2016/4/7.
 */
var app = angular.module("app",["ngFilter"]);
app.controller("importBookExamController",["$scope","$filter",function($scope,$filter) {
    win.hideLoading();
    $scope.step = 0;
    $scope.uuid = "";
    //$(".PopLayer").on("click",".PopLayer-bottom-OK",function(){alert(2334);$(".PopLayer").remove();})
    /*导入文件*/
    $scope.importFile = function() {
        cerateFileFormData({
            url : config.basePath + "school/enroll/upload?type=1",
            callback : function(data) {
                console.log(data);
                Layer.miss({width:250,height:90,title:"正在处理请稍候",time:3600000});
                $scope.uuid = data.result.pageData;
                var uuidforData;
                var counti;//定时器查询次数
                clearInterval(uuidforData);
                counti = 600;//半秒一次，最多轮查300秒，600次
				//改成2秒拿一次
                uuidforData = setInterval(function(){
                    counti--;
                    if(!counti){
                        clearInterval(uuidforData);
                        $(".PopLayer-miss").remove();$(".PopLayer-mask").remove();
                        Layer.alert({width:400,height:150,type:"error",title:"读取失败，请检查上传资源"});
                        return false;
                    }
                    $.AJAX({
                        url: config.basePath + "school/enroll/upload/uuid",
                        type:"get",
                        data:{uuid:$scope.uuid},
                        success:function(data){
                            var DATA = getListData(data);
                            if(DATA){
                                clearInterval(uuidforData);
                                //alert("拿到数据");
                                $(".PopLayer").remove();
                                $scope.total = DATA.total;
                                $scope.valid = DATA.valid;
                                $scope.headDesc = DATA.headDesc;
                                $scope.uuid = DATA.uuid;
                                $scope.tableNo = DATA.tableNo;
                                $scope.datas = DATA.dataList;
                                $scope.step = 1;
                                $scope.$apply();
                            }
                        }
                    })
                },1000)
            }
        });
    };

    /*确认导入 */
    $scope.comfirmImport = function() {
        Layer.confirm({width:320,height:160,title:"是否确认导入?",header:"确认导入"},function(){
            $.AJAX({
                url : config.basePath+"school/enroll/update/state",
                data : {
                    uuid : $scope.uuid,
                    state : 1
                },
                success:function(data) {
                    var DATA = getListData(data);
                    $scope.step = 2;
                    $scope.$apply();
                    Layer.miss({width:200,height:100,title:"操作成功",closeMask:true});
                }
            });
        });
    }

    /*取消导入*/
    $scope.cancelImport = function() {
        Layer.confirm({width:320,height:160,title:"是否放弃导入?",header:"取消导入"},function(){
            $.AJAX({
                url : config.basePath+"school/enroll/update/state",
                data : {
                    uuid : $scope.uuid,
                    state : 2
                },
                success:function(data){
                    $scope.step = 0;
                    $scope.$apply();
                    Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                }
            });
        });
    }

}]);