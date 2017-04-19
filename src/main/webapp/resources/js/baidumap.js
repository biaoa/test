// 百度地图API功能
			var map = new BMap.Map("l-map"); // 创建Map实例
			map.centerAndZoom(new BMap.Point(116.404, 39.915), 13);
			function myFun(result){
			    var cityName = result.name;
			    map.setCenter(cityName);
			}
			var myCity = new BMap.LocalCity();
			myCity.get(myFun);
			window.openInfoWinFuns = null;
			var options = {
				onSearchComplete: function(results) {
					console.log(results);
					// 判断状态是否正确
					if(local.getStatus() == BMAP_STATUS_SUCCESS) {
						var s = [];
						s.push('<div style="font-family:"微软雅黑"; font-size: 12px;">');
						s.push('<div style="background: none repeat scroll 0% 0% rgb(255, 255, 255);">');
						s.push('<ol style="list-style: none outside none; padding: 0pt; margin: 0pt;">');
						openInfoWinFuns = [];
						for(var i = 0; i < results.getCurrentNumPois(); i++) {
							var marker = addMarker(results.getPoi(i).point, i);
							var openInfoWinFun = addInfoWindow(marker, results.getPoi(i), i);
							openInfoWinFuns.push(openInfoWinFun);
							// 默认打开第一标注的信息窗口
							var selected = "";
							if(i == 0) {
								selected = "background-color:#f0f0f0;";
								openInfoWinFun();
							}

							s.push('<li id="list' + i + '" address="' + results.getPoi(i).address + '" lat="'+results.getPoi(i).point.lat+'" lng="'+results.getPoi(i).point.lng+'" style="margin: 0px 0pt; padding: 0pt 5px 0pt 3px; cursor: pointer; overflow: hidden; line-height: 15px;' + selected + '" onclick="openInfoWinFuns[' + i + '](),showaddress(this)">');
//							s.push('<span style="width:1px;background:url(red_labels.gif) 0 ' + (2 - i * 20) + 'px no-repeat;padding-left:10px;margin-right:3px"> </span>');
							s.push('<p style="color:#00c;">' + results.getPoi(i).title.replace(new RegExp(results.keyword, "g"), '<b>' + results.keyword + '</b>') + '</p>');
							s.push('<p style="color:#666;">地址：' + results.getPoi(i).address + '</p>');
							s.push('</li>');
							s.push('');
						}
						s.push('</ol></div></div>');
						document.getElementById("r-result").innerHTML = s.join("");
					}
				}
			};

			function showaddress($objLI) {
				$("#kq_search").val($objLI.textContent);
				console.log();
				$("#lat").val($objLI.attributes[2].value);
				$("#lng").val($objLI.attributes[3].value);
			}
			// 添加标注
			function addMarker(point, index) {
				var myIcon = new BMap.Icon("http://api.map.baidu.com/img/markers.png", new BMap.Size(23, 25), {
					offset: new BMap.Size(10, 25),
					imageOffset: new BMap.Size(0, 0 - index * 25)
				});
				var marker = new BMap.Marker(point, {
					icon: myIcon
				});
				map.addOverlay(marker);
				return marker;
			}
			// 添加信息窗口
			function addInfoWindow(marker, poi, index) {
				var maxLen = 10;
				var name = null;
				if(poi.type == BMAP_POI_TYPE_NORMAL) {
					name = "地址：  "
				} else if(poi.type == BMAP_POI_TYPE_BUSSTOP) {
					name = "公交：  "
				} else if(poi.type == BMAP_POI_TYPE_SUBSTOP) {
					name = "地铁：  "
				}
				// infowindow的标题
				var infoWindowTitle = '<div style="font-weight:bold;color:#CE5521;font-size:14px">' + poi.title + '</div>';
				// infowindow的显示信息
				var infoWindowHtml = [];
				infoWindowHtml.push('<table cellspacing="0" style="table-layout:fixed;width:100%;font:12px arial,simsun,sans-serif"><tbody>');
				infoWindowHtml.push('<tr>');
				infoWindowHtml.push('<td style="vertical-align:top;line-height:16px;width:38px;white-space:nowrap;word-break:keep-all">' + name + '</td>');
				infoWindowHtml.push('<td style="vertical-align:top;line-height:16px">' + poi.address + ' </td>');
				infoWindowHtml.push('</tr>');
				infoWindowHtml.push('</tbody></table>');
				var infoWindow = new BMap.InfoWindow(infoWindowHtml.join(""), {
					title: infoWindowTitle,
					width: 200
				});
				var openInfoWinFun = function() {
					marker.openInfoWindow(infoWindow);
					for(var cnt = 0; cnt < maxLen; cnt++) {
						if(!document.getElementById("list" + cnt)) {
							continue;
						}
						if(cnt == index) {
							document.getElementById("list" + cnt).style.backgroundColor = "#f0f0f0";
						} else {
							document.getElementById("list" + cnt).style.backgroundColor = "#fff";
						}
					}
				}
				marker.addEventListener("click", openInfoWinFun);
				return openInfoWinFun;
			}
			var local = new BMap.LocalSearch(map, options);
			$("#kq_button").click(function() {
				var your_place = $("#kq_search").val()
				local.search(your_place);
			});
	
			$(function() {
				$(".time_edit").click(function() {
					$("#bg").css({
						display: "block",
						height: $(document).height()
					});
					var $box = $('.kq_time_site');
					$box.css({
						//设置弹出层距离左边的位置
						left: ($("body").width() - $box.width()) / 2 - 20 + "px",
						//设置弹出层距离上面的位置
						top: ($(window).height() - $box.height()) / 3 + $(window).scrollTop() + "px",
						display: "block"
					});
				});
				//点击关闭按钮的时候，遮罩层关闭
				$(".close,.check_btn3").click(function() {
					$("#bg,.kq_time_site").css("display", "none");
				});

			});

			$(function() {
				$(".special-date").click(function() {
					$("#bg").css({
						display: "block",
						height: $(document).height()
					});
					var $box = $('.kq_time_site2');
					type = $(this).attr("val");
					$box.css({
						//设置弹出层距离左边的位置
						left: ($("body").width() - $box.width()) / 2 - 20 + "px",
						//设置弹出层距离上面的位置
						top: ($(window).height() - $box.height()) / 3 + $(window).scrollTop() + "px",
						display: "block"
					});
				});

				$(".close,.check_btn3").click(function() {
					$("#bg,.kq_time_site2").css("display", "none");
				});

			});

			$(function() {
				$(".kq_menber").click(function() {
					$("#bg").css({
						display: "block",
						height: $(document).height()
					});
					var $box = $('.eoffice');
					$box.css({
						//设置弹出层距离左边的位置
						left: ($("body").width() - $box.width()) / 2 - 20 + "px",
						//设置弹出层距离上面的位置
						top: ($(window).height() - $box.height()) / 3 + $(window).scrollTop() + "px",
						display: "block"
					});
				});

				$(".btn-gray").click(function() {
					$("#bg,.eoffice").css("display", "none");
				});

			});
			$(function() {
				$(".map_add").click(function() {
					$("#bg").css({
						display: "block",
						height: $(document).height()
					});
					var $box = $('.baidumap');
					$box.css({
						//设置弹出层距离左边的位置
						left: ($("body").width() - $box.width()) / 2 - 20 + "px",
						//设置弹出层距离上面的位置
						top: ($(window).height() - $box.height()) / 3 + $(window).scrollTop() + "px",
						display: "block"
					});
				});

				$(".map_qx").click(function() {
					$("#bg,.baidumap").css("display", "none");
				});

			});
			
			
				$(function() { //页面加载完毕后执行 
				$(".area_add").click(function() { //添加操作 
					var kq_search = $("#kq_search").val();
					var temp_lat = $("#lat").val();
					var temp_lng = $("#lng").val();
					if(check_address.indexOf(kq_search)>-1){
						layer.msg("已选择过该地址");
						return false;
					}else{
						var temp_address = create_attendance_address(kq_search,temp_lat,temp_lng);
						$(".clearfix2").after('<div class="kq_area"><span>'+kq_search+'</span><span class="area_del" v="'+kq_search+'">删除</span></div>'); //在p标签前加入所要生成的代码
						check_address.push(kq_search);
						address_array.push(temp_address);
					}
				});
				$(".timeadd").click(function() { //添加操作 
					var date = $("#spec_time").val();
					var remark = $("#remark").val();
					if(date==""){
						layer.msg("请选择时间");
						return false;
					}
					var obj = create_special_date(date,remark,type);
					if(check_special_date(obj)){
						if(type==0){
							$("#bfj").after('<div class="add-work"><span>'+date+'</span><span>'+remark+'</span><span class="date_del" v="'+date+'" style="margin-left:10px;float:right;color:red;cursor: pointer;">删除</span></div>'); //在p标签前加入所要生成的代码
						}else{
							$("#fj").after('<div class="add-work"><span>'+date+'</span><span>'+remark+'</span><span class="date_del" v="'+date+'" style="margin-left:10px;float:right;color:red;cursor:pointer;">删除</span></div>'); //在p标签前加入所要生成的代码
						}
						special_date_array.push(obj);
					}
				});
				$(".jkl").click(function() { //添加操作 				
					$(".clearfix").after('<div class="white_names"><span class="white_name">隔壁老王</span><span class="white_job">保洁负责人</span><span class="area_del">删除</span></div>'); //在p标签前加入所要生成的代码
				});
				
			});
			
		