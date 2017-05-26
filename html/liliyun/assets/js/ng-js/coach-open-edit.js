var app=angular.module("app",["ngFilter"]);

/*main 控制器*/
app.controller("CoachOpenEdit",["$scope","$filter",function($s,$filter){

    $s.coachId=getQueryString("coachId"); //获取注册教练ID值

    /*获取该教练详情*/
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

    //$s.auditData.dryLicence1Pic = getQiniu($s.auditData.dryLicence1);
    //function getQiniu(imgkey){
    //    return ($.AJAX({
    //        url: config.basePath + "files/downUrlByKey",
    //        type:'get',
    //        data:{
    //            picName: imgkey,
    //            timestamp:time(),
    //            sign:1,
    //        },
    //        success: function(data){
    //            console.log(data);
    //            return data
    //        }
    //    }))
    //}

    /****车辆动态增加*******/
    $s.objItemAdd = function(){
        if(!$s.dataView.carCheckList){$s.dataView.carCheckList=[];}
        var addItem = {
            "carNo":"",
            "driveType":"",
            "drivePhoto":"",
            "drivePhoto2":"",
            "carImg":"",
        };
        $s.dataView.carCheckList.push(addItem)
        //console.log($s.dataView.carCheckList)
    }

    /****车辆动态删除*******/
    $s.objItemDel = function(objArr,index){
        objArr.splice(index,1)
    }

    /*************************上传图片************************/
    /*获取七牛CMS专用TOKEN并缓存*/
    if(!getCookie("qnCoachRegToken")){
        $.ajax({
            type: "get",
            data:{userId:$s.coachId,userType:1},
            url:config.basePath+"file/cmsUpToken",
            async: false,
            success:function(data){
                $s.imgToken = JSON.parse(data.result.pageData).upToken;
                console.log($s.imgToken)
                setCookie("qnCoachRegToken",$s.imgToken,1)
            }
        });
    }
    else{$s.imgToken = getCookie("qnCoachRegToken");}

    /*上传图片*/
    $s.uploadImg = function(type,objImg,field,$event){
        $("#createImg").remove();
        // http://obqfnhv9x.bkt.clouddn.com
        var html='<div id="createImg" class="hidden"><form method="post" enctype="multipart/form-data" name="form1" id="f2">'+
            '<input type="hidden"  id="token" name="token" value="'+$s.imgToken+'" >'+
            //'<input name="key" value="" id="key">'+
            '<input type="file" id="imgfile" name="file"/></form></div>';
        if(!$('#createImg').length){
            $('body').append(html);
        };
        $('#imgfile').click();
        $('#imgfile').change(function(){
            /*实时显示选择的图片*/
            var objUrl = getObjectURL(this.files[0]) ;
            //   console.log("objUrl = "+objUrl) ;

            if($("#imgfile").val()){
                var filepath = $("#imgfile").val();
                var postfix = filepath.substring(filepath.lastIndexOf('.'),filepath.length)
                if(postfix !='.jpg' && postfix !='.jpeg' && postfix !='.png' && postfix !='.bmp' ){
                    $('#createImg').remove();
                    Layer.alert({width:300,height:150,type:"error",title:'格式不正确！'});
                    return;
                }

                var path = $("#imgfile").val();
                //var timestamp=new Date().getTime();
                //$("#key").val("wxschool_"+path.substring(path.lastIndexOf("\\")+1,path.lastIndexOf("."))+'_'+timestamp);
                /*选择图片后，执行如下动作（还未上传）*/
                $($event.target).children("img").prop("src", objUrl);
                console.log($($event.target).children("img"))
                ///*直接上传到七牛云*/
                getimgKey(objImg,field);

                ///*确定上传到七牛云*/
                //$("#uploadButton").removeClass("z-locked")
                //$s.uploadConfirm = function($event){
                //    var btnObj = $($event.target);
                //    btnObj.addClass("z-locked");
                //    getimgKey(type,objImg,btnObj);
                //}

            }
        })
    }
    /*获取选择的图片路径*/
    function getObjectURL(file) {
        var url = null ;
        if (window.createObjectURL!=undefined) { // basic
            url = window.createObjectURL(file) ;
        } else if (window.URL!=undefined) { // mozilla(firefox)
            url = window.URL.createObjectURL(file) ;
        } else if (window.webkitURL!=undefined) { // webkit or chrome
            url = window.webkitURL.createObjectURL(file) ;
        }
        return url ;
    }
    /*上传图片*/
    function getimgKey (objImg,field){
        $("#token").val($s.imgToken);
        var form = new FormData(document.getElementById("f2"));
        $.ajax({
            // url:"http://up.qiniu.com",
            url:"https://up.qbox.me",
            type:"post",
            data:form,
            processData:false,
            contentType:false,
            async: false,
            success:function(data){
                console.log(data)
                objImg[field]=data.key;
                /*判断更新了哪些字段*/
                ////当上传图片成功，判断更新了哪些
                if(!objImg.changedFields){objImg.changedFields = {};}
                objImg.changedFields[field]=true;
                if(field=="drPhoto"){
                    objImg.isNewDrPhoto=objImg.changedFields.drPhoto2?3:1;
                }
                if(field=="drPhoto2"){
                    objImg.isNewDrPhoto=objImg.changedFields.drPhoto?3:2;
                }
                if(field=="drivePhoto"){
                    objImg.isNewDrivePhoto=objImg.changedFields.drivePhoto2?3:1;
                }
                if(field=="drivePhoto2"){
                    objImg.isNewDrivePhoto=objImg.changedFields.drivePhoto?3:2;
                }
                if(field=="carImg"){
                    objImg.isNewCarImg=1;
                }

                //if(!objImg.changedFields){objImg.changedFields = {};}
                //objImg.changedFields[field]==true;

                console.log($s.dataView)
                //上传成功后移除生成的FORM表单，以免下一个上传冲突重复
                $("#createImg").remove();
            }
        })

    }

    /************************提交修改数据******************************/
    $s.carInfoChange = function(objItem,objField,objClass){
        $("."+objClass).removeClass("z-border-red");
        if(!objItem.changedFields){objItem.changedFields = {};}
        objItem.changedFields[objField]=true;
        if(objClass == 'carNoForm'){
            objItem.isNewCarDrive = objItem.changedFields.driveType?3:1;
        }
        if(objClass == 'carTypeForm'){
            objItem.isNewCarDrive = objItem.changedFields.driveType?3:2;
        }
        console.log($s.dataView)
    }
    //$(".coach-open-details").on("change",".z-required",function(){$(this).removeClass("z-border-red")});
    $(".coach-open-details").on("click",".z-required",function(){$(this).removeClass("z-border-red")});
    /*提交编辑*/
    $s.submitMsg = function(){
        if($s.dataView.carCheckList.length==0){
            Layer.alert({width:330,height:150,type:"error",title:"请至少增加一条车辆信息"});
            return false;
        }
        var dataOK = true;
        //较验车牌号必填
        $(".z-required").each(function(){
            //车牌非空，符合正则，车型必选，城市必选
            console.log($(this).attr("placeholder")+"-"+$(this).val())
            if(($(this).hasClass("required-carnum")&&(!$(this).val()||!(/^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$/.test($(this).val()))))||($(this).hasClass("required-cartype")&&(!$(this).val()))||(($(this).hasClass("required-city"))&&(!$(this).val()))){dataOK = false;$(this).addClass("z-border-red");}
        })
        $(".z-required-img").each(function(){
            if($(this).attr("src")=="assets/img/default-img.png"){dataOK = false;$(this).addClass("z-border-red")}
        })
        if(!dataOK){
            Layer.alert({width:300,height:150,type:"error",title:"请确保录入信息完整无误"});
            return false;
        }

        console.log($(".required-city").val())
        Layer.confirm({width:330,height:160,title:"确认保存该教练认证资料?",header:"编辑认证信息"},function(){
            $.AJAX({
                url: config.basePath+"coach/updateRegCheck",
                data:{
                    userId:$s.coachId,
                    cityName:$(".required-city").val().indexOf("市")>0?$(".required-city").val():$(".required-city").val()+"市",
                    drPhoto:$s.dataView.drPhoto,
                    drPhoto2:$s.dataView.drPhoto2,
                    isNewDrPhoto:$s.dataView.isNewDrPhoto,
                    carJsonList:angular.toJson($s.dataView.carCheckList),
                },
                success:function(data){
                    Layer.miss({width:250,height:90,title:"操作成功！",closeMask:true});
                    setTimeout(function(){window.location.href="coach-open-details.html?coachId="+$s.coachId},1000)
                }
            })
        })
    }

}]);