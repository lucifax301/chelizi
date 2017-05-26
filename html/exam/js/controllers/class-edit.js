
app.controller('ClassEdit', ['$scope','$filter', function($s,$filter){
    $s.$emit('changeTitle', '编辑排班');//修改标题
    $s.theDay = new Date().date("y-m-d");//默认显示当天的排班信息
    $s.classId = getUrl("classId");

    /*获取指定班别详情*/
    $.AJAX({
        type:"get",
        url:config.basePath+"examPlace/class/one",
        data:{classId:$s.classId,placeId:sessionStorage.getItem("schoolPlaceId")},
        success:function(data){
            $s.editData = JSON.parse(data.result.pageData);
            console.log($s.editData);
            $s.dayInfo = new Date($s.editData.pstart).date("y-m-d");
            $s.timeInfo = new Date($s.editData.pstart).date("h:i")+"-"+new Date($s.editData.pend).date("h:i")
            console.log($s.editData)
            $s.$apply();
        }
    })

    /*提交编辑*/
    $s.submitClass = function($event){
        $($event.target).addClass("pointer-none");
        if(!$s.editData.outerPrice || $s.editData.outerPrice<=0){
            Popup.alert({type:'msg',style:'width:80%',title:'每小时价格输入有误',header:"提示"}); return false;
        }
        ///*获取C1C2车数之和必须大于0,且预留数量不能大于总数量（已知两者皆为非负整数，这里只做加判断）*/
        //if(($s.editData.c1 + $s.editData.c2)==0 || $s.editData.c1<0 || $s.editData.c2<0){
        //    Popup.alert({type:'msg',style:'width:80%',title:'车辆数输入有误',header:"提示"}); return false;
        //}
        //C1C2车辆数之和大于0，且为非负数
        if(($s.editData.c1 == "") && ($s.editData.c2 == "") && ($s.editData.c1 !=0) && ($s.editData.c2 !=0)){
            Popup.alert({type:'msg',style:'width:80%',title:"C1和C2车辆数必填其一",header:"提示"});return false;
        }
        $s.editData.c1 = $s.editData.c1 || 0;
        $s.editData.c2 = $s.editData.c2 || 0;
        if($s.editData.c1<0 || (Math.ceil($s.editData.c1)-$s.editData.c1)>0){
            Popup.alert({type:'msg',style:'width:80%',title:"C1车辆数应为非负整数",header:"提示"});return false;
        }
        if($s.editData.c2<0 || (Math.ceil($s.editData.c2)-$s.editData.c2)>0){
            Popup.alert({type:'msg',style:'width:80%',title:"C2车辆数应为非负整数",header:"提示"});return false;
        }
        if(($s.editData.c1+$s.editData.c2)<=0){
            Popup.alert({type:'msg',style:'width:80%',title:"C1和C2车辆数之和必须大于零",header:"提示"});
            return false;
        }
        if($s.editData.outerPrice<$s.editData.favorOut){
            Popup.alert({type:'msg',style:'width:80%',title:'奖励金额不能大于每小时价格',header:"提示"}); return false;
        }
        $.AJAX({
            url:config.basePath+"examPlace/class/update",
            type:"post",
            data:{
                classId:$s.classId,
                placeId:sessionStorage.getItem("schoolPlaceId"),
                pstart:new Date($s.editData.pstart).date("y-m-d h:i:s"),
                outerPrice:$s.editData.outerPrice,
                innerPrice:$s.editData.innerPrice,//该参数没有被编辑过，所以不用*100
                c1:$s.editData.c1||0,
                c2:$s.editData.c2||0,
                c1inner:$s.editData.c1inner,
                c2inner:$s.editData.c2inner,
                innerExpire:$s.editData.innerExpire,
                favorType:$s.editData.favorType,
                favorIn:$s.editData.favorIn,
                favorOut:$s.editData.favorOut
            },
            success:function(data){
                console.log(data);
                Popup.miss({haveHeader:true,title:'更新成功'});
                setTimeout(function(){window.location.href="#/class-view"},1500)
            }
        })
    }

    //再次编辑时恢复按钮
    $("input").focus(function(){$(".button-submit").removeClass("pointer-none")})







}])



