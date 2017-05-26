//解析URL获取参数   如 getUrl（'page'）
function getUrl(id){
	var nowurl = window.location.href.split('&&');
	for(var i in nowurl){
		if(nowurl[i].indexOf(id+'=')>-1){
			var fenge = nowurl[i].split('=');
			return decodeURI(fenge[1]);
		}
	}
}

function getCurrentUrl(){
	var nowurl = window.location.href.split('#');
	return nowurl[1];
}

function Page (json){
	this.nowPage=parseInt(json.nowPage);   //当前页
	this.parent=json.parent;  //分页内容所放的div元素
	this.call=json.callback;
	this.totalCount=json.totalCount;
	this.pageSize=json.pageSize;
	this.totalPage=Math.ceil(this.totalCount/this.pageSize); //总页数 
	this.html="";
	this.type=json.type || 1;
	this.setting=json.setting || {
		defaultPage:5,         //默认展示的页数
		firstPageText:"首页",  //第一页的字 （可以是：Home）
		prevPageText:"上一页", //上一页的字 
		nextPageText:"下一页", //下一页的字
		lastPageText:"最后一页"    //尾页的字
	};
	this.centPage=Math.ceil(this.setting.defaultPage/2);  //中间页
	this.search = json.search;//搜索值
	this.nowURL = location.href.split('#');
	
	this.init(); //初始化

	
}
//初始化函数
Page.prototype.init=function(){
	this.parent.html(""); //清空
	this.totalPageText(); //页数显示信息
	this.firstPage();  //首页
	this.prevPageText(); //上一页
	this.everyPage();  //分页
	this.nextPage();   //下一页
	this.lastPage();   //尾页
	this.pageSearch(); //搜索页
	this.parent.append(this.html);
	this.callback();
}

//循环页数
Page.prototype.everyPage=function(){
	if(this.type == 1){
		if(this.totalPage<=this.setting.defaultPage){
			for(var i=1;i<=this.totalPage;i++){
				if(this.nowPage==i){
					this.html+="<a href='#"+i+"' class='btn btn-default btn-sm btn-danger nowPage'>"+i+"</a>";
				}else{
					this.html+="<a href='#"+i+"' class='btn btn-default btn-sm'>"+i+"</a>";
				}	
			}	
		}else{
			console.log(this.setting.defaultPage,this.centPage);
			for(var i=1;i<=this.setting.defaultPage;i++){
				var page1 = this.nowPage-this.centPage+i; 
				var page2 = this.totalPage-this.setting.defaultPage+i;
				console.log(page1,page2);
				if(this.nowPage<this.centPage){
					if(this.nowPage==i){
						this.html+="<a href='#"+i+"' class='btn btn-default btn-sm btn-danger nowPage'>"+i+"</a>";
					}else{
						this.html+="<a href='#"+i+"' class='btn btn-default btn-sm'>"+i+"</a>";
					}	
				}else if(this.nowPage>this.totalPage-this.centPage){
					if(this.setting.defaultPage-(this.totalPage-this.nowPage)==i){
						this.html+="<a href='#"+this.nowPage+"' class='btn btn-default btn-sm btn-danger nowPage'>"+this.nowPage+"</a>";
					}else{
						this.html+="<a href='#"+page2+"' class='btn btn-default btn-sm'>"+page2+"</a>";
					}
				}else {
					if(this.centPage==i){
						this.html+="<a href='#"+page1+"' class='btn btn-default btn-sm btn-danger nowPage'>"+page1+"</a>";
					}else{
						this.html+="<a href='#"+page1+"' class='btn btn-default btn-sm'>"+page1+"</a>";
					}	
				}	
			}
		}
	}else if(this.type == 2){
		if(this.totalPage<=this.setting.defaultPage){
			for(var i=1;i<=this.totalPage;i++){
				if(this.nowPage==i){
					this.html+="<a href='javascript:' data-page='"+i+"' class='btn btn-default btn-sm btn-danger nowPage'>"+i+"</a>";
				}else{
					this.html+="<a href='javascript:' data-page='"+i+"' class='btn btn-default btn-sm'>"+i+"</a>";
				}	
			}	
		}else{
			for(var i=1;i<=this.setting.defaultPage;i++){
				var page1 = this.nowPage-this.centPage+i; 
				var page2 = this.totalPage-this.setting.defaultPage+i;
				
				if(this.nowPage<this.centPage){
					if(this.nowPage==i){
						this.html+="<a href='javascript:' data-page='"+i+"' class='btn btn-default btn-sm btn-danger nowPage'>"+i+"</a>";
					}else{
						this.html+="<a href='javascript:' data-page='"+i+"' class='btn btn-default btn-sm' onclick='pageClick()'>"+i+"</a>";
					}	
				}else if(this.nowPage>this.totalPage-this.centPage){
					if(this.setting.defaultPage-(this.totalPage-this.nowPage)==i){
						this.html+="<a href='javascript:' data-page='"+this.nowPage+"' class='btn btn-default btn-sm btn-danger nowPage'>"+this.nowPage+"</a>";
					}else{
						this.html+="<a href='javascript:' class='btn btn-default btn-sm' data-page='"+page2+"'>"+page2+"</a>";
					}
				}else {
					if(this.centPage==i){
						this.html+="<a href='javascript:' data-page='"+page1+"' class='btn btn-default btn-sm btn-danger nowPage'>"+page1+"</a>";
					}else{
						this.html+="<a href='javascript:' class='btn btn-default btn-sm' data-page='"+page1+"'>"+page1+"</a>";
					}	
				}	
			}
		}
	}
}

//首页
Page.prototype.firstPage=function(){
	if(this.nowPage>this.centPage && this.totalPage>=this.setting.defaultPage+1){
		if(this.type == 1){
			this.html+="<a href='#1' class='btn btn-default btn-sm'>"+this.setting.firstPageText+"</a>";
		}else if(this.type == 2){
			this.html+="<a href='javascript:' data-page='1' class='btn btn-default btn-sm'>"+this.setting.firstPageText+"</a>";
		}
		
	}
}
//上一页
Page.prototype.prevPageText=function(){
	if(this.nowPage>=2){
		if(this.type == 1){
			this.html+="<a href='#"+(this.nowPage-1)+"' class='btn btn-default btn-sm'>"+this.setting.prevPageText+"</a>";
		}else if(this.type == 2){
			this.html+="<a href='javascript:' data-page='"+(this.nowPage-1)+"' class='btn btn-default btn-sm'>"+this.setting.prevPageText+"</a>";
		}
	}
}

//下一页
Page.prototype.nextPage=function(){
	if(this.totalPage - this.nowPage >= 1){
		if(this.type == 1){
			this.html+="<a href='#"+(this.nowPage+1)+"' class='btn btn-default btn-sm'>"+this.setting.nextPageText+"</a>";
		}else if(this.type == 2){
			this.html+="<a href='javascript:' data-page='"+(this.nowPage+1)+"' class='btn btn-default btn-sm'>"+this.setting.nextPageText+"</a>";
		}
	}
}

//尾页
Page.prototype.lastPage=function(){
	if(this.totalPage-this.nowPage>=this.centPage && this.totalPage > this.setting.defaultPage){
		if(this.type == 1){
			this.html+="<a href='#"+this.totalPage+"' class='btn btn-default btn-sm'>"+this.setting.lastPageText+"</a>";
		}else if(this.type == 2){
			this.html+="<a href='javascript:' data-page='"+this.totalPage+"' class='btn btn-default btn-sm'>"+this.setting.lastPageText+"</a>";
		}
	}
}

//总共页数
Page.prototype.totalPageText=function(){
	this.html+="<span class='page-msg'>共 <span>"+this.totalCount+"</span> 条记录  共 <span>"+this.totalPage+"</span> 页</span>&nbsp;&nbsp;&nbsp;&nbsp;";
}

//搜索页数
Page.prototype.pageSearch=function(){
	if(this.totalPage>this.setting.defaultPage){
    	this.html+="<span class='Page-search-span'>跳转到<input type='text' class='btn btn-default btn-sm' style='width:60px;margin:auto 5px;margin-top:-5px' id='Page-search'>页 </span><button id='pageSearch' class='btn btn-default btn-sm'>确定</button>" 
    }
}

//点击分页执行的函数
Page.prototype.callback=function(){
	var This=this;
	if(this.type==1){
		//按hash值改变分页
	    this.parent.find("a").bind("click",function(){
	    	
	        var nowPage=$(this).attr("href").substring(1);
	        var nowURL = location.href.split('#');
	        if(this.search || getUrl('search')){
	        	location.hash= nowURL[1] +"#&&page="+nowPage + "&&search=" + getUrl('search');
	        }else{
	        	location.hash= nowURL[1] +"#&&page="+nowPage;
	        }
	        return false;
	    }); 
	    //输入搜索查询
	    this.parent.find("#pageSearch").click(function(){
	    	
	        var nowPage=This.parent.find('#Page-search').val();	        
	        var nowURL = location.href.split('#');
	        if(this.search || getUrl('search')){
	        	location.hash= nowURL[1] +"#&&page="+nowPage + "&&search=" + getUrl('search');
	        }else{
	        	location.hash= nowURL[1] +"#&&page="+nowPage;
	        }
	    });
	}else if(this.type==2){
		//给每个a绑定事件
	    this.parent.find("a").bind("click",function(){
	        var nowPage=$(this).attr("data-page");
	        This.getPage(nowPage);
	        return false;
	    }); 
	    //输入搜索查询
	    this.parent.find("#pageSearch").click(function(){
	        var nowPage=This.parent.find('#Page-search').val();	       
	        if(parseInt(nowPage)>0){
	        	This.getPage(nowPage);
	        } 
	    });
	}
    
}

/*请求分页函数*/
Page.prototype.getPage=function(nowPage){
    this.parent.html(""); //清空
    //写入分页
    new Page({
            parent:this.parent,
            nowPage:nowPage,
            type:this.type,
            totalPage:this.totalPage,
            pageSize:this.pageSize,
            totalCount:this.totalCount,
            setting: this.setting,
            callback:this.call
    });
    console.log(nowPage);
    this.call(nowPage);   //传参
}

