<%@ page import="mediavault.Viewers.*" %>
<html>
<head>
<title>File Search</title>
<link href="liststyle.css" rel="Stylesheet" type="text/css" />
</head>
<body>
	<table id="mytable" style="border-spacing: 0">
		<tr>
			<th>File Name</th>
			<th>Creation Date</th>
			<th>Content Type</th>
			<th>Size</th>
			<th>Owner</th>
			<th>Description</th>
			<th>Download</th>
			<th>Delete</th>
			<th>Edit Information</th>
		</tr>
		<%
			String searchName = request.getParameter("searchName");
			out.print(Viewer.SearchByName(searchName));
		%>
	</table>
</body>
</html>