
app.controller('ClassAdd', ['$scope','$filter', function($s,$filter){
    $s.$emit('changeTitle', '新增排班');//修改标题
    $s.allTimesArr = {};//多选已排日期时，获取时段集合
    $s.favorType = 2;//优惠配置：1-返课时；2-返金额
    $s.favorIn=1;
    $s.favorOut="";
    $s.minHours = 1;//最低时间段
    $s.c1 = "";
    $s.c2 = "";
    $s.c1inner = 0;
    $s.c2inner = 0;
    $s.oldSteps = [];//搜集已有排班时间段
    $s.pdateMsg = "";//排班成功后提示语
    var jumpOut=false;//判断交叉时段后，跳出循环

    /*获取指定日期及之后若干天的排班情况和日期数据*/
    $.AJAX({
        type:'get',
        url:config.basePath+"examPlace/class/date",
        data:{
            "placeId":sessionStorage.getItem("schoolPlaceId"),
            "pdate":$filter('date')(new Date(), "yyyy-MM-dd"),
            "days":7
        },
        success:function(data){
            $s.getDaysInfo = JSON.parse(data.result.pageData);
            //console.log($s.getDaysInfo)
            $s.$apply();
        }
    });

    //初始化预设时间段(对象操作)
    $s.timeSteps = [
        {timeFrom:"", timeTo:""}
    ]
    /*时间段的自增加*/
    $s.objStepAdd = function(hour){
        var lastArrItem = $s.timeSteps[$s.timeSteps.length-1]
        if(!(lastArrItem.timeFrom && lastArrItem.timeTo)){
            Popup.alert({type:'msg',style:'width:80%',title:'请先设置好已添加的时间段',header:"提示"}); return false;
        }
        var popFrom = lastArrItem.timeTo;
        var popTo = new Date(new Date(popFrom).getTime()+hour*3600000);
        $s.timeSteps.push({timeFrom:popFrom, timeTo:popTo})
    }
    /*时间段的自减*/
    $s.objStepDel = function(delIndex){
        $s.timeSteps.splice(delIndex,1)
    }

    /*获取某一天排班信息*/
    $s.getArrangeList=function(){
        $.AJAX({
            type:"get",
            url:config.basePath+"examPlace/class",
            data:{
                placeId:sessionStorage.getItem("schoolPlaceId"),
                pdate:$s.theDay
            },
            success:function(data){
                $s.getTheday = JSON.parse(data.result.pageData);
                var timesArrTemp = [];//该数组为当日时间段集合
                for (var i=0;i<$s.getTheday.length;i++){
                    var timeItemTemp = {timeFrom:getTimestamp(new Date($s.getTheday[i].pstart).date("h:i")),timeTo:getTimestamp(new Date($s.getTheday[i].pend).date("h:i"))}
                    timesArrTemp.push(timeItemTemp)
                }
                $s.allTimesArr[$s.theDay]=timesArrTemp;
                //console.log($s.allTimesArr)
                $s.$apply();
            }
        });
    };

    /*把分秒转换成1970年时间戳(因为不同日期的的时间戳并不来自于同一天，无法排序计算较验)*/
    function getTimestamp(hhii){
        var time = "Thu Jan 01 1970 "+hhii+":00 GMT+0800 (中国标准时间)";
        return new Date(time).getTime();
    }
    /*把时间戳转换成分秒*/
    function getTimeHI(timeStamp){
        return new Date(timeStamp).date("h:i");
    }

    /*选择日期,并将所选的已有排班的所有时间段拉出来集合*/
    $s.selectDay = function($event){
        var evTarget = $($event.target);
        //针对已有排班的选择重点较验,实时获取所选日期的所有时段集合，供后面选择时段时较验用
        if(evTarget.parent("div").hasClass("hasClass")){
            if(evTarget.prop('checked')==true){
                $s.theDay = new Date(evTarget.val()).date('y-m-d')
                $s.getArrangeList();
            }
            else{
                $s.theDay = new Date(evTarget.val()).date('y-m-d')
                delete $s.allTimesArr[$s.theDay];
            }
        }
    }

    /*（取当前所有时间段，加上已有排班的某个时间段，两者集合排序，冒泡将每个时段的开始时间戳与其上一个时间段的结束时间戳相比，前者早于后者，则判断为不合法）*/
    function checkTimeArr(objArr,day){
        var tmpArr = objArr.sort(function(a,b){return (a.timeFrom > b.timeFrom)?1:-1});
        console.log(tmpArr)
        if(!jumpOut){
            for(var i=1;i<tmpArr.length;i++){
                if(tmpArr[i].timeFrom<tmpArr[i-1].timeTo){
                    var dayMsg = day ? ",请关注"+day+"日排班" : "";
                    var timeMsg = getTimeHI(tmpArr[i-1].timeFrom)+"~"+getTimeHI(tmpArr[i-1].timeTo)+"和"+getTimeHI(tmpArr[i].timeFrom)+"~"+getTimeHI(tmpArr[i].timeTo);
                    Popup.alert({type:'msg',style:'width:80%',title:timeMsg +'时段冲突'+dayMsg,header:"提示"});
                    jumpOut=true;
                    return false;
                }
            }
        }

    }

    function checkMoney(money,txt){
        //价格不能小于0
        if(money<0){
            Popup.alert({type:'msg',style:'width:80%',title:txt+"必须大于0",header:"提示"});jumpOut = true;return false;
        }
        //价格不能小于0
        if(money>10000){
            Popup.alert({type:'msg',style:'width:80%',title:txt+"不能大于一万",header:"提示"});jumpOut = true;return false;
        }
        //价格不能小于0
        if(!(/^[0-9]*(\.[0-9]{1,2})?$/.test(money)) && money!=""){
            Popup.alert({type:'msg',style:'width:80%',title:txt+"小数点不能超出两位",header:"提示"});jumpOut = true;return false;
        }
    }

    $s.submitClass = function($event){
        jumpOut=false;//判断交叉时段后，跳出循环
        //获取排班日期
        var pdateArr = [];//用于提交AJAX
        var pdateMsgArr = [];//用于成功后文字提示
        $(".date-wrap input.arrange-day:checked").each(function(){
            pdateArr.push(new Date($(this).val()).date("y-m-d"));
            pdateMsgArr.push(new Date($(this).val()).date("d")+"号")
        })
        if(pdateArr.length==0){
            Popup.alert({type:'msg',style:'width:80%',title:'请选择排班日期',header:"提示"}); return false;
        }

        //较验当前排的时间段重复和整段
        var arrTmp1 = [];
        var pstartArr = [];
        for(var i=0;i< $s.timeSteps.length; i++){
            //拿时间戳，用于各种较验
            arrTmp1[i]={timeFrom:(new Date($s.timeSteps[i].timeFrom)).getTime(), timeTo:(new Date($s.timeSteps[i].timeTo)).getTime()};
            //拿时间字符串，用于提交AJAX
            pstartArr.push((new Date($s.timeSteps[i].timeFrom)).date("h:i:s")+"-"+(new Date($s.timeSteps[i].timeTo)).date("h:i:s"))
            //如果结束时间为00:00:00，则将此时间戳推后24小时,向AJAX提交的字符串也要改成24:00:00
            if(new Date($s.timeSteps[i].timeTo).date("h:i:s") == "00:00:00"){
                arrTmp1[i].timeTo=arrTmp1[i].timeTo+24*3600*1000;
                pstartArr[i] = (new Date($s.timeSteps[i].timeFrom)).date("h:i:s")+"-24:00:00";
            }
            //时间段不能为空
            if(isNaN(parseInt(arrTmp1[i].timeFrom))||isNaN(parseInt(arrTmp1[i].timeTo))){
                Popup.alert({type:'msg',style:'width:80%',title:'预约时间段不能为空',header:"提示"}); return false;
            }
            //时间段不能跨日
            if((new Date($s.timeSteps[i].timeFrom).getHours()>=22) && (new Date($s.timeSteps[i].timeTo).getHours()<22) && ((new Date($s.timeSteps[i].timeTo).date("h:i:s")!="00:00:00"))){
                Popup.alert({type:'msg',style:'width:80%',title:'排班不能跨日期',header:"提示"}); return false;
            }
            //结束时间要大于开始时间
            if(arrTmp1[i].timeTo<=arrTmp1[i].timeFrom){
                Popup.alert({type:'msg',style:'width:80%',title:'结束时间要大于开始时间',header:"提示"}); return false;
            }
            //间隔为整小时
            if((parseInt(arrTmp1[i].timeTo)-parseInt(arrTmp1[i].timeFrom))%3600000!=0){
                Popup.alert({type:'msg',style:'width:80%',title:'起止时间段须为整小时',header:"提示"}); return false;
            }
        }
        //console.log(arrTmp1)

        //较验当前排的时间段交叉
        checkTimeArr(arrTmp1)
        if(jumpOut){return false;}

        //把勾选的已有排班，遍历每天，每个时间段，分别加班当前新增的时间段中较验交叉
        for(var day in $s.allTimesArr){
            for(var i=0;i<$s.allTimesArr[day].length;i++){
                var arrTmp2 = arrTmp1.concat($s.allTimesArr[day]);
                //较验合并当前遍历项结合之后的项
                checkTimeArr(arrTmp2,day);
            }
        }

        //价格不能为空且大于0
        if(!$s.outerPrice){
            Popup.alert({type:'msg',style:'width:80%',title:"价格不能为空,且必须大于零",header:"提示"});jumpOut = true;return false;
        }

        //价格格式的通用较验
        checkMoney($s.outerPrice,"价格");
        if(jumpOut){return false;}

        //C1C2车辆数之和大于0，且为非负数
        if(($s.c1 == "") && ($s.c2 == "") && ($s.c1 !=0) && ($s.c2 !=0)){
            Popup.alert({type:'msg',style:'width:80%',title:"C1和C2车辆数必填其一",header:"提示"});return false;
        }
        $s.c1 = $s.c1 || 0;
        $s.c2 = $s.c2 || 0;
        if($s.c1<0 || (Math.ceil($s.c1)-$s.c1)>0){
            Popup.alert({type:'msg',style:'width:80%',title:"C1车辆数应为非负整数",header:"提示"});return false;
        }
        if($s.c2<0 || (Math.ceil($s.c2)-$s.c2)>0){
            Popup.alert({type:'msg',style:'width:80%',title:"C2车辆数应为非负整数",header:"提示"});return false;
        }
        if(($s.c1+$s.c2)<=0){
            Popup.alert({type:'msg',style:'width:80%',title:"C1和C2车辆数之和必须大于零",header:"提示"});
            return false;
        }

        //奖励金额为正确金额，且小于每小时价格
        $s.favorOut = $s.favorOut || 0;
        if($s.favorOut>$s.outerPrice){
            Popup.alert({type:'msg',style:'width:80%',title:"奖励金额不能大于价格",header:"提示"});return false;
        }
        checkMoney($s.favorOut,"奖励");
        if(jumpOut){return false;}

        if(!jumpOut){
            $s.pdateMsg = "您已成功排好"+pdateMsgArr.join(",")+"的排班！";
            $s.pdateLink = pdateArr[0];
            $.AJAX({
                url:config.basePath+"examPlace/class",
                data:{
                    placeId:sessionStorage.getItem("schoolPlaceId"),
                    pdate:pdateArr.join(","),
                    pstart:pstartArr.join(","),
                    outerPrice:$s.outerPrice*100,
                    innerPrice:$s.outerPrice*100,
                    minHours:$s.minHours,//定死为1
                    c1:$s.c1,
                    c2:$s.c2,
                    c1inner:$s.c1inner,//预留C1车辆（通用功能没有内部一说，因此设置为0）
                    c2inner:$s.c1inner,//预留C2车辆（通用功能没有内部一说，因此设置为0）
                    innerExpire:7,//开课前多少天，内部预留失效（通用功能没有内部预留一说，因此让它全部失效）
                    favorType:$s.favorType,//奖励方式为2  1：满时间返时间  2：满时间返金额  3：无
                    favorIn:$s.favorIn,//满一小时
                    favorOut:$s.favorOut*100,//送多少元
                },
                success:function(data){
                    $s.$emit('changeTitle', '排班完成');//修改标题
                    $(".container-success").fadeIn();
                }
            })
        }


    }



}])



