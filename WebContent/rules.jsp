<%@ include file="header.jsp" %>
<%@ page import="com.UI" %>
<%@ page import="com.Database" %>

<script>document.getElementById("rulesButton").className += "active";</script>

<%
	if (request.getParameter("button") != null){
		// handle editing a rule
		if (request.getParameter("button").equals("edit")){
		}
		// handle deleting a rule
		else{
			new Database().deleteRule(request);
		}
	}
%>

<div class="page-header">
	<h1>Rules</h1>
</div>

<table class="table table-hover">
	<tr>
		<th>ID</th>
		<th>Rule name</th>
		<th>Data field</th>
		<th>Specification</th>
		<th>Boundary type</th>
		<th>Boundary condition</th>
		<th><!-- placeholder for edit button --></th>
		<th><!-- placeholder for delete button --></th>
	</tr>
	<% out.print(new UI().displayRules()); %>
</table>

<%@ include file="footer.jsp" %>