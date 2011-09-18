<%@ page import="mediavault.Viewers.*" %>
<%@ page import="mediavault.Models.*" %>
<%@ page import="com.google.appengine.api.datastore.*" %>
<%@ page import="com.dusbabek.lib.id3.*" %>
<html>
<head>
<title>File Search</title>
<link href="liststyle.css" rel="Stylesheet" type="text/css" />
</head>
<body>
<%
String blobkey = request.getParameter("blob_key");
String contenttype = request.getParameter("contenttype");

MediaFile mediaFile = null;
if (contenttype.toLowerCase().equals(new String("audio/mp3"))) {
	mediaFile = new AudioFile(blobkey);
}
else {
	mediaFile = new MediaFile(blobkey);
}
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
<%
if (contenttype.toLowerCase().equals(new String("audio/mp3"))) {
	if (!((AudioFile)mediaFile).hasTag()) {
		try {
			((AudioFile)mediaFile).UpdateMetadata();
		
%>
Artist: <input type="text" name="artist" value="<% out.print(((AudioFile)mediaFile).getArtist()); %>">
Title: <input type="text" name="title" value="<% out.print(((AudioFile)mediaFile).getTitle()); %>">
Album: <input type="text" name="album" value="<% out.print(((AudioFile)mediaFile).getAlbum()); %>"><br />
Track: <input type="text" name="track" value="<% out.print(((AudioFile)mediaFile).getTrack()); %>">
Genre: <input type="text" name="genre" value="<% out.print(((AudioFile)mediaFile).getGenre()); %>">
Year: <input type="text" name="year" value="<% out.print(((AudioFile)mediaFile).getYear()); %>"><br />
Comment: <input type="text" width="80" name="comment" value="<% out.print(((AudioFile)mediaFile).getComment()); %>">
<%
		}
		catch (Exception e) {
			
		}
	}
}
%>
<input type="submit" value="submit">
</form>
</body>
</html>