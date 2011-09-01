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
<script type="text/javascript" src="jquery-1.6.2.min.js"></script>
<script type="text/javascript"> 
   
$(document).ready(function(){ 
$("#fileform").submit(function() {	
        var filepath=$("input[name='myFile']").val(); 
        var extStart=filepath.lastIndexOf("."); 
        var ext=filepath.substring(extStart,filepath.length).toUpperCase(); 
        if(ext!=".BMP"&&ext!=".PNG"&&ext!=".GIF"&&ext!=".JPG"&&ext!=".JPEG"){ 
        alert("invalid picture type!"); 
        return false; 
        } 
   
   return true;	      
}); 
}); 
</script>
<title>Upload Test</title>
</head>
<body>
	<form id="fileform" action="<%=blobstoreService.createUploadUrl("/upload")%>"
		method="post" enctype="multipart/form-data">
		<input type="text" name="foo"> <input type="file"
			name="myFile"> <input type="submit" value="Submit">
	</form>
</body>
</html>