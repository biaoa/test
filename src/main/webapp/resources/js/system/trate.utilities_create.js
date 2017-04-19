var overallsArray=new Array();
var basePath;
$(document).ready(function(){ 
	basePath=$("#basePath").val();
	//保存生成房号
	$("#createOrderBtn").click(function(){
		var setprice=$("#setprice").text();
		if(setprice==undefined || setprice==''){
			 layer.alert("未设置单价，需要在缴费单价设置模块设置单价!");
			 return false;
		}
		var type=$("#type").val();
		var yearMonth=$("#datepicker").val();
		var recordTime=$("#datepicker2").val();
		if(recordTime==''){
			 layer.msg("选择抄表时间");
			 return false;
		}
		overallsArray=[];
		$(".contentLi").each(function(i){
			var name=$(this).find("[name=name] input").text();
			var roomNo=$(this).find("[name=roomNo]").text();
			var lastMeterReading=$(this).find("[name=lastMeterReading]").text();
			var thisMeterReading=$(this).find("[name=thisMeterReading] input").val();
			var balance=$(this).find("[name=balance]").text();
			if($.trim(thisMeterReading)==''||Number($.trim(thisMeterReading))==0){
				alert("房号："+roomNo+"，本次抄表不能为空,且不能小于上次抄表")
				return false;
			}
			
			if($.trim(thisMeterReading)!=''){
				if(parseFloat($.trim(lastMeterReading))>parseFloat($.trim(thisMeterReading))){
					alert("房号："+roomNo+"，本次抄表不能小于上次抄表，请核对！")
					return false;
				}
			}
			
			var pooledPrice=$(this).find("[name=pooledPrice] input").val();
			var obj=new WaterVO($.trim(name),$.trim(roomNo),$.trim(lastMeterReading),$.trim(thisMeterReading),$.trim(pooledPrice),$.trim(balance));
		})
//		console.log(overallsArray);
		if(overallsArray.length<$(".contentLi").length){
			 layer.msg("当前页面数据没有填完整！")
			 return false;
		}
		$.ajax({
			type : "post", //表单提交类型 
			url : basePath+"/utilities/utilitiesCreateOrder", //表单提交目标 
			data : {jsonStr:JSON.stringify(overallsArray),
				type:type,
				recordTime:recordTime,
				yearMonth:yearMonth}, //表单数据
			success : function(data) {
				//这是是上传完成之后的操作
				alert(data.msg);
 				if(data.code==0){
 					window.location.reload();
 				}
			}
			
		})
	})
	
})

function　WaterVO (name,roomNo,lastMeterReading,thisMeterReading,pooledPrice,balance){
	this.name = name;
	this.roomNo = roomNo;
	this.lastMeterReading = lastMeterReading;
	this.thisMeterReading = thisMeterReading;
	this.pooledPrice = pooledPrice;
	this.balance = balance;
	overallsArray.push(this);
}
