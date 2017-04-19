
//一些常用的js

/**
 * @param userName
 *            要验证的用户名
 * 
 * @param base
 *            验证用户是否存在
 * 
 */
function checkUserExsit(userName, base) {
	if(userName==''||userName==null){
		return false;
	}
	var result;
	console.log(window.location.href);
	$.ajax({
		type : "post",
		url : base + "/sys/checkUserExsit",
		async : false,
		data : {
			userName : userName
		},
		success : function(data) {
			console.log(data);
			if (data.code == 0) {
				layer.msg(data.msg);
				result = true;
			} else {
				result = false;
			}
		}
	});
	return result;
}
/**
 * 验证手机号码
 * @param phone
 * @returns {Boolean}
 */
function checkPhone(phone) {
	var result = true;
	if (!(/^1[3|4|5|7|8]\d{9}$/.test(phone))) {
		layer.msg("手机号码格式错误");
		result = false;
	}
	return result;
}

/**
 * 验证电话号码
 * @param telPhone
 * @returns {Boolean}
 */
function checkTelephone(tel_phone){
	var result = true;
	if(!/^0\d{2,3}-?\d{7,8}$/.test(tel_phone)){
		layer.msg("固定电话有误，请重填");
		result = false;
		}
	return result;
}

/**
 * 验证座机号码 400，800开头10位数
 * @param telPhone
 * @returns {Boolean}
 */
function checkTelephone2(tel_phone){
	var result = true;
	if(!/^[48]00\d{7}$/.test(tel_phone)){
		layer.msg("座机号码输入有误!");
		result = false;
	}
	return result;
}
/**
 * 验证2种电话
 * @param phone
 * @returns {Boolean}
 */
function checkTwoPhone(phone){
	var result = true;
	var flag=true;
	var flag2=true;
	if (!(/^1[3|4|5|7|8]\d{9}$/.test(phone))) {
		flag = false;
	}
	if(!/^0\d{2,3}-?\d{7,8}$/.test(phone)){
		flag2 = false;
	}
//	alert(flag);
//	alert(flag2);
	if(!flag&&!flag2){
		result = false;
	}
	return result;
}
/**
 * 验证3种电话
 * @param phone
 * @returns {Boolean}
 */
function checkThreePhone(phone){
	var result = true;
	var flag=checkTwoPhone(phone);
	var flag2=true;
	if(!/^[48]00\d{7}$/.test(phone)){
		flag2 = false;
	}
	if(!flag&&!flag2){
		result = false;
	}
	return result;
}

function RemoveArrayItemByval(array,value){
	for(var i =0;i<array.length;i++){
		if(array[i]==value){
			array.splice(i,1);
		}
	}
	return array;
}

function changeToDecimal(v) {
    if (isNaN(v)) {//参数为非数字
        return 0;
    }
    var fv = parseFloat(v);
    fv = Math.round(fv * 100) / 100; //四舍五入，保留两位小数
    var fs = fv.toString();
    var fp = fs.indexOf('.');
    if (fp < 0) {
        fp = fs.length;
        fs += '.';
    }
    while (fs.length <= fp + 2) { //小数位小于两位，则补0
        fs += '0';
    }
    return fs;
}
