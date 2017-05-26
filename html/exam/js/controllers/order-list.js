
app.controller('OrderList', ['$scope','$filter', function($s,$filter){
    $s.$emit('changeTitle', '预约详情');//修改标题
    $s.defaultPage=1;  //默认请求页
    $s.pageSize=7;    //每页显示-显示条数
    $s.classId = getUrl("classId");
    $s.pstart = getUrl("pstart");
    $s.pstartTxt=$s.pstart.split("-")[0].substr(0,5)+"-"+$s.pstart.split("-")[1].substr(0,5)
    $s.pdate = getUrl("pdate");
    $s.state = "";//订单状态。0-未支付；1-已支付；2-练考中；3-已完成；4-已取消；5-已关闭'。查询全部则传空。
    $s.drtype = "";//练考车型 1为C1，2为C2，查询全部则传空
    $s.coachName="";
    $s.schoolName="";
    $s.carNo="";
    $s.coachMobile="";
    $s.orderId="";
    $s.showMoreLock = false;//默认无锁
    $s.showMoreEnd = false;

    /*排班按钮的显示*/
    var hourTxt = $s.pstart.substr(-8)=="00:00:00" ? "23:59:59" : $s.pstart.substr(-8);
    var classTimeSnamp = new Date(($s.pdate + " " + hourTxt).replace(/-/g,"/")).getTime();
    var newSnamp = new Date().getTime();
    $s.showEditButton = (classTimeSnamp < newSnamp) ? false : true;

    /*获取订单列表并展示*/
    $s.datas = [];//存储列表数据
    $s.getDataList=function(){
        if($s.showMoreLock == true || $s.showMoreEnd == true){console.log("有锁,不加载");return;}//如果有锁则不加载
        $s.showMoreLock = true;
        $.AJAX({
            type:"get",
            url:config.basePath+"examPlace/order",
            data:{
                "pageNo": $s.defaultPage,
                "pageSize": $s.pageSize,
                "pdate": $s.pdate,
                "pstart":$s.pstart,
                "classId":$s.classId,
                "state": $s.state,
                "drtype":$s.drtype,
                "placeId": sessionStorage.getItem("schoolPlaceId"),
                "coachName": $s.coachName,
                "schoolName": $s.schoolName,
                "carNo":$s.carNo,
                "coachMobile": $s.coachMobile,
                "orderId":$s.orderId
            },
            success:function(data){
                var DATA=JSON.parse(data.result.pageData);
                $s.total=DATA.total;
                /*数据渲染页面*/
                $s.dataPage=DATA.dataList;
                $s.datas = $s.datas.concat($s.dataPage);
                if($s.datas.length==$s.total){
                    console.log("加载完了，不加了")
                    $s.showMoreEnd = true;
                }
                $s.$apply();
                $s.showMoreLock = false;//解锁
            }
        });
    };
    $s.getDataList();

    $s.showMore = function(){
        $s.defaultPage++;
        $s.getDataList();
    }

    /*滑动翻页*/
    $(window).on("scroll",function(){
        $s.windowHeight = $(window).height();
        $s.bodyHeight = $("body").height();
        $s.scrollTop = document.body.scrollTop ? document.body.scrollTop : document.documentElement.scrollTop;
        //console.log($s.bodyHeight + "," + $s.windowHeight + "," + $s.scrollTop)
        if($s.bodyHeight < $s.windowHeight + $s.scrollTop +20){
            $s.showMoreLock = false;
            $s.showMore();
        }
    })

    /****************关闭排班*****************/
    $s.shutClass = function(){
        Popup.customHtml({type:'msg',closeBut:true,style:'width:80%',header:"关闭排班",html:'<textarea rows="3" id="shutMsg" style="border:none;text-align:center; color:#999; width: 100%; font-size: 34px;" placeholder="请输入关闭理由"></textarea><div class="footer"><span class="no" onclick="closeThisPopup(this)">取消</span><span class="yes">确定</span></div>',callback:function(){
            if(!$('#shutMsg').val() || $('#shutMsg').val().trim().length==0){
                Popup.alert({type:'msg',style:'width:80%',title:"请输入关闭理由",header:"提示"});
                return false;
            }
            $.AJAX({
                type:"post",
                url:config.basePath+"examPlace/class/close",
                data:{
                    "classId":$s.classId,
                    "remark":$('#shutMsg').val()
                },
                success:function(data){
                    console.log(data);
                    $(".popup").fadeOut().remove();
                    Popup.miss({haveHeader:true,title:'该排班已关闭'});
                    setTimeout(function(){window.location.href="#/class-view"},1000)
                }
            });
        }});
    }









}])



