<%@ include file="header.jsp" %>
<%@ page import="com.Database" %>
<%@ page import="com.UI" %>

<script>document.getElementById("defineNewRulesButton").className += "active";</script>

<div class="page-header">
	<h1>Define New Rule</h1>
</div>

<div class="jumbotron">
	<form class="form-horizontal" role="form" method="post" action="<% new Database().defineRule(request, response); %>">
		<div class="form-group">
			<label for="inputRuleName" class="col-sm-3 control-label">Rule Name</label>
			<div class="col-sm-6">
				<input type="text" class="form-control" id="inputRuleName" name="ruleName" placeHolder="Enter a rule name">
			</div>
		</div>
		<div class="form-group">
			<label for="inputDataField" class="col-sm-3 control-label">Data Field</label>
			<div class="col-sm-6">
				<select class="form-control" id="inputDataField" name="dataField">
					<% out.print(new UI().displayDataFieldsDropdown(session)); %>
				</select>
			</div>
		</div>
		<div class="form-group">
			<label for="inputSpecification" class="col-sm-3 control-label"></label>
			<div class="col-sm-6">
				<select class="form-control" id="inputSpecification" name="specification">
					<option>Value</option>
					<option>Average Value</option>
					<option>Growth Rate</option>
					<option>Standard Deviation</option>
				</select>
			</div>
		</div>
		<div class="form-group">
			<label for="inputBoundaryType" class="col-sm-3 control-label">Must be</label>
			<div class="col-sm-6">
				<select class="form-control" id="inputBoundaryType" name="boundaryType">
					<option>Less than</option>
					<option>Less than or equal to</option>
					<option>Greater than</option>
					<option>Greater than or equal to</option>
					<option>Equal to</option>
					<option>Not equal to</option>
				</select>
			</div>
		</div>
		<div class="form-group">
			<label for="inputBoundaryCondition" class="col-sm-3 control-label"></label>
			<div class="col-sm-6">
				<input type="text" class="form-control" id="inputBoundaryCondition" name="boundaryCondition" placeHolder="Enter a value">
			</div>
		</div>
		<div class="col-sm-offset-3 col-sm-6">
			<button type="submit" class="btn btn-default">Submit</button>
		</div>
	</form>
</div>

<%@ include file="footer.jsp" %>