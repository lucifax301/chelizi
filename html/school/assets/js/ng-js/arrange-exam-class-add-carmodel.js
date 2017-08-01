/*angular for 提现*/
var app=angular.module("app",["ngFilter"]);
/*main 控制器*/
app.controller("ArrangeExamClassAdd",["$scope","$filter",function($s,$filter){

    $s.allTimesArr = {};//多选已排日期时，获取时段集合
    $s.favorType = "1";//优惠配置：1-返课时；2-返金额
    $s.favorIn=2;
    $s.favorOut=0.5;
    $s.minHours = 2;//最低时间段
    $s.c1 = 0;
    $s.c2 = 0;
    $s.c1inner = 0;
    $s.c2inner = 0;
    $s.innerPrice=0;


    /*获取指定日期及之后若干天的排班情况和日期数据*/
    $.AJAX({
        type:'get',
        url:config.basePath+"examPlace/class/date",
        data:{
            "placeId":1,
            "pdate":$filter('date')(new Date(), "yyyy-MM-dd"),
            "days":14
        },
        success:function(data){
            $s.getDaysInfo = JSON.parse(data.result.pageData);
            console.log($s.getDaysInfo)
            $s.$apply();
        }
    });
    
    $s.getCars = function(){
        $.AJAX({
            type:'get',
            url:config.basePath+"examPlace/cars",
            data:{
                
            },
            success:function(data){
                $s.c1 = JSON.parse(data.result.c1);
                $s.c2 = JSON.parse(data.result.c2);
                //$s.c1inner = JSON.parse(data.result.c1inner);
                //$s.c2inner = JSON.parse(data.result.c2inner);
                $s.$apply();
            }
        });
    }
    
    $s.getCars();

    /*获取某一天排班信息*/
    $s.getArrangeList=function(){
        $.AJAX({
            type:"get",
            url:config.basePath+"examPlace/class",
            data:{
                placeId:1,
                pdate:$s.theDay
            },
            success:function(data){
                $s.getTheday = JSON.parse(data.result.pageData);
                var timesArrTemp = [];//该数组为当日时间段集合
                for (var i=0;i<$s.getTheday.length;i++){
                    var timeItemTemp = [new Date($s.getTheday[i].pstart).date("hi"),new Date($s.getTheday[i].pend).date("hi")];
                    timesArrTemp.push(timeItemTemp)
                }
                $s.allTimesArr[$s.theDay]=timesArrTemp;
                console.log($s.allTimesArr)
                $s.$apply();
            }
        });
    };


    /*选择日期,并将所选的已有排班的所有时间段拉出来集合*/
    $s.selectDay = function($event){
        //alert($($event.target).prop('checked'))
        var evTarget = $($event.target);
        //针对已有排班的选择重点较验,实时获取所选日期的所有时段集合，供后面选择时段时较验用
        if(!evTarget.parent("li").hasClass("noArrangeClass")){
            if(evTarget.prop('checked')==true){
                $s.theDay = new Date(evTarget.val()).date('y-m-d')
                $s.getArrangeList();
            }
            else{
                $s.theDay = new Date(evTarget.val()).date('y-m-d')
                delete $s.allTimesArr[$s.theDay];
            }
            //因涉及到多日较验，提倡先选好了日期再去选时段，只要是重选了日期，后面的时段要重新设置
            $("#itemAuto0").siblings(".item-auto-item").remove()
            $("#itemAuto0").children("input").val("");
        }
    }

    /*时间段表单*/
    var datePickerOptions = {datepicker:false, format:'H:i', step:30};
    //不允许手动输入，只能从控件中设置值
    $('.date-time').datetimepicker(datePickerOptions).on("keydown",function(){return false;});

    /*勾选优惠配置*/
    $s.showRc = function($event,rctype){
        $s.favorType = rctype;
    }

    /*自增时间段*/
    var itemAddId = 1;
    $s.zItemAdd = function($event){
        $s.autoFormItemTPL = '<div class="item-auto-item z-mb10" id="itemAuto'+itemAddId+'">\
            <input type="text" placeholder="请选择起始时间" datatype="*" errormsg="请选择起始时间" nullmsg="请选择起始时间" class="form-control date-time date-time-from" >\
            <span class="z-ml10 z-mr10">到</span>\
            <input type="text" placeholder="请选择结束时间" datatype="*" errormsg="请选择结束时间" nullmsg="请选择结束时间" class="form-control date-time date-time-to" >\
            <span class="item-auto-label item-auto-del"><span class="z-ico-del">X</span></span>\
            </div>';
        //点增加按钮时动态插入的HTML内容
        $($event.target).parent(".item-auto-btn").before($s.autoFormItemTPL);
        //更新时间选择器
        $('.date-time').datetimepicker(datePickerOptions);
        //如果之前合法地有一项起始时间段都有值，则此项起始时间默认从上一项推后2小时
        var thisTempObj = $("#itemAuto"+itemAddId);
        var prevTempFrom = thisTempObj.prev(".item-auto-item").children(".date-time-from").val();
        var prevTempTo = thisTempObj.prev(".item-auto-item").children(".date-time-to").val();
        if(prevTempFrom && prevTempTo){
            thisTempObj.children(".date-time-from").val(prevTempTo);
            autoNextTime(thisTempObj.children(".date-time-from"));
        }
        itemAddId++
    }

    /*自减时间段*/
    $(".item-auto-exam").on("click",".z-ico-del",function(){
        $(this).parent(".item-auto-label").parent(".item-auto-item").slideUp("normal").remove();
    })

    /*时间段失去焦点时，自动较验并填写下一项*/
    $(".item-auto-exam").on("blur",".date-time",function(){
        //不管是起始段还是结束时段，先较验一遍
        checkTimepicker($(this));
        autoNextTime($(this));
    })
    /*根据起始值自动填写结束值的函数*/
    function autoNextTime(thisFormObj){
        var thisHourVal = parseInt(thisFormObj.val().substring(0,2));
        var thisMinVal = thisFormObj.val().substr(3);
        var thisNumVal = parseInt(thisFormObj.val().replace(/:/,""));
        //当选择了22点之后的时段，则必定跨天了，非法
        if(thisFormObj.hasClass("date-time-from")&&thisNumVal>2200){
            Layer.alert({width:400,height:160,title:"2小时后的结束时段不能跨天哦",header:"操作提示"});
            thisFormObj.val("");
            return false;
        }
        //如果是起始段，自动填充下一时段的值（后推二小时），并较验该值
        if((thisHourVal>=0)&&thisFormObj.hasClass("date-time-from")){
            var nextHourVal = (thisHourVal==22)?00:(thisHourVal+2);
            nextHourVal = (nextHourVal<10)?"0"+nextHourVal.toString():nextHourVal.toString();
            thisFormObj.parent(".item-auto-item").children(".date-time-to").val(nextHourVal+":"+thisMinVal).removeClass("Validform_error");
            checkTimepicker(thisFormObj.parent(".item-auto-item").children(".date-time-to"));
        }
    }
    /*较验时间段的合法性函数*/
    function checkTimepicker(curObj){
        //当被较验项是结束时间段且为00:00时，自动变为24:00
        var curTime = ((curObj.hasClass("date-time-to"))&&(curObj.val()=="00:00"))?"24:00":curObj.val();
        console.log(curTime);
        var curTimeData = "";
        if(curTime!=""){
            curTimeData = parseInt(curTime.replace(":",""));
            //判断开始时间段应该小于结束时间段
            var anotherTime = curObj.siblings(".date-time").val();
            if(anotherTime!=""){
                var anotherTimeData = parseInt(anotherTime.replace(":",""))
                //当前项是起始项且起始项大于等于结束项  ||  当前项是结束项且起始项大于等于结束项
                if(((curObj.hasClass("date-time-from"))&&(curTimeData >= anotherTimeData))||((curObj.hasClass("date-time-to"))&&(curTimeData <= anotherTimeData)&&(parseInt(curTimeData)!=0000))){

                    Layer.alert({width:400,height:160,title:"起始时间必须早于结束时间!",header:"操作提示"});
                    curObj.parent(".item-auto-item").children("input").val("");
                    return false;
                }
                //if((anotherTimeData - curTimeData)%($s.minHours*100)!=0){//最初设计的起止时差为最小时段倍数，因产品逻辑问题，改为起止差须等于最小时段
                if(Math.abs(anotherTimeData - curTimeData)!=($s.minHours*100)){
                    console.log(anotherTimeData +"-"+ curTimeData)
                    Layer.alert({width:400,height:160,title:"预约时间段限制为"+$s.minHours+"小时",header:"操作提示"});
                    curObj.parent(".item-auto-item").children("input").val("");return;
                }
            }
            //将时间转成数字，先针对当前新增的时段，逐条判断大小，在区间中则表示有交叉(为空的不比较，因为后面还有一关较验为空项)
            $(".item-auto-item").each(function(){
                if($(this).attr("id")!=curObj.parent(".item-auto-item").attr("id")){
                    var fromTime = $(this).children(".date-time-from").val();
                    var toTime = $(this).children(".date-time-to").val();
                    toTime = (toTime=="00:00") ? "24:00" : toTime;
                    if(fromTime && toTime){
                        var fromTimeData = parseInt(fromTime.replace(":",""));
                        var toTimeData = parseInt(toTime.replace(":",""));
                        //jiaocha1：所填值位于当前遍历项的起始值之间，判为交叉
                        var jiaocha1= (curTimeData > fromTimeData)&&(curTimeData < toTimeData);
                        //jiaocha2：所填值为结束时间段，并且它的起始值小于当前遍历项的起始值，并且它的结束值大于当前遍历项的结束值，判为包含，类似交叉
                        var jiaocha2 = (curObj.hasClass("date-time-to"))&&(parseInt(curObj.siblings(".date-time-from").val().replace(":",""))<fromTimeData)&&(parseInt(curTime.replace(":",""))>toTimeData)
                        //jiaocha3:当前值为起始值，且该起始值等于当前遍历项的起始值
                        var jiaocha3 = (curObj.hasClass("date-time-from"))&&(curTime==fromTime)
                        //jiaocha4:当前值为结束值，且该结束值等于当前遍历项的结束值
                        var jiaocha4 = (curObj.hasClass("date-time-to"))&&(curTime==toTime)
                        if(jiaocha1||jiaocha2||jiaocha3||jiaocha4){
                            console.log(curTimeData+"+"+fromTimeData+"+"+toTimeData)
                            Layer.alert({width:400,height:160,title:"不能存在交叉时间段!",header:"操作提示"});
                            curObj.parent(".item-auto-item").children("input").val("");
                            return false;
                        }

                    }
                }
            })
            //针对勾选的已有排班的日期，全部遍历过一遍
            for(var item in $s.allTimesArr){
                var itemArr = $s.allTimesArr[item];
                console.log($s.allTimesArr[item]);
                for(var i=0;i<itemArr.length;i++){
                    var fromTime = itemArr[i][0];
                    var toTime = itemArr[i][1];
                    toTime = (toTime=="0000") ? "2400" : toTime;

                    var fromTimeData = parseInt(fromTime);
                    var toTimeData = parseInt(toTime);
                    //console.log(curTimeData+","+fromTimeData+","+toTimeData);
                    //jiaocha1：所填值位于当前遍历项的起始值之间，判为交叉
                    var jiaocha1= (curTimeData > fromTimeData)&&(curTimeData < toTimeData);
                    //jiaocha2：所填值为结束时间段，并且它的起始值小于当前遍历项的起始值，并且它的结束值大于当前遍历项的结束值，判为包含，类似交叉
                    var jiaocha2 = (curObj.hasClass("date-time-to"))&&(parseInt(curObj.siblings(".date-time-from").val().replace(":",""))<fromTimeData)&&(parseInt(curTime.replace(":",""))>toTimeData)
                    //jiaocha3:当前值为起始值，且该起始值等于当前遍历项的起始值
                    var jiaocha3 = (curObj.hasClass("date-time-from"))&&(curTimeData==fromTimeData)
                    //jiaocha4:当前值为结束值，且该结束值等于当前遍历项的结束值
                    var jiaocha4 = (curObj.hasClass("date-time-to"))&&(curTimeData==toTimeData)
                    if(jiaocha1||jiaocha2||jiaocha3||jiaocha4){
                        //console.log(curTimeData+"+"+fromTimeData+"+"+toTimeData)
                        Layer.alert({width:400,height:160,title:"时段交叉!请关注"+item+"日排班",header:"操作提示"});
                        curObj.parent(".item-auto-item").children("input").val("");
                        return false;
                    }
                }
            }

        }
    }

    //表单验证及提交
    $("#addForm").Validform({
        tiptype:3,
        showAllError:true,
        btnSubmit:"#btn_submit",
        callback:function(){
            $s.c1 = $s.c1 || 0;
            $s.c2 = $s.c2 || 0;
            $s.c1inner = $s.c1inner || 0;
            $s.c2inner = $s.c2inner || 0;
            $s.innerExpire = $s.innerExpire || 0;
            /*获取及较验选定的多个预约日期*/
            var arrangeDaysArr=[];//用于计算距今期限大于预留期限，格式为时间戳
            var arrangeDaysArrSubmit=[];//用于提交AJAX，格式为2016-09-28,2016-09-29
            $(".z-arrange-days .arrange-day").each(function(){
                if($(this).prop("checked")==true){
                    arrangeDaysArr.push($(this).val());
                    arrangeDaysArrSubmit.push($filter('date')(new Date($(this).val()), "yyyy-MM-dd"));
                }
            })
            if(arrangeDaysArr.length==0){
                Layer.alert({width:400,height:160,title:"请至少点选一个预约日期!",header:"操作提示"});return false;
            }
            /*所选的最早一天距今日要大于等于预留日期  | 获取所选的第一天时间戳 减去当前时间戳的回归零点，得到距离天数*/
            var firstDay = new Date(arrangeDaysArr[0]).getTime();
            var nowDay = new Date((new Date().date("y-m-d")+" 00:00:00").replace(/-/g,"/")).getTime();

            if((firstDay - nowDay)<$s.innerExpire*24*60*60*1000){
                Layer.alert({width:400,height:160,title:"预留日期不能大于排入日期距离今天的天数!",header:"操作提示"});return false;
            }

            /*获取及较验选定的多个时间段*/
            var arrangeTimeArr = [];
            $(".item-auto-item").each(function(){
                var timeFrom = $(this).children(".date-time-from").val()+":00";
                var timeTo = $(this).children(".date-time-to").val()+":00";
                timeTo = (timeTo=="00:00:00") ? "24:00:00" : timeTo;
                arrangeTimeArr.push(timeFrom+"-"+timeTo);
            })
            console.log(arrangeTimeArr);

            /*获取C1C2车数之和必须大于0,且预留数量不能大于总数量（已知两者皆为非负整数，这里只做加判断）*/
            if(($s.c1 + $s.c2)==0){
                Layer.alert({width:400,height:160,title:"C1数量和C2数量之和必须大于0!",header:"操作提示"});return false;
            }
            if($s.c1<$s.c1inner){
                Layer.alert({width:400,height:160,title:"预留C1数量不能大于C1总数量!",header:"操作提示"});return false;
            }
            if($s.c2<$s.c2inner){
                Layer.alert({width:400,height:160,title:"预留C2数量不能大于C2总数量!",header:"操作提示"});return false;
            }

            var submitJson={
                placeId:1,
                pdate:arrangeDaysArrSubmit.join(","),
                pstart:arrangeTimeArr.join(","),
                outerPrice:$s.outerPrice*100,
                innerPrice:$s.innerPrice*100,
                minHours:$s.minHours,
                c1:$s.c1,
                c2:$s.c2,
                c1inner:$s.c1inner,
                c2inner:$s.c2inner,
                innerExpire:$s.innerExpire,
                favorType:$s.favorType,
                favorIn:$s.favorIn,
                favorOut:$s.favorOut*100,
            }
            $.AJAX({
                url:config.basePath+"examPlace/class",
                type:"post",
                data:submitJson,
                success:function(data){
                    Layer.miss({width:250,height:90,title:"操作成功！",closeMask:true});
                    setTimeout(function(){window.location.href="arrange-exam-class.html"},1500)
                }
            })
            return false;
        }
    })

}]);
