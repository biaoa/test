$(document).ready(function() {
	$("#text1").click(function() {
		$("#p1").toggle();
		$(this).toggleClass("problems2");
	});
	$("#text2").click(function() {
		$("#p2").toggle();
		$(this).toggleClass("problems2");
	});
	$("#text3").click(function() {
		$("#p3").toggle();
		$(this).toggleClass("problems2");
	});
	$("#text4").click(function() {
		$("#p4").toggle();
		$(this).toggleClass("problems2");
	});
});