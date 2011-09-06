<%@ page
	import="com.google.appengine.api.blobstore.BlobstoreServiceFactory"%>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService"%>
<%@ page import="com.google.appengine.api.blobstore.BlobInfo"%>
<%@ page import="com.google.appengine.api.blobstore.BlobInfoFactory"%>
<%@ page import="java.util.Iterator"%>
<%@ page
	import="com.google.appengine.api.datastore.DatastoreServiceFactory"%>
<%@ page import="com.google.appengine.api.datastore.DatastoreService"%>
<%@ page import="com.google.appengine.api.datastore.Query"%>
<%@ page import="com.google.appengine.api.datastore.Entity"%>
<%@ page import="com.google.appengine.api.datastore.FetchOptions"%>
<%@ page import="com.google.appengine.api.datastore.Key"%>
<%@ page import="com.google.appengine.api.datastore.KeyFactory"%>
<%@ page import="com.google.appengine.api.datastore.PreparedQuery"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>

<html>
<head>
<title>File List</title>
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
			<th>Delete</th>
		</tr>
		<%
			String listType = request.getParameter("listtype");

			BlobInfoFactory blobinfofactory = new BlobInfoFactory();
			Iterator<BlobInfo> itBlobinfo = blobinfofactory.queryBlobInfos();
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			Key fileKey = KeyFactory.createKey("fileKey", "fileKey");
			int i = 0;
			while (itBlobinfo.hasNext()) {
				BlobInfo blobinfo = itBlobinfo.next();
				if (listType.equals(new String("1"))
						|| (listType.equals(new String("2")) && blobinfo
								.getContentType()
								.substring(0,
										blobinfo.getContentType().indexOf("/"))
								.equals(new String("image")))
						|| (listType.equals(new String("3")) && blobinfo
								.getContentType()
								.substring(0,
										blobinfo.getContentType().indexOf("/"))
								.equals(new String("audio")))
						|| (listType.equals(new String("4")) && blobinfo
								.getContentType()
								.substring(0,
										blobinfo.getContentType().indexOf("/"))
								.equals(new String("video")))
						|| (listType.equals(new String("5")) && blobinfo
								.getContentType()
								.substring(0,
										blobinfo.getContentType().indexOf("/"))
								.equals(new String("text")))) {
					Query q = new Query("fileinfo", fileKey);
					q.addFilter("blobkey", Query.FilterOperator.EQUAL, blobinfo
							.getBlobKey().getKeyString());
					PreparedQuery pq = datastore.prepare(q);
					if (i % 2 == 0) {
						out.println("<tr><th class=\"spec\"><a href=\"/serve?blob_key="
								+ blobinfo.getBlobKey().getKeyString()
								+ "\">"
								+ blobinfo.getFilename() + "</a></th>");
						out.println("<th class=\"spec\">"
								+ blobinfo.getCreation().toString() + "</th>");
						out.println("<th class=\"spec\">"
								+ blobinfo.getContentType() + "</th>");
						out.println("<th class=\"spec\">" + blobinfo.getSize()
								+ "</th>");
						out.println("<th class=\"spec\">");
						for (Entity result : pq.asIterable()) {
							out.println(((User) result.getProperty("owner"))
									.getNickname());
						}
						out.println("</th>");
						out.println("<th class=\"spec\">" + 
						out.println("<th class=\"spec\"><a href=\"/delete?blob_key="
								+ blobinfo.getBlobKey().getKeyString()
								+ "\">delete</a></th>");
						out.println("</tr>");
					} else {
						out.println("<tr><th class=\"specalt\"><a href=\"/serve?blob_key="
								+ blobinfo.getBlobKey().getKeyString()
								+ "\">"
								+ blobinfo.getFilename() + "</a></th>");
						out.println("<th class=\"specalt\">"
								+ blobinfo.getCreation().toString() + "</th>");
						out.println("<th class=\"specalt\">"
								+ blobinfo.getContentType() + "</th>");
						out.println("<th class=\"specalt\">"
								+ blobinfo.getSize() + "</th>");
						out.println("<th class=\"specalt\">");
						for (Entity result : pq.asIterable()) {
							out.println(((User) result.getProperty("owner"))
									.getNickname());
						}
						out.println("</th>");
						out.println("<th class=\"specalt\"><a href=\"/delete?blob_key="
								+ blobinfo.getBlobKey().getKeyString()
								+ "\">delete</a></th>");
						out.println("</tr>");
					}
				}

				i++;
			}
		%>
	</table>
</body>
</html>