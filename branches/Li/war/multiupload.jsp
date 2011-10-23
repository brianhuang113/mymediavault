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
<link href="plupload/jquery.plupload.queue.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="plupload/plupload.full.js"></script>
<script type="text/javascript" src="plupload/jquery.plupload.queue.js"></script>
<script type="text/javascript">
    $(function() {
      var uploader = $("#uploader").pluploadQueue({
        runtimes: 'html5,flash',
        url: '<%=blobstoreService.createUploadUrl("/upload")%>',
                                use_query_string: false,
                                multipart: true,
                                flash_swf_url: 'plupload/plupload.flash.swf',
                                filters : [
            {title : "Image files", extensions : "jpg,gif,png,jpeg,bmp"},
            {title : "Zip files", extensions : "zip"},
            {title : "Audio files", extensions : "wav,mp3,mid"},
            {title : "Video files", extensions : "rm,rmvb,mkv,avi,mp4,mpg,swf,mov,wmv"},
            {title : "Text files", extensions : "txt"}
        ]
      }).pluploadQueue();
    	  
    	  uploader.bind('UploadFile', function(up, file) {
    	        $.ajax({
    	        	async: false,
    	            url: '/generateUploadURL',
    	            success: function(data) {
    	            	
    	              up.settings.url = data;
    	            }
    	        });
    	      });
    	  
      
    });
  </script>

<title>Upload file</title>
</head>
<body>
<form>
		<div id="uploader">
		</div>
	</form>
</body>
</html>