$(document).ready(function() {
				//user_list指向li同时给<a>添加hover
				$(".user_list").mouseover(function() {
					$(this).find(".selected_tip").addClass("hover");
				});
				$(".user_list").mouseout(function() {
					$(this).find(".selected_tip").removeClass("hover");
				});
			});
			

$(document).ready(function() {
				//传输文字并添加<a>标签
//				$(".user_list").click(function() {
//					var word = $(this).text();
//					word = $.trim(word);
//					var has = false;
//					//alert(word);
//					$(".selected_user li").each(function() {
//						//alert($(this).text());
//						if($(this).text() == word) {
//							has = true;
//							$(this).remove();
//						}
//					});
//					//alert(has);
//					if(!has) {
//						$(".selected_user").append("<li>" + word + "<span class='shanchu'><i class='icon-close'></i></span></li>");
//						$(this).prepend("<a class='selected_tip hover' href='javascript:void(0)'><i class='icon-check-circle'></i></a> ");
//					} else {
//						$(this).children("a").remove();
//					}
//				});

				//取消选中、remove <a>标签
				$(".selected_user").delegate("li", "click", function() {
					$(this).remove();
					var name = $(this).text();
					//alert(name);
					$(".user_list").each(function() {
						if($.trim($(this).text()) == name) {
							$(this).children("a").remove();
						}
					});
				});

				//全选、清空
				$(".allselect").click(function() {
					$(".selected_user").children("li").remove();
					$(".user_list").children("a").remove();
					$(".user_list").each(function() {
						$(".selected_user").append("<li>" + $.trim($(this).text()) + "<span class='shanchu'><i class='icon-close'></i></span></li>");
						$(this).prepend("<a class='selected_tip' href='javascript:void(0)'><i class='icon-check-circle'></i></a> ");
					});
				});
				$(".allclear").click(function() {
					$(".selected_user").children("li").remove();
					$(".user_list").children("a").remove();
				});
			});