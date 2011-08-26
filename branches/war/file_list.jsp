<%@ page
	import="com.google.appengine.api.blobstore.BlobstoreServiceFactory"%>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService"%>
<%@ page import="com.google.appengine.api.blobstore.BlobInfo"%>
<%@ page import="com.google.appengine.api.blobstore.BlobInfoFactory"%>
<%@ page import="java.util.Iterator"%>

<html>
<head>
<title>File List</title>
</head>
<body>
	<table>
		<tr>
			<td>File Name</td>
			<td>Creation Date</td>
			<td>Content Type</td>
			<td>Size</td>
		</tr>
		<%
			BlobInfoFactory blobinfofactory = new BlobInfoFactory();
			Iterator<BlobInfo> itBlobinfo = blobinfofactory.queryBlobInfos();
			while (itBlobinfo.hasNext()) {
				BlobInfo blobinfo = itBlobinfo.next();
				out.println("<tr><td><a href=\"download.jsp?blob_key="
						+ blobinfo.getBlobKey().getKeyString() + "\">"
						+ blobinfo.getFilename() + "</a></td>");
				out.println("<td>" + blobinfo.getCreation().toString()
						+ "</td>");
				out.println("<td>" + blobinfo.getContentType() + "</td>");
				out.println("<td>" + blobinfo.getSize() + "</td>");
				out.println("</tr>");
			}
		%>
	</table>
</body>
</html>