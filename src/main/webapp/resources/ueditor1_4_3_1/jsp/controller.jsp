<%@page import="org.slf4j.Logger"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.linle.common.ueditor.ActionEnter"
	import="org.slf4j.Logger"
	import="org.slf4j.LoggerFactory"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%
	
    request.setCharacterEncoding( "utf-8" );
	response.setHeader("Content-Type" , "text/html");
	
	String rootPath = application.getRealPath( "/" );
	out.write( new ActionEnter( request, rootPath ).exec() );
%>
