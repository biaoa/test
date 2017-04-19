$(document).ready(function(){	
	$("#log").click(function(){
  	$(".tip").show();
  	return false;
  });
});


//$(document).ready(function() {
//            $("#pager").pager({ pagenumber: 1, pagecount: 10, buttonClickCallback: PageClick });
//        });
//
//        PageClick = function(pageclickednumber) {
//            $("#pager").pager({ pagenumber: pageclickednumber, pagecount: 10, buttonClickCallback: PageClick });
//            $("#result").html("Clicked Page " + pageclickednumber);
//        }

//$(function() {
//		$("#test_1").selectpick({
//			container: '.test_1'			
//		});
//		$("#test_2").selectpick({container:'.test_2',disabled:true});
//		$("#test_3").selectpick({container:'.test_3',onSelect:function(value,text){
//			enAble();
//		}
//		});
//	});
	

$(function (){
	$("#datetimepicker1").on("click",function(e){
		e.stopPropagation();
		$(this).lqdatetimepicker({
			css : 'datetime-hour'
		});

	});


	$("#datetimepicker2").on("click",function(e){
		e.stopPropagation();
		$(this).lqdatetimepicker({
			css : 'datetime-hour'
		});

	});

	$("#datetimepicker3").on("click",function(e){
		e.stopPropagation();
		$(this).lqdatetimepicker({
			css : 'datetime-day',
			dateType : 'D',
			selectback : function(){

			}
		});

	});
});

 

//	$(function(){
//
//		$(".table_solid").tableUI();
//
//	});
	
/*system-time*/
function time()
{

  var now= new Date();
  var year=now.getFullYear();
  var month=now.getMonth();  
  var hours=now.getHours();
  var minutes=now.getMinutes();
  var timeValue = "" +((hours >= 12) ? "FM " : "AM " );
  var date=now.getDate();
//写入相应id
document.getElementById("info1").innerHTML=hours+":"+minutes+"&nbsp;&nbsp;"+timeValue;
document.getElementById("info2").innerHTML=year+"-"+(month+1)+"-"+date;
} 
$(document).ready(function(){	
	$(".sets").mouseover(function(){
  	$(".s-t").show();
  	return false;
  });
});


/*ie h5标签*/
 