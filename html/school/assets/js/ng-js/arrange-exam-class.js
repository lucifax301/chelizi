/*angular for 提现*/
var app=angular.module("app",["ngFilter"]);

/*main 控制器*/
app.controller("ArrangeExamClass",["$scope","$filter",function($s,$filter){
    $s.theDay = getQueryString("pdate") ? getQueryString("pdate"): new Date().date("y-m-d");//默认显示当天的排班信息
    $s.theDayTime = new Date().getTime();
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.pageSize=10;    //每页显示-显示条数
	$s.carType="";           //所学车型
	$s.carLevel="";   //汽车等级
	$s.carNo="";              //高级查询

    /*获取指定日期及之后若干天的排班情况和日期数据*/
    $s.getDays = function(){
        $.AJAX({
            type:'get',
            url:config.basePath+"examPlace/class/date",
            data:{
                "type":0,
                "pdate":new Date().date("y-m-d"),
                "days":14
            },
            success:function(data){
                $s.getDaysInfo = JSON.parse(data.result.pageData);
                $s.$apply();
            }
        });
    }
    $s.getDays();

    clearInterval(checkToday);
    var checkToday = setInterval(function(){
        if($s.getDaysInfo){
            clearInterval(checkToday);
            /*当HASH指定了日期，则默认指定日期高亮*/
            if(getQueryString("pdate")){
               $(".z-select-days div").each(function(){
                    if($(this).text().substr(0,5) == getQueryString("pdate").substr(5)){
                        $(this).addClass("active")
                    }
               })
            /*默认为第一天的高亮*/
            }else{
                $(".z-select-days div:first-child").addClass("active");
            }

        }
    },500)

    /*获取某一天排班信息，默认为当日*/
    $s.getArrangeList=function(){
        $.AJAX({
            type:"get",
            url:config.basePath+"examPlace/class",
            data:{
            	type:0,
                pdate:$s.theDay
            },
            success:function(data){
                $s.getTheday = JSON.parse(data.result.pageData);
                if($s.getTheday){
                    $s.c1All = 0; $s.c1innerAll = 0; $s.c1outerAll = 0; $s.c1bookAll = 0;
                    $s.c1bookInnerAll = 0; $s.c1bookOuterAll = 0;
                    $s.c2All = 0; $s.c2innerAll = 0; $s.c2outerAll = 0; $s.c2bookAll = 0;
                    $s.c2bookInnerAll = 0; $s.c2bookOuterAll = 0;
                    $s.innerPriceAll = 0; $s.outerPriceAll = 0;

                    $s.canShut = "false";//默认不能关闭当日所有排班（请求当日所有排班时遍历，只要有一项未关闭，canShut为true）
                    var nowTime = new Date().getTime();//当前时间戳
                    for(var i=0;i<$s.getTheday.length;i++){
                        /*近三天的排班不能编辑（只能三天以后的）*/
                        if((new Date($s.getTheday[i].pstart).getTime() - nowTime) < 3*24*3600*1000){
                            $s.getTheday[i].noEdit = true;
                        }
                        //只要有一项子排班非关闭，就允许关闭当日所有排班
                        if($s.getTheday[i].state!=1){$s.canShut=true;}
                        //以下参数用于统计当日的整体概况信息
                        $s.c1All += $s.getTheday[i].c1;
                        $s.c1innerAll += $s.getTheday[i].c1inner;
                        $s.c1outerAll += $s.getTheday[i].c1outer;
                        $s.c1bookAll += $s.getTheday[i].c1book;
                        $s.c1bookInnerAll += $s.getTheday[i].c1bookInner;
                        $s.c1bookOuterAll += $s.getTheday[i].c1bookOuter;

                        $s.c2All += $s.getTheday[i].c2;
                        $s.c2innerAll += $s.getTheday[i].c2inner;
                        $s.c2outerAll += $s.getTheday[i].c2outer;
                        $s.c2bookAll += $s.getTheday[i].c2book;
                        $s.c2bookInnerAll += $s.getTheday[i].c2bookInner;
                        $s.c2bookOuterAll += $s.getTheday[i].c2bookOuter;

                        $s.innerPriceAll += $s.getTheday[i].innerPrice;
                        $s.outerPriceAll += $s.getTheday[i].outerPrice;
                    }
                    //console.log("c1总量："+$s.c1All)
                    //console.log("C1内部总量："+$s.c1innerAll)
                    //console.log("C1外部总量："+$s.c1outerAll)
                    //console.log("C1预约总量："+$s.c1bookAll)
                    //console.log("C1内部预约总量："+$s.c1bookInnerAll)
                    //console.log("C1外部预约总量："+$s.c1bookOuterAll)
                    //
                    //console.log("c2总量："+$s.c2All)
                    //console.log("C2内部总量："+$s.c2innerAll)
                    //console.log("C2外部总量："+$s.c2outerAll)
                    //console.log("C2预约总量："+$s.c2bookAll)
                    //console.log("C2内部预约总量："+$s.c2bookInnerAll)
                    //console.log("C2外部预约总量："+$s.c2bookOuterAll)

                    $s.$apply();

                    /*当该日有排班时，显示三项饼图：未约，内部已约，外部已约*/
                    if($s.getTheday.length>0){
                        $s.chartArr = [
                            {value:$s.c1All+$s.c2All-$s.c1bookAll-$s.c2bookAll, name:'未预约'},
                            {value:$s.c1bookInnerAll + $s.c2bookInnerAll, name:'内部已预约'},
                            {value:$s.c1bookOuterAll + $s.c2bookOuterAll, name:'外部已预约'},
                        ];
                        $s.legend = {
                            orient: 'vertical',
                            left: 'left',
                            top:'bottom',
                            data: ['未预约','内部已预约','外部已预约']
                        }
                        var myChart = echarts.init(document.getElementById('pieData'));
                        myChart.setOption({
                            title:{text:'预约概况',subtext:$s.theDay,x:'center'},
                            tooltip : {
                                trigger: 'item',
                                formatter: "{a} <br/>{b} : {c} ({d}%)"
                            },
                            color:["#a5a5a5","#5A9BD5","#ED7D31"],
                            legend:$s.legend,
                            series : [
                                {
                                    name: $s.theDay,
                                    type: 'pie',
                                    radius: '55%',
                                    data:$s.chartArr
                                }
                            ]
                        })
                    }

                }

            }
        });
    };
    $s.getArrangeList();

    /*点击或选取某一天，展示当天排班数据*/
    $s.setTheDay = function($event,theDate){
        $($event.target).addClass("active").siblings().removeClass("active");
        $(".arrange-cdate").removeClass("active");
        $s.theDay = new Date(theDate).date("y-m-d");
        $s.theDayTime = new Date(theDate).getTime();
        $s.getArrangeList();
    }

    /*时间输入控件*/
    $('#customDate').datetimepicker({
        lang:'ch',
        timepicker:false,
        format:'Y-m-d',
        formatDate:'Y-m-d',
        onChangeDateTime:function(currentDateTime){
            //if($(this).is(':blur')){alert(234)}
            if($(':focus').length==1){
                $(".z-select-days > div").removeClass("active")
                $(".arrange-cdate").addClass("active")
                $s.theDay = currentDateTime.date('y-m-d');
                $s.getArrangeList()
            }
        }
    }).on("keydown",function(){return false;});
    $.datetimepicker.setLocale('ch');//设置中文

	/*hash值改变的时候加载数据列表*/
	window.onhashchange=function(){
		win.showLoading();
		$s.defaultPage=location.hash.substring(2) || 1;
		$s.getDataList();
	}
	
	/*按所学车型筛选列表数据*/
	$s.getDataForCarType=function($event,type){
		$($event.target).addClass('active').siblings().removeClass("active");
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.carType=type;
		$s.getDataList();
	}

	/*按汽车等级筛选列表数据*/
	$s.getDataForAccountStatus=function($event,type){
		$($event.target).addClass('active').siblings().removeClass("active");
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.carLevel=type;
		$s.getDataList();
	}

	/*按分页条数筛选列表数据*/
	$s.getDataForPage=function(){
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.getDataList();
	}

	/*高级查询*/
	$s.getDataForSearch=function(){
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.getDataList();
	}

	/*按驾校筛选列表数据*/
	$s.getDataForSchool=function(){
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.schoolName=$s.schoolNameSelect;
		$s.getDataList();
	}

	/*数据导出*/
	$s.carDataExport=function(){
		//调起数据导出
		dataExportForIframe({
			getSearchs:getJson($s.defaultPage).data,
			total:$s.total,
			url:'car/export-excel',
		});
	}

/*****复制排班***********************************************************/
    /*include 加载完成后执行*/
    $s.copyClassLoad=function(){
        /*点击按钮关闭弹出层*/
        $(".closeAlert").click(function(){
            $(this).parents("div.edit-alert").fadeOut("fast");
            //消掉自增的时间段，并清除内容输入的时间段
            $("#itemAuto0").siblings(".item-auto-item").remove();
            $("#itemAuto0 input").val("");
            //已勾选的日期无须清除，因为在加载弹窗时重新请求了14天的日期
    })

        /*获取指定日期及之后若干天的排班情况和日期数据,此日期段不能和主页面中的日期段共用，单独另写的*/
        $s.getCopyDays = function(){
            $.AJAX({
                type:'get',
                url:config.basePath+"examPlace/class/date",
                data:{
                    "type":0,
                    "pdate":new Date().date("y-m-d"),
                    "days":14
                },
                success:function(data){
                    console.log(data)
                    $s.getCopyDaysInfo = JSON.parse(data.result.pageData);
                    $s.$apply();
                }
            });
        }

        /*时间段表单*/
        var datePickerOptions = {datepicker:false, format:'H:i', step:30};
        $('.date-time').datetimepicker(datePickerOptions).on("keydown",function(){return false;});

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
            $("#copyExamClassCon").css("marginTop",parseInt(($(win).height()- $("#copyExamClassCon").height()-100)/2)+"px");
        }

        /*自减时间段*/
        $(".item-auto-exam").on("click",".z-ico-del",function(){
            $(this).parent(".item-auto-label").parent(".item-auto-item").slideUp("normal").remove();
        })

        /*判断时间不能交叉,且为最小时间段的倍数*/
        $(".item-auto-exam").on("blur",".date-time",function(){
            checkTimepicker($(this));
            autoNextTime($(this));
        })
    }

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
        var curTimeData = "";
        console.log("被检查项：")
        console.log(curObj)
        console.log("被检查时间"+curTime)
        if(curTime!=""){
            curTimeData = parseInt(curTime.replace(":",""));
            //判断开始时间段应该小于结束时间段
            var anotherTime = curObj.siblings(".date-time").val();
            console.log("被检查项的另一半时间"+anotherTime)
            if(anotherTime!=""){
                var anotherTimeData = parseInt(anotherTime.replace(":",""))
                //当前项是起始项且起始项大于等于结束项  ||  当前项是结束项且起始项大于等于结束项
                if(((curObj.hasClass("date-time-from"))&&(curTimeData >= anotherTimeData))||((curObj.hasClass("date-time-to"))&&(curTimeData <= anotherTimeData)&&(parseInt(curTimeData)!=0000))){
                    Layer.alert({width:400,height:160,title:"起始时间必须早于结束时间!",header:"操作提示"});
                    curObj.parent(".item-auto-item").children("input").val("");
                    return false;
                }
                console.log(anotherTimeData - curTimeData);
                //if((anotherTimeData - curTimeData)%($s.minHours*100)!=0){//最初设计的起止时差为最小时段倍数，因产品逻辑问题，改为起止差须等于最小时段
                console.log(Math.abs(anotherTimeData - curTimeData));
                console.log($s.copyData.minHours*100);
                if(Math.abs(anotherTimeData - curTimeData)!=($s.copyData.minHours*100)){
                    Layer.alert({width:400,height:160,title:"预约时间段限制为"+$s.copyData.minHours+"小时",header:"操作提示"});
                    curObj.parent(".item-auto-item").children("input").val("");return;
                }
            }
            //将时间转成数字，逐条判断大小，在区间中则表示有交叉(为空的不比较，因为后面还有一关较验为空项)
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
            ////如果是结束段，赋一个$s的全局，将后推二小时后的时段自动写在新建的时段的起始段上（需在zItemAdd函数中）并较验该值
            //if(curObj.hasClass("date-time-to")){
            //    $s.nextTimeTemp = curObj.val()
            //}
        }
    }

    $s.copyClass=function(item){
        $s.getCopyDays();
        $(".copy-exam-class").fadeIn("fast");
        /*弹出层垂直居中*/
        $("#copyExamClassCon").css("marginTop",parseInt(($(win).height()- $("#copyExamClassCon").height()-100)/2)+"px");
        /*修改*/
        $s.copyData = item;
    }

    $s.submitCopy=function($event){
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
        if((firstDay - nowDay)<$s.copyData.innerExpire*24*60*60*1000){
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

        var submitJson={
            classId:$s.copyData.id,
            pdate:arrangeDaysArrSubmit.join(","),
            pstart:arrangeTimeArr.join(","),
        }
        console.log(submitJson)
        $.AJAX({
            url:config.basePath+"examPlace/class/copy",
            type:"post",
            data:submitJson,
            success:function(data){
                $($event.target).parents("div.edit-alert").fadeOut("fast");
                Layer.miss({width:250,height:90,title:"复制成功",closeMask:true});
                $s.getDays();
                $s.getArrangeList();
                //消掉自增的时间段，并清除内容输入的时间段..已勾选的日期无须清除，因为在加载弹窗时重新请求了14天的日期
                $("#itemAuto0").siblings(".item-auto-item").remove();
                $("#itemAuto0 input").val("");
            }
        })
    }

/*****关闭排班***********************************************************/
    $s.shutClass = function(obj,range){
        var ids = "";
        var idsArr= [];
        //当点击当日的关闭时，获取当日所有排班的集合
        if(range=="all"){
            for(var i=0;i<obj.length;i++){
                idsArr.push(obj[i].id)
            }
            ids = idsArr.join();
        //当点击某个时间段的关闭时，直接传该时间段排班的ID
        }else{
            ids =  obj.id;
        }
        console.log(ids)
        Layer.confirmNotByTextAlert({
            header:"关闭排班",
            width:400,
            height:260,
            botByText:'请填写理由',
            title:"请填写关闭排班的理由",
        },function(notByText){
            $.AJAX({
                url:config.basePath+"examPlace/class/close",
                data:{
                    classId:ids,
                    remark:notByText,
                },
                success:function(data){
                    Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                    /*更新列表*/
                    $s.getDays();
                    $s.getArrangeList();
                }
            });/*AJAX end*/
        });
    }

/*****延迟排班***********************************************************/
    /*include 加载完成后执行*/
    $s.delayClassLoad=function(){
        /*点击按钮关闭弹出层*/
        $(".delay-exam-class .closeAlert").click(function(){
            $(this).parents("div.edit-alert").fadeOut("fast");
            /*清空数据*/
            $s.delayData.ids="";
            $s.delayData.delayTime="";
            $s.delayData.delayReason="";
        })
    }

    $s.delayClass=function(item,index){
        $(".delay-exam-class").fadeIn("fast");
        /*弹出层垂直居中*/
        $("#delayExamClassCon").css("marginTop",parseInt(($(win).height()- $("#delayExamClassCon").height()-100)/2)+"px");
        $s.delayData = item;
        $s.delayData.delayTex = new Date($s.delayData.pstart).date("h:i")+"-"+new Date($s.delayData.pend).date("h:i");
        $s.delayIndex = index;
    }

    $s.submitDelay = function($event){
        if(!$s.delayData.delayTime || $s.delayData.delayTime<1 || $s.delayData.delayTime>$s.delayData.minHours*60-1){
            Layer.alert({width:400,height:150,type:"msg",title:"延班时间应大于0且小于最小时间段"});
            return false;
        }
        if(!$s.delayData.delayReason){
            Layer.alert({width:300,height:150,type:"msg",title:"请输入延班理由"});
            return false;
        }

        /*当前排班之后的排班全部顺延，获取其后所有未延班的ID集合，及时间段集合*/
        var delayIdsArr = [];
        for(var i=$s.delayIndex;i<$s.getTheday.length;i++){
            if(i==$s.delayIndex){
                delayIdsArr.push($s.getTheday[i].id);
            }
            if(i>$s.delayIndex){
                if(($s.getTheday[i].rstart == $s.getTheday[i-1].rend)&&($s.getTheday[i].rstart == $s.getTheday[i].pstart)){
                    delayIdsArr.push($s.getTheday[i].id);
                }
                else{break;}
            }
        }
        $s.delayData.delayIds = delayIdsArr.join();
        //当前被延的排片日期时间2016-09-15 12:00-14:00
        var delayFrom = new Date($s.getTheday[$s.delayIndex].pstart).date("y-m-d h:i")+"-"+new Date($s.getTheday[$s.delayIndex].pend).date("h:i");
        //将要延到的时间段如：12:10-14:10
        var delayTarget =new Date(new Date($s.getTheday[$s.delayIndex].pstart).getTime()+$s.delayData.delayTime*60*1000).date("h:i")+"-"+new Date(new Date($s.getTheday[$s.delayIndex].pend).getTime()+$s.delayData.delayTime*60*1000).date("h:i");

        $.AJAX({
            url:config.basePath+"examPlace/class/delay",
            data:{
                classId:$s.delayData.delayIds,
                num:$s.delayData.delayTime,
                remark:$s.delayData.delayReason,
            },
            success:function(data){
                $($event.target).parents("div.edit-alert").fadeOut("fast");
                Layer.alert({width:480,height:170,type:"msg",title:"已将"+delayFrom+"的排班延迟"+$s.delayData.delayTime+"分钟<br>至"+delayTarget+"。如果其后有连续时间，也将顺延。"});
                $s.getArrangeList();
                /*清空数据*/
                $s.delayData.ids="";
                $s.delayData.delayTime="";
                $s.delayData.delayReason="";
            }
        })


    }


}]);
