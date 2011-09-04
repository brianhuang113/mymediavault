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
        if(ext!=".MP3"&&ext!=".WAV"&&ext!=".BMP"&&ext!=".PNG"&&ext!=".GIF"&&ext!=".JPG"&&ext!=".JPEG"
        		&&ext!=".MID"&&ext!=".AVI"&&ext!=".MP4"&&ext!=".MPG"&&ext!=".MKV"&&ext!=".RMVB"
        		&&ext!=".RM"&&ext!=".SWF"&&ext!=".MOV"&&ext!=".WMV"&&ext!=".TXT"){ 
        alert("invalid file type!"); 
        return false; 
        } 
   
   return true;	      
}); 
}); 
</script>
<title>Upload file</title>
</head>
<body>
	<form id="fileform" action="<%=blobstoreService.createUploadUrl("/upload")%>"
		method="post" enctype="multipart/form-data">
		<input type="text" name="foo"> <input type="file"
			name="myFile"> <input type="submit" value="Submit">
	</form>
</body>
</html>