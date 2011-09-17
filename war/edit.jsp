<%@ page import="mediavault.Viewers.*" %>
<%@ page import="mediavault.Models.*" %>
<%@ page import="com.google.appengine.api.datastore.*" %>
<html>
<head>
<title>File Search</title>
<link href="liststyle.css" rel="Stylesheet" type="text/css" />
</head>
<body>
<%
String blobkey = request.getParameter("blob_key");

MediaFile mediaFile = new MediaFile(blobkey);
String filename = mediaFile.getFileName();
filename = filename.substring(0, filename.indexOf("."));
%>
<form id="edit" action="/update" method="post" target="main">
File name: <br />
<input type="hidden" name="blobkey" value="<% out.print(blobkey); %>">
<input type="hidden" name="contenttype" value="<% out.print(mediaFile.getContentType()); %>">
<input type="text" name="filename" value="<% out.print(filename); %>"> <br />
<input type="checkbox" name="isShared" >Shared<br/>
Description: <br />
<textarea name="desc" cols="80" rows="6"><% out.print(mediaFile.getDesc()); %></textarea> <br />
<input type="submit" value="submit">
</form>
</body>
</html>