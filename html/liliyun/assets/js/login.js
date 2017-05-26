//关闭加载层
win.hideLoading();
/***************************************************用户登陆*********************************************************************/
/*清空缓存数据*/

sessionStorage.setItem("accessData","");
/*展示用户名*/
$('#username').val(localStorage.getItem("lili-username"));

/*登录*/
$('#admin-submit').click(function(){ login(); });
$(document).keydown(function (event) { if(event.keyCode==13){login();} });

/*登录函数*/
function  login(){
	var username = $('#username').val().trim();
	console.log(username.length)
	var password = $('#password').val().trim();
	if(!regCombination('*',[2,20]).test(username)){
		Layer.alert({width:300,height:150,type:"error",title:"请填写用户名！"});
		return false;
	}
	if(!regCombination('*',[2,20]).test(password)){
		Layer.alert({width:300,height:150,type:"error",title:"请填密码！"});
		return false;
	}
	win.showLoading();
	$.AJAX({
		type:"post",
		url:config.basePath+"login/check",
		data:{account:username,password:password},
		success:function(data){
			//设置浏览器cookie
			localStorage.setItem("lili-username",username);
            //获取用户权限列表
            $.AJAX({
                type:"get",
                async:"false",
                url:config.basePath+"privilege/getUserMenu",
                success:function(data){
                    window.menuPer = JSON.parse(data.data)
                    sessionStorage.accessData = JSON.stringify(window.menuPer);
                    console.log("已从登录页请求到权限列表并缓存：如下")
                    console.log(window.menuPer)
                    var lililist = window.menuPer.list;
                    /*如果缓存中存在记忆URL,则登录后尽量跳到记忆页面，或个人设置页面*/
                    if((sessionStorage.getItem("lili-url")!="")&&(sessionStorage.getItem("lili-url")!=null)){
                        /*缓存的记忆页面文件静态名*/
                        var liliurl = sessionStorage.getItem("lili-url").match(/(?=[^\/]+$).+(?=\.html)/)+".html";
                        liliurl = (liliurl=="data-statistics.html")?"datas/data-statistics.html":liliurl;
                        //console.log("已有缓存的记忆页面:"+liliurl)
                        var jumptoliliurl = false;//默认认为记忆中的历史页面无权限
                        sessionStorage.accessData = JSON.stringify(window.menuPer);
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
                        console.log("没有缓存的静态页面")
                        if(lililist.length>0){
                            console.log("跳到权限值的第一个页面")
                            window.location.href = lililist[0].url;//至少有一个权限的页面
                        }
                        else{
                            console.log("没有任何权限值，跳到个人设置页")
                            window.location.href = "change-password.html";
                        }
                    }

                }
            })
		}
	});
}



