<%@ include file="header.jsp" %>
<%@ page import="com.Database" %>
<%@ page import="com.UI" %>

<script>document.getElementById("ruleViolationsButton").className += "active";</script>

<div class="page-header">
	<h1>Rule Violations</h1>
</div>

<form class="form-horizontal" role="form" method="post" action="ruleviolations.jsp">
	<div class="form-group">
		<label for="chooseRuleFilter" class="col-sm-4 control-label">Filter By Rule</label>
		<div class="col-sm-4">
			<select class="form-control" id="chooseRuleFilter" name="ruleFilter" onchange="this.form.submit()">
				<option></option>
				<% out.print(new UI().displayRulesDropdown(request)); %>
			</select>
		</div>
	</div>
</form>
	
<table class="table table-hover">
	<tr>
		<th>Rule</th>
		<th>City</th>
		<th>Year</th>
		<th>Data Field</th>
	</tr>
	<% out.print(new UI().displayRuleViolations(request)); %>
</table>

<%@ include file="footer.jsp" %>