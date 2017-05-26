
app.controller("RcConsumption",["$scope","$filter",function($s,$filter){

    /*模拟数据*/
    $s.getDataList = function(){
        var data = {
            code:0,msg:"请求正确",
            result:{
                list: [
                    {water:1232123123132,school:"深港",cTime:"2016-10-25 12:12:32",coachAccount:"muyu",coachName:"木鱼",cType:"预约广仁考场",orderId:556156231321565132,pay:200.00,status:1,info:"这个订单完成得很愉快"},
                    {water:9988774532111,school:"广仁",cTime:"2016-10-26 16:22:56",coachAccount:"muyu",coachName:"木鱼",cType:"预约西丽考场",orderId:611231564561532135,pay:180.00,status:1,info:"这个订单未完成"}
                ],
                total: 2,
                defaultPage:1
            }
        }
        $s.datas = data.result.list;
        $s.total = data.result.total;
        $s.defaultPage = data.result.defaultPage;
    }
    $s.getDataList();


    $s.authDel=function(id){
        Layer.confirm({width:300,height:160,title:"确认取消该教练授权？",header:"取消授权"},function(){
            $.AJAX({
                url:config.basePath+"xxx/xxxx",
                data: {coachId:id},
                success:function(data){
                    Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                    $s.getDataList();
                }
            });
        });
    }

    /**************修改限额***********/
    //弹窗
    $s.setLimitLoad = function(objItem){
        $s.setData = objItem;
        if($s.setData.limit>0){$s.setData.limitType=1}
        else{$s.setData.limitType=0}
    }
    //提交订单
    $s.setLimit = function(invild){

    }

    //弹窗隐藏时更新数据
    $(function () { $('.set-limit-wrap').on('hide.bs.modal', function () {
        $s.getDataList();
    })})

    /**************取消授权***********/
    $s.authDel=function(coachId){
        //$('.btn-auth-del').unbind('click').click(function () {
        //    console.log(coachId)
        //    $.AJAX({
        //        url:config.basePath+"xxx/xxxx",
        //        data:{coachId:coachId},
        //        success:function(data){
        //            Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
        //            // $('#editCoach').modal('hide');
        //            $s.$apply();
        //            $s.getDataList();
        //        }
        //    });
        //})
        Layer.confirm({width:300,height:160,title:"确认删除此班级信息？",header:"删除班级"},function(){
            $.AJAX({
                url:config.basePath+"xxx/xxxxx",
                data:{coachId:coachId},
                success:function(data){
                    Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                    // $('#editCoach').modal('hide');
                    $scope.getDataList();
                }
            });
        });
    }






}])





