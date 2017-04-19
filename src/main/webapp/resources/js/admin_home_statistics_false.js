var basepath;
$(document).ready(function() {
	basepath=$("#basepath").val();
	$(".main_click1").click(function(){	 //main_all_click1 
	    $(".main_click1").removeClass("main_a3_active");
		$(this).addClass("main_a3_active");
		var timeRule=$(this).attr("tag");
		amountStatistics(timeRule);
  	});
  
	$(".main_click2").click(function(){	  
	    $(".main_click2").removeClass("main_a3_active");
		$(this).addClass("main_a3_active");
		var timeRule=$(this).attr("tag");
		ordersStatistics(timeRule);
  	});	
	//活跃用户
	$(".main_click3").click(function(){	  
	    $(".main_click3").removeClass("main_a3_active");
		$(this).addClass("main_a3_active");
		var timeRule=$(this).attr("tag");
		var communityId=$(".activeUser_selected").val();
		activeUserStatistics(timeRule,communityId);
 	 });	
	
	//注册用户
	$(".main_click4").click(function(){	  
	    $(".main_click4").removeClass("main_a3_active");
		$(this).addClass("main_a3_active");
		var timeRule=$(this).attr("tag");
		var communityId=$(".registerUser_selected").val();
		registerUserStatistics(timeRule,communityId);
 	 });
	
	 //获取所有小区 赋值给select2下拉框
	 $.ajax({  
         url : basepath+'/sys2/getAllCommunityList',
         type : 'POST',
         dataType : 'json',
         success : function(data){
        	    var communityList=data.paramsMap.communityList;
        	    $(".registerUser_selected,.activeUser_selected").select2({
        	 	   data: communityList,
        	 	  placeholder:'选择小区筛选',
        	 	  allowClear:true
        	 	 })
         }
     });
	
	 // 小区用户注册统计select 事件
	 $(".registerUser_selected").change(function() {
		 var id=$(this).val();
		 var timeRule=$('.main_click4.main_a3_active').attr("tag");
		 registerUserStatistics(timeRule,id);
	 })
	 
	 // 活跃用户数量 select 事件	
	 $(".activeUser_selected").change(function() {
		 var id=$(this).val();
		 var timeRule=$('.main_click3.main_a3_active').attr("tag");
		 activeUserStatistics(timeRule,id);
	 })
	
	//总注册用户--柱状形统计
	userStatistics();
	//活跃用户--折线图统计
	activeUserStatistics("sevenDay",'');//默认七天
	//总营业额--折线图统计
	amountStatistics("sevenDay");
	//总订单数--折线图统计
	ordersStatistics("sevenDay");
	//总订单数--饼状图统计
	ordersPieStatistics("sevenDay");
	//小区用户注册--折线图统计
	registerUserStatistics("sevenDay",'');
});  
function userStatistics(){
	//定义oChart的布局环境
	//布局环境组成：X轴、Y轴、数据显示、图标标题
	var oOptions = {  
	    //设置图表关联显示块和图形样式
	    chart: {  
	        renderTo: 'container',  //设置显示的页面块
	        //type:'line'                //设置显示的方式
	        type: 'column'
	    },
	    credits: {
	        enabled: false
	    },
	    //图标标题
	    title: {  
//	        text: '总注册用户'
	        text: null, //设置null则不显示标题
	    },
	    
	    //x轴
	    xAxis: {
	        title: {
	            text: '用户类型'
	        },
	        categories: []  
	    },
	    tooltip: {  
            valueSuffix: '个'  
        }, 
	    //y轴
	    yAxis: {
	        title: { text: '数量' }
	    },
        plotOptions: {
        	  column: {
        	        dataLabels: {//柱状图显示数值
        	        enabled:true 
        	        }
        	    }
        },
	    //数据列
	    series: [] 
//	    series: [{
//	        	data:[]
//	        }] 
	}; 
	//异步读取数据并加载到图表
	LoadSerie_Ajax(oOptions);
}

function activeUserStatistics(timeRule,communityId){
	//定义oChart的布局环境
	//布局环境组成：X轴、Y轴、数据显示、图标标题
	var oOptions2 = {  
	    //设置图表关联显示块和图形样式
	    chart: {  
	        renderTo: 'container_activeUser',  //设置显示的页面块
	        type:'line'                //设置显示的方式
	    },
	    credits: {
	        enabled: false
	    },
	    //图标标题
	    title: {  
//	        text: '活跃用户数量'
	        text: null, //设置null则不显示标题
	    },
	    
	    //x轴
	    xAxis: {
	        title: {
	            text: '日期'
	        },
	        categories: []  
	    },
	    //y轴
	    yAxis: {
	        title: { text: '数量' }
	    },
	    plotOptions: {
            
        },
        tooltip: {  
            valueSuffix: '个'  
        },
	    //数据列
	    series: [] 
	}; 
	//异步读取数据并加载到图表
	LoadSerie_Ajax2(oOptions2,timeRule,communityId);
}

function amountStatistics(timeRule){
	//定义oChart的布局环境
	//布局环境组成：X轴、Y轴、数据显示、图标标题
	var oOptions3 = {  
	    //设置图表关联显示块和图形样式
	    chart: {  
	        renderTo: 'container_amount',  //设置显示的页面块
	        type:'line'                //设置显示的方式
	    },
	    credits: {
	        enabled: false
	    },
	    //图标标题
	    title: {  
//	        text: '总营业额'
	        text: null, //设置null则不显示标题
	    },
	    
	    //x轴
	    xAxis: {
	        title: {
	            text: '日期'
	        },
	        categories: []  
	    },
	    //y轴
	    yAxis: {
	        title: { text: '金额' }
	    },
	    plotOptions: {
            
        },
        tooltip: {  
            valueSuffix: '元'  
        },
	    //数据列
	    series: [] 
	}; 
	//异步读取数据并加载到图表
	LoadSerie_Ajax3(oOptions3,timeRule);
}
function ordersStatistics(timeRule){
	//定义oChart的布局环境
	//布局环境组成：X轴、Y轴、数据显示、图标标题
	var oOptions4 = {  
	    //设置图表关联显示块和图形样式
	    chart: {  
	        renderTo: 'container_orders',  //设置显示的页面块
	        type:'line'                //设置显示的方式
	    },
	    credits: {
	        enabled: false
	    },
	    //图标标题
	    title: {  
//	        text: '总订单数量'
	        text: null, //设置null则不显示标题
	    },
	    
	    //x轴
	    xAxis: {
	        title: {
	            text: '日期'
	        },
	        categories: []  
//	        categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
	    },
	    //y轴
	    yAxis: {
	        title: { text: '数量' }
	    },
	    plotOptions: {
            
        },
        tooltip: {  
            valueSuffix: '个'  
        },
	    //数据列
        series:[]
//	    series:[{name: '总订单数量',data:[0,0,0,0,0,19,99,52,84,921,0,0]},{name: '成功订单数量',data:[0,0,0,0,0,4,9,13,9,31,0,0]}]
	}; 
	//异步读取数据并加载到图表
	LoadSerie_Ajax4(oOptions4,timeRule);
}

function ordersPieStatistics(timeRule){
	//定义oChart的布局环境
	//布局环境组成：X轴、Y轴、数据显示、图标标题
	var oOptions5 = {  
	    //设置图表关联显示块和图形样式
	    chart: {  
	        renderTo: 'container_ordersPie',  //设置显示的页面块
	        type:'pie'                //设置显示的方式
	    },
	    credits: {
	        enabled: false
	    },
	    //图标标题
	    title: {  
//	        text: '总订单数量'
	        text: null, //设置null则不显示标题
	    },
	    
	    //x轴
	    xAxis: {
	        title: {
	            text: '日期'
	        },
	        categories: []  
//	        categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
	    },
	    //y轴
	    yAxis: {
	        title: { text: '数量' }
	    },
	    plotOptions: {
            
        },
        tooltip: {  
            valueSuffix: '个'  
        },
	    //数据列
        series:[]
//        [{
//            name: '数量',
//            data: [
//                ['线上订单数量',75.0],
//                ['线下订单数量',25.0]               
//            ]
//        }]
//	    series:[{name: '总订单数量',data:[0,0,0,0,0,19,99,52,84,921,0,0]},{name: '成功订单数量',data:[0,0,0,0,0,4,9,13,9,31,0,0]}]
	}; 
	//异步读取数据并加载到图表
	LoadSerie_Ajax5(oOptions5,timeRule);
}

function registerUserStatistics(timeRule,communityId){
	//定义oChart的布局环境
	//布局环境组成：X轴、Y轴、数据显示、图标标题
	var oOptions6 = {  
	    //设置图表关联显示块和图形样式
	    chart: {  
	        renderTo: 'container_registerUser',  //设置显示的页面块
	        type:'line'                //设置显示的方式
	    },
	    credits: {
	        enabled: false
	    },
	    //图标标题
	    title: {  
//	        text: '活跃用户数量'
	        text: null, //设置null则不显示标题
	    },
	    
	    //x轴
	    xAxis: {
	        title: {
	            text: '日期'
	        },
	        categories: []  
	    },
	    //y轴
	    yAxis: {
	        title: { text: '数量' }
	    },
	    plotOptions: {
            
        },
        tooltip: {  
            valueSuffix: '个'  
        },
	    //数据列
	    series: [] 
	}; 
	//异步读取数据并加载到图表
	LoadSerie_Ajax6(oOptions6,timeRule,communityId);
}
//注册用户--柱状图   异步读取数据并加载到图表
function LoadSerie_Ajax(oOptions) { 
		//定义一个Highcharts的变量，初始值为null
	    var oChart = new Highcharts.Chart(oOptions);
        oChart.showLoading(); 
        $.ajax({  
            url : basepath+'/sys2/getRegisterCountByCharts',
            type : 'POST',
            dataType : 'json',
            success : function(data){
//           	 	var categories=data.paramsMap.categories;
           	    var seriesdata=data.paramsMap.data;
//           	    console.log(seriesdata);
//           	    console.log(seriesdata)
                 var oSeries = {
                    name: "数量",
                    data: eval("" + seriesdata + "")
                };
                oChart.addSeries(oSeries);
            }
        });
//        var oSeries = {
//                name: "数量",
//                data: [360,160,40,360]
//            };
//        oChart.addSeries(oSeries);
        var categories=['小区用户', '合作小区', '商家', '物业']  ;
        oChart.xAxis[0].setCategories(categories);
        oChart.hideLoading(); 
}
//活跃用户--折线图
function LoadSerie_Ajax2(oOptions2,timeRule,communityId) {
	   //定义一个Highcharts的变量
	   var oChart2 = new Highcharts.Chart(oOptions2);
       oChart2.showLoading(); 
       $.ajax({  
           url : basepath+'/sys2/getActiveUserStatisticss',
           type : 'POST',
           dataType : 'json',
           data:{'timeRule':timeRule,'communityId':communityId},//sevenDay thirtyDay thisYear
           success : function(data){
        	   if(data.code==0){
        		   var categories=data.paramsMap.categories;
        		   var seriesdata=data.paramsMap.data;
        		   console.log(seriesdata);
                   var oSeries = {
                      name: "数量",
                      data: eval("" + seriesdata + "")
                    };
//        	        var oSeries = {
//		                 name: "数量",
//		                 data: [60,160,340,30,760,160,40,40,60,60,20,40,10,260,560,70,40,30,760,160,40,40,60,60,20,40,10,260,560,70,40]
//		             };
                  oChart2.addSeries(oSeries);
                  oChart2.xAxis[0].setCategories(eval("" + categories + ""));
                  oChart2.hideLoading(); 
        	   }
           }
       });
//       var categories=['10-1','10-1','10-2','10-3','10-4','10-5','10-6','10-7','10-8','10-9','10-10','10-11','10-12','10-13','10-14','10-15','10-16','10-17','10-18','10-19','10-20','10-21','10-22','10-23','10-24','10-25','10-26','10-27','10-28','10-29','10-30'];
//       var categories=[];
//       oChart2.xAxis[0].setCategories(categories);
//       oChart2.hideLoading(); 
}
//总营业额统计--折线图
function LoadSerie_Ajax3(oOptions3,timeRule) {
	   //定义一个Highcharts的变量
	var oChart3 = new Highcharts.Chart(oOptions3);
	oChart3.showLoading(); 
    $.ajax({  
        url : basepath+'/sys2/getSalesStatisticss',
        type : 'POST',
        dataType : 'json',
        data:{'timeRule':timeRule},//sevenDay thirtyDay thisYear
        success : function(data){
     	   if(data.code==0){
     		   var categories=data.paramsMap.categories;
     		   var seriesdata=data.paramsMap.data;
                var oSeries = {
                   name: "金额",
                   data: eval("" + seriesdata + "")
                 };
                oChart3.addSeries(oSeries);
                oChart3.xAxis[0].setCategories(eval("" + categories + ""));
                oChart3.hideLoading(); 
     	   }
        }
    });
}
//订单数量统计--折线图
function LoadSerie_Ajax4(oOptions4,timeRule) {
	//定义一个Highcharts的变量
	var oChart4 = new Highcharts.Chart(oOptions4);
	oChart4.showLoading(); 
	 $.ajax({  
	     url : basepath+'/sys2/getOrdersStatisticss',
	     type : 'POST',
	     dataType : 'json',
	     data:{'timeRule':timeRule},//sevenDay thirtyDay thisYear
	     success : function(data){
	  	   if(data.code==0){
	  		   var categories=data.paramsMap.categories;
	  		   var seriesdata=data.paramsMap.data;
	  		   var seriesdata2=data.paramsMap.data2;
//	  		   console.log(seriesdata2);
//	  		   var series="[{name: '总订单数量',data:[0,0,0,0,0,19,99,52,84,921,0,0]},{name: '成功订单数量',data:[0,0,0,0,0,4,9,13,9,31,0,0]}]";
	  		   
	  		 oChart4.addSeries(
		  				 {  
		                name: '总订单数量',  
		                data: eval("" + seriesdata + "")
		            	}
	            	);  
	  		 oChart4.addSeries(
	  				 {  
	                name: '成功订单数量',  
	                data: eval("" + seriesdata2 + "")
	            	}
            	);  
               oChart4.xAxis[0].setCategories(eval("" + categories + ""));
               oChart4.hideLoading(); 
	  	   }
	     }
	 });
}
//订单数量统计--饼形图
function LoadSerie_Ajax5(oOptions5,timeRule) {
	//定义一个Highcharts的变量
	var oChart5 = new Highcharts.Chart(oOptions5);
	oChart5.showLoading(); 
	 $.ajax({  
	     url : basepath+'/sys2/getOrderStatisticsByPie',
	     type : 'POST',
	     dataType : 'json',
	     data:{'timeRule':timeRule},//sevenDay thirtyDay thisYear
	     success : function(data){
	  	   if(data.code==0){
	  		   var seriesdata=data.paramsMap.data;
	  		   
//	  		 var seriesdata="data: [['线上订单数量',75.0],['线下订单数量',25.0]]";
	  		 oChart5.addSeries(
	  				 {  
	                name: '数量',  
	                data: eval("" + seriesdata + "")
	            	}
            	); 
	  		 oChart5.hideLoading(); 
	  	   }
	     }
	 });
}

//小区用户注册统计--折线图
function LoadSerie_Ajax6(oOptions6,timeRule,communityId) {
	   //定义一个Highcharts的变量
	   var oChart6 = new Highcharts.Chart(oOptions6);
	   oChart6.showLoading(); 
       $.ajax({  
           url : basepath+'/sys2/getRegisterUserStatisticss',
           type : 'POST',
           dataType : 'json',
           data:{'timeRule':timeRule,'communityId':communityId},//sevenDay thirtyDay thisYear
           success : function(data){
        	   if(data.code==0){
        		   var categories=data.paramsMap.categories;
        		   var seriesdata=data.paramsMap.data;
                   var oSeries = {
                      name: "数量",
                      data: eval("" + seriesdata + "")
                    };
//        	        var oSeries = {
//		                 name: "数量",
//		                 data: [60,160,340,30,760,160,40,40,60,60,20,40,10,260,560,70,40,30,760,160,40,40,60,60,20,40,10,260,560,70,40]
//		             };
                  oChart6.addSeries(oSeries);
                  oChart6.xAxis[0].setCategories(eval("" + categories + ""));
                  oChart6.hideLoading(); 
        	   }
           }
       });
}