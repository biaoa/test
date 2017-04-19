 function formatDate(date, format) {   
	     if (!date) return;   
	     if (!format) format = "yyyy-MM-dd";   
	     switch(typeof date) {   
	         case "string":   
	             date = new Date(date.replace(/-/, "/"));   
	             break;   
	         case "number":   
	             date = new Date(date);   
	             break;   
	     }    
	     if (!date instanceof Date) return;   
	     var dict = {   
	         "yyyy": date.getFullYear(),   
	         "M": date.getMonth() + 1,   
	         "d": date.getDate(),   
	         "H": date.getHours(),   
	         "m": date.getMinutes(),   
	         "s": date.getSeconds(),   
	         "MM": ("" + (date.getMonth() + 101)).substr(1),   
	         "dd": ("" + (date.getDate() + 100)).substr(1),   
	         "HH": ("" + (date.getHours() + 100)).substr(1),   
	         "mm": ("" + (date.getMinutes() + 100)).substr(1),   
	         "ss": ("" + (date.getSeconds() + 100)).substr(1)   
	     };       
	     return format.replace(/(yyyy|MM?|dd?|HH?|ss?|mm?)/g, function() {   
	         return dict[arguments[0]];   
	     });                   
	 }   
	   
// 	 alert(formatDate("2010-04-30", "yyyy-MM-dd HH:mm:ss"));   
// 	 alert(formatDate("2010-4-29 1:50:00", "yyyy-MM-dd HH:mm:ss")); 