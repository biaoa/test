var basePath;
$(document).ready(function(){ 
	 basePath=$("#basePath").val();
	
	//新增话题
	$("#addTopicBtn").click(function() {
		window.location.href =basePath+"/topic/toAddTopic";
	});
	
	//删除
	$(".del").click(function() {
		var id = $(this).attr("val");
		layer.confirm('确定删除:?', function(index){
			$.post(basePath+"/topic/delTopic/" + id, function(data) {
				layer.msg(data.msg,{
			     	shift: 0
			     }, function(){
			    	 location.reload();
			     });
				;
			}, 'json');
			  layer.close(index);
		}); 
	
	});
		
	//topicComment 评论/回复
	$("#replySureBtn").click(function() {
		var topicId=$("#topicId").val();
		var replyUserId=$("#replyUserId").val();	
		var isMain=$("#isMain").val();	
		var content=$("#replyContent").val();	
		var topicTypeId=$("#comm_topicTypeId").val();
		$.ajax({
			type : "post",
			url :basePath+"/topic/addTopicComment",
			data : {'topicId':topicId,
					'toUserId':replyUserId,
					'isMain':isMain,
					'content':content},
			success : function(data) {
				layer.msg(data.msg,{
			     	shift: 0
			     }, function(){
//			    	 $(".close-x").click();
			    	 var comment=data.paramsMap.comment;
			    	 //追加刚评论内容样式
			    	 if(comment!=null){
			    		 var fromUserNameImg=comment.fromUserNameImg;
			    		 var fromUserId=comment.fromUserId;
			    		 var fromUserName=comment.fromUserName;
			    		 var toUserName=comment.toUserName;
			    		 var createTime=comment.createTime;
			    		 closeTextarea();
			    		 var tbody = "";  
			    		 var trs = ""; 
			    		 var li_class = ""; 
			    		 var n=1;
			    		 var li_html=$(".first_no_border").html();
			    		 if(li_html==null||li_html==undefined){
				 	        	li_class="first_no_border";
			    		 }
			    		 if(isMain==1){
							trs +='<li class="'+li_class+'">'
								trs +='<div class="person-img">'
								trs +='<img src="'+basePath+'"/'+fromUserNameImg+'" />'
								trs +='</div>'
								trs +='<div class="reply-name">'
								trs +='<span class="person-name" fromUserId='+fromUserId+' fromUserName='+fromUserName+'>'+fromUserName+'</span> ：'
								trs +='<span class="reply-nr">'+content+'</span>'
								trs +='<div class="reply-time">'
								trs +='<span  onclick=\'disabledPublish("'+fromUserId+'","'+fromUserName+'","'+topicTypeId+'")\' class="lzl_time stop-btn-hf"><i class="fa fa-times-circle"></i>禁用发布</span>'
								trs +='<span  onclick=\'disabledReply("'+fromUserId+'","'+fromUserName+'","'+topicTypeId+'")\' class="lzl_time stop-btn-fb"><i class="fa fa-warning"></i>禁用回复</span>'	
								trs +='<span   class="lzl_time">'+formatDate(createTime, "yyyy-MM-dd HH:mm")+'</span>'
								trs +="<a href='javascript:void(0)' fromUserId='"+fromUserId+"' fromUserName='"+fromUserName+"'  onclick=\"replayShow("+fromUserId+",'"+fromUserName+"')\" class='lzl_s_r'>回复</a>"
								trs +='</div>'
								trs +='</div>'
								trs +='</li>'
						}else{
							trs +='<li>'
								trs +='<div class="person-img">'
								trs +='<img src="'+basePath+'"/'+fromUserNameImg+'" />'
								trs +='</div>'
								trs +='<div class="reply-name">'
								trs +='<span class="person-name" fromUserId='+fromUserId+' fromUserName='+fromUserName+'>'+fromUserName+'</span>'
								trs +='<span>回复 </span>'
								trs +='<span class="person-name"  toUserName='+toUserName+'>'+toUserName+'</span> ：'
								trs +='<span class="reply-nr">'+content+'</span>'
								trs +='<div class="reply-time">'
								trs +='<span  onclick=\'disabledPublish("'+fromUserId+'","'+fromUserName+'","'+topicTypeId+'")\' class="lzl_time stop-btn-hf"><i class="fa fa-times-circle"></i>禁用发布</span>'
								trs +='<span  onclick=\'disabledReply("'+fromUserId+'","'+fromUserName+'","'+topicTypeId+'")\' class="lzl_time stop-btn-fb"><i class="fa fa-warning"></i>禁用回复</span>'	
								trs +='<span  class="lzl_time">'+formatDate(createTime, "yyyy-MM-dd HH:mm")+'</span>'
								trs +="<a href='javascript:void(0)' fromUserId='"+fromUserId+"' fromUserName='"+fromUserName+"'  onclick=\"replayShow("+fromUserId+",'"+fromUserName+"')\" class='lzl_s_r'>回复</a>"
								trs +='</div>'
								trs +='</div>'
								trs +='</li>'
									
							
						}
			    		 tbody += trs; 
			    		 if(li_html==null||li_html==undefined){//没有li
			    			 $(".reply-ul").html(tbody);
			    		 }else{
			    			 $(".reply-ul li:last-child").after(tbody);
			    		 }
			    	 }
			     });
			}
		});
		
	});
	//修改点赞数
	$(".upd_support").click(function(){
		var supportNum=$(this).attr("supportNum");
		var topicId=$(this).attr("topicId");
		$("#v_supportNum").val(supportNum);
		$("#v_topicId").val(topicId);
		$("#upd_support_bg").css({
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
	});	
	//修改点赞确定
	$(".supportBtn").click(function(){
		var supportNum=$("#v_supportNum").val();
		var topicId=$("#v_topicId").val();
		if(supportNum==""){
			 layer.msg("请输入点赞数");
			 return ;
		}
		if(isNaN(supportNum)){
			 layer.msg("点赞数必须为数字");
			 return ;
		}
		$.ajax({
			type : "post", //表单提交类型 
			url : basePath+"/topic/setTop", //表单提交目标 
			data : {topicId:topicId,supportNum:supportNum}, //表单数据
			success : function(data) {
				 layer.msg(data.msg,{
				     	shift: 0
				     }, function(){
				    	 $("#close").click();
				    	 window.location.href=basePath+"/topic/list";
				     }); 
			}
		});
	});	
	
	//点赞
	$(".support-showbtn").click(function(){
		var topicId=$(this).attr("topicId");
		$("#support_bg").css({
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
		$.ajax({
			type : "post", //表单提交类型 
			url : basePath+"/topic/selectSupportUsers", //表单提交目标 
			data : {topicId:topicId}, //表单数据
			success : function(data) {
				 var sumCounts=data.paramsMap.sumCounts;
				 var list=data.paramsMap.list;
				 $("#bg_supportUserCount").text("参与点赞"+sumCounts+"人");
			 	var tbody = ""; 
		 		$.each(list,function(n,obj) {   
		 			var userName=obj.userName;
		 			var userLogo=obj.userNameImg;
		 			var createTime=obj.createTime;
		 	        var trs = "";  
		 	        if(userName==null||userName==""){
		 	        	userName="用户已不存在";
		 	        }
		 	       	trs +='<div class="voter-photo">'
						trs +='<div class="vote-img">'
							trs +='<img src="'+basePath+'/'+userLogo+'" />'
						trs +='</div>'
						trs +='<div class="vote-mz">'
							trs +='<p id="bg_voteUserName">'+userName+'</p>'
							trs +='<p style="width:200px;" id="bg_voteUserCreateDate">'+formatDate(createTime, "yyyy-MM-dd HH:mm")+'</p>'
						trs +='</div>'
					trs +='</div>'
		 	        
					tbody += trs;     
			 	    
		 	  	 });  
				 $("#bg_contentDiv").html(tbody);
			 }
		}); 
	});
   
	
	//查看举报
	$(".report-showbtn").click(function(){
		var topicId=$(this).attr("topicId");
		$("#options_bg").css({
        	display: "block", height: $(document).height()
    	});
		var $box = $('.box2');
		$box.css({
		    //设置弹出层距离左边的位置
		    left: ($("body").width() - $box.width()) / 2 - 10 + "px",
		    //设置弹出层距离上面的位置
		    top: ($(window).height() - $box.height()) / 3 + $(window).scrollTop() + "px",
		    display: "block"
		});
		$.ajax({
			type : "post", //表单提交类型 
			url : basePath+"/topic/selectReportUsers", //表单提交目标 
			data : {topicId:topicId}, //表单数据
			success : function(data) {
				 var sumCounts=data.paramsMap.sumCounts;
				 var list=data.paramsMap.list;
				 $("#bg_reportUserCount").text("参与举报"+sumCounts+"人");
			 	var tbody = ""; 
		 		$.each(list,function(n,obj) {   
		 			var userName=obj.userName;
		 			var userLogo=obj.userNameImg;
		 			var createTime=obj.createTime;
		 	        var trs = "";  
		 	        if(userName==null||userName==""){
		 	        	userName="用户已不存在";
		 	        }
					trs +='<div class="voter-photo">'
						trs +='<div class="vote-img">'
							trs +='<img src="'+basePath+'/'+userLogo+'" />'
						trs +='</div>'
						trs +='<div class="vote-mz">'
							trs +='<p >'+userName+'</p>'
							trs +='<p style="width:200px;" >'+formatDate(createTime, "yyyy-MM-dd HH:mm")+'</p>'
						trs +='</div>'
					trs +='</div>'
					tbody += trs;     
			 	    
		 	  	 });  
				 $("#report_contentDiv").html(tbody);
			 }
		}); 
	});
   
	
	//查看评论详情
	$(".reply-showbtn").click(function() {
		var topicId=$(this).attr("topicId");
		var userId=$(this).attr("userId");
		var topicTypeId=$(this).attr("topicTypeId");
		$("#comm_topicTypeId").val(topicTypeId);
		$("#topicId").val(topicId);
		$("#userId").val(userId);
		$("#title_id").text("评论详情 ");
		$.ajax({
			type : "post",
			url :basePath+"/topic/getTopicDetail",
			data : {topicId:topicId},
			success : function(data) {
				 var commentlist=data.paramsMap.commentlist;
//					 console.log(commentlist);
			 	var tbody = ""; 
		 		$.each(commentlist,function(n,obj) {   
		 			var commentId=obj.commentId;
		 			var fromUserId=obj.fromUserId;
		 			var fromUserName=obj.fromUserName;
		 			var fromUserNameImg=obj.fromUserNameImg;
		 			var toUserName=obj.toUserName;
		 			var content=obj.content;
		 			var createTime=obj.createTime;
		 			var isMain=obj.isMain;
		 			var identityId=obj.identityId;//评论者身份
//		 			console.log("identityId{}"+identityId);
		 	        var trs = "";  
		 	        var li_class="";
		 	        if(n==0){
		 	        	li_class="first_no_border";
		 	        }
		 	        if(fromUserName==null){
		 	        	fromUserName="用户已不存在";
		 	        }
		 	       if(toUserName==null){
		 	    	  toUserName="用户已不存在";
		 	        }
					if(isMain==1){
						trs +='<li class="'+li_class+'">'
							trs +='<div class="person-img">'
							trs +='<img src="'+basePath+'"/'+fromUserNameImg+'" />'
							trs +='</div>'
							trs +='<div class="reply-name">'
							trs +='<span class="person-name" fromUserId='+fromUserId+' fromUserName='+fromUserName+'>'+fromUserName+'</span> ：'
							trs +='<span class="reply-nr">'+content+'</span>'
							trs +='<div class="reply-time">'
							trs +='<span  onclick=\'disabledPublish("'+fromUserId+'","'+fromUserName+'","'+topicTypeId+'","'+identityId+'")\' class="lzl_time stop-btn-hf"><i class="fa fa-times-circle"></i>禁用发布</span>'
							trs +='<span  onclick=\'disabledReply("'+fromUserId+'","'+fromUserName+'","'+topicTypeId+'","'+identityId+'")\' class="lzl_time stop-btn-fb"><i class="fa fa-warning"></i>禁用回复</span>'
							trs +='<span   class="lzl_time">'+formatDate(createTime, "yyyy-MM-dd HH:mm")+'</span>'
//							<a href="javascript:void(0)"  onclick=\'disabledPublish("'+fromUserId+'","'+fromUserName+'","'+topicTypeId+'")\' >禁用发布</a>
							trs +="<a href='javascript:void(0)' fromUserId='"+fromUserId+"' fromUserName='"+fromUserName+"'  onclick=\"replayShow("+fromUserId+",'"+fromUserName+"')\" class='lzl_s_r'>回复</a>"
							trs +='</div>'
							trs +='</div>'
							trs +='</li>'
					}else{
						trs +='<li>'
							trs +='<div class="person-img">'
							trs +='<img src="'+basePath+'"/'+fromUserNameImg+'" />'
							trs +='</div>'
							trs +='<div class="reply-name">'
							trs +='<span class="person-name" fromUserId='+fromUserId+' fromUserName='+fromUserName+'>'+fromUserName+'</span>'
							trs +='<span>回复 </span>'
							trs +='<span class="person-name"  toUserName='+toUserName+'>'+toUserName+'</span> ：'
							trs +='<span class="reply-nr">'+content+'</span>'
							trs +='<div class="reply-time">'
							trs +='<span  onclick=\'disabledPublish("'+fromUserId+'","'+fromUserName+'","'+topicTypeId+'","'+identityId+'")\' class="lzl_time stop-btn-hf"><i class="fa fa-times-circle"></i>禁用发布</span>'
							trs +='<span  onclick=\'disabledReply("'+fromUserId+'","'+fromUserName+'","'+topicTypeId+'","'+identityId+'")\' class="lzl_time stop-btn-fb"><i class="fa fa-warning"></i>禁用回复</span>'	
							trs +='<span  class="lzl_time">'+formatDate(createTime, "yyyy-MM-dd HH:mm")+'</span>'
							trs +="<a href='javascript:void(0)' fromUserId='"+fromUserId+"' fromUserName='"+fromUserName+"'  onclick=\"replayShow("+fromUserId+",'"+fromUserName+"')\" class='lzl_s_r'>回复</a>"
//								trs +='<a href="javascript:void(0)" fromUserId="'+fromUserId+'" fromUserName="'+fromUserName+'"  onclick=\'replayShow("+fromUserId+","'+fromUserName+'")\' class="lzl_s_r">回复</a>'

							trs +='</div>'
							trs +='</div>'
							trs +='</li>'
					}
					tbody += trs; 
		 	  	 });  
				 $(".reply-ul").html(tbody);
			 }
		}); 
		showCommentDetail();
	});

	
	//我也说一句
	$(".reply-speak").bind("click",function() {
		var userId=$("#userId").val();
		$("#replyUserId").val(userId);	
		$("#isMain").val('1');	
		$(".reply-input").css("display", "block");
		$(".choice-name").css("display", "none");
	});
	
	//关闭
	$(".close-x").click(function() {
		$("#replyUserId").val('');	
		$("#isMain").val('');	
		$(".reply-input,.choice-name").css("display", "none");
		$(".reply-input").css("display", "none");
		$("#bg,.all-reply").css("display", "none");
	});
	  //点击关闭按钮的时候，遮罩层关闭
  	 $("#close,#supportCloseBtn,#reportCloseBtn").click(function () {
       $("#support_bg,.box3,#upd_support_bg,.box4,#options_bg,.box2").css("display", "none");
  	 });	
	$('.reply-list').niceScroll({
		cursorcolor: "#ccc", //#CC0071 光标颜色
		cursoropacitymax: 0.8, //改变不透明度非常光标处于活动状态（scrollabar“可见”状态），范围从1到0
		touchbehavior: false, //使光标拖动滚动像在台式电脑触摸设备
		cursorwidth: "3px", //像素光标的宽度
		cursorborder: "0", // 游标边框css定义
		cursorborderradius: "3px", //以像素为光标边界半径
		autohidemode: true //是否隐藏滚动条
	});
	
	//禁用用户回复/评论确定事件
	$("#disb_replyBtn").click(function(){
		var userId=$("#v_toUserId").val();
		var topicTypeId=$("#v_topicTypeId").val();
		$.ajax({
			type : "post", //表单提交类型 
			url : basePath+"/topicUserManager/updateIsReply", //表单提交目标 
			data : {userId:userId,commTopicTypeId:topicTypeId,isReply:1}, //表单数据
			success : function(data) {
				 layer.msg(data.msg,{
				     	shift: 0,
				     	icon: 1
				     }, function(){
				    	 $(".layui-layer-btn1,.layui-layer-close1").click();
				     }); 
			}
		});
	});	
	
	//禁用用户发布确定事件
	$("#disb_publishBtn").click(function(){
		var userId=$("#v_toUserId2").val();
		var topicTypeId=$("#v_topicTypeId2").val();
		$.ajax({
			type : "post", //表单提交类型 
			url : basePath+"/topicUserManager/updateIsPublish", //表单提交目标 
			data : {userId:userId,topicTypeId:topicTypeId,isPublish:1}, //表单数据
			success : function(data) {
				 layer.msg(data.msg,{
				     	shift: 0,
				     	icon: 1
				     }, function(){
				    	 $(".layui-layer-btn1,.layui-layer-close1").click();
				     }); 
			}
		});
	});	

	
	$(".layui-layer-btn1,.layui-layer-close1").click(function() {
		$(".layui-layer").css("display", "none");
	});
})
//禁用用户发布话题
function disabledPublish(toUserId,fromUserName,topicTypeId,identityId){
	if(identityId!=4){//居民
		layer.msg('官方账号不能禁用！', {icon: 5});
		return false;
	}
	//默认话题类型，若数据库已存在，则取数据库里的，则默认当前话题的类型
//	$.ajax({
//		type : "post", //表单提交类型 
//		url : basePath+"/topicUserManager/selectById", //表单提交目标 
//		data : {userId:toUserId}, //表单数据
//		async:false,
//		success : function(data) {
//	    	 var obj=data.paramsMap.topicUserManager;
//	    	 if(obj!=null&&obj!=undefined){
//    			 var temp_typeId=obj.topicTypeId;
//    			 if(temp_typeId!=0&&temp_typeId!=undefined){
//    					$("#v_topicTypeId2").val(temp_typeId);
//    			}else{
//    					$("#v_topicTypeId2").val(topicTypeId);
//    			}
//	    	 }else{
//	    		 $("#v_topicTypeId2").val(topicTypeId);
//	    	 }
//	    	
//		}
//	});
	$("#v_toUserId2").val(toUserId);
	
	var $replystop = $('.public-layer.layui-layer');
	$replystop.css({
		//设置弹出层距离左边的位置
		left: ($("body").width() - $replystop.width()) / 2 - 20 + "px",
		//设置弹出层距离上面的位置
		top: ($(window).height() - $replystop.height()) / 4 + $(window).scrollTop() + "px",
		display: "block"
	});
	
	
}
//禁用用户回复
function disabledReply(toUserId,fromUserName,commTopicTypeId,identityId){
	if(identityId!=4){//居民
		layer.msg('官方账号不能禁用！', {icon: 5});
		return false;
	}
	//默认话题类型，若数据库已存在，则取数据库里的，则默认当前话题的类型
//	$.ajax({
//		type : "post", //表单提交类型 
//		url : basePath+"/topicUserManager/selectById", //表单提交目标 
//		data : {userId:toUserId}, //表单数据
//		async:false,
//		success : function(data) {
//			console.log();
//	    	 var obj=data.paramsMap.topicUserManager;
//	    	 if(obj!=null&&obj!=undefined){
//	    			var temp_typeId=obj.commTopicTypeId;
//	    			 if(temp_typeId!=0&&temp_typeId!=undefined){
//	    					$("#v_topicTypeId").val(temp_typeId);
//	    			}else{
//	    					$("#v_topicTypeId").val(commTopicTypeId);
//	    			}
//	    	 }else{
//	    		 $("#v_topicTypeId").val(commTopicTypeId);
//	    	 }
//	    	
//		}
//	});
	$("#v_toUserId").val(toUserId);
	
	var $replystop = $('.reply-layer.layui-layer');
	$replystop.css({
		//设置弹出层距离左边的位置
		left: ($("body").width() - $replystop.width()) / 2 - 20 + "px",
		//设置弹出层距离上面的位置
		top: ($(window).height() - $replystop.height()) / 4 + $(window).scrollTop() + "px",
		display: "block"
	});

}


//点击回复
function replayShow(toUserId,fromUserName){
	$("#replyName").text(fromUserName);
	$("#replyUserId").val(toUserId);
	$("#isMain").val('0');	
	$(".reply-input,.choice-name").css("display", "block");
}
function closeTextarea(){
	$("#replyUserId").val('');	
	$("#isMain").val('');	
	$("#replyContent").val('')
	$(".reply-input,.choice-name").css("display", "none");
}
//置顶/取消置顶
function setTop(id,istop){
		$.ajax({
			type : "post", //表单提交类型 
			url : basePath+"/topic/setTop", //表单提交目标 
			data : {topicId:id,isTop:istop}, //表单数据
			success : function(data) {
				 layer.msg(data.msg,{
				     	shift: 0
				     }, function(){
				    	 window.location.href=basePath+"/topic/list";
				     }); 
			}
		});
}
	
//显示评论详情界面
function showCommentDetail(){
	$("#bg").css({
		display: "block",
		height: $(document).height()
	});
	var $reply = $('.all-reply');
	$reply.css({
		//设置弹出层距离左边的位置
		left: ($("body").width() - $reply.width()) / 2 - 20 + "px",
		//设置弹出层距离上面的位置
		top: ($(window).height() - $reply.height()) / 4 + $(window).scrollTop() + "px",
		display: "block"
	});
}
	