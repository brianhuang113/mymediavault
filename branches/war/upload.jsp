<%@ page
	import="com.google.appengine.api.blobstore.BlobstoreServiceFactory"%>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%
	BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();
%>


<html>
<head>
<title>Upload Test</title>
</head>
<body>
	<form action="<%=blobstoreService.createUploadUrl("/upload")%>"
		method="post" enctype="multipart/form-data">
		<input type="text" name="foo"> <input type="file"
			name="myFile"> <input type="submit" value="Submit">
	</form>
</body>
</html>