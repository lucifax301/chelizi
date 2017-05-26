//关闭加载层
win.hideLoading();
/***************************************************用户登陆*********************************************************************/
$(function(){
    var validObj = $("#loginForm").Validform({
        tiptype:3,
        showAllError:true,
        btnSubmit:"#admin-submit",
        callback:function(){
            $.AJAX({
                url:config.basePath+"login/check",
                type:"post",
                data:{
                    account:$('#username').val().trim(),
                    password:$('#password').val().trim()
                },
                success:function(data){
                    //设置浏览器cookie
                    localStorage.setItem("school-username",$('#username').val().trim());
                    //设置登录后的跳转首页 iframeSrc
                    //window.location.href=sessionStorage.getItem("school-url") || config.homeUrl;
                    //获取用户权限列表
                    $.AJAX({
                        type:"get",
                        async:"false",
                        url:config.basePath+"privilege/getUserMenu",
                        success:function(data){
                            console.log(sessionStorage.getItem("school-url"))
                            window.menuPer = JSON.parse(data.data)
                            var tempNavItemUser = "";
                            var tempi="";
                            console.log(window.menuPer.list)
                            for(var i=0;i<window.menuPer.list.length;i++){
                                console.log(window.menuPer.list[i].id)
                                //遍历到个人设置页，则把它剔出来，放到最后面
                                if(parseInt(window.menuPer.list[i].id)==150300){
                                    tempNavItemUser = window.menuPer.list[i];
                                    tempi = i;
                                }
                                //遍历到排班项，则判定为考场端，把默认面从 datas/data-statistics.html改成arrange-exam-class.html
                                if(parseInt(window.menuPer.list[i].id)==2030000){
                                    sessionStorage.setItem("school-url","arrange-exam-class.html")
                                    sessionStorage.setItem("isExamGround","1");
                                }
                            }
                            if(sessionStorage.getItem("isExamGround")>0){
                                window.location.href="arrange-exam-class.html";
                            }
                            window.menuPer.list.splice(tempi,1);
                            window.menuPer.list.push(tempNavItemUser);
                            sessionStorage.accessData2 = JSON.stringify(window.menuPer);
                            console.log("已从登录页请求到权限列表并缓存：如下")
                            console.log(window.menuPer)
                            var lililist = window.menuPer.list;
                            /*如果缓存中存在记忆URL,则登录后尽量跳到记忆页面，或个人设置页面*/
                            if((sessionStorage.getItem("school-url")!="")&&(sessionStorage.getItem("school-url")!=null)){
                                /*缓存的记忆页面文件静态名*/
                                var liliurl = sessionStorage.getItem("school-url").match(/(?=[^\/]+$).+(?=\.html)/)+".html";
                                liliurl = (liliurl=="data-statistics.html")?"datas/data-statistics.html":liliurl;
                                //console.log("已有缓存的记忆页面:"+liliurl)
                                var jumptoliliurl = true;//默认认为记忆中的历史页面无权限此处原为false，因考场临时需要改为true
                                sessionStorage.accessData2 = JSON.stringify(window.menuPer);
                                for(var i=0; i<lililist.length; i++){
                                    //console.log("遍历第"+i+"个权限URL:"+lililist[i].url)
                                    if(liliurl == lililist[i].url){
                                        //console.log("记忆中的历史页面有权限");
                                        jumptoliliurl = true; continue;
                                    }
                                }
                                window.location.href=jumptoliliurl? liliurl :"change-password.html";

                            }
                            /*如果缓存中没有记忆URL，则直接跳到它有权限的第一个页面页面，或个人设置页面*/
                            else{
                                //console.log("没有缓存的静态页面")
                                //if(lililist.length>0){
                                //    console.log("跳到权限值的第一个页面")
                                //    window.location.href = lililist[0].url;//至少有一个权限的页面
                                //}
                                //else{
                                    console.log("没有任何权限值，跳到个人设置页")
                                    window.location.href = "change-password.html";
                                //}
                            }

                        }
                    })
                }
            })
            return false;
        }
    })

})

/*展示用户名*/
$('#username').val(localStorage.getItem("school-username"));

/*登录*/
//$('#admin-submit').click(function(){ login(); });
$(document).keydown(function (event) { if(event.keyCode==13){login();} });


/*登录函数*/
function login(){
    $("#loginForm").Validform({tiptype:2,callback:function(){
        var username = $('#username').val().trim();
        var password = $('#password').val().trim();
        win.showLoading();
        $.AJAX({
            type:"post",
            url:config.basePath+"login/check",
            data:{account:username,password:password},
            success:function(data){
                //设置浏览器cookie
                localStorage.setItem("school-username",username);
                //设置登录后的跳转首页 iframeSrc
                window.location.href=sessionStorage.getItem("school-url") || config.homeUrl;
            }
        });
        return false;
    }});
}




