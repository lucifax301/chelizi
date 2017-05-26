/*angular for 提现*/
var app=angular.module("app",["ngFilter"]);

/*main 控制器*/
app.controller("CoachOpenDetails",["$scope",function($s){
    $s.coachId = getQueryString("coachId");

//模拟数据-获取本教练认证详情
//    $s.coachData={
//        "auditState":1,
//        "auditMark":"这个车太丑了，不能上道！",
//        "dryLicence1":"image/png/kaoshiwancheng@3x.png",
//        "dryLicence2":"image/png/liantong_banner3x.png",
//        "carInfo":[
//            {
//                "carNum":"粤B1234学",
//                "carType":"C2",
//                "carLicence1":"image/png/quxianxingshi@3x.png",
//                "carLicence2":"image/png/xianxuehoufu2@3x.png",
//                "carView":"image/png/xydlbanener@3x.jpg",
//            },
//            {
//                "carNum":"粤B5678学",
//                "carType":"C1",
//                "carLicence1":"p11246.jpg",
//                "carLicence2":"p11267.jpg",
//                "carView":"p13478.jpg",
//            }
//        ]
//    }
//    $s.coachView=$s.coachData;

    $s.getDataView = function(){
        $.AJAX({
            url:config.basePath+"coach/regCoachDetail",
            type:"get",
            data:{coachId:$s.coachId},
            success:function(data){
                $s.dataView=JSON.parse(data.result.pageData)
                console.log($s.dataView)
                $s.$apply();
            }
        })
    }
    $s.getDataView();

    /*----------审核通过------------*/
    $s.setPass = function(cityname){
        if(!cityname){
            Layer.alert({width:350,height:160,title:"该教练尚未设置城市！",header:"操作提示"});
            return false;
        }
        Layer.confirm({width:400,height:200,title:"请确定该教练信息无误，审核通过后<br>该教练可以接单，请确认是否通过该<br>教练的认证信息。",header:"教练审核通过"},function(){
            $.AJAX({
                url:config.basePath+"coach/regCoachCheck",
                data:{coachId:$s.coachId,checkDrState:2,checkDrRemark:""},
                success:function(data){
                    Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                    $s.getDataView();
                    setTimeout(function(){window.location.href="coach-open.html"},1000);
                }
            })
        })
    }

    /*----------审核不通过------------*/
    $s.setNG=function(){
        /*确认拒绝*/
        Layer.confirmNotByTextAlert({
            header:"审核不通过",
            width:400,
            height:260,
            botByText:'请填写审核不通过的原因',
            title:"请填写审核不通过的原因",
            errmsg:'请填写审核不通过的原因！',
        },function(notByText){
            $.AJAX({
                url:config.basePath+"coach/regCoachCheck",
                data:{
                    "coachId":$s.coachId,
                    "checkDrState":3,
                    "checkDrRemark":notByText,
                },
                success:function(data){
                    /*提示成功信息*/
                    Layer.miss({width:250,height:90,title:"操作成功！",closeMask:true});
                    /*更新列表*/
                    $s.getDataView();
                    setTimeout(function(){window.location.href="coach-open.html"},1000);
                }
            });
        });
    }


}]);