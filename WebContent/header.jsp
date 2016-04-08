<%@ page import="com.User" %>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>CitiQuants</title>
		
		<!-- Bootstrap -->
		<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
		<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">
		<script src="//netdna.boostrapcdn.com/boostrap/3.1.1/js/boostrap.min.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	
	</head>
	<body>
	
	<%
		if (request.getSession().getAttribute("User") == null){
			//response.sendRedirect("login.jsp");
		}else{
		}
	%>
	
		<div id="navigationBar" class="navbar navbar-inverse navbar-static-top" role="navigation">
			<div class="container">
				<div class="navbar-header">
					<a class="navbar-brand">CitiQuants</a>
				</div>
				<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav">
						<li id="rulesButton"><a href="rules.jsp">Rules</a></li>
						<li id="defineNewRulesButton"><a href="definerule.jsp">Define New Rule</a></li>
						<li id="ruleViolationsButton"><a href="ruleviolations.jsp">Rule Violations</a></li>
						<li id="uploadDataButton"><a href="upload.jsp">Upload Data</a></li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<li><a href="<% session.setAttribute("User", null); %>">Log Out</a></li>
					</ul>
				</div>
			</div>
		</div>
		
		<div class="container">