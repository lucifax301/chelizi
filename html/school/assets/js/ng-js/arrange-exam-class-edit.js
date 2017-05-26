/*angular for 提现*/
var app=angular.module("app",["ngFilter"]);
/*main 控制器*/
app.controller("ArrangeExamClassEdit",["$scope","$filter",function($s,$filter){
    $s.classId = getQueryString("classId");
    //$s.favorType = "1";//优惠配置：1-返课时；2-返金额
    //$s.favorIn=2;
    //$s.favorOut=0.5;
    //$s.minHours = 2;//最低时间

    /*获取当前班别详情*/
    $.AJAX({
        type:"get",
        url:config.basePath+"examPlace/class/one",
        data:{classId:$s.classId},
        success:function(data){
            $s.editData = getListData(data);
            $s.editData.innerPrice = $s.editData.innerPrice/100;
            $s.editData.outerPrice = $s.editData.outerPrice/100;
            $s.editData.favorOut = $s.editData.favorOut/100;
            $s.dayInfo = new Date($s.editData.pstart).date("y-m-d");
            $s.timeInfo = new Date($s.editData.pstart).date("h:i")+"-"+new Date($s.editData.pend).date("h:i")
            console.log($s.editData);
            $s.$apply();
        }
    })

    /*勾选优惠配置*/
    $s.showRc = function($event,rctype){
        $s.favorType = rctype;
    }
    //表单验证及提交
    $("#addForm").Validform({
        tiptype:3,
        showAllError:true,
        btnSubmit:"#btn_submit",
        callback:function(){
            $s.editData.c1 = $s.editData.c1 || 0;
            $s.editData.c2 = $s.editData.c2 || 0;
            $s.editData.c1inner = $s.editData.c1inner || 0;
            $s.editData.c2inner = $s.editData.c2inner || 0;
            $s.editData.innerExpire = $s.editData.innerExpire || 0;
            console.log($s.editData)
            /*获取C1C2车数之和必须大于0,且预留数量不能大于总数量（已知两者皆为非负整数，这里只做加判断）*/
            if(($s.editData.c1 + $s.editData.c2)==0){
                Layer.alert({width:400,height:160,title:"C1数量和C2数量之和必须大于0!",header:"操作提示"});return false;
            }
            if($s.editData.c1<$s.editData.c1inner){
                Layer.alert({width:400,height:160,title:"预留C1数量不能大于C1总数量!",header:"操作提示"});return false;
            }
            if($s.editData.c2<$s.editData.c2inner){
                Layer.alert({width:400,height:160,title:"预留C2数量不能大于C2总数量!",header:"操作提示"});return false;
            }

            /*所编辑的日期距今日要大于等于预留日期  | 获取编辑项的时间戳 减去当前时间戳的回归零点，得到距离天数*/
            var editDay = new Date($s.dayInfo+" 00:00:00").getTime();
            var nowDay = new Date((new Date().date("y-m-d")+" 00:00:00").replace(/-/g,"/")).getTime();
            if((editDay - nowDay)<$s.editData.innerExpire*24*60*60*1000){
                Layer.alert({width:400,height:160,title:"预留日期不能大于排入日期距离今天的天数!",header:"操作提示"});return false;
            }

            var submitJson={
                classId:$s.classId,
                outerPrice:$s.editData.outerPrice*100,
                innerPrice:$s.editData.innerPrice*100,
                c1:$s.editData.c1,
                c2:$s.editData.c2,
                c1inner:$s.editData.c1inner,
                c2inner:$s.editData.c2inner,
                innerExpire:$s.editData.innerExpire,
                favorType:$s.editData.favorType,
                favorIn:$s.editData.favorIn,
                favorOut:$s.editData.favorOut*100,
            }
            console.log(submitJson)
            $.AJAX({
                url:config.basePath+"examPlace/class/update",
                type:"post",
                data:submitJson,
                success:function(data){
                    console.log(data);
                    Layer.miss({width:250,height:90,title:"操作成功！",closeMask:true});
                    setTimeout(function(){window.location.href="arrange-exam-class.html?pdate="+new Date($s.editData.pstart).date("y-m-d")},1500)
                }
            })
            return false;
        }
    })
}]);