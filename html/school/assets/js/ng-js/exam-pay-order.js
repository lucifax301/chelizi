/*angular for 提现*/
var app=angular.module("app",["ngFilter"]);

/*main 控制器*/
app.controller("ExamGroundOrder",["$scope","$filter",function($s,$filter){

    /*------------------------------------------订单分页数据及其查询-----------------------------------------------------*/
    $s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
    $s.pageSize=40;    //每页显示-显示条数
    $s.classId=getQueryString("classId") ? getQueryString("classId") : "";//班别ID，此点用于查看班别的订单
    $s.pdate2 = getQueryString("pdate") ? getQueryString("pdate") : "";//排班日期（此条用于显示指定一整天的订单，属于新增项，注意于$s.pdate的区别）
    $s.pdate = $s.pdate2 ? $s.pdate2 : (getQueryString("pstart") ? (new Date(getQueryString("pstart")).date("y-m-d")):"");//日期。格式：2016-09-12。查询全部则传空
    $s.showTimePicker = false;//默认不显示时段筛选，当起止为同一天时才显示
    if(getQueryString("pstart")&&getQueryString("pend")){
        if(new Date(getQueryString("pend")).date("h:i:s")=="00:00:00"){
            $s.pstart = new Date(getQueryString("pstart")).date("h:i:s")+"-24:00:00";
        }
        else{
            $s.pstart = new Date(getQueryString("pstart")).date("h:i:s")+"-"+new Date(getQueryString("pend")).date("h:i:s")
        }
    }
    else{
        $s.pstart = "";
    }
    $s.pend = getQueryString("pend") ? new Date(getQueryString("pend")).date("h:i:s") : "";//日期。格式：08:00:00。
    $s.pend = ($s.pend=="00:00:00") ? "24:00:00" : $s.pend;
    $s.currentOrder = getQueryString("currentOrder");//为1则表示指定时间段内的订单，为空则为所有订单查询

    $s.state = "";//订单状态。0-未支付；1-已支付；2-练考中；3-已完成；4-已取消；5-已关闭'。查询全部则传空。
    $s.coachName="";//模糊搜索：教练姓名
    $s.coachMobile="";//模糊搜索：教练手机号
    $s.payorderId="";//模糊搜索：订单号
    $s.searchType = "coachName";//默认搜索类型，订单号
    $s.search = "";
	
	$s.$watch('state',function(){
		 $s.getDataList()
	});
    /*获取订单列表并展示*/
    $s.getDataList=function(){
        var json=getJson($s.defaultPage);
        $.AJAX({
            type:"get",
            url:json.url,
            data:json.data,
            success:function(data){
                var DATA=getListData(data);
                $s.total=DATA.total;
                /*数据渲染页面*/
                $s.datas=DATA.dataList;
                console.log($s.datas)
                $s.$apply();
                /*冒泡弹出太长的文字*/
                topLongText();
                //分页请求
                new Page({
                    parent:$("#copot-page"),
                    nowPage:$s.defaultPage,
                    pageSize:$s.pageSize,
                    totalCount:DATA.total,
                }); //分页请求完毕
            }
        });
    };
    $s.getDataList();
    /*参数配置函数*/
    function getJson(nowPage){
        var json={
            url:config.basePath+"examPlace/payorder",
            data: {
                "pageNo": nowPage,
                "pageSize": parseInt($s.pageSize),
                "pdate": $s.pdate,
                "state": $s.state,
                "coachName": $s.coachName,
                "coachMobile": $s.coachMobile,
                "payorderId":$s.orderId
            }
        };
        /*增加搜索条件*/
        json.data[$s.searchType]=$s.search;
        return json;
    };

	$s.cancelOrder=function(itemData){
		console.log(itemData)
        Layer.confirm({width:350,height:160,title:"确认退款取消"+itemData.coachName+"的订单？",header:"确认"},function(){
			$.AJAX({
				url: config.basePath+"examPlace/cancelpayorder",
				type : "POST",
				data: {
					payOrderId: itemData.payorderId,
					placeId:itemData.placeId
				},
				success : function(data){
					Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
					$s.getDataList();
				}
			});
		});
	}
	
	$s.downloadpayorder=function(){
		//调起数据导出
		dataExportForIframe({
			getSearchs:getJson($s.defaultPage).data,
			total:$s.total,
			url:'examPlace/payorder/export',
		});
	}

    /*hash值改变的时候加载数据列表*/
    window.onhashchange=function(){
        win.showLoading();
        $s.defaultPage=location.hash.substring(2) || 1;
        $s.getDataList();
    }

    /*按日期筛选列表数据*/
    $s.getDataForDate=function($event,type){
        $($event.target).addClass('active').siblings().removeClass("active");
        switch(type){
            case 'all':
                $s.pdate="";
                $s.showTimePicker=false;
                $(".dateSelect").val("");
                break;
            case '0':
                $s.pdate=new Date().date("y-m-d");
                $s.showTimePicker=true;
                $(".dateSelect").val("");
                break;
            case '1':
                $s.pdate=new Date((new Date().getTime()+24*3600*1000)).date("y-m-d");
                $s.showTimePicker=true;
                $(".dateSelect").val("");
                break;
            case '2':
                $s.pdate=new Date((new Date().getTime()+48*3600*1000)).date("y-m-d");
                $s.showTimePicker=true;
                $(".dateSelect").val("");
                break;
        };
        win.showLoading();
        $s.defaultPage=1; //默认第一页
        $s.getDataList();
    }

    /*按输入日期筛选数据列表*/
    $('.dateSelect').datetimepicker({
        lang:'ch',
        timepicker:false,
        format:'Y-m-d',
        onChangeDateTime:function(currentDateTime){
            var fromVal = $(".select-from").val();
            var toVal = $(".select-to").val();
            //只有选择某一天时，才让选择某个时段（跨天不让选择时段）
            if(fromVal==toVal){$s.showTimePicker=true;}
            else{$s.showTimePicker = false;$(".date-time").val("");$s.pstart="";}

            if(($(':focus').length==1) && fromVal && toVal){
                //起始日期不能大于结束日期
                var fromTimeData = fromVal.replace(/-/g,"");
                var toTimeData = toVal.replace(/-/g,"");
                if(fromTimeData > toTimeData){
                    Layer.alert({width:400,height:160,title:"起始日期不能早于终点日期!",header:"操作提示"});
                    $(".dateSelect").val("");
                    return false;
                }
                //console.log("选定的日期段为："+fromVal + toVal)
                $(".getOneDay li").removeClass("active");
                $s.pdate = fromVal+","+toVal;
                win.showLoading();
                $s.defaultPage=1; //默认第一页
                $s.getDataList();
            }
        }
    }).on("keydown",function(){return false;});
    $.datetimepicker.setLocale('ch');//设置中文

    /*按时间段筛选列表数据*/
    $s.getDataForTimeAll=function($event){
        $($event.target).addClass('active');
        $(".date-time").val("");
        $s.pstart="";
        win.showLoading();
        $s.defaultPage=1; //默认第一页
        $s.getDataList();
    }

    /*按输入时间段筛选数据列表*/
    /*时间段表单*/
    var datePickerOptions = {datepicker:false, format:'H:i', step:30,onChangeDateTime:function(currentDateTime){
        var fromVal = $(".date-time-from").val();
        var toVal = $(".date-time-to").val();
        toVal = (toVal=="00:00")?"24:00":toVal;
        if(($(':focus').length==1) && fromVal && toVal){
            //为精确筛选范围，将时间提前一秒，如选择02:00-04:00,实传02:00:00-03:59:59
            var toValH = parseInt(toVal.substr(0,2));//获取小时
            var toValI = parseInt(toVal.substr(3));//获取分钟
            //情况一：当不是整点时，分钟减一，秒数为59秒
            if(toValI>0){
                toVal = toValH + ":" + (toValI-1)
            }
            //情况一：当是整点时，小时减一，+59:59
            else{
                toVal = toValH-1+":59";
            }
            //起始日期不能大于结束日期
            var fromTimeData = parseInt(fromVal.replace(":",""))
            var toTimeData = parseInt(toVal.replace(":",""))
            if(fromTimeData > toTimeData){
                Layer.alert({width:400,height:160,title:"起始时间段不能早于结束时间段!",header:"操作提示"});
                $(".date-time").val("");
                return false;
            }
            //console.log("选定的日期段为："+fromVal + toVal)
            $("#getAllTime").removeClass("active");
            $s.pstart = fromVal+":00-"+toVal+":59";
            win.showLoading();
            $s.defaultPage=1; //默认第一页
            $s.getDataList();
        }
    }};
    $('.date-time').datetimepicker(datePickerOptions).on("keydown",function(){return false;});


    /*更新搜索框提示文字*/
    $("#search_type").change(function(){
        $("#search_placeholder").attr("placeholder","输入"+$(this).children("option:selected").text()+"查询")
    })

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

    /*-----------------------------------关闭订单------------------------------------------------------*/
    /*确认支付*/
    $s.payOrder=function(itemData){
    	console.log(itemData)
        Layer.confirm({width:350,height:160,title:"确认付款"+itemData.coachName+"的订单？",header:"确认"},function(){
			$.AJAX({
				url: config.basePath+"examPlace/payorder",
				type : "POST",
				data: {
					payOrderId: itemData.payorderId,
				},
				success : function(data){
					Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
					$s.getDataList();
				}
			});
		});
    }

    /*数据导出*/
    $s.orderDataExport=function(){
        //调起数据导出
        dataExportForIframe({
            getSearchs:getJson($s.defaultPage).data,
            total:$s.total,
            url:'order/export-excel',
        });
    }

    /*显示打印时间*/
    setInterval(function(){
        $("#printTime").text(new Date().date("y-m-d h:i:s"))
    },1000)

}]);
