function getRandomNum(Min,Max) {   
    var Range = Max - Min;   
    var Rand = Math.random();   
    return(Min + Math.round(Rand * Range));   
}  

function formValid(formId) {
    return $('#' + formId).data('bootstrapValidator').isValid();
}

function resetForm(formId) {
    $('#' + formId).data('bootstrapValidator').resetForm(); 
}

function validateDate(formId,attrId) {
    $('#' + formId).data('bootstrapValidator').updateStatus(attrId, 'NOT_VALIDATED').validateField(attrId);
}

function initCheckDate(obj) {
    laydate({format: 'YYYY-MM-DD', 
            isclear: false,
            choose: function(datas){
                validateDate($(obj).parents('form').attr('id'),obj.name)}
            });
}

function initDate() {
    laydate({format: 'YYYY-MM-DD', isclear: false});
}

/*firame 上传文件函数*/
function createFileForm(json){
    var html='<div id="createFileHtml"><form enctype="multipart/form-data" target="fileIframeName" method="post" id="uploadForm" class="hidden">\
    <input type="file" name="file" id="expInputFile"><input type="text" name="token" id="qiniutoken"></form>\
    <iframe class="hidden" name="fileIframeName"></iframe></div>';
    if(!$('#createFileHtml').length){
        $('body').append(html);
    };
    $('#expInputFile').click();
    $('#expInputFile').change(function(){
        json.callback();
    });
}

/*FormData 上传文件函数*/
function cerateFileFormData(json){
    var filename=json.filename?json.filename:'filename'
    var html='<div id="createFileHtml" class="hidden"><form enctype="multipart/form-data" id="uploadForm">\
    <input type="file" name="'+filename+'" id="expInputFile"></div></form>';
    if(!$('#createFileHtml').length){
        $('body').append(html);
    };
    $('#expInputFile').click(); 
    $('#expInputFile').change(function(){
        win.showLoading();
        var formData = new FormData($("#uploadForm")[0]); 
        if(json.data){
            for(var i in json.data){
                formData.append(i, json.data[i]);
            };
        };
        $.FileAJAX({
            url:json.url,
            data:formData,
            success:function(data){
                $('#createFileHtml').remove();
                json.callback(data);
            },
            error:function(data){
                console.log(data)
                $('#createFileHtml').remove();
                if(json.errorMsg){
                    json.error(data);
                }else{
                    Layer.alertMor({width:400,height:230,type:"msg",type:"error",title:data.msg});
                };
            }
        });
    });
}

/*获取上传input file对象的URL 图片预览时使用*/
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

//字符串占位符
String.prototype.format = function() {
    if(arguments.length==0) return this;
    for(var s=this, i=1; i<=arguments.length; i++)
        s=s.replace(new RegExp("\\{"+i+"\\}","g"), arguments[i-1]);
    return s;
};

//获取七牛云上传文件token
function getQiniuToken() {
    var token;
    $.AJAX({
        url : config.basePath + "qiniuToken",
        async: false,
        success : function(data){
            token = data.result;
        }
    });
    return token;
}

//七牛云上传,返回上传文件路径
function uploadFile() {
    var filename;
    $.FileAJAX({
        url : "http://up.qiniu.com",
        data : new FormData(document.getElementById('uploadForm')),
        async : false,
        success:function(data){
            filename = data.key;
        }
    });
    return filename;
}