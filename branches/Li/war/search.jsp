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
		</tr>
		<%
			String searchName = request.getParameter("searchName");
		
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			Key fileKey = KeyFactory.createKey("fileKey", "fileKey");

			UserService userService = UserServiceFactory.getUserService();
			User curUser = userService.getCurrentUser();
			Query q = new Query("fileinfo", fileKey);

			PreparedQuery pq = datastore.prepare(q);

			for (Entity result : pq.asIterable()) {
				String filename = (String) result.getProperty("filename");
				filename = filename.substring(0, filename.lastIndexOf(".")).toLowerCase();
				
				if (filename.indexOf(searchName.toLowerCase()) >= 0)
				{
				BlobKey blobKey = (BlobKey) result.getProperty("blobkey");
				Date createdDate = (Date) result.getProperty("date");
				String fileName2 = (String) result.getProperty("filename");
				User user = (User) result.getProperty("owner");
				Integer fileSize = Integer.parseInt(result.getProperty("size").toString());
				String contentType = (String) result.getProperty("contenttype");

				out.println("<tr><th class=\"spec\"><a href=\"/serve?blob_key="
						+ blobKey.getKeyString() + "\">" + fileName2
						+ "</a></th>");
				out.println("<th class=\"spec\">" + createdDate.toString()
						+ "</th>");
				out.println("<th class=\"spec\">" + contentType + "</th>");
				out.println("<th class=\"spec\">" + fileSize.toString()
						+ "</th>");
				out.println("<th class=\"spec\">" + user.getNickname());

				out.println("</th>");
				if (result.getProperty("desc") == null)
					out.println("<th class=\"spec\"></th>");
				else
					out.println("<th class=\"spec\">" + result.getProperty("desc") + "</th>");
				
				out.println("<th class=\"spec\"><a href=\"/download?blob_key="
						+ blobKey.getKeyString() + "\">download</a></th>");
				if (curUser.getEmail().equals(user.getEmail()))
				{
				out.println("<th class=\"spec\"><a href=\"/delete?blob_key="
						+ blobKey.getKeyString() + "\">delete</a></th>");
				}
				else
				{
					out.println("<th class=\"spec\"></th>");
				}
				out.println("</tr>");
				}
			}
		%>
	</table>
</body>
</html>