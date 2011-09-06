<%@ page
	import="com.google.appengine.api.blobstore.BlobstoreServiceFactory"%>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService"%>
<%@ page import="com.google.appengine.api.blobstore.BlobInfo"%>
<%@ page import="com.google.appengine.api.blobstore.BlobInfoFactory"%>
<%@ page import="com.google.appengine.api.blobstore.BlobKey"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Date"%>
<%@ page
	import="com.google.appengine.api.datastore.DatastoreServiceFactory"%>
<%@ page import="com.google.appengine.api.datastore.*" %>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ page import="java.util.List" %>
<html>
<head>
<title>File edit</title>
<link href="liststyle.css" rel="Stylesheet" type="text/css" />
</head>
<body>
<%
String blobkey = request.getParameter("blob_key");

DatastoreService datastore = DatastoreServiceFactory
.getDatastoreService();
Key fileKey = KeyFactory.createKey("fileKey", "fileKey");
Query q = new Query("fileinfo", fileKey);
q.addFilter("blobkey", Query.FilterOperator.EQUAL, blobkey);
PreparedQuery pq = datastore.prepare(q);
String filename = "";
String desc = "";
String filetype = "";
for (Entity result : pq.asIterable()) {
	filename = result.getProperty("filename").toString();
	filename = filename.substring(0, filename.lastIndexOf("."));
	filetype = result.getProperty("filename").toString();
	filetype = filetype.substring(filetype.lastIndexOf("."));
	if (result.getProperty("desc") != null)
		desc = result.getProperty("desc").toString();
}
%>
<form id="edit" action="Update" method="post" target="main">
File name: <br />
<input type="text" name="filename" value="<% out.print(filename); %>"> 
 <label for="filetype"><% out.print(filetype); %></label>
<br />
Description: <br />
<textarea name="desc" cols="80" rows="6"><% out.print(desc); %></textarea> <br />
<input type="submit" value="submit">
</form>
</body>
</html>