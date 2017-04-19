/**
 * 将base改为basePath
 */
$(document).ready(function(){ 
	//点击新增投票按钮
	$("#add").click(function(){
		clearAddWinData()
		$("#viewVoteWin").fadeOut();
		$("#addVoteWin").fadeIn();
		
	})
	//保存
	$("#form1").submit(function() {
		var content = $("#content").val();
		var statDate = $("#statDate").val();
		var endDate = $("#endDate").val();
		var optionsContents= new Array();
		$("[optionsContent=optionsContent]").each(function() {
			if(($(this).val()).trim()!=""){
				optionsContents.push($(this).val());
			}
		})
		
		if(content==""){
			layer.msg("投票主题不能为空");
			return false;
		}
		if(statDate==""){
			layer.msg("开始时间不能为空");
			return false;
		}
		if(endDate==""){
			layer.msg("结束时间不能为空");
			return false;
		} 
		if(statDate<endDate){
			layer.msg("开始时间不能小于结束时间");
			return false;
		}
		if(optionsContents.length<2){
			layer.msg("发票选项最少填两项");
			return false;
		}
		var ajax_url = $(this).attr("action"); //表单目标 
		var ajax_type = $(this).attr('method'); //提交方法 
		//var ajax_data = $(this).serialize();
		var ajax_data = $(this).serialize()+"&optionsContents="+optionsContents.join("@;"); //表单数据 
		$.ajax({
			type : ajax_type, //表单提交类型 
			url : ajax_url, //表单提交目标 
			data : ajax_data, //表单数据
			success : function(data) {
				 layer.msg(data.msg,{
				     	shift: 0
				     }, function(){
				    	 if(data.code==0){
				    		 $("#addVoteWin").fadeOut();
					    	 window.location.reload();
				    	 }
				     }); 
			}
		});
		return false; //阻止表单的默认提交事件 
	});
    //删除
	$("[name=delBtn]").click(function(){
		var id = $(this).attr("themeId");
		var info = $(this).attr("info");
		layer.confirm('确定删除:'+info+'?', function(index){
			$.post(basePath+"/vote/del?id=" + id, function(result) {
				 layer.msg(result.msg,{
				     	shift: 0
				     }, function(){
				    	 window.location.href=basePath+"/vote/list";
				     }); 
			}, 'json');
			  layer.close(index);
			  return false;//阻止触发行事件showBg()
		}); 
		
	})
	
	//编辑      
	$(".edit_").click(function(){
		var data = eval("("+$(this).attr("vote")+")");
		var voteOptionsList = data.voteOptionsList;
		$("#content").val(data.content);
		$("#startDate").val(data.startDate);
		$("#endDate").val(data.endDate);
		if(data.remindTime=='0.5'){
			$("#remindTime").val(0.5);
		}else if(data.remindTime=='6'){
			$("#remindTime").val(6);
		}else if(data.remindTime=='12'){
			$("#remindTime").val(12);
		}else if(data.remindTime=='24'){
			$("#remindTime").val(24);
		}else {
			$("#remindTime").val(0);
		}
		
		$("#id").val(data.themeId);
		$("#imgUrl").val(data.imgUrl);
		$(".uploadimg").remove();
		$("#url").append("<div id=" + data.imgUrl + " class='uploadimg'> <img width=50 height=50 src='"+basePath+"/" + data.imgUrl + "' /> " +
                   "<a href=javascript:delimg('" + data.imgUrl + "','logo')>删除</a> </div>");
		
		//选择模式 #addVoteWin 
		if(data.pattern==2){
			$("input[name=pattern][value=2]").attr("checked",true);//多选
			 $(".check-num").show();
			 $("#patternCount").val(data.patternCount);
		}else{
			$("input[name=pattern][value=1]").attr("checked",true);//单选
		}
		//投票隐私
		if(data.votePrivacy==0){
			$("input[name=votePrivacy][value=0]").attr("checked",true);//公开
		}else{
			$("input[name=votePrivacy][value=1]").attr("checked",true);//匿名
		}
		//投票权限 
		if(data.votePrvlg==0){
			$("input[name=votePrvlg][value=0]").attr("checked",true);//区分居住地址
		}else{
			$("input[name=votePrvlg][value=1]").attr("checked",true);//全部用户
		}
	
	
	
	   	 var tbody = ""; 
 		 if(voteOptionsList.length==0){
 			 	var trs2 = "";
 			 	trs2 +='<div class="vote-input">'
 	     	 	trs2 +='<input optionsContent="optionsContent" class="my-choice" type="text" />'
 	    		trs2 +='</div>'
 	       		trs2 +='<div class="vote-input vote-input2">'
 	     	 	trs2 +='<input optionsContent="optionsContent" class="my-choice" type="text"/>'
 	    		trs2 +='</div>'
 	    		tbody += trs2;      
 		 }else{
 			$.each(voteOptionsList,function(n,vo) {   
	 			var content=vo.content;
	 	        var trs = ""; 
	 	       if(n==0){
	 	    	  trs +='<div class="vote-input">'
		 	     	 	trs +='<input optionsContent="optionsContent" class="my-choice" type="text" value="'+content+'"/>'
		 	    		trs +='</div>'
	 	       }else if(n==1){
	 	       		trs +='<div class="vote-input vote-input2">'
	 	     	 	trs +='<input optionsContent="optionsContent" class="my-choice" type="text" value="'+content+'"/>'
	 	    		trs +='</div>'
	 			}else{
	 				trs +='<div class="vote-input vote-input2">'
 	    			trs +='<input optionsContent="optionsContent"  class="my-choice" type="text" value="'+content+'"/>'
 	    			trs +='<span onclick="addOptions()" class="delOptions">删除</span></div>'
	 			}
	 	    		
		 	    tbody += trs;   
	 	    });  
 		 }
	  
		$("#voteWin_title").text("编辑投票信息");
		$("#viewVoteWin").fadeOut();
		$("#addVoteWin").fadeIn();
		
		//投票选项列表input
 		$("#optionsDiv .vote-input").remove();
 		$("#optionsDiv .clearfix").before(tbody);
	})
		
	//right 删除投票
	$("#v_del").click(function(){
		var id =  $("#v_themedId").val();
		var info =$("#v_content").text();
		layer.confirm('确定删除:'+info+'?', function(index){
			$.post(basePath+"/vote/del?id=" + id, function(result) {
				 layer.msg(result.msg,{
				     	shift: 0
				     }, function(){
				    	 window.location.href=basePath+"/vote/list";
				     }); 
			}, 'json');
			  layer.close(index);
		}); 
	})
	
	//点击参与投票成员--show界面
	$("#v_votePeople").click(function(){
		var v_votePeopleSum=$(this).text();
		if(v_votePeopleSum=='参与投票成员（0）'){
			 layer.msg("暂时没有投票成员");
			return;
		}
		$("#bg").css({
        	display: "block", height: $(document).height()
    	});
		var $box = $('.box3');
		$box.css({
		    //设置弹出层距离左边的位置
		    left: ($("body").width() - $box.width()) / 2 - 10 + "px",
		    //设置弹出层距离上面的位置
		    top: ($(window).height() - $box.height()) / 3 + $(window).scrollTop() + "px",
		    display: "block"
		});
		var id =  $("#v_themedId").val();
		$.ajax({
			type : "post", //表单提交类型 
			url : basePath+"/vote/getVoteUsersById", //表单提交目标 
			data : {id:id}, //表单数据
			success : function(data) {
				 var optionCounts=data.optionCounts;
				 var voteUserList=data.voteUserList;
				 $("#bg_voteUserCount").text("参与投票"+optionCounts+"人");
				//console.log(data);
			 	var tbody = ""; 
		 		$.each(voteUserList,function(n,voteUser) {   
		 			var userName=voteUser.user.name;
		 			var userLogo=voteUser.user.logo;
		 			var createDateStr=voteUser.createDateStr;
		 			//alert(userName);
		 	        var trs = "";  
						trs +='<div class="voter-photo">'
							trs +='<div class="vote-img">'
								trs +='<img src="'+basePath+'/'+userLogo+'" />'
							trs +='</div>'
							trs +='<div class="vote-mz">'
								trs +='<p id="bg_voteUserName">'+userName+'</p>'
								trs +='<p style="width:200px;" id="bg_voteUserCreateDate">'+createDateStr+'</p>'
							trs +='</div>'
						trs +='</div>'
						
						tbody += trs;     
			 	    
		 	  	 });  
				 $("#bg_contentDiv").html(tbody);
			 }
		}); 
	});
   
	//点击选项行事件  --参与投票选项详情界面
	$("[name=optionsLi]").live("click",function(){
		var id =  $("#v_themedId").val();
		var optionsId =$(this).attr("optionsId");
		$("#options_bg").css({
        	display: "block", height: $(document).height()
    	});
		var $box = $('.box4');
		$box.css({
		    //设置弹出层距离左边的位置
		    left: ($("body").width() - $box.width()) / 2 - 10 + "px",
		    //设置弹出层距离上面的位置
		    top: ($(window).height() - $box.height()) / 3 + $(window).scrollTop() + "px",
		    display: "block"
		});
		
		$.ajax({
			type : "post", //表单提交类型 
			url : basePath+"/vote/getVoteOptionsById", //表单提交目标 
			data : {themeId:id,optionsId:optionsId}, //表单数据
			success : function(data) {
				 var voteOptionsCounts=data.voteOptionsCounts;
				 var voteOptionsList=data.voteOptionsList;
				 $("#optBg_optionCount").text("投此选项的人（"+voteOptionsCounts+"）");
				//console.log(data);
			 	var tbody = ""; 
			 	var optionName="";
		 		$.each(voteOptionsList,function(n,obj) {   
		 			var userName=obj.user.name;
		 			var userLogo=obj.user.logo;
		 			var createDateStr=obj.createDateStr;
		 			optionName=obj.content;
		 	        var trs = "";  
						trs +='<div class="voter-photo">'
							trs +='<div class="vote-img">'
								trs +='<img src="'+basePath+'/'+userLogo+'" />'
							trs +='</div>'
							trs +='<div class="vote-mz">'
								trs +='<p id="bg_voteUserName">'+userName+'</p>'
								trs +='<p style="width:200px;" id="bg_voteUserCreateDate">'+createDateStr+'</p>'
							trs +='</div>'
						trs +='</div>'
						tbody += trs; 
			 	   
		 	  	 });  
		 		 $("#optBg_optionName").text("选项:"+optionName);
				 $("#optBg_contentDiv").html(tbody);
			 }
		}); 
	});
	
	  //点击关闭按钮的时候，遮罩层关闭
   	 $(".close").click(function () {
        $("#bg,#options_bg,.box3,.box4").css("display", "none");
   	 });
  
	$(".add-choice").click(function() { //添加操作 				
		$(".clearfix").before('<div class="vote-input vote-input2"><input optionsContent="optionsContent"  class="my-choice" type="text"><span onclick="addOptions()" class="delOptions">删除</span></div>'); //在p标签前加入所要生成的代码

	});
	$("#two-choice").click(function() {
		$("#patternCount").val(2);
		$(".check-num").show();
	});
	$("#one-choice").click(function() {
		$("#patternCount").val('');
		$(".check-num").hide();
	});
	$(".delOptions").live("click" ,function() { //通过 live() 方法附加的事件处理程序适用于匹配选择器的当前及未来的元素（比如由脚本创建的新元素） 
		$(this).parent().remove(); //移除当前点击元素的父元素 
	});
	$("#patternCount").focus(function() {
		addOptions();
	});
});

//行事件,查看右边详情
function showBg(themeId) {
	$("#addVoteWin").fadeOut();
	$.ajax({
		type : "post", //表单提交类型 
		url : basePath+"/vote/getVoteById", //表单提交目标 
		data : {id:themeId}, //表单数据
		success : function(data) {
			//var obj = eval("("+data.vote+")");
			 var obj=data.vote;
			 //console.log(obj);
			
			 $("#v_themedId").val(obj.themeId);
			 $("#v_content").text(obj.content);//发布主题
			 if(obj.imgUrl!=""){
				 $("#v_imgUrl").show();
				 $("#v_imgUrl").attr("src",basePath+"/"+obj.imgUrl);//发布主题图片
			 }else{
				 $("#v_imgUrl").hide();
			 }
			 $("#v_user_logo").attr("src",basePath+"/"+obj.user.logo);
			 $("#v_userName").text(obj.user.name);//创建者
			 $("#v_createDate").text(obj.createDateStr);//发布时间
			 //匿名，还是公开
			 if(obj.votePrivacy==0){
				 $("#v_votePrivacy").text("(匿名投票)");
			 }else{
				 $("#v_votePrivacy").text("(公开投票)");
			 }
			//单选，还是多选
			 if(obj.pattern==2){
				 $("#v_pattern").text("多选");
				 if(obj.patternCount!=''&&obj.patternCount!=0&&obj.votePrivacy==0){
					 $("#v_votePrivacy").text(",最多"+obj.patternCount+"项(匿名投票)");
				 }else{
					 $("#v_votePrivacy").text(",最多"+obj.patternCount+"项(公开投票)");
				 }
			 }else{
				 $("#v_pattern").text("单选");
			 }
			 //投票总数=
			 var sumVoteCount=obj.voteCount;
			 $("#v_sumVoteCount").text(obj.voteCount);
			 $("#v_votePeople").text("参与投票成员（"+obj.voteUserCount+"）");
			// $("#v_endDate").text(endDate.substring(0,endDate.lastIndexOf(':').length));
			 $("#v_endDate").text(obj.endDate);
			 //状态
			 if(obj.status==0){
				 $("#v_status").text("未开始");
			 }else if(obj.status==1){
				 $("#v_status").text("进行中");
			 }else if(obj.status==2){
				 $("#v_status").text("已结束");
			 }
			 //voteCount=2 votePrivacy=1 votePrvlg=1 status=1 optionsList
			    var tbody = ""; 
		 		$.each(obj.voteOptionsList,function(n,vo) {   
		 			var optionsCount=vo.optionsCount;
		 			var percent=GetPercent(optionsCount,sumVoteCount);
		 			var style_cur="";
		 			var style_name="";
		 			if(optionsCount>0){
		 				style_cur="cursor: pointer;";
		 				style_name="optionsLi";
		 			}
		 	        var trs = "";  
			 	    trs +='<div name="'+style_name+'" optionsId="'+vo.optionsId+'" style="'+style_cur+'" class="skillbar clearfix " data-percent="'+percent+'">'
			 	    trs +='<div class="skillbar-title"><span name="optionsContent">'+vo.content+'</span></div>'
			 	    trs +='<div class="skillbar-bar" ></div>'
			 	    trs +='<div class="skill-bar-percent" name="optionsCount">'+vo.optionsCount+'票</div>'
			 	    trs +='</div> '
			 	    tbody += trs;     
		 	  });  
			 
			  $("#optionsList").html(tbody);
			 
				jQuery('.skillbar').each(function(){
					jQuery(this).find('.skillbar-bar').animate({
						width:jQuery(this).attr('data-percent')
					},2000);
				});
		}
	});
	$("#viewVoteWin").show();
}
//清空新增页面数据
function clearAddWinData(){
	$("#content").val('');
	$("#startDate").val('');
	$("#endDate").val('');
	$("#remindTime").val(0);
	
	$("#id").val('');
	$("#imgUrl").val('');
	$(".uploadimg").remove();
	//$("#url").html('');
	
	//选择模式 #addVoteWin 
	$("input[name=pattern][value=1]").attr("checked",true);//单选
	//投票隐私
	$("input[name=votePrivacy][value=1]").attr("checked",true);//公开
	//投票权限 
	$("input[name=votePrvlg][value=1]").attr("checked",true);//全部用户

   	 var tbody = ""; 
	 	var trs2 = "";
	 	trs2 +='<div class="vote-input">'
    	trs2 +='<input optionsContent="optionsContent" class="my-choice" type="text" />'
   		trs2 +='</div>'
       trs2 +='<div class="vote-input vote-input2">'
    	trs2 +='<input optionsContent="optionsContent" class="my-choice" type="text"/>'
   		trs2 +='</div>'
   		tbody += trs2;      
  
	$("#voteWin_title").text("新增投票信息");
	//投票选项列表input
		$("#optionsDiv .vote-input").remove();
		$("#optionsDiv .clearfix").before(tbody);
}
//计算百分比
function GetPercent(num, total) { 
	num = parseFloat(num); 
	total = parseFloat(total); 
	if (isNaN(num) || isNaN(total)) { 
	return "-"; 
	} 
	return total <= 0 ? "0%" : (Math.round(num / total * 10000) / 100.00 + "%"); 
} 

$(function () {
	 $('#startDate,#endDate').datetimepicker({
			 lang:  'ch',
			 minDate:'-1970/01/01'
		});  
   $('#file_upload').uploadify({
        'swf': basePath+'/resources/uploadify/uploadify.swf', //指定上传控件的主体文件，默认‘uploader.swf’
        'uploader': basePath+'/sys/file/uploadFile', //指定服务器端上传处理文件
        'auto': true, //手动上传
        'buttonImage': basePath+'/resources/uploadify/uploadify-browse.png', //浏览按钮背景图片
        'multi': false, //单文件上传
        'fileTypeExts': '*.gif; *.jpg; *.png;', //允许上传的文件后缀
        'fileSizeLimit': '300MB', //上传文件的大小限制，单位为B, KB, MB, 或 GB
        'successTimeout': 30, //成功等待时间
        'queueSizeLimit': 1,
        'fileObjName': 'file',
        'onUploadStart': function (file) {  
            $("#file_upload").uploadify("settings", "formData", {'type': 'vote'});  
        },
        'onUploadSuccess': function (file, data) {//每成功完成一次文件上传时触发一次
            data = eval("(" + data + ")");
            $(".uploadimg").remove();
            $('#url').append("<div id=" + data.value + " class='uploadimg'> <img width=50 height=50 src='"+basePath+"/" + data.value + "'/> " +
                    "<a href=javascript:delimg('" + data.value + "','imgUrl')>删除</a> </div>");
            $("#imgUrl").val(data.value);
        },
        'onUploadError': function (file, data) {//当上传返回错误时触发
            $('#url').append("<li>" + data + "</li>");

        }
    });
});

function delimg(obj, tval) {
    var url = basePath+"/sys/file/delimg";
    $.post(url, {'imgpath': obj}, function (data) {
        if (data.code == 0) {
            document.getElementById(obj).remove();
            document.getElementById(tval).value = "";
        }
    });
}
        
function addOptions() {
	var dv_sumNum = 0;
	var dv_num = 0;
	var min=2;              
	$(".my-choice").each(function() {
			if($(this).val() != "") {
				dv_num++;
				dv_sumNum++;
			}else{
				dv_num = min;
			}
		})
	if(dv_sumNum<2){
		dv_num=2;
	}else{
		dv_num=dv_sumNum;
	}	
	var text = document.getElementById("patternCount");
	text.onkeyup = function() {
		this.value = this.value.replace(/\D/g, '');
		if(text.value > dv_num) {
			text.value = dv_num;
		}else if(text.value < min){
			text.value = min;
		}
	}				

}
