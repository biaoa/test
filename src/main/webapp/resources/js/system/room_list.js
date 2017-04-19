/**
 * 房号管理js脚本
 */
var overallsArray=new Array();
$(document).ready(function(){ 
	//预览
	$(".commint").click(function() {
		var building=$("#building").val();//幢
		var unit=$("#unit").val();//单元
		var ceng=$("#ceng").val();//层
		var roomNum=$("#roomNum").val();//房数
		var choosePattern=$("#choosePattern").val();
		
		if(building==""){
			 layer.msg("请输入幢");
			 return false;
		}
		if(unit==""){
			 layer.msg("请输入单元");
			 return false;	
		}
		if(ceng==""){
			 layer.msg("请输入楼层");
			 return false;
		}else{
			if(isNaN(ceng)){
				 layer.msg("楼层必须为数字");
				 return false;
			}
		}
		if(roomNum==""){
			 layer.msg("请输入房数");
			 return false;
		}else{
			if(isNaN(roomNum)){
				 layer.msg("房数必须为数字");
				 return false;
			}
		}
		
		if(choosePattern=='yes'){
			if(isNaN(building)){
				 layer.msg("幢必须为数字");
				 return false;
			}
			if(isNaN(unit)){
				 layer.msg("单元必须为数字");
				 return false;
			}
		}
		//组织数据
		overallsArray=[];
		
		if(choosePattern=='no'){
			organizeData2(building,unit,ceng,roomNum);
		}else{
			organizeData1(building,unit,ceng,roomNum);
		}
		

		var tbody="";
		for (var i = 0; i < overallsArray.length; i++) {
			var obj=overallsArray[i];//存到每个li里 overall='obj'
			var str="";
			str="<tr class='contentLi' overall="+obj.overall+">"
				str+="<td name='building'>"+obj.building+"</td>"
				str+="<td name='unit'>"+obj.unit+"</td>"
				str+="<td name='room'>"+obj.room+"</td>"
				str+="<td><a class='rom-delet' name='delRommLi'>删除</a></td>"
			str+="</tr>"
				
			tbody+=str
		}
		$(".contentLi").remove();
		$("#themeDiv").after(tbody);
		showListWin();
	});
	
	//删除
	$(".rom-delet").live("click",function() {
		$(this).parent().parent().remove();
	
	});
	
	//保存生成房号
	$("#produceRoom_saveBtn").click(function(){
		overallsArray=[];
		$(".contentLi").each(function(){
			var building=$(this).find("[name=building]").text();
			var unit=$(this).find("[name=unit]").text();
			var room=$(this).find("[name=room]").text();
			var overall=$(this).attr("overall");
			var obj=new Overall($.trim(building),$.trim(unit),$.trim(room),$.trim(overall));
		})
//		console.log(overallsArray);
				
		if(overallsArray.length==0){
			 layer.msg("当前没有数据");
			 return false;
		}
		$.ajax({
			type : "post", //表单提交类型 
			url : basePath+"/community/insertBatchRooms", //表单提交目标 
			data : {jsonStr:JSON.stringify(overallsArray)}, //表单数据
			success : function(data) {
				if(data.code==0){
					 layer.msg(data.msg,{
			             	shift: 0,
			             	time: 1500
			             }, function(){
			            	 var fail_conunt=data.paramsMap.fail_conunt;
			            	 var success_conunt=data.paramsMap.success_conunt;
			            	 var fail_overalls=data.paramsMap.fail_overalls;
			            	 if(fail_conunt>0){
			            		 alert("成功导入房号"+success_conunt+"个；系统已存在房号"+fail_conunt+"个无需导入【"+fail_overalls+"】");
			            	 }else{
			            		 alert("成功导入房号"+success_conunt+"个");
			            	 }
			            	 window.location.reload();
			             }); 
				}else {
					 layer.msg(data.msg);
				}
			}
			
		})
	})
	
	
	$("#choosePattern").change(function() {
		var choosePattern=$(this).val();
		if(choosePattern=='no'){
			$("#ruleSpan").text("生成总房号数规则：1*1*M*N");
		}else{
			$("#ruleSpan").text("生成总房号数规则：X*Y*M*N");
		}
	})
	/***********样式操作start*********************************************/
	//弹出生成房号界面
	$(".produceRoomBtn").click(function() {
		$("#bg").css({
			display: "block",
			height: $(document).height()
		});
		var $box = $('.box-1');
		$box.css({
			//设置弹出层距离左边的位置
			left: ($("body").width() - $box.width()) / 2 - 20 + "px",
			//设置弹出层距离上面的位置
			top: ($(window).height() - $box.height()) / 2 + $(window).scrollTop() + "px",
			display: "block"
		});
		
	});
	
	//关闭
	$(".close-qx").click(function() {
		$("#bg , .box-1").css("display", "none");
	});
	$(".close-fh").click(function() {
		$(".box-2").css("display", "none");
	});
	
	//改变幢加载单元
	$("#buildingType").change(function(){
		var building=$(this).val();
		$.ajax({
			type : "post", //表单提交类型 
			url :basePath+"/community/loadUnitName", //表单提交目标 
			data : {building:building}, //表单数据
			success : function(data) {
					var lists=data.list;
//					console.log(lists);
					var tbody='<option value="" selected="selected">全部</option>';
					$.each(data.list,function(n,obj) {   
		            	var str="";
		            	str+='<option value="'+obj.name+'">'+obj.name+'</option>'
		            	tbody+=str;	
					 }); 
					$("#unitType").html(tbody);
	            	
			}
			
		})
	});
	
	if(buildingType!=''){
		$.ajax({
			type : "post", //表单提交类型 
			url :basePath+"/community/loadUnitName", //表单提交目标 
			data : {building:buildingType}, //表单数据
			success : function(data) {
					var lists=data.list;
					var tbody='<option value="" selected="selected">全部</option>';
					$.each(data.list,function(n,obj) {   
						var selected = "";
		            	var str="";
		            	if(obj.name==unitType){
		            		selected="selected='selected'";
		            	}
		            	str+='<option value="'+obj.name+'" '+selected+'>'+obj.name+'</option>'
		            	tbody+=str;	
					 }); 
					$("#unitType").html(tbody);
	            	
			}
			
		})
	}
	
})

function showListWin(){
	var $box2 = $('.box-2');
	$box2.css({
		//设置弹出层距离左边的位置
		left: ($("body").width() - $box2.width()) / 2 - 20 + "px",
		//设置弹出层距离上面的位置
		top: ($(window).height() - $box2.height()) / 2 + $(window).scrollTop() + "px",
		display: "none"
	});
	$('.divbox').delay(1500).hide(0);
	$(".box-2").css("display", "block");
}
//单元每层楼房数相同组织方法
function organizeData1(building,unit,ceng,roomNum){
	for (var i = 1; i <= building; i++) {
		for (var j = 1; j <= unit; j++) {
			for (var j2 = 1; j2 <= ceng; j2++) {
				for (var k = 1; k <= roomNum; k++) {
					var room="";//房号
					var overall="";//详情
					if(k<10){
						overall=i+"-"+j+"-"+j2+"0"+k;
						room=j2+"0"+k;
					}else{
						overall=i+"-"+j+"-"+j2+k;
						room=j2+k;
					}
//					overallsList.push(overall);
					var obj=new Overall(i,j,room,overall);
				}
			}
		}
	}
	
}
//单元每层楼房数不相同组织方法
function organizeData2(building,unit,ceng,roomNum){
	for (var j2 = 1; j2 <= ceng; j2++) {
		for (var k = 1; k <= roomNum; k++) {
			var room="";//房号
			var overall="";//详情
			if(k<10){
				overall=building+"-"+unit+"-"+j2+"0"+k;
				room=j2+"0"+k;
			}else{
				overall=building+"-"+unit+"-"+j2+k;
				room=j2+k;
			}
//			overallsList.push(overall);
			var obj=new Overall(building,unit,room,overall);
		}
	}
	
}

function　Overall (building,unit,roomNum,overall){
	this.building = building;
	this.unit = unit;
	this.room = roomNum;
	this.overall = overall;
	overallsArray.push(this);
}
