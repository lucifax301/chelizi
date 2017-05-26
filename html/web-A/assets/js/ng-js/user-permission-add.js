app.controller('RoleAdd',["$scope",function($s){
    
    /*获取所有菜单项*/
    $s.getAllMenus = function() {
        $.AJAX({
            type : "GET",
            url : config.basePath + "privilege/allprivilege",
            success : function(data){
                $s.permissionData = data.result;
                $s.$apply();
            }
        })
    }

    $s.getAllMenus();

    /*表单块收缩*/
    $s.packItemToggle = function($event){
        var icoType = $($event.target).html();
        if(icoType == "-"){$($event.target).html("+").parent("h2.z-toggle-h2").next("div").css("display","none");}
        else if(icoType == "+"){$($event.target).html("-").parent("h2.z-toggle-h2").next("div").css("display","block");}
    }

    /*三级checkbox点击切换选中状态，并勾选它的二级和顶级*/
    $(".role-permission-add").on("click",".item-level3",function(){
        var isProp = $(this).prop("checked");
        $(this).prop("checked",isProp);
        //判断上级是否勾选
        checkParent($(this).parent().siblings("label").children("input"))
        //判断顶级是否勾选
        checkParent($(this).parent().parent().parent().siblings("h2").children("input"))
    })
    /*二级checkbox点击切换选中状态，并全选其下的三级，并勾选它的顶级*/
    $(".role-permission-add").on("click",".item-level2",function(){
        var isProp = $(this).prop("checked");
        $(this).parent("label").siblings(".checkbox-item").children("input").prop("checked",isProp)
        //判断顶级是否勾选
        checkParent($(this).parent().parent().parent().siblings("h2").children("input"))
    })
    /*一级checkbox点击切换选中状态，并全选其下的二级*/
    $(".role-permission-add").on("click",".item-level1",function(){
        var isProp = $(this).prop("checked");
        $(this).parent("h2").siblings("div").find("input").prop("checked",isProp)
    })
    /*每个级别的checkbox，只要有一项勾选，则自动勾选*/
    function checkParent(targetParent){
        if(targetParent.parent().siblings("div").find("input").filter(function(index) {return $(this).prop("checked") == true;}).length>0){targetParent.prop("checked",true)}
        else{
            targetParent.prop("checked",false)
        }
    }

    /*提交表单*/
    $s.submitData = function() {
        var submitArr = [];
        $(".role-permission-add input[type=checkbox]").each(function() {
            if($(this).prop("checked")==true){submitArr.push($(this).attr("value"))}
        })
        if(!$s.name){
            Layer.alert({width:300,height:150,type:"msg",title:"请输入角色名称"});
            return false;
        }
        if(submitArr.length == 0){
            Layer.alert({width:300,height:150,type:"msg",title:"您尚未勾选权限"});
            return false;
        }
        Layer.confirm({width:320,height:160,title:"确认新增该角色？",header:"新建角色"},function() {
            var submitStr = submitArr.toString();
            $.AJAX({
                url : config.basePath + "privilege/addRole",
                data : {
                    name : $s.name,
                    remark : $s.remark,
                    privilegestr : submitStr,
                },
                success : function(data) {
                    Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                    setTimeout(function() {window.location.href="#/user/permission"},1500);
                }
            })
        })
    }
}])