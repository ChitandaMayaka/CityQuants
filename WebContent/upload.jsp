<%@ include file="header.jsp" %>
<%@ page import="com.Database" %>

<script>document.getElementById("uploadDataButton").className += "active";</script>

<div class="page-header">
	<h1>Upload Data</h1>
</div>

<div class="jumbotron">
	<form class="form-horizontal" role="form" method="post" action="UploadServlet" enctype="multipart/form-data">
		<div class="form-group">
			<label for="inputFile" class="col-sm-3 control-label">File input</label>
			<div class="col-sm-6">
				<input type="file" id="inputFile" name="file">
			</div>				
		</div>
		<div class="col-sm-offset-3 col-sm-6">
			<button type="submit" class="btn btn-default">Upload</button>
		</div>
	</form>
</div>

<%@ include file="footer.jsp" %>