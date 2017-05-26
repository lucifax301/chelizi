var app=angular.module("app",[]);
app.controller("CouponCondition",["$scope",function($s){

    /*加载城市数据列表*/
    $.AJAX({
        type:'get',
        url:config.basePath+"school/queryCity",
        data:{
            "rlevel":2,
            "pid":"",
	    shield:0
        },
        success:function(data){
            /*数据渲染页面*/
            $s.citys=JSON.parse(data.result.pageData);
            $s.$apply();
        }
    });

    /*时间输入控件*/
    $('#reservStart').daterangepicker({'format': 'YYYY-MM-DD','singleDatePicker': true,'autoclose': true, "timePicker": true,"timePicker24Hour": true, "timePickerSeconds": true,"autoApply": true,"locale":{ "format": "YYYY-MM-DD HH:mm:ss",}});
    $('#reservEnd').daterangepicker({'format': 'YYYY-MM-DD','singleDatePicker': true,'autoclose': true, "timePicker": true,"timePicker24Hour": true, "timePickerSeconds": true,"autoApply": true,"locale":{ "format": "YYYY-MM-DD HH:mm:ss",}});



    /*加载指定科目类型的数据，因产品设计问题，此为静态数据，后续需求变动改此JSON即可*/
    $s.zdkmARR = [
        {id:"zdkmBox1",value:"1|11",info:"科目二基础训练"},
        {id:"zdkmBox2",value:"6|16",info:"科目二应试训练"},
        {id:"zdkmBox3",value:"2|12",info:"科目二考场模拟"},
        {id:"zdkmBox4",value:"3|13",info:"科目三基础训练"},
        {id:"zdkmBox5",value:"7|17",info:"科目三应试训练"},
        {id:"zdkmBox6",value:"4|14",info:"科目三考场模拟"},
        {id:"zdkmBox7",value:"101",info:"体验课程"},
        {id:"zdkmBox8",value:"5|15",info:"陪驾"}
    ]

    /*参数配置函数*/
    $s.editData={};
    /*带加减的数字输入控件*/
    $s.editData.getNum = 1;
    $s.zNumReduce = function($event){$s.editData.getNum <= 1 ? "" : $s.editData.getNum--;}
    $s.zNumAdd = function($event){$s.editData.getNum++;}

    function addCouponCondiJson(url){
        var json={
            type:$s.editData.type,
            descri:$s.editData.descri,
        };
        return {
            url:config.basePath+url,
            data:json
        }
    }

    /*选择大类时传值*/
    $s.setType = function($event,typeVal){
        $s.editData.type = typeVal;
        $(".table-coupon-condi-add .shade-color").slideDown();
        $($event.target).parent("th").siblings("td").find(".shade-color").slideUp("fast");
    }

    /*提交数据*/
    $s.submitEditMsg = function(){
        clearTimeout("errorMsg");
        $s.confirmSelVals = "";
        $s.confirmLayerH = 140;//自定义Layer的高度（仅用于指定科目和指定区域的确认弹窗）
        //当没有选择条件类型时：
        if(!($s.editData.type==0||$s.editData.type==1||$s.editData.type==2||$s.editData.type==3)){
            $(".table-coupon-condi-add th").css("border-color","red");
            $(".error-xlcs").html("请选择条件类型").slideDown("fast");
            //$(".error-zdkm").html("请选定科目类型").slideDown();
            errorMsg = setTimeout(function(){
                $(".table-coupon-condi-add th").css("border-color","#ddd");
                $(".error-xlcs").empty().slideUp("fast");
            },1500)
            return false;
        }

        //radio选指定科目时
        switch($s.editData.type){
            /*--指定科目----------------------------*/
            case 2:
                $s.confirmHead = "指定科目";
                $s.ZDKMselVals = [];//"指定科目"勾选子项的空数组
                //遍历已勾选的CHECKBOX
                $s.confirmSelVals="<br>";
                $('.zdkm-wrap input[name="zdkmBox"]:checked').each(function(){
                    if($(this).val()!=""){$s.ZDKMselVals.push($(this).val())}
                    if($(this).attr("confirmTxt")){$s.confirmSelVals+=$(this).attr("confirmTxt")+"<br>";$s.confirmLayerH += 22}
                })
                $s.editData.param1 = $s.ZDKMselVals.join("|");//合并成一串
                $s.editData.param2 = "-";
                //以下代码判断不合格提交
                if(!$s.editData.param1){
                    //当参数为空（即没有勾选checkbox时，提示错误）
                    clearTimeout("errorMsg");
                    $(".zdkm-wrap .shade-wrap").css("border","1px solid red");
                    $(".error-zdkm").css("padding-left","150px").html("请勾选指定科目").slideDown("fast");
                    errorMsg = setTimeout(function(){
                        $(".zdkm-wrap .shade-wrap").css("border","none");
                        $(".error-zdkm").empty().css({"display":"none","padding":"0"});
                    },1500)
                    return false;
                }
                break;
            /*--指定区域----------------------------*/
            case 1:
                $s.confirmHead = "指定区域";
                $s.ZDQYselVals=[];//"指定区域"勾选子项的空数组
                //遍历已勾选的CHECKBOX
                $s.confirmSelVals="<br>";
                $('.zdqy-wrap input[name="zdqyBox"]:checked').each( function(){
                    if($(this).val()!=""){$s.ZDQYselVals.push($(this).val())}
                    if($(this).attr("confirmTxt")){$s.confirmSelVals+=$(this).attr("confirmTxt")+"<br>";$s.confirmLayerH += 22}
                })
                $s.editData.param1 = $s.ZDQYselVals.join("|");//合并成一串
                $s.editData.param2 = "-";
                //当子项没有选择时
                if(!$s.editData.param1){
                    //当参数为空（即没有勾选checkbox时，提示错误）
                    clearTimeout("errorMsg");
                    $(".zdqy-wrap .shade-wrap").css("border","1px solid red");
                    $(".error-zdqy").css("padding-left","150px").html("请勾选指定区域").slideDown("fast");
                    errorMsg = setTimeout(function(){
                        $(".zdqy-wrap .shade-wrap").css("border","none");
                        $(".error-zdqy").empty().css({"display":"none","padding":"0"});
                    },1500)
                    return false;
                }
                break;
            /*--指定时间----------------------------*/
            case 0:
                $s.confirmHead = "指定时间";
                $s.editData.param1 = $s.editData.reservStart;
                $s.editData.param2 = $s.editData.reservEnd;
                if(!($s.editData.param1 && $s.editData.param2)){
                    //当参数为空（即没有指定时间时，提示错误）
                    clearTimeout("errorMsg");
                    $(".zdsj-wrap .shade-wrap").css("border","1px solid red");
                    $(".error-zdsj").css("padding-left","150px").html("请设置起点终点时间").slideDown("fast");
                    errorMsg = setTimeout(function(){
                        $(".zdsj-wrap .shade-wrap").css("border","none");
                        $(".error-zdsj").empty().css({"display":"none","padding":"0"});
                    },1500)
                    return false;
                }
                $s.confirmLayerH = 190;
                $s.confirmSelVals = "开始："+$s.editData.param1+"<br>结束："+$s.editData.param2;
                break;
            /*--限领次数----------------------------*/
            case 3:
                $s.confirmHead = "限领次数";
                $s.editData.param1 = $s.editData.getNum;
                $s.editData.param2 = "-";
                $s.confirmSelVals = $s.editData.param1;
                $s.confirmLayerH = 165;
                break;
        }


        //当没有填写条件说明时
        if(!$s.editData.descri){
            $(".error-tjsm").css("border-color","red");
            $(".error-tjsm").html("请填写条件说明").slideDown("fast");
            //$(".error-zdkm").html("请选定科目类型").slideDown();
            errorMsg = setTimeout(function(){
                $(".error-tjsm").css("border-color","#ddd");
                $(".error-tjsm").empty().slideUp("fast");
            },1500)
            return false;
        }

        Layer.confirmTextAlert({width:350,height:$s.confirmLayerH,title:$s.confirmHead+":"+$s.confirmSelVals,header:"新建券条件"},function(){
            $.AJAX({
                url:config.basePath+"coupon/add-cond",
                data:{
                    type:$s.editData.type,
                    param1:$s.editData.param1,
                    param2:$s.editData.param2,
                    descri:$s.editData.descri
                },
                success:function(data){
                    Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                    setTimeout(function(){window.location.href="coupon-condition.html";},1000)
                }
            });
        });
    }

}]);