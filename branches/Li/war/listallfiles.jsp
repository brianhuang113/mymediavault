<%@ page import="mediavault.Viewers.*"%>

<html>
<head>
<title>File List</title>
<link href="liststyle.css" rel="Stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
	<form action="/multidownload" method="post">
		<br />
		<table id="mytable" style="border-spacing: 0">
			<tr>
				<th><input type="submit" value="download"></th>
				<th>File Name</th>
				<th>Creation Date</th>
				<th>Content Type</th>
				<th>Size</th>
				<th>Owner</th>
				<th>Description</th>
				<th>Download</th>
				<th>Delete</th>
				<th>Edit Information</th>
				<th>Play</th>
			</tr>
			<%
				String listType = request.getParameter("listtype");
				out.print(Viewer.ListAllFiles(listType));
			%>
		</table>
	</form>
</body>
</html>