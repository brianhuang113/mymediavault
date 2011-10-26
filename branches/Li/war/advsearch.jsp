<%@ page import="mediavault.Viewers.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.*" %>
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
			<th>Play</th>
		</tr>
		<%
			String searchContent = request.getParameter("searchContent");
			Boolean isDesc = Boolean.parseBoolean(request.getParameter("chkdesc"));
			Boolean isArtist = Boolean.parseBoolean(request.getParameter("chkartist"));
			Boolean isAlbum = Boolean.parseBoolean(request.getParameter("chkalbum"));
			Boolean isGenre = Boolean.parseBoolean(request.getParameter("chkgenre"));
			String rType = request.getParameter("rtype");
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date startDate = null, endDate = null;
			try {
				startDate = dateFormat.parse(request.getParameter("startdate"));
			} catch (Exception e)
			{}
			try {
				endDate = dateFormat.parse(request.getParameter("enddate"));
			} catch (Exception e)
			{}
			
			out.print(Viewer.AdvSearch(searchContent, isDesc, isArtist, isAlbum, isGenre, rType, startDate, endDate));
		%>
	</table>
</body>
</html>