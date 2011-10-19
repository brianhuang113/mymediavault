<%@ page import="mediavault.Viewers.*" %>

<html>
<head>
<title>File List</title>
<link href="liststyle.css" rel="Stylesheet" type="text/css" />
<link href="playlist.css" rel="Stylesheet" type="text/css" />
<script type="text/javascript" src="jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="jquery.tools.min.js"></script>
<script type="text/javascript" src="flowplayer-3.2.6.min.js"></script>
<script type="text/javascript" src="flowplayer.playlist-3.0.8.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
#player {
	width:465px;
	height:330px;
	float:left;
	margin:0 0 40px 20px;
}
</style>
<script>
$(function() {

// setup player
$f("player", "http://releases.flowplayer.org/swf/flowplayer-3.2.7.swf", {	
	
	clip: {
		baseUrl: 'http://blip.tv/file/get',
		subTitle: 'mediavault'
	},
	
	playlist: [
	   		
	   	],
	
	// show playlist buttons in controlbar
	plugins: {
		controls: {
			url: 'flowplayer.controls-tube-3.2.5.swf',
			playlist: true,
			time: false
		}
	}
});



// enable playlist for elements under div.clips
$f("player").playlist("div.clips", {loop:true});

});

</script>
</head>
<body>
<form action="/multidownload" method="post">
<div class="clips" style="float:left" id="myplaylist">
		<a href="${url}">
		${title} <span>${subTitle}</span>
		<em>${time}</em>
	</a>
</div>
<div id="player"></div>
<button type="button" onClick="$f().addClip({url: 'KimAronson-TwentySeconds75235.flv', title: 'Two little girls'}, 0)">Add clip to the beginning</button>
<br />
	<table id="mytable" style="border-spacing: 0">
		<tr>
			<th><input type="submit" value="download"></th>
			<th>File Name</th>
			<th>Creation Date</th>
			<th>Content Type</th>
			<th>Size</th>
			<th>Owner</th>
			<th>Description</th>
			<th>Download</th>
			<th>Delete</th>
			<th>Edit Information</th>
			<th>Add to Playlist</th>
		</tr>
		<%
			String listType = request.getParameter("listtype");
			out.print(Viewer.ListAllFiles(listType));
		%>
	</table>

		
	</form>
</body>
</html>